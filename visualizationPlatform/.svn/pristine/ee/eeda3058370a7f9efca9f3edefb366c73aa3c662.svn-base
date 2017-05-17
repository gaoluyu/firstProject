package androidServer.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import androidServer.bean.User;
import androidServer.bean.WorkParameter;
import androidServer.service.DBService;
import androidServer.util.Constants;
import androidServer.util.JSONUtil;
import androidServer.util.SMSUtil;
import androidServer.util.SessionUtils;
import androidServer.util.WebUtil;

@Controller
@RequestMapping("userAdmin")
public class UserAdminController {

	@Autowired
	DBService dbService;

	/**
	 * 获取未读消息数目
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("messageNumber")
	@ResponseBody
	public Map<String, Object> messageNumber(HttpServletRequest request, HttpServletResponse response) {
		User user = SessionUtils.getUser(request);
		long number = 0;
		if (user != null) {
			String operatorMnc = user.getNetwork_mnc();
			number = dbService.getMessageNumber(user.getUsername(), operatorMnc);
		}
		Map<String, Object> map = WebUtil.okResponse(request);
		map.put("count", number);
		return map;
	}

	/**
	 * 获取用户基本信息 hu n
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("userInfo")
	@ResponseBody
	public Map<String, Object> getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> data = new HashMap<String, Object>();
		User user = SessionUtils.getUser(request);
		String msg;
		if (user != null) {
			msg = "获取用户信息成功";
			data = WebUtil.okResponse(request);
			data.put("msg", msg);
			data.put("User", user);
		} else {
			data = WebUtil.errorResponse(request, WebUtil.ERROR, "获取用户信息失败，未登录");
		}
		return data;
	}

	/**
	 * 修改账户密码
	 * 
	 * @param request
	 * @param response
	 * @param oldpassword
	 * @param newpassword
	 * @return
	 */
	@RequestMapping("modifyPassword")
	@ResponseBody
	public Map<String, Object> modifyPassword(HttpServletRequest request, HttpServletResponse response,
			String oldpassword, String newpassword) {
		Map<String, Object> data = new HashMap<String, Object>();
		User user = SessionUtils.getUser(request);
		String msg = null;
		if (user != null) {
			if (oldpassword.equals(user.getPassword())) {
				boolean status = dbService.modifyPassword(newpassword, user.getUsername());
				if (status) {
					msg = "密码修改成功";
					SessionUtils.removeUser(request);
					data = WebUtil.okResponse(request);
				}
			} else {
				msg = "原密码不正确";
				data = WebUtil.errorResponse(request, WebUtil.PASSWORD_ERROR, Constants.ErrorType.PASSWORD_ERROR);
			}
			data.put("msg", msg);
		}
		return data;
	}

	/**
	 * 获取用户设置信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getUserSetting")
	@ResponseBody
	public Map<String, Object> getUserSetting(HttpServletRequest request, HttpServletResponse response) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = new HashMap<String, Object>();
		if (user != null) {
			List<Map<String, Object>> list = dbService.getUserSetting(user);
			data = WebUtil.okResponse(request);
			data.put("msg", "获取用户设置成功");
			data.put("list", list);
		} else {
			data = WebUtil.errorResponse(request, WebUtil.ERROR, "获取用户设置失败，未登录");
		}
		return data;
	}

	/**
	 * 更改用户设置 data json字符转
	 * [{"operator":operator,"netType":netType,"warningValue":warningValue,}...]
	 * 
	 * @param request
	 * @param response
	 * @param datka
	 * @return
	 */
	@RequestMapping("modifySetting")
	@ResponseBody
	public Map<String, Object> modifySetting(HttpServletRequest request, HttpServletResponse response, String data) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = SessionUtils.getUser(request);
		String msg;
		System.out.println(data);
		if (user != null) {
			boolean status = dbService.updateUserSetting(user, data);
			if (status) {
				msg = "系统设置成功";
				map = WebUtil.okResponse(request);
				map.put("msg", msg);
			} else {
				map = WebUtil.errorResponse(request, WebUtil.ERROR, "系统设置失败");
			}
		}
		return map;
	}

	/**
	 * 获取用户覆盖率设置
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getCoverageSetting")
	@ResponseBody
	public Map<String, Object> getCoverageSetting(HttpServletRequest request, HttpServletResponse response) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = new HashMap<String, Object>();
		if (user != null) {
			List<Map<String, Object>> CoverageList = dbService.getCoverageSetting(user);
			data = WebUtil.okResponse(request);
			data.put("msg", "获取用户覆盖率设置成功");
			data.put("CoverageList", CoverageList);
		} else {
			data = WebUtil.errorResponse(request, WebUtil.ERROR, "获取用户覆盖率设置失败，未登录");
		}
		return data;
	}

	/**
	 * 更改用户覆盖率设置
	 * 
	 * @param request
	 * @param response
	 * @param datka
	 * @return
	 */
	@RequestMapping("modifyCoverage")
	@ResponseBody
	public Map<String, Object> modifyCoverage(HttpServletRequest request, HttpServletResponse response,
			int coverageRatio) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = SessionUtils.getUser(request);
		String msg;
		if (user != null) {
			boolean status = dbService.updateCoverageSetting(user, coverageRatio);
			if (status) {
				msg = "更改覆盖率设置成功";
				map = WebUtil.okResponse(request);
				map.put("msg", msg);
			} else {
				map = WebUtil.errorResponse(request, WebUtil.ERROR, "更改覆盖率设置失败");
			}
		}
		return map;
	}

	/**
	 * 获取网络等级设置
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getNetworkList")
	@ResponseBody
	public Map<String, Object> getNetworkList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> data = new HashMap<String, Object>();

		List<Map<String, Object>> NetworkList = dbService.getNetworkList();
		data = WebUtil.okResponse(request);
		data.put("msg", "获取用户覆盖率设置成功");
		data.put("NetworkList", NetworkList);
		return data;
	}

	/**
	 * 更改网络等级设置
	 * 
	 * @param request
	 * @param response
	 * @param datka
	 * @return
	 */
	@RequestMapping("modifyNetworklist")
	@ResponseBody
	public Map<String, Object> modifyNetworklist(HttpServletRequest request, HttpServletResponse response,
			String data) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = SessionUtils.getUser(request);
		String msg;
		System.out.println(data);
		if (user != null) {
			boolean status = dbService.updateNetworklist(user, data);
			if (status) {
				msg = "更改网络等级设置成功";
				map = WebUtil.okResponse(request);
				map.put("msg", msg);
			} else {
				map = WebUtil.errorResponse(request, WebUtil.ERROR, "更改网络等级设置失败");
			}
		}
		return map;
	}

	/**
	 * 分页获取消息列表
	 * 
	 * @param request
	 * @param response
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("messageList")
	@ResponseBody

	public Map<String, Object> messageList(HttpServletRequest request, HttpServletResponse response, int startPage,
			int pageSize) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = new HashMap<String, Object>();
		if (user != null) {
			String operatorMnc = user.getNetwork_mnc();
			List<Map<String, Object>> list = dbService.getMessage(user.getUsername(), startPage, pageSize, operatorMnc);
			List<Map<String, Object>> warningList = dbService.getSettingValue(user.getUsername());
			long totalCount = dbService.getMessageTotalNumber(user.getUsername(), operatorMnc);
			data = WebUtil.okResponse(request);
			data.put("list", list);
			data.put("warningValue", warningList);
			data.put("msg", "获取告警消息成功");
			data.put("totalCount", totalCount);
		} else {
			data = WebUtil.errorResponse(request, WebUtil.ERROR, "获取告警消息失败，未登录");
		}
		return data;
	}

	/**
	 * 阅读某消息
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("messageDetail")
	@ResponseBody
	public Map<String, Object> messageDetail(HttpServletRequest request, HttpServletResponse response, int id) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = new HashMap<String, Object>();
		if (user != null) {
			List<Map<String, Object>> list = dbService.getMessageDetail(user.getUsername(), id);
			List<Map<String, Object>> userSettinglist = dbService.getUserSetting(user);
			data = WebUtil.okResponse(request);
			data.put("list", list);
			data.put("userSettinglist", userSettinglist);
			data.put("msg", "获取详细告警消息成功");
		} else {
			data = WebUtil.errorResponse(request, WebUtil.ERROR, "获取详细告警消息失败，未登录");
		}
		return data;
	}

	/**
	 * 确认告警消息
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("MesConfirm")
	@ResponseBody
	public Map<String, Object> MesConfirm(HttpServletRequest request, HttpServletResponse response, int id) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = new HashMap<String, Object>();
		boolean status = dbService.modifyMark(user.getUsername(), id, "已确认", "");
		if (status)
			data.put("msg", "确认告警消息状态成功");
		else
			data = WebUtil.errorResponse(request, WebUtil.ERROR, "确认告警消息状态失败");

		return data;
	}

	/**
	 * 处理告警消息
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @param telephone
	 * @return
	 */
	@RequestMapping("MesHandle")
	@ResponseBody
	public Map<String, Object> MesHandle(HttpServletRequest request, HttpServletResponse response, int id,
			String telephone, String position, String netType, long date, String strength) {
		User user = SessionUtils.getUser(request);
		Map<String, Object> data = new HashMap<String, Object>();
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String startTime = format1.format(date);

		String NoticeMsg = SMSUtil.sendHandleMes(telephone, position, netType, startTime, strength);
		boolean status = dbService.modifyMark(user.getUsername(), id, "已处理", telephone);

		if (status) {
			data = WebUtil.okResponse(request);
			data.put("msg", "处理告警消息状态成功");
		} else
			data = WebUtil.errorResponse(request, WebUtil.ERROR, "处理告警消息状态失败");

		return data;
	}
}
