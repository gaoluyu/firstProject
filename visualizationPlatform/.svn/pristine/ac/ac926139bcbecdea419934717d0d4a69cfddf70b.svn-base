package androidServer.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import androidServer.service.DBService;
import androidServer.util.SessionUtils;
import androidServer.util.WebUtil;

@Controller
@RequestMapping("/LTE")
public class LTEStatisticController {
	@Autowired
	DBService dbService;

	/**
	 * 获取指定building(或floor)的楼内,在一段时间里收集到属于此楼cid的某一运营商4G信号的rsrq、rsrq值
	 * 
	 * @param start
	 * @param end
	 * @param network
	 * @param buildingId
	 * @param floor
	 * @Return (rsrp, rsrq)
	 */
	@RequestMapping("getRsrpRsrq")
	@ResponseBody
	Map<String, Object> network(HttpServletRequest request, HttpServletResponse response, int buildingId,
			long startTime, long endTime, int floor, String network) {
		// String network="移动";
		List<Map<String, Object>> list = dbService.getRsrpRsrqData(startTime, endTime, floor, buildingId, network);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("rlist", list);
		return data;
	}

	/**
	 * 获取指定building(或floor)的楼内,在一段时间里收集到属于此楼cid的某运营商4G信号的累计分布曲线(
	 * 从小到大的各dl_bps或ul_bps信号数占总信号数量比例的累加值)
	 * 
	 * @param start
	 * @param end
	 * @param network
	 * @param buildingId
	 * @param floor
	 * @Return (dl_bps or ul_bps, per)
	 */
	@RequestMapping("getCDFData")
	@ResponseBody
	Map<String, Object> getCDFbps(HttpServletRequest request, HttpServletResponse response, int buildingId,
			long startTime, long endTime, int floor, String network) {
		// String network="移动";
		Map<String, Object> lists = dbService.getCDFbpsData(startTime, endTime, floor, buildingId, network);
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("cdfLists", lists);
		return data;
	}

}
