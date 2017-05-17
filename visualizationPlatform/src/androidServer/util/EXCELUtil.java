package androidServer.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import androidServer.bean.WorkParameter;

public class EXCELUtil {

	public static String getPostfix(String path) {
		if (path == null || Common.EMPTY.equals(path.trim())) {
			return Common.EMPTY;
		}
		if (path.contains(Common.POINT)) {
			return path.substring(path.lastIndexOf(Common.POINT) + 1, path.length());
		}
		return Common.EMPTY;
	}

	public static List<WorkParameter> readExcel(String path, int startRow, int readSize) throws IOException {
		if (path == null || Common.EMPTY.equals(path)) {
			return null;
		} else {
			String postfix = getPostfix(path);
			if (!Common.EMPTY.equals(postfix)) {
				if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
					return readXls(path, startRow, readSize);
				} else if (Common.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
					return readXlsx(path, startRow, readSize);
				}
			} else {
				System.out.println(path + Common.NOT_EXCEL_FILE);
			}
		}
		return null;
	}

	public static List<WorkParameter> readXlsx(String path, int startRow, int readSize) throws IOException {
		System.out.println(Common.PROCESSING + path);
		InputStream is = new FileInputStream(path);
		System.out.println(1);
		@SuppressWarnings("resource")
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<WorkParameter> list = new ArrayList<WorkParameter>();
		System.out.println(2);
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// Read the Row
			System.out.println("last row number " + xssfSheet.getLastRowNum());
			int endRow = Math.min(xssfSheet.getLastRowNum(), startRow + readSize - 1);
			for (int rowNum = startRow; rowNum <= endRow; rowNum++) {
				// System.out.println(rowNum);
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					WorkParameter wp = parse2WorkParameter(xssfRow);
					list.add(wp);

				}
			}
		}
		return list;
	}

	private static WorkParameter parse2WorkParameter(Row ssfRow) {
		String province = ssfRow.getCell(0) == null ? null : ssfRow.getCell(0).getStringCellValue();
		String city = ssfRow.getCell(1) == null ? null : ssfRow.getCell(1).getStringCellValue();
		String region = ssfRow.getCell(2) == null ? null : ssfRow.getCell(2).getStringCellValue();
		String network = ssfRow.getCell(3) == null ? null : ssfRow.getCell(3).getStringCellValue();
		double ci = ssfRow.getCell(4) == null ? 0 : ssfRow.getCell(4).getNumericCellValue();
		double cid = ssfRow.getCell(5) == null ? 0 : ssfRow.getCell(5).getNumericCellValue();
		double lac = ssfRow.getCell(6) == null ? 0 : ssfRow.getCell(6).getNumericCellValue();
		double enodeb = ssfRow.getCell(7) == null ? 0 : ssfRow.getCell(7).getNumericCellValue();

		if (network != null && network.equals("移动4G"))
			ci = 256 * enodeb + cid;

		String freqChannel = null;
		if (ssfRow.getCell(8) != null && ssfRow.getCell(8).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
			freqChannel = String.valueOf((int) (ssfRow.getCell(8).getNumericCellValue()));
		else if (ssfRow.getCell(8) != null && ssfRow.getCell(8).getCellType() == HSSFCell.CELL_TYPE_STRING)
			freqChannel = ssfRow.getCell(8).getStringCellValue();

		String cellNameEng = null;
		if (ssfRow.getCell(9) != null) {
			ssfRow.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
			cellNameEng = ssfRow.getCell(9).getStringCellValue();
		}
		String cellNameCh = ssfRow.getCell(10) == null ? null : ssfRow.getCell(10).getStringCellValue();
		String cellAddress = null;
		if (ssfRow.getCell(11) != null) {
			ssfRow.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
			cellAddress = ssfRow.getCell(11).getStringCellValue();
		}
		// String cellAddress = ssfRow.getCell(11) == null ? null :
		// ssfRow.getCell(11).getStringCellValue();
		double longitude = ssfRow.getCell(12) == null ? 0 : ssfRow.getCell(12).getNumericCellValue();
		double latitude = ssfRow.getCell(13) == null ? 0 : ssfRow.getCell(13).getNumericCellValue();
		String belonging = ssfRow.getCell(14) == null ? null : ssfRow.getCell(14).getStringCellValue();
		String scene = ssfRow.getCell(15) == null ? null : ssfRow.getCell(15).getStringCellValue();
		String converageType = ssfRow.getCell(16) == null ? null : ssfRow.getCell(16).getStringCellValue();

		WorkParameter workParameter = new WorkParameter();
		workParameter.setProvince(province);
		workParameter.setCity(city);
		workParameter.setRegion(region);
		workParameter.setNetwork(network);
		workParameter.setCi((int) ci);
		workParameter.setWpcid((int) cid);
		workParameter.setLac((int) lac);
		workParameter.setEnodeb((int) enodeb);
		workParameter.setFreqChannel(freqChannel);
		workParameter.setCellNameEng(cellNameEng);
		workParameter.setCellNameCh(cellNameCh);
		workParameter.setCellAddress(cellAddress);
		workParameter.setLongitude(longitude);
		workParameter.setLatitude(latitude);
		workParameter.setBelonging(belonging);
		workParameter.setScene(scene);
		workParameter.setConverageType(converageType);
		if (converageType != null && converageType.equals("室内")) {
			workParameter.setIsIndoor(1);
		} else {
			workParameter.setIsIndoor(0);
		}
		return workParameter;
	}

	/**
	 * Read the Excel 2003-2007
	 * 
	 * @param path
	 *            the path of the Excel
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("null")
	public static List<WorkParameter> readXls(String path, int startRow, int readSize) throws IOException {
		System.out.println(Common.PROCESSING + path);
		InputStream is = new FileInputStream(path);
		@SuppressWarnings("resource")
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<WorkParameter> list = new ArrayList<WorkParameter>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// Read the Row
			int endRow = Math.min(hssfSheet.getLastRowNum(), startRow + readSize - 1);
			for (int rowNum = startRow; rowNum <= endRow; rowNum++) {
				System.out.println(rowNum);
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					// String province =
					// hssfRow.getCell(0).getStringCellValue();
					// String city = hssfRow.getCell(1).getStringCellValue();
					// String region = hssfRow.getCell(2).getStringCellValue();
					// String network = hssfRow.getCell(3).getStringCellValue();
					// double ci = hssfRow.getCell(4).getNumericCellValue();
					// double cid = hssfRow.getCell(5).getNumericCellValue();
					// double lac = hssfRow.getCell(6).getNumericCellValue();
					// double enodeb = hssfRow.getCell(7).getNumericCellValue();
					// if (network != null && network.equals("移动4G"))
					// ci = 256 * enodeb + cid;
					// String freqChannel = null;
					// if (hssfRow.getCell(8).getCellType() ==
					// HSSFCell.CELL_TYPE_NUMERIC)
					// freqChannel = String.valueOf((int)
					// (hssfRow.getCell(8).getNumericCellValue()));
					// else if (hssfRow.getCell(8).getCellType() ==
					// HSSFCell.CELL_TYPE_STRING)
					// freqChannel = hssfRow.getCell(8).getStringCellValue();
					//
					// String cellNameEng =
					// hssfRow.getCell(9).getStringCellValue();
					// String cellNameCh =
					// hssfRow.getCell(10).getStringCellValue();
					// String cellAddress =
					// hssfRow.getCell(11).getStringCellValue();
					// double longitude =
					// hssfRow.getCell(12).getNumericCellValue();
					// double latitude =
					// hssfRow.getCell(13).getNumericCellValue();
					// String belonging =
					// hssfRow.getCell(14).getStringCellValue();
					// String scene = hssfRow.getCell(15).getStringCellValue();
					// String converageType =
					// hssfRow.getCell(16).getStringCellValue();
					//
					// WorkParameter workParameter = new WorkParameter();
					// workParameter.setProvince(province);
					// workParameter.setCity(city);
					// workParameter.setRegion(region);
					// workParameter.setNetwork(network);
					// workParameter.setCi((int) ci);
					// workParameter.setWpcid((int) cid);
					// workParameter.setLac((int) lac);
					// workParameter.setEnodeb((int) enodeb);
					// workParameter.setFreqChannel(freqChannel);
					// workParameter.setCellNameEng(cellNameEng);
					// workParameter.setCellNameCh(cellNameCh);
					// workParameter.setCellAddress(cellAddress);
					// workParameter.setLongitude(longitude);
					// workParameter.setLatitude(latitude);
					// workParameter.setBelonging(belonging);
					// workParameter.setScene(scene);
					// workParameter.setConverageType(converageType);
					// if (converageType != null && converageType.equals("室内"))
					// {
					// workParameter.setIsIndoor(1);
					// } else {
					// workParameter.setIsIndoor(0);
					// }
					WorkParameter wp = parse2WorkParameter(hssfRow);
					list.add(wp);

				}
			}
		}
		return list;
	}

	/**
	 * 
	 * @param path
	 * @param startRow
	 *            开始读的行数
	 * @param readSize
	 * @return
	 * @throws IOException
	 */
	public static List<WorkParameter> getExcelContent(String path, int startRow, int readSize) throws IOException {
		List<WorkParameter> list = readExcel(path, startRow, readSize);
		return list;
	}

	public static class ExportExcel<T> {

		// public void exportExcel(Collection<T> dataset, OutputStream out) {
		// exportExcel("测试POI导出EXCEL文档", null, dataset, out, "yyyy-MM-dd");
		// }
		//
		//
		// public void exportExcel(String[] headers, Collection<T> dataset,
		// OutputStream out) {
		// exportExcel("测试POI导出EXCEL文档", headers, dataset, out, "yyyy-MM-dd");
		// }
		//
		//
		// public void exportExcel(List sheetInf, String[] headers,
		// Collection<T> dataset,
		// OutputStream out, String pattern) {
		// exportExcel("测试POI导出EXCEL文档", headers, dataset, out, pattern);
		// }

		public HSSFSheet setStyle(HSSFWorkbook workbook, String title, List sheetInf, String[] headers) {
			HSSFSheet sheet = workbook.createSheet(title);
			HSSFCellStyle headStyle = workbook.createCellStyle();
			HSSFCellStyle infStyle = workbook.createCellStyle();
			HSSFCellStyle filedStyle = workbook.createCellStyle();

			// 设置表格默认列宽
			sheet.setDefaultColumnWidth((short) 16);
			// ******** 生成标题的样式*************************

			headStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			HSSFFont headfont = workbook.createFont();
			// headfont.setColor(HSSFColor.VIOLET.index);
			headfont.setFontHeightInPoints((short) 16);
			headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			headStyle.setFont(headfont);

			// **********创建信息栏样式****************************

			infStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			infStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			infStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			infStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			infStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			infStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			infStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			infStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			HSSFFont infFont = workbook.createFont();
			// infFont.setColor(HSSFColor.VIOLET.index);
			infFont.setFontHeightInPoints((short) 12);
			infFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			infStyle.setFont(infFont);

			// ******** 生成属性样式************************

			filedStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			filedStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			filedStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			filedStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			filedStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			filedStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			filedStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFFont filedFont = workbook.createFont();
			// font.setColor(HSSFColor.VIOLET.index);
			filedFont.setFontHeightInPoints((short) 12);
			filedFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			filedStyle.setFont(filedFont);

			// 输入表头
			HSSFRow headRow = sheet.createRow(0);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
			headRow.setHeightInPoints(40);
			HSSFCell headcell = headRow.createCell(0);
			headcell.setCellStyle(headStyle);
			HSSFRichTextString headtext = new HSSFRichTextString(title);
			headcell.setCellValue(headtext);

			// 产生表格信息栏
			HSSFRow infRow = sheet.createRow(1);
			for (int j = 0; j <= headers.length - 1; j++) {
				HSSFCell cell = HSSFCellUtil.getCell(infRow, j);
				cell.setCellStyle(infStyle);
			}
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, headers.length - 1));
			infRow.setHeightInPoints(20);
			HSSFCell infCell = infRow.createCell(0);
			infCell.setCellStyle(infStyle);
			String me = (String) sheetInf.get(0) + "    " + "制表时间: "
					+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
			HSSFRichTextString infText = new HSSFRichTextString(me);
			infCell.setCellValue(infText);

			// 产生表格属性行
			HSSFRow FIledRow = sheet.createRow(2);
			for (short i = 0; i < headers.length; i++) {
				HSSFCell cell = FIledRow.createCell(i);
				cell.setCellStyle(filedStyle);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}

			return sheet;
		}

		/**
		 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符合一定条件的数据以EXCEL 的形式输出到指定IO设备上
		 * 
		 * @param title
		 *            表格标题名
		 * @param headers
		 *            表格属性列名数组
		 * @param dataset
		 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
		 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
		 * @param out
		 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
		 * @param pattern
		 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
		 */
		@SuppressWarnings({ "unchecked", "deprecation" })
		public void exportExcel(String title, List sheetInf, String[] headers, Collection<T> dataset,
				OutputStream out) {
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			String pattern = "yyyy-MM-dd HH:mm:ss";
			HSSFSheet sheet = setStyle(workbook, title, sheetInf, headers);
			// ********设置数据样式*******************************
			HSSFCellStyle dataStyle = workbook.createCellStyle();
			dataStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			dataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFFont dataFont = workbook.createFont();
			dataFont.setFontHeightInPoints((short) 8);
			dataFont.setColor(HSSFColor.BLACK.index);
			dataFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			dataStyle.setFont(dataFont);
			// 遍历集合数据，产生数据行
			Iterator<T> it = dataset.iterator();
			int index = 1;
			while (it.hasNext()) {
				index++;
				// System.out.println(index);
				HSSFRow row = sheet.createRow(index + 1);
				T t = (T) it.next();
				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				Field[] fields = t.getClass().getDeclaredFields();
				for (short i = 0; i < fields.length; i++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(dataStyle);
					Field field = fields[i];
					String fieldName = field.getName();
					// System.out.println(fieldName);
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					try {
						Class tCls = t.getClass();
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						// 判断值的类型后进行强制类型转换
						String textValue = null;
						// if (value instanceof Integer) {
						// int intValue = (Integer) value;
						// cell.setCellValue(intValue);
						// } else if (value instanceof Float) {
						// float fValue = (Float) value;
						// textValue = new HSSFRichTextString(
						// String.valueOf(fValue));
						// cell.setCellValue(textValue);
						// } else if (value instanceof Double) {
						// double dValue = (Double) value;
						// textValue = new HSSFRichTextString(
						// String.valueOf(dValue));
						// cell.setCellValue(textValue);
						// } else if (value instanceof Long) {
						// long longValue = (Long) value;
						// cell.setCellValue(longValue);
						// }
						if (value instanceof Boolean) {
							boolean bValue = (Boolean) value;
							textValue = "男";
							if (!bValue) {
								textValue = "女";
							}
						} else if (value instanceof Date) {
							Date date = (Date) value;
							SimpleDateFormat sdf = new SimpleDateFormat(pattern);
							textValue = sdf.format(date);
							// } else if (value instanceof byte[]) {
							// // 有图片时，设置行高为60px;
							// row.setHeightInPoints(60);
							// // 设置图片所在列宽度为80px,注意这里单位的一个换算
							// sheet.setColumnWidth(i, (short) (35.7 * 80));
							// // sheet.autoSizeColumn(i);
							// byte[] bsValue = (byte[]) value;
							// HSSFClientAnchor anchor = new HSSFClientAnchor(0,
							// 0,
							// 1023, 255, (short) 6, index, (short) 6, index);
							// anchor.setAnchorType(2);
							// patriarch.createPicture(anchor,
							// workbook.addPicture(
							// bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
						} else if (value == null) {
							textValue = "";
						} else {
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
						}
						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
						if (textValue != null) {
							Pattern p = Pattern.compile("^//d+(//.//d+)?$");
							Matcher matcher = p.matcher(textValue);
							if (matcher.matches()) {
								// 是数字当作double处理
								cell.setCellValue(Double.parseDouble(textValue));
							} else {
								HSSFRichTextString richString = new HSSFRichTextString(textValue);
								HSSFFont font3 = workbook.createFont();
								font3.setColor(HSSFColor.BLACK.index);
								richString.applyFont(font3);
								cell.setCellValue(richString);
							}
						}
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						// 清理资源
					}
				}
			}
			try {
				workbook.write(out);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public void exportInspectExcel(String title, List sheetInf, String[] header,
				List<Map<String, Object>> inspectRageReport, List<Map<String, Object>> inspectTimeReport,
				OutputStream out) {
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			String pattern = "yyyy-MM-dd HH:mm:ss";
			HSSFSheet rageSheet = setStyle(workbook, "巡检范围报表", sheetInf, header);
			HSSFSheet timeSheet = setStyle(workbook, "巡检时间报表", sheetInf, header);
			// ********设置数据样式*******************************
			HSSFCellStyle dataStyle = workbook.createCellStyle();
			dataStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			dataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFFont dataFont = workbook.createFont();
			dataFont.setFontHeightInPoints((short) 8);
			dataFont.setColor(HSSFColor.BLACK.index);
			dataFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			dataStyle.setFont(dataFont);

			// 产生数据行

			for (int mark = 0; mark < 2; mark++) {
				int index = 1;
				HSSFSheet sheet;
				List<Map<String, Object>> report;
				if (mark == 0) {
					sheet = rageSheet;
					report = inspectRageReport;
				} else {
					sheet = timeSheet;
					report = inspectTimeReport;
				}
				for (int i = 0; i < report.size(); i++) {
					Map<String, Object> inspectRow = report.get(i);
					index++;
					HSSFRow row = sheet.createRow(index + 1);
					for (int j = 0; j < header.length; j++) {
						if (j == 0) {
							HSSFCell cell = row.createCell(j);
							cell.setCellStyle(dataStyle);
							cell.setCellValue((String) (inspectRow.get("inspector")));
						} else if (j == 1) {
							HSSFCell cell = row.createCell(j);
							cell.setCellStyle(dataStyle);
							cell.setCellValue((String) (inspectRow.get("buildingName")));
						} else {
							HSSFCell cell = row.createCell(j);
							cell.setCellStyle(dataStyle);
							cell.setCellValue(inspectRow.get(header[j]) + "");
							// System.out.println(report.get(j-2 +
							// (header.length-2)*i).get(header[j]));
						}
					}
				}
			}
			try {
				workbook.write(out);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
