package androidServer.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.corba.se.impl.orbutil.closure.Constant;

import androidServer.annotation.Authenticate;
import androidServer.service.DBService;
import androidServer.service.StatisticService;
import androidServer.util.Constants;
import androidServer.util.SessionUtils;
import androidServer.util.WebUtil;

@Controller
@RequestMapping("buildingStatistic")
public class BuildingStatisticController {
	@Autowired
	DBService dbService;
	@Autowired
	StatisticService statService;

	/**
	 * 模糊查询buildingName，给出相似的结果
	 * 
	 * @param request
	 * @param response
	 * @param buildingName
	 * @return
	 */
	@RequestMapping("getBuildingLike")
	@ResponseBody
	Map<String, Object> getBuildingLike(HttpServletRequest request, HttpServletResponse response, String buildingName) {
		String province = SessionUtils.getProvince(request);
		String city = SessionUtils.getCity(request);
		List<Map<String, Object>> list = dbService.getbBuildingLike(province, city, buildingName);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("list", list);
		return data;
	}

	@RequestMapping("getBuildingName")
	@ResponseBody
	Map<String, Object> getBuildingName(HttpServletRequest request, HttpServletResponse response, int buildingId) {

		String name = dbService.getBuildingName(buildingId);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("buildingName", name);
		return data;
	}

	/**
	 * 获取指定building(或某一层)内，一段时间里采集到本cid(或者所有cid)的某运营商2G、3G、4G信号各自的总数
	 * 
	 * @param request
	 * @param response
	 * @param building
	 * @param floor
	 * @param network
	 * @return (network, number)
	 */
	@RequestMapping("dataforPie")
	@ResponseBody
	Map<String, Object> getNum(HttpServletRequest request, HttpServletResponse response, long startTime, long endTime,
			String network, int buildingId, int floor) {
		// CoverageRatio
		boolean isLocalCid = true; // 采集到的是本cid小区的信号
		List<Map<String, Object>> tList = dbService.getNetworkTypeNum(startTime, endTime, network, buildingId, floor,
				isLocalCid);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("tList", tList);
		return data;
	}

	/**
	 * 获取指定building(或某一层)内2G、3G、4G网络的覆盖信息
	 * 
	 * @param request
	 * @param response
	 * @param building
	 * @param floor
	 * @param network
	 * @return (_networkType, cid, number)
	 */
	@RequestMapping("dataforBar_1")
	@ResponseBody
	Map<String, Object> getBarData(HttpServletRequest request, HttpServletResponse response, long startTime,
			long endTime, String network, int buildingId, int floor) {
		boolean isLocalCid = true;
		// 获取指定building(或某一层)内，一段时间内采集到本cid的某运营商2G、3G、4G信号各自的总数
		List<Map<String, Object>> tList = dbService.getNetworkTypeNum(startTime, endTime, network, buildingId, floor,
				isLocalCid);
		isLocalCid = false;
		// 获取指定building(或某一层)内，一段时间内采集到所有cid的某运营商2G、3G、4G信号各自的总数
		List<Map<String, Object>> fList = dbService.getNetworkTypeNum(startTime, endTime, network, buildingId, floor,
				isLocalCid);
		// 获取指定building(或某一层)内，一段时间内采集到某一运营商2G、3G、4G信号中属于各种cid的信号总数
		List<Map<String, Object>> cList = dbService.getCidNum(startTime, endTime, network, buildingId, floor);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("tList", tList);
		data.put("fList", fList);
		data.put("cList", cList);
		return data;
	}

	/**
	 * 获取指定building(或某一层)内某运营商2G、3G、4G网络的告警数目以及正常数目的比例
	 * 
	 * @param request
	 * @param response
	 * @param building
	 * @param floor
	 * @param network
	 * @return (networkType, number)
	 */
	@RequestMapping("dataforBar_2")
	@ResponseBody
	Map<String, Object> getBarNum(HttpServletRequest request, HttpServletResponse response, long startTime,
			long endTime, String network, int buildingId, int floor) {
		boolean isLocalCid = true;
		List<Map<String, Object>> tList = dbService.getNetworkTypeNum(startTime, endTime, network, buildingId, floor,
				isLocalCid);
		// warningNumber
		String userId = SessionUtils.getUser(request).getUsername();
		String operator = network;
		// 获取指定building(或某一层)内，一段时间里采集到属于特定operator，且信号强度小于用户设置2G、3G、4G报警值的信号(属于此楼cid)数量
		List<Map<String, Object>> wList = dbService.getWarningNum(startTime, endTime, network, buildingId, floor,
				userId, operator);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("fList", tList);
		data.put("wList", wList);
		return data;
	}

	/**
	 * 获取指定building(或floor)的楼内某运营商2G、3G、4G信号强度的累积分布曲线
	 * 
	 * @param request
	 * @param response
	 * @param network
	 * @param buildingId
	 * @param floor
	 * @return (signalStrength, per)
	 */
	@RequestMapping("dataforCDF")
	@ResponseBody
	Map<String, Object> getCDF(HttpServletRequest request, HttpServletResponse response, long startTime, long endTime,
			String network, int buildingId, int floor) {
		// 在指定building(或floor)的楼内，获取一段时间里收集到属于本楼cid的某运营商2G信号的累计分布曲线(从小到大的各个强度值信号数占总信号数量比例的累加值)
		List<Map<String, Object>> cdfList_2G = dbService.getCdfData(startTime, endTime, network, buildingId, floor,
				Constants.Network._2G);
		// 在指定building(或floor)的楼内，获取一段时间里收集到属于本楼cid的某运营商3G信号的累计分布曲线(从小到大的各个强度值信号数占总信号数量比例的累加值)
		List<Map<String, Object>> cdfList_3G = dbService.getCdfData(startTime, endTime, network, buildingId, floor,
				Constants.Network._3G);
		// 在指定building(或floor)的楼内，获取一段时间里收集到属于本楼cid的某运营商4G信号的累计分布曲线(从小到大的各个强度值信号数占总信号数量比例的累加值)
		List<Map<String, Object>> cdfList_4G = dbService.getCdfData(startTime, endTime, network, buildingId, floor,
				Constants.Network._4G);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("cdfList_2G", cdfList_2G);
		data.put("cdfList_3G", cdfList_3G);
		data.put("cdfList_4G", cdfList_4G);

		statService.cdfAlignment(data);
		return data;
	}

}
