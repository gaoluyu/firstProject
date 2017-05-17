package androidServer.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import androidServer.bean.IndoorDate;
import androidServer.bean.IndoorList;
import androidServer.service.DBService;
import androidServer.util.Constants;
import androidServer.util.SessionUtils;
import androidServer.util.WebUtil;

@Controller
@RequestMapping("/indoor")
public class IndoorController {

	@Autowired
	DBService dbService;

	/**
	 * 获取指定ci、building和floor内的beacon信息，以及指定时间内每个beacon区域采集到指定network的无线信号强度的平均值
	 * 
	 * @param request
	 * @param response
	 * @param building
	 * @param floor
	 * @param ci
	 * @param startTime
	 * @param endTime
	 * @param network
	 * @return (b.id,AVG(signalStrength),b.x,b.y,b.description)
	 */

	@RequestMapping("indoorMap")
	@ResponseBody
	Map<String, Object> getIndoorMap(HttpServletRequest request, HttpServletResponse response, long startTime,
			long endTime, int floor, int building, int ci, String network) {
		List<Map<String, Object>> list = null;
		List<Map<String, Object>> warningBottomlist = null;
		if (network != null) {
			list = dbService.getIndoorMapPoints(startTime, endTime, floor, building, ci, network);
			warningBottomlist = dbService.getWarningBottom(network);
		}
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("list", list);
		data.put("warningBottomlist", warningBottomlist);
		return data;
	}

	/**
	 * 获取指定building和floor内的beacon总数
	 * 
	 * @param request
	 * @param response
	 * @param building
	 * @param floor
	 * @return (count(*))
	 */
	@RequestMapping("totalBeacon")
	@ResponseBody
	Map<String, Object> getIndoorMap(HttpServletRequest request, HttpServletResponse response, int floor, int building,
			String network) {
		// String province = SessionUtils.getProvince(request);
		// String city = SessionUtils.getCity(request);
		long number = dbService.getBeaconTotalNumber(floor, building);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("totalCount", number);
		return data;
	}

	/**
	 * 获取指定ci、building和floor内每个beacon指定network的IndoorList类信息，beacon总数，
	 * 指定netType的报警值，网络等级bottom区间
	 * 
	 * @param request
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @param floor
	 * @param building
	 * @param ci
	 * @param startPage
	 *            显示第几页
	 * @param pageSize
	 *            每页显示的数目
	 * @return (IndoorList,totalCount,warningValue,warningBottomlist)
	 */
	@RequestMapping("indoorList")
	@ResponseBody
	Map<String, Object> getIndoorList(HttpServletRequest request, HttpServletResponse response, long startTime,
			long endTime, int floor, int building, int ci, int startPage, int pageSize, String network) {
		// String province = SessionUtils.getProvince(request);
		// String city = SessionUtils.getCity(request);
		List<IndoorList> list = null;
		List<Map<String, Object>> warningBottomlist = null;
		if (network != null)
			list = dbService.getIndoorList(startTime, endTime, floor, building, ci, startPage, pageSize, network);
		long totalNumber = dbService.getBeaconTotalNumber(floor, building);
		int warningValue = dbService.getNetworkWarningValue(network, SessionUtils.getUser(request).getUsername());
		warningBottomlist = dbService.getWarningBottom(network);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("list", list);
		data.put("totalCount", totalNumber);
		data.put("warningValue", warningValue);
		data.put("warningBottomlist", warningBottomlist);
		return data;
	}

	/**
	 * 获取指定ci的beacon在一段时间内，每一天采集到的指定network信号的强度信息
	 * 
	 * @param request
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @param beaconId
	 * @param ci
	 * @param network
	 * @return (date,averageStrength,maxRssi,minRssi,sortTime)
	 */
	@RequestMapping("beaconCurve")
	@ResponseBody
	Map<String, Object> getBeaconCurve(HttpServletRequest request, HttpServletResponse response, long startTime,
			long endTime, String beaconId, int ci, String network) {
		List<IndoorDate> list = null;
		if (network == null)
			list = null;
		else
			list = dbService.getBeaconCurve(startTime, endTime, beaconId, ci, network);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("list", list);
		return data;
	}

	/**
	 * 获取用户管理区域内building的物理信息
	 * 
	 * @param request
	 *            response
	 * @return ("buildingId","name","ci")
	 */
	@RequestMapping("loadCell")
	@ResponseBody
	Map<String, Object> loadBuilding(HttpServletRequest request, HttpServletResponse response) {
		String province = SessionUtils.getProvince(request);
		String city = SessionUtils.getCity(request);
		String operator_mnc = SessionUtils.getUser(request).getNetwork_mnc();
		List<Map<String, String>> list = dbService.getBuilding(province, city, operator_mnc);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("list", list);
		return data;
	}

	/**
	 * 获取指定building的楼层信息
	 * 
	 * @param request
	 *            response buildingId
	 * @return ("floorUpGround" "floorUnderGround")
	 */
	@RequestMapping("loadFloor")
	@ResponseBody
	Map<String, Object> loadFloor(HttpServletRequest request, HttpServletResponse response, int buildingId) {

		Map<String, Long> floor = dbService.getFloor(buildingId, 0);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("floor", floor);
		// System.out.println("loadfloor");
		return data;
	}

	/**
	 * 获取指定building指定floor的地图
	 * 
	 * @param request
	 * @param response
	 * @param buildingId
	 * @return (floor,url)
	 */
	@RequestMapping("mapUrl")
	@ResponseBody
	Map<String, Object> mapUrl(HttpServletRequest request, HttpServletResponse response, int buildingId, int floor) {
		List<Map<String, Object>> list = dbService.getBuildingMap(buildingId, floor);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("list", list);
		return data;
	}

	/**
	 * 获取session中的数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("dataInSession")
	@ResponseBody
	Map<String, Object> getDataInSession(HttpServletRequest request, HttpServletResponse response) {
		return WebUtil.okResponse(request);
	}

	/**
	 * 获取指定ci、building和floor内所有beacon采集的指定network的每一条无线信号的信息
	 * 
	 * @param request
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @param floor
	 * @param building
	 * @param ci
	 * @param network
	 * @return (i.position,i.signalStrength,ib.distance)
	 */
	@RequestMapping("indoorHeatData")
	@ResponseBody
	Map<String, Object> indoorHeatData(HttpServletRequest request, HttpServletResponse response, long startTime,
			long endTime, int floor, int building, int ci, String network) {
		List<Map<String, Object>> list = null;
		if (network != null) {
			list = dbService.getIndoorMapHeatPoints(startTime, endTime, floor, building, ci, network);
		}
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("list", list);
		return data;
	}

}
