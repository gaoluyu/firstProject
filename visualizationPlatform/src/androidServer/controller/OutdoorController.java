package androidServer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import androidServer.bean.HeatData;
import androidServer.service.DBService;
import androidServer.service.HeatService;
import androidServer.util.SessionUtils;
import androidServer.util.WebUtil;

@Controller
@RequestMapping("/outdoor")
public class OutdoorController {

	@Autowired
	DBService dbService;
	@Autowired
	HeatService heatService;

	@RequestMapping("pointList")
	@ResponseBody
	Map<String, Object> getPointList(HttpServletRequest request, HttpServletResponse response, long startTime,
			long endTime, String wpNetwork, int ciMayDefault) {
		String province = SessionUtils.getProvince(request);
		String city = SessionUtils.getCity(request);
		Map<String, Object> data = WebUtil.okResponse(request);
//		System.out.println(1);
		List<Map<String, Object>> list = dbService.getOutdoorMapPoints(province, city, startTime, endTime, wpNetwork,
				ciMayDefault);
//		System.out.println(2);
		List<Map<String, Object>> warningBottomlist = dbService.getWarningBottom(wpNetwork);
//		System.out.println(3);
		List<Map<String, Object>> coverageRatio = dbService.getNetworkCoverageRatio(wpNetwork,
				SessionUtils.getUser(request).getUsername());
		// System.out.println("size"+list.get(1).size());
		data.put("list", list);
		data.put("covergeRatio", coverageRatio);
		data.put("warningBottomlist", warningBottomlist);
		return data;
	}

	/**
	 * 获取各个场强值的详细信息
	 * 
	 * @param request
	 * @param response
	 * @param cid
	 * @param startTime
	 * @param endTime
	 * @param network
	 * @return
	 */
	@RequestMapping("pointAvg")
	@ResponseBody
	Map<String, Object> getAvg(HttpServletRequest request, HttpServletResponse response, int cid, long startTime,
			long endTime, String network) {
		Map<String, Object> data = WebUtil.okResponse(request);
		List<Map<String, Object>> list = dbService.getAvgPoints(cid, startTime, endTime, network);
		int warningValue = dbService.getNetworkWarningValue(network, SessionUtils.getUser(request).getUsername());
		int warningNum = dbService.getCidWarningNum(cid, warningValue, startTime, endTime, network);
		data.put("warnNum", warningNum);
		data.put("avgList", list);
		return data;
	}

	@RequestMapping("setCiInSession")
	@ResponseBody
	Map<String, Object> getPointList(HttpServletRequest request, HttpServletResponse response, int ci, String network) {
		SessionUtils.setCi(request, ci);
		SessionUtils.setNetwork(request, network);
		// System.out.println("ci" + SessionUtils.getCity(request));
		Map<String, Object> data = WebUtil.okResponse(request);
		return data;
	}

	/**
	 * 返回热力图数据以及城市的中心坐标
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("strengthHeat")
	@ResponseBody
	Map<String, Object> strengthHeat(HttpServletRequest request, HttpServletResponse response) {
		String province = SessionUtils.getProvince(request);
		String city = SessionUtils.getCity(request);
		Map<String, Object> coord = heatService.getCenterCoordinate(province, city);
		List<HeatData> list = heatService.getHeatData(province, city);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("list", list);
		data.put("coord", coord);
		return data;
	}

	/**
	 * 返回搜索的label :name 和 value: Civalue
	 * 
	 * @param request
	 * @param response
	 * @param network
	 * @return
	 */
	@RequestMapping("getCiOrCNameLike")
	@ResponseBody
	Map<String, Object> getCiOrCNameLike(HttpServletRequest request, HttpServletResponse response, String CiorCName,
			String network) {
		String province = SessionUtils.getProvince(request);
		String city = SessionUtils.getCity(request);

		List<Map<String, Object>> list = dbService.getCiOrCNameLike(province, city, CiorCName, network);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("list", list);

		return data;
	}
}
