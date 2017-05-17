package androidServer.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import androidServer.service.DBService;
import androidServer.util.WebUtil;

@Controller
@RequestMapping("/excel")
public class ExcelController {

	@Autowired
	DBService dbService;

	@RequestMapping("allWorkParameter")
	@ResponseBody
	Map<String, Object> getAvailableBeacon(HttpServletRequest request,
			HttpServletResponse response, String filePath) {
		System.out.println("filePath:" + filePath);
		boolean status = dbService.insertParameter(filePath);
		Map<String, Object> result = new HashMap<String, Object>();
		if (status) {
			result = WebUtil.okResponse(request);
		} else {
			result.put("status", "error");
		}
		return result;
	}
}
