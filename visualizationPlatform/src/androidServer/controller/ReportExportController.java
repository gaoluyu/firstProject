package androidServer.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import androidServer.annotation.Authenticate;
import androidServer.annotation.AuthenticatePlus;
import androidServer.bean.BusinessReport;
import androidServer.bean.User;
import androidServer.bean.WarningReport;
import androidServer.bean.WorkParameter;
import androidServer.bean.InspectReport;
import androidServer.service.DBService;
import androidServer.util.AuthenticateConstant;
import androidServer.util.Constants;
import androidServer.util.SessionUtils;
import androidServer.util.WebUtil;

@Controller
@RequestMapping("re")

public class ReportExportController {

	@Autowired
	DBService dbService;

	@RequestMapping("searchRegion")
	@ResponseBody
	public Map<String, Object> searchRegion(HttpServletRequest request, HttpServletResponse response, String region) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = WebUtil.okResponse(request);
		List<Map<String, String>> regions = dbService.getBuildingRegionList(user.getProvince(), user.getCity(), region);
		data.put("list", regions);
		return data;
	}

	/**
	 * 获取告警报表
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @return
	 */
	@RequestMapping("warningReport")
	@ResponseBody
	public Map<String, Object> warningReport(HttpServletRequest request, HttpServletResponse response, long startTime,
			long endTime, int startPage, int pageSize, String regionMayDefault) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = new HashMap<String, Object>();
		// 是否显示所有数据
		int isAll = 0;
		List<WarningReport> warningReport = dbService.getWarningReport(user.getUsername(), startTime, endTime,
				startPage, pageSize, isAll, regionMayDefault);
		int totalCount = dbService.getTotalWarningCount(user.getUsername(), startTime, endTime, regionMayDefault);
		data = WebUtil.okResponse(request);
		data.put("waringReport", warningReport);
		data.put("totalCount", totalCount);
		data.put("province", user.getProvince());
		data.put("city", user.getCity());
		data.put("startTime", startTime);
		data.put("endTime", endTime);
		return data;
	}

	/**
	 * 告警报表下载
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @return
	 */
	@RequestMapping("downWarningReport")
	@ResponseBody
	public Map<String, Object> downWarningReport(HttpServletRequest request, HttpServletResponse response,
			long startTime, long endTime, int startPage, int pageSize, String regionMayDefault) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = new HashMap<String, Object>();
		List sheetInf = new ArrayList();
		String url = "";
		int isAll = 1;
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

		String title = "告警报表";
		String[] headers = { "区域", "楼宇", "楼层", "故障天线", "网络", "信号强度(dBm)", "告警阈值(dBm)", "处理状态", "维护人手机号", "故障时间" };
		sheetInf.add("省份:" + user.getProvince() + "    " + "城市:" + user.getCity() + "  	 " + "开始时间:" + "   "
				+ format1.format(startTime) + "   " + "结束时间:" + format1.format(endTime));

		List<WarningReport> warningReport = dbService.getWarningReport(user.getUsername(), startTime, endTime,
				startPage, pageSize, isAll, regionMayDefault);
		url = dbService.downWarningReport(title, sheetInf, headers, warningReport);
		data = WebUtil.okResponse(request);
		data.put("url", url);
		return data;
	}

	/**
	 * 获取业务报表
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @return
	 */
	@RequestMapping("businessReport")
	@ResponseBody
	public Map<String, Object> businessReport(HttpServletRequest request, HttpServletResponse response, long startTime,
			long endTime, int startPage, int pageSize, String network, String region) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = new HashMap<String, Object>();
		// 是否显示所有数据
		int isAll = 0;
		List<BusinessReport> buinessReport = dbService.getBusinessReport(user.getProvince(), user.getCity(), startTime,
				endTime, startPage, pageSize, isAll, user.getNetwork_mnc(), network, region);
		data = WebUtil.okResponse(request);
		data.put("businessReport", buinessReport);
		// data.put("totalCount",totalCount);
		data.put("province", user.getProvince());
		data.put("city", user.getCity());
		data.put("startTime", startTime);
		data.put("endTime", endTime);
		int totalCount = buinessReport.size();
		data.put("total", totalCount);
		return data;
	}

	/**
	 * 业务报表下载
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @return
	 */
	@RequestMapping("downBusinessReport")
	@ResponseBody
	public Map<String, Object> downBusinessReport(HttpServletRequest request, HttpServletResponse response,
			long startTime, long endTime, int startPage, int pageSize, String network, String region) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = new HashMap<String, Object>();
		List sheetInf = new ArrayList();
		String url = "";
		int isAll = 1;
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String title = "业务报表";
		String[] headers = { "小区CI", "LAC", "freqChannel", "小区中文名", "小区英文名", "小区地址", "隶属", "覆盖场景", "区域", "eNB", "经度",
				"纬度", "信号强度平均值(dBm)", "平均RSRP", "平均RSRQ", "平均SINR", "网络类型" };
		sheetInf.add("省份:" + user.getProvince() + "    " + "城市:" + user.getCity() + "  	 " + "开始时间:" + "   "
				+ format1.format(startTime) + "   " + "结束时间:" + format1.format(endTime));

		List<BusinessReport> businessReport = dbService.getBusinessReport(user.getProvince(), user.getCity(), startTime,
				endTime, startPage, pageSize, isAll, user.getNetwork_mnc(), network, region);
		url = dbService.downBusinessReport(title, sheetInf, headers, businessReport);
		data = WebUtil.okResponse(request);
		data.put("url", url);
		return data;
	}

	// /**
	// * 巡检范围报表统计
	// *
	// * @param request
	// * @param response
	// * @param username
	// * @return
	// */
	// @RequestMapping("inspectReport")
	// @ResponseBody
	// public Map<String, Object> inspectReport(HttpServletRequest request,
	// HttpServletResponse response, long startTime,
	// long endTime) {
	// User user = SessionUtils.getUser(request);
	// List<Map<String, Object>> inspectRageReport =
	// dbService.getInspectReport(user.getProvince(), user.getCity(),
	// startTime, endTime);
	// List<String> dateString = dbService.getDateStringList(startTime,
	// endTime);
	//
	// Map<String, Object> data = null;
	// if (inspectRageReport != null) {
	// data = WebUtil.okResponse(request);
	// data.put("inspectRageReport", inspectRageReport);
	// data.put("dateStringList", dateString);
	// data.put("province", user.getProvince());
	// data.put("city", user.getCity());
	// data.put("startTime", startTime);
	// data.put("endTime", endTime);
	// } else {
	// data = WebUtil.errorResponse(request, WebUtil.ERROR,
	// Constants.ErrorType.ERROR);
	// }
	// return data;
	// }
	//
	// /**
	// * 巡检时间报表统计
	// *
	// * @param request
	// * @param response
	// * @param username
	// * @return
	// */
	// @RequestMapping("inspectTimesReport")
	// @ResponseBody
	// public Map<String, Object> inspectTimesReport(HttpServletRequest request,
	// HttpServletResponse response,
	// long startTime, long endTime) {
	// User user = SessionUtils.getUser(request);
	// List<Map<String, Object>> inspectTimeReport =
	// dbService.getInspectTimesReport(user.getProvince(),
	// user.getCity(), startTime, endTime);
	// List<String> dateString = dbService.getDateStringList(startTime,
	// endTime);
	// Map<String, Object> data = null;
	// if (inspectTimeReport != null) {
	// data = WebUtil.okResponse(request);
	// data.put("inspectTimeReport", inspectTimeReport);
	// data.put("dateStringList", dateString);
	// } else {
	// data = WebUtil.errorResponse(request, WebUtil.ERROR,
	// Constants.ErrorType.ERROR);
	// }
	// return data;
	//
	// }

	// /**
	// * 巡检报表下载
	// *
	// * @param request
	// * @param response
	// * @param username
	// * @return
	// */
	// @RequestMapping("downInspectReport")
	// @ResponseBody
	// public Map<String, Object> downInspectReport(HttpServletRequest request,
	// HttpServletResponse response,
	// long startTime, long endTime) {
	// User user = SessionUtils.getUser(request);
	// Map<String, Object> data = new HashMap<String, Object>();
	// List sheetInf = new ArrayList();
	// int isAll = 1;
	// DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	// String title = "巡检报表";
	//
	// List<String> dateString = dbService.getDateStringList(startTime,
	// endTime);
	// String[] common = { "姓名", "楼宇" };
	// String[] date = dateString.toArray(new String[dateString.size()]);
	//
	// String[] header = new String[common.length + date.length];
	// System.arraycopy(common, 0, header, 0, common.length);
	// System.arraycopy(date, 0, header, common.length, date.length);
	// // for(int i = 0;i <header.length;i ++) {
	// // System.out.println(header[i]);
	// // }
	//
	// sheetInf.add("省份:" + user.getProvince() + " " + "城市:" + user.getCity() +
	// " " + "开始时间:" + " "
	// + format1.format(startTime) + " " + "结束时间:" + format1.format(endTime));
	//
	// List<Map<String, Object>> inspectRageReport =
	// dbService.getInspectReport(user.getProvince(), user.getCity(),
	// startTime, endTime);
	// List<Map<String, Object>> inspectTimeReport =
	// dbService.getInspectTimesReport(user.getProvince(),
	// user.getCity(), startTime, endTime);
	//
	// String url = dbService.downInspectReport(title, sheetInf, header,
	// inspectRageReport, inspectTimeReport);
	//
	// data = WebUtil.okResponse(request);
	// data.put("url", url);
	// return data;
	// }
	//

	/**
	 * 新巡检报表
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @return
	 */
	@RequestMapping("InspectReport")
	@ResponseBody
	@AuthenticatePlus(AuthenticateConstant.AuthenticationBit.inspect_admin_bit)
	public Map<String, Object> InspectReport(HttpServletRequest request, HttpServletResponse response, long startTime,
			long endTime, int buildingId, int startPage, int pageSize) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = new HashMap<String, Object>();
		// 是否显示所有数据
		int isAll = 0;
		List<InspectReport> inspectReport = dbService.getInspectReport(user.getProvince(), user.getCity(), startTime,
				endTime, startPage, pageSize, isAll, buildingId);
		int totalCount = dbService.getInspectReport(user.getProvince(), user.getCity(), startTime, endTime, startPage,
				pageSize, 1, buildingId).size();
		data = WebUtil.okResponse(request);
		data.put("inspectReport", inspectReport);
		data.put("province", user.getProvince());
		data.put("city", user.getCity());
		data.put("startTime", startTime);
		data.put("endTime", endTime);
		data.put("total", totalCount);
		return data;
	}

	/**
	 * 巡检报表下载
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @return
	 */
	@RequestMapping("downInspectReport")
	@ResponseBody
	@AuthenticatePlus(AuthenticateConstant.AuthenticationBit.inspect_admin_bit)
	public Map<String, Object> downInspectReport(HttpServletRequest request, HttpServletResponse response,
			long startTime, long endTime, int startPage, int pageSize, int buildingId) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = new HashMap<String, Object>();
		List sheetInf = new ArrayList();
		String url = "";
		int isAll = 1;
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String title = "巡检报表";
		String[] headers = { "用户名", "巡更日期", "巡更时间", "巡更地点", "MAC地址" };
		sheetInf.add("省份:" + user.getProvince() + "    " + "城市:" + user.getCity() + "  	 " + "开始时间:" + "   "
				+ format1.format(startTime) + "   " + "结束时间:" + format1.format(endTime));

		List<InspectReport> inspectReport = dbService.getInspectReport(user.getProvince(), user.getCity(), startTime,
				endTime, startPage, pageSize, isAll, buildingId);
		url = dbService.downInspectReport(title, sheetInf, headers, inspectReport);
		data = WebUtil.okResponse(request);
		data.put("url", url);
		return data;
	}

	@RequestMapping("loadWPRegion")
	@ResponseBody
	public Map<String, Object> loadWPRegion(HttpServletRequest request, HttpServletResponse response) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = WebUtil.okResponse(request);
		List<String> list = dbService.getWPRegionList(user.getProvince(), user.getCity(), user.getNetwork_mnc());
		data = WebUtil.okResponse(request);
		data.put("list", list);
		return data;
	}

}
