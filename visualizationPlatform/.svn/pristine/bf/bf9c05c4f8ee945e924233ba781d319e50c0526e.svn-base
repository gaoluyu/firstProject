package androidServer.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.swing.FilePane;
import androidServer.service.DBService;
import androidServer.util.Constants;
import androidServer.util.EncryptUtil;
import androidServer.util.WebUtil;

@Controller
public class FileUploadController {
	// shandiao
	@Autowired
	DBService dbService;

	@ResponseBody
	@RequestMapping(value = "/upload.do")
	public Map<String, Object> upload(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) {

		System.out.println("开始");
		String fileName = file.getOriginalFilename();
		String fileNameHash = EncryptUtil.getNewFileName() + ".jpg";
		// String fileName = new Date().getTime()+".jpg";

		File targetFile = new File("D:/temp/buildingMap", fileNameHash);
		if (!targetFile.exists()) {
			System.out.println("transfer");
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> data = WebUtil.okResponse(request);
		data.put("url", fileNameHash);
		return data;
	}

	@RequestMapping(value = "/excelUpload.do")
	@ResponseBody
	public Map<String, Object> upload(@RequestParam(value = "cell", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> data = null;
		System.out.println("开始");
		if (file == null) {
			// System.out.println("no file");
			data = WebUtil.errorResponse(request, WebUtil.UPLOAD_ERROR, Constants.ErrorType.UPLOAD_ERROR);
			return data;
		}
		String fileName = EncryptUtil.getNewFileName() + ".xlsx";
		// System.out.println("fileName=" + fileName);
		File targetFile = new File("E:/excelUpload", fileName);
		String filePath = "E:/excelUpload/" + fileName;
		if (!targetFile.exists()) {
			// System.out.println("transfer excel");
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data = WebUtil.okResponse(request);
		data.put("fileName", fileName);
		data.put("filePath", filePath);
		return data;
	}
}
