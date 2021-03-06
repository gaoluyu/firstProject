package androidServer.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import androidServer.bean.Beacon;
import androidServer.bean.Building;
import androidServer.bean.BusinessReport;
import androidServer.bean.IndoorBeaconRecord;
import androidServer.bean.IndoorDate;
import androidServer.bean.IndoorList;
import androidServer.bean.IndoorRecord;
import androidServer.bean.InspectNumber;
import androidServer.bean.InspectReport;
import androidServer.bean.InspectTimes;
import androidServer.bean.Inspection;
import androidServer.bean.Inspector;
import androidServer.bean.KeepAliveLocation;
import androidServer.bean.Neighbor;
import androidServer.bean.Sim;
import androidServer.bean.User;
import androidServer.bean.WarningReport;
import androidServer.bean.WorkParameter;
import androidServer.mapper.AndroidMapper;
import androidServer.mapper.AppMapper;
import androidServer.mapper.BuildingStatisticMapper;
import androidServer.mapper.CellAdminMapper;
import androidServer.mapper.DeviceAdminMapper;
import androidServer.mapper.IndoorMapper;
import androidServer.mapper.LTEStatisticMapper;
import androidServer.mapper.NeighborMapper;
import androidServer.mapper.InspectAdminMapper;
import androidServer.mapper.OutdoorMapper;
import androidServer.mapper.ReportMapper;
import androidServer.util.Constants;
import androidServer.mapper.UserMapper;
import androidServer.mapper.WarningMapper;
import androidServer.util.EXCELUtil;
import androidServer.util.EXCELUtil.ExportExcel;
import androidServer.util.GPSConverter;
import androidServer.util.JSONUtil;
import androidServer.util.SessionUtils;

@Service
public class DBService {

	@Autowired
	AndroidMapper androidMapper;
	@Autowired
	OutdoorMapper outdoorMapper;
	@Autowired
	IndoorMapper indoorMapper;
	@Autowired
	CellAdminMapper cellAdminMapper;
	@Autowired
	DeviceAdminMapper deviceAdminMapper;
	@Autowired
	TransactionTemplate transactionTemplate;
	@Autowired
	UserMapper userMapper;
	@Autowired
	WarningMapper warningMapper;
	@Autowired
	BuildingStatisticMapper buildingStatisticMapper;
	@Autowired
	LTEStatisticMapper lteStatisticMapper;
	@Autowired
	InspectAdminMapper inspectAdminMapper;
	@Autowired
	ReportMapper reportMapper;
	@Autowired
	AppMapper appMapper;
	@Autowired
	NeighborMapper neighborMapper;
	private int buildingId;

	public List<Map<String, Object>> getBeaconList(Sim sim, String province, String city) {
		androidMapper.insertOrUpdateSim(sim);
		return androidMapper.getAvailableBeaconList(province, city);
	}

	public long getBeaconTotalNumber(int floor, int building) {
		return androidMapper.getTotalBeaconNumber(floor, building);
	}

	/**
	 * 
	 * @param list
	 * @param indoorBeaconRecords
	 * @param inspector
	 * @param listObject
	 * @return
	 */
	public boolean addIndoorRecords(final List<IndoorRecord> indoorRecords,
			final List<IndoorBeaconRecord> indoorBeaconRecords, final List<Map<String, Object>> speeds,
			final String inspector) {
		// if (list.size() == 0)
		// return true;
		// int insertNum = androidMapper.insertIndoor(list);
		// System.out.println("insert " + insertNum);
		// // if(insertNum==list.size())
		boolean status = transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					if (indoorRecords.size() != 0)
						androidMapper.insertIndoor(indoorRecords, inspector);
					if (indoorBeaconRecords.size() != 0)
						androidMapper.insertIndoorBeaconRecord(indoorBeaconRecords);
					if (speeds.size() != 0)
						androidMapper.updateIndoorRecord(speeds);
				} catch (Exception e) {
					e.printStackTrace();
					status.setRollbackOnly();
					return false;
				}
				return true;
			}
		});
		System.out.println(status);
		return status;
	}

	public boolean addNeighborRecords(List<Neighbor> list) {
		if (list.size() == 0)
			return true;
		int insertNum = androidMapper.insertNeighbor(list);
		System.out.println("insert " + insertNum);
		// if(insertNum==list.size())
		return true;
	}

	public List<Map<String, Object>> getOutdoorMapPoints(String province, String city, long timeStart, long timeEnd,
			String wpNetwork, int ciMayDefault) {

		Timestamp timeStartTS = new Timestamp(timeStart);
		Timestamp timeEndTS = new Timestamp(timeEnd);
		if (wpNetwork == null)
			return null;
		String[] list = getOperatprAndType(wpNetwork);

		return outdoorMapper.selectOutdoorMapPoints(province, city, wpNetwork, list[0], list[1], timeStartTS, timeEndTS,
				ciMayDefault, list[2]);

	}

	public List<Map<String, Object>> getAvgPoints(int cid, long startTime, long endTime, String network) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		String[] list = getOperatprAndType(network);

		Map<String, Object> data = outdoorMapper.getAvgIndex(cid, timeStartTS, timeEndTS, list[0], list[1]);

		Map<String, Object> speed = outdoorMapper.getAvgSpeed(cid, timeStartTS, timeEndTS, list[0], list[1]);
		for (String key : speed.keySet()) {
			data.put(key, speed.get(key));
		}

		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		l.add(data);
		return l;
	}

	public int getCidWarningNum(int cid, int warningValue, long startTime, long endTime, String wpNetwork) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		String[] list = getOperatprAndType(wpNetwork);
		// System.out.println(warningValue);
		return outdoorMapper.getWarningNum(cid, timeStartTS, timeEndTS, list[0], list[1], warningValue);
	}

	/**
	 * 
	 * @param wpNetwork
	 * @return [0] operator [1] typeList [2] dataType
	 */
	private String[] getOperatprAndType(String wpNetwork) {
		String operator = "'未知'";
		String type = "'未知'";
		String dataType = "'未知'";
		if (wpNetwork.contains("移动")) {
			operator = Constants.Network.ChinaMobile;
		} else if (wpNetwork.contains("联通")) {
			operator = Constants.Network.ChinaUnion;
		} else if (wpNetwork.contains("电信")) {
			operator = Constants.Network.ChinaTelecom;
		}

		if (wpNetwork.contains("2")) {
			type = Constants.Network._2G;
			dataType = "2G";
		} else if (wpNetwork.contains("3")) {
			type = Constants.Network._3G;
			dataType = "3G";
		} else if (wpNetwork.contains("4")) {
			type = Constants.Network._4G;
			dataType = "4G";
		}

		return new String[] { operator, type, dataType };

	}

	private String getOperatorNameFromMnc(String mnc) {
		if (Constants.Network.ChinaMobile.equals(mnc))
			return "移动";
		if (Constants.Network.ChinaUnion.equals(mnc))
			return "联通";
		if (Constants.Network.ChinaTelecom.equals(mnc))
			return "电信";
		if (Constants.Network.All.equals(mnc))
			return "";
		return "nothing";
	}

	public List<Map<String, Object>> getIndoorMapPoints(long timeStart, long timeEnd, int floor, int building, int ci,
			String network) {
		Timestamp timeStartTS = new Timestamp(timeStart);
		Timestamp timeEndTS = new Timestamp(timeEnd);
		String[] d = getOperatprAndType(network);
		String mncList = d[0];
		String netList = d[1];
		return indoorMapper.selectIndoorMapPoints(timeStartTS, timeEndTS, floor, building, ci, mncList, netList);
	}

	public List<Map<String, String>> getBuilding(String province, String city, String operator_mnc) {
		// System.out.println(province + "\n" + city);
		String operator = getOperatorNameFromMnc(operator_mnc);

		return indoorMapper.selectBuildings(province, city, operator + "%");
	}

	public Map<String, Long> getFloor(int buildingId, int ci) {
		// TODO Auto-generated method stub
		// System.out.println(buildingId);
		return indoorMapper.selectFloor(buildingId, ci);
	}

	public List<IndoorList> getIndoorList(long startTime, long endTime, int floor, int building, int ci, int startPage,
			int pageSize, String network) {
		// 暂时不要分页
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		String[] d = getOperatprAndType(network);
		String mncList = d[0];
		String netList = d[1];
		// 获取指定ci、building和floor内每个beacon的物理信息，以及指定时间内每个beacon区域里采集到指定network的无线信号强度的平均值、最大值、最小值
		List<IndoorList> list = indoorMapper.selectIndoorList(timeStartTS, timeEndTS, ci, floor, building,
				pageSize * (startPage - 1), pageSize, mncList, netList);
		// 获取指定ci、building和floor内每个beacon的物理信息，以及指定时间内每个beacon区域里采集到所有network的无线信号强度的平均值、最大值、最小值
		List<IndoorList> listAll = indoorMapper.selectIndoorListWithAllCid(timeStartTS, timeEndTS, floor, building,
				pageSize * (startPage - 1), pageSize, mncList, netList);
		if (list.size() == listAll.size())
			for (int i = 0; i < listAll.size(); i++) {
				String position = listAll.get(i).getPosition();
				if (list.get(i).getPosition().equals(position)) {
					int number = list.get(i).getNumber();
					int numberAll = listAll.get(i).getNumber();
					if (numberAll != 0)
						list.get(i).setRatio(((float) number) / numberAll);
					else
						list.get(i).setRatio(0);
				}
			}
		indoorListOrderByStrength(list);
		return list;
	}

	public void indoorListOrderByStrength(List<IndoorList> list) {
		Collections.sort(list, new Comparator<IndoorList>() {

			@Override
			public int compare(IndoorList o1, IndoorList o2) {
				int a1 = o1.getAverageStrength();
				int a2 = o2.getAverageStrength();
				if (a1 < a2)
					return -1;
				if (a1 == a2)
					return 0;
				return 1;
			}
		});
		;
	}

	public List<IndoorDate> getBeaconCurve(long startTime, long endTime, String beaconId, int ci, String network) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		String[] d = getOperatprAndType(network);
		String mncList = d[0];
		String netList = d[1];
		List<IndoorDate> listDB = indoorMapper.selectRssiByDay(timeStartTS, timeEndTS, beaconId, ci, mncList, netList);
		for (IndoorDate id : listDB)
			System.out.println(id.getDate());
		List<String> timeTable = getTimeList(startTime, endTime);
		List<IndoorDate> list = new ArrayList<IndoorDate>();
		for (String item : timeTable) {
			IndoorDate exist = null;
			for (IndoorDate id : listDB) {
				if (item.equals(id.getDate())) {
					exist = id;
					break;
				}
			}
			if (exist != null)
				list.add(exist);
			else {
				exist = new IndoorDate();
				exist.setDate(item);
				list.add(exist);
			}
		}
		return list;
	}

	private List<String> getTimeList(long startTime, long endTime) {
		Calendar d = Calendar.getInstance();
		d.setTimeInMillis(startTime);
		List<String> list = new ArrayList<String>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-M-d");
		while (d.getTimeInMillis() <= endTime) {
			list.add(fmt.format(new Date(d.getTimeInMillis())));
			d.add(Calendar.DATE, 1);
		}
		return list;
	}

	public boolean insertParameter(final String path) {
		System.out.println("loading in batch\nsource file :" + path);
		final GPSConverter converter = new GPSConverter();
		// 批量插入的事务管理
		boolean status = transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					int length = 0;
					int eachLoadSize = 5000;
					int start = 1;
					int eachInsertSize = 500;
					do {
						System.out.println("load from " + start);
						List<WorkParameter> list = EXCELUtil.getExcelContent(path, start, eachLoadSize);
						converter.startConvert(list);
						start += eachLoadSize;
						length = list.size();

						System.out.println("loaded :" + length);
						if (length > eachInsertSize && length != 0) {
							int desperse = length % eachInsertSize;
							for (int i = 0; i < length - desperse; i = i + eachInsertSize) {
								List<WorkParameter> temp = new ArrayList<WorkParameter>();
								for (int j = 0; j < eachInsertSize; j++) {
									temp.add(list.get(i + j));
								}
								cellAdminMapper.insertWorkParameter(temp);
							}
							for (int i = 0; i < desperse; i++) {
								List<WorkParameter> temp = new ArrayList<WorkParameter>();
								temp.add(list.get(length - desperse + i));
								cellAdminMapper.insertWorkParameter(temp);
							}
						} else {
							cellAdminMapper.insertWorkParameter(list);
						}
						list = null;
					} while (length == eachLoadSize);// 如果excel还有未读的行数，则eachLoadSize不会大于length
				} catch (Exception e) {
					e.printStackTrace();
					status.setRollbackOnly();
					return false;
				}
				return true;
			}
		});
		converter.stop();
		System.out.println("insertWorkParameter " + status);
		return status;
	}

	public List<WorkParameter> getAllWorkParameter(String province, String city, int startPage, int pageSize,
			String networkMnc, int ciMayDefault) {
		String operator = getOperatorNameFromMnc(networkMnc);
		// System.out.println(operator);
		// System.out.println(networkMnc);
		return cellAdminMapper.selectWorkParameter(province, city, pageSize * (startPage - 1), pageSize, operator + "%",
				ciMayDefault);

	}

	public long getWPTotalNumber(String province, String city, String networkMnc, int ciMayDefault) {
		String operator = getOperatorNameFromMnc(networkMnc);
		return cellAdminMapper.selectTotalNumber(province, city, operator + "%", ciMayDefault);
	}

	public WorkParameter getWorkParameter(String province, String city, Integer ci, String network) {
		return cellAdminMapper.selectWorkParameterWithCI(province, city, ci, network);
	}

	public List<String> getProvince() {

		return cellAdminMapper.selectProvince();
	}

	public List<String> getCity(String province) {
		return cellAdminMapper.selectCity(province);
	}

	public boolean insertParameter(WorkParameter wp) {
		int num = cellAdminMapper.insertSingleWorkParameter(wp);
		if (num == 1)
			return true;
		return false;
	}

	public void deleteParameter(String province, String city, int ci, String network) {
		cellAdminMapper.deleteWP(province, city, ci, network);

	}

	public List<Building> getAllBuilding(String province, String city, int startPage, int pageSize, int idMayDefault) {
		return deviceAdminMapper.selectAllBuilding(province, city, (startPage - 1) * pageSize, pageSize, idMayDefault);

	}

	public long getBuildingTotalCount(String province, String city, int idMayDefault) {
		return deviceAdminMapper.selectBuildingTotalCount(province, city, idMayDefault);
	}

	public Building getBuilding(Integer id) {
		return deviceAdminMapper.selectBuilding(id);
	}

	public String getBuildingName(Integer id) {
		return deviceAdminMapper.selectBuildingName(id);
	}

	public int insertBuilding(Building building) {
		int num = deviceAdminMapper.insertSingleBuilding(building);
		int id = 0;
		if (num == 1)
			id = deviceAdminMapper.selectBuildingInsertId();
		return id;
	}

	public boolean updateBuilding(Building building) {
		int num = deviceAdminMapper.updateBuilding(building);
		if (num == 1)
			return true;
		return false;
	}

	public void deleteBuilding(String province, String city, int id, String unit) {
		deviceAdminMapper.deleteBuilding(province, city, id, unit);

	}

	public List<Beacon> getBeaconList(int id, int startPage, int pageSize) {
		return deviceAdminMapper.selectBeacon(id, (startPage - 1) * pageSize, pageSize);

	}

	public Beacon getBeacon(String beaconInSession) {
		return deviceAdminMapper.selectSingleBeacon(beaconInSession);

	}

	public boolean insertBeacon(Beacon beacon) {
		int num = deviceAdminMapper.insertSingleBeacon(beacon);
		if (num == 1)
			return true;
		return false;
	}

	public void deleteBeacon(String mac) {
		deviceAdminMapper.deleteBeacon(mac);

	}

	public long getBeaconTotalNumber(int building) {
		return deviceAdminMapper.getBeaconCount(building);
	}

	/**
	 * 
	 * @param uesr
	 * @return
	 */
	public boolean insertUser(User user) {
		int num = userMapper.insertingUser(user);
		if (num == 1)
			return true;
		else
			return false;
	}

	public boolean initialCoveRatio(User user) {
		int coverageRatio = 96;
		int num = 0;
		String[] operator = { "移动", "联通", "电信" };
		String[] netType = { "2G", "3G", "4G" };
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				num = userMapper.initialCoveRatio(user, operator[i], netType[j], coverageRatio);
			}
		}
		if (num == 1)
			return true;
		else
			return false;

	}

	public boolean initialWarningValue(User user) {
		List<Map<String, Object>> warningValueList = userMapper.getRedBottom();
		int warningValue = 0;
		int num = 0;
		String[] netType = { "2G", "3G", "4G" };
		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				warningValue = (int) warningValueList.get(i).get("red_bottom");
			} else if (i == 1) {
				warningValue = (int) warningValueList.get(i).get("red_bottom");
			} else if (i == 2) {
				warningValue = (int) warningValueList.get(i).get("red_bottom");
			}
			int thresHold = 10;
			num = userMapper.initialWarningValue(user.getUsername(), warningValue, netType[i], thresHold);
		}
		if (num == 1)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	public boolean checkUsername(String username) {
		if (username != null)
			username = username.trim();
		String mark = userMapper.checkingUsername(username);
		System.out.println(username + "\n" + mark);
		if (mark != null && mark.equals(username)) // 通过什么判断是否找到
			return false;
		else
			return true;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	public User selectUser(String username) {
		return userMapper.selectUser(username);
		// System.out.println(username + "\n" + password);
	}

	public List<Map<String, Object>> buildingCiDetail(int buildingId) {

		return deviceAdminMapper.selectBuildingCi(buildingId);
	}

	public boolean deleteBuildingCi(int buildingId) {
		int n = deviceAdminMapper.deleteBuildingCi(buildingId);
		if (n == 1)
			return true;

		return false;
	}

	public void updateBuildingCi(String updateList) {

		if (updateList == null)
			return;
		List<Map<String, Object>> list = JSONUtil.jsonToJavaList(updateList, HashMap.class);

		deviceAdminMapper.updateBuildingCi(list);
	}

	public boolean insertBuildingCi(String insertList) {

		if (insertList == null)
			return false;
		List<Map<String, Object>> list = JSONUtil.jsonToJavaList(insertList, HashMap.class);

		if (list.size() != 0) {
			int num = deviceAdminMapper.insertBuildingCi(list);
			if (num >= 0)
				return true;
			return false;
		} else {
			return true;
		}
	}

	public boolean updateBuildingMap(String updateList) {
		List<Map<String, Object>> list = JSONUtil.jsonToJavaList(updateList, HashMap.class);
		if (list.size() == 0)
			return true;
		int num = deviceAdminMapper.updateBuildingMap(list);
		if (num >= 0)
			return true;
		return false;
	}

	public boolean updateUserWarning(final Timestamp start, final Timestamp end) {
		boolean result = transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					warningMapper.truncateScan();
					Timestamp fragmentStart = new Timestamp(start.getTime() - 15 * 24 * 3600 * 1000);
					warningMapper.udpateScan(fragmentStart, end, "trendency");
					warningMapper.udpateScan(start, end, "standard");
					warningMapper.insertWarningBoxBelowStandard(start, end);
					warningMapper.insertWarningBoxWithBadTrend(start, end);
				} catch (Exception e) {
					e.printStackTrace();
					status.setRollbackOnly();
					return false;
				}
				return true;
			}
		});
		return result;

	}

	public long getMessageNumber(String username, String operator_mnc) {
		String operator = getOperatorNameFromMnc(operator_mnc);
		return warningMapper.selectMessageNumber(username, operator + "%");
	}

	public List<Map<String, Object>> getBuildingMap(int buildingId, int floor) {
		return deviceAdminMapper.selectBuildingMap(buildingId, floor);
	}

	/**
	 * 
	 * @param floor
	 *            0 代表删除整个楼
	 * @param buildingId
	 */
	public void deleteBuildingMap(int floor, int buildingId) {
		deviceAdminMapper.deleteBuildingMap(floor, buildingId);

	}

	public boolean modifyPassword(String newpassword, String username) {
		int num;
		num = userMapper.modifyPassword(newpassword, username);
		if (num > 0) {
			return true;
		} else
			return false;
	}

	public List<Map<String, Object>> getUserSetting(User user) {
		String opreatorMnc = user.getNetwork_mnc();
		String operator = getOperatorNameFromMnc(opreatorMnc);
		return userMapper.getUserSetting(user.getUsername(), operator + "%");
	}

	public boolean updateUserSetting(User user, String data) {
		int num;
		List<Map<String, Object>> list = JSONUtil.jsonToJavaList(data, HashMap.class);
		num = userMapper.updateUserSetting(user.getUsername(), user.getProvince(), user.getCity(), list);
		if (num > 0)
			return true;
		else
			return false;
	}

	public List<Map<String, Object>> getCoverageSetting(User user) {
		return userMapper.getCoverageSetting(user.getUsername());
	}

	public boolean updateCoverageSetting(User user, int coverageRatio) {
		int num;
		String username = user.getUsername();
		num = userMapper.updateCoverageSetting(username, coverageRatio);
		if (num > 0)
			return true;
		else
			return false;
	}

	public List<Map<String, Object>> getNetworkList() {

		return userMapper.getNetworkList();
	}

	public boolean updateNetworklist(User user, String data) {
		int num;
		List<Map<String, Object>> list = JSONUtil.jsonToJavaList(data, HashMap.class);
		num = userMapper.updateNetworklist(list);
		if (num > 0)
			return true;
		else
			return false;
	}

	public int getNetworkWarningValue(String network, String username) {
		// 返回integer而不是Int,为了防止查询为空时的非法转换
		Integer value = userMapper.getNetworkWarningValue(username, network);
		return value == null ? 0 : value;
	}

	public List<Map<String, Object>> getNetworkCoverageRatio(String network, String username) {
		return userMapper.getNetworkCoverageRatio(username, network);
	}

	public List<Map<String, Object>> getWarningBottom(String wpnetType) {
		String netType;
		if (wpnetType.contains("2G")) {
			netType = "2G";
		} else if (wpnetType.contains("3G")) {
			netType = "3G";
		} else {
			netType = "4G";
		}
		return userMapper.getWarningBottom(netType);
	}

	/**
	 * 
	 * @param network
	 *            e.g.联通3G
	 * @return
	 */

	public List<Map<String, Object>> getBuildingLike(String province, String city, String buildingName) {

		return buildingStatisticMapper.selectBuildingLike(province, city, buildingName);
	}

	public List<Map<String, Object>> getMessage(String username, int startPage, int pageSize, String operatorMnc) {
		String operator = getOperatorNameFromMnc(operatorMnc);
		return userMapper.getMessage(username, (startPage - 1) * pageSize, pageSize, operator + "%");

	}

	public List<Map<String, Object>> getMessageDetail(String username, int id) {
		return userMapper.getMessageDetail(username, id);
	}

	public List<Map<String, Object>> getSettingValue(String username) {
		return userMapper.getSettingValue(username);
	}

	public boolean modifyMark(String username, int id, String mark, String telephone) {
		int num;
		num = userMapper.modifyMark(username, id, mark, telephone);
		if (num > 0)
			return true;
		else
			return false;
	}

	public int dropBuildingCi(int buildingId) {
		return deviceAdminMapper.deleteBuildingCiWithBid(buildingId);
	}

	public List<Map<String, Object>> getbBuildingLike(String province, String city, String buildingName) {

		return buildingStatisticMapper.selectBuildingLike(province, city, buildingName);
	}

	public String networkChange(String network) {
		String mncList = null;
		if (network.contains("移动")) {
			mncList = Constants.Network.ChinaMobile;
		} else if (network.contains("联通")) {
			mncList = Constants.Network.ChinaUnion;
		} else if (network.contains("电信")) {
			mncList = Constants.Network.ChinaTelecom;
		}
		return mncList;
	}

	public List<Map<String, Object>> getNetworkTypeNum(long start, long end, String network, int buildingId, int floor,
			boolean isLocalCid) {
		String mncList = networkChange(network);
		System.out.println(mncList);
		Timestamp startTime = new Timestamp(start);
		Timestamp endTime = new Timestamp(end);
		// System.out.println("endtime:" + endTime);
		// System.out.println(buildingId);
		// System.out.println("floor:" + floor);
		return buildingStatisticMapper.selectCoverageRatio(startTime, endTime, mncList, buildingId, floor, isLocalCid);
	}

	public List<Map<String, Object>> getWarningNum(long start, long end, String network, int buildingId, int floor,
			String userId, String operator) {
		String mncList = networkChange(network);
		Timestamp startTime = new Timestamp(start);
		Timestamp endTime = new Timestamp(end);
		return buildingStatisticMapper.selectWarningNumber(startTime, endTime, mncList, buildingId, floor, userId,
				operator);

	}

	public List<Map<String, Object>> getCidNum(long start, long end, String network, int buildingId, int floor) {
		String mncList = networkChange(network);
		Timestamp startTime = new Timestamp(start);
		Timestamp endTime = new Timestamp(end);
		return buildingStatisticMapper.selectCidNum(startTime, endTime, mncList, buildingId, floor);
	}

	public long getMessageTotalNumber(String username, String operatorMnc) {
		String operator = getOperatorNameFromMnc(operatorMnc);
		return warningMapper.selectMessageTotalNumber(username, operator + "%");
	}

	public List<Map<String, Object>> getCiOrCNameLike(String province, String city, String ciorCiName, String network) {
		// System.out.println(province + " " + city + " " + ciorCiName + " " +
		// network);
		return outdoorMapper.selectCiOrCNameLike(province, city, ciorCiName, network);

	}

	public List<Map<String, Object>> getCdfData(long start, long end, String network, int buildingId, int floor,
			String networkType) {
		String mncList = networkChange(network);
		Timestamp startTime = new Timestamp(start);
		Timestamp endTime = new Timestamp(end);
		return buildingStatisticMapper.selectCdfData(startTime, endTime, mncList, buildingId, floor, networkType);
	}

	public List<Map<String, Object>> getIndoorMapHeatPoints(long startTime, long endTime, int floor, int building,
			int ci, String network) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		String[] d = getOperatprAndType(network);
		String mncList = d[0];
		String netList = d[1];
		return indoorMapper.selectIndoorMapHeatPoints(timeStartTS, timeEndTS, floor, building, ci, mncList, netList);
	}

	public List<Map<String, Object>> getRsrpRsrqData(long startTime, long endTime, int floor, int buildingId,
			String type) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		String[] d = getOperatprAndType(type);
		String mncList = d[0];
		return lteStatisticMapper.selectRsrpRsrq(timeStartTS, timeEndTS, mncList, buildingId, floor);
	}

	public Map<String, Object> getCDFbpsData(long startTime, long endTime, int floor, int buildingId, String type) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		String[] d = getOperatprAndType(type);
		String mncList = d[0];
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Integer>> dlcdf = lteStatisticMapper.selectCDFbps(timeStartTS, timeEndTS, mncList, buildingId,
				floor, 1);
		List<Map<String, Integer>> ulcdf = lteStatisticMapper.selectCDFbps(timeStartTS, timeEndTS, mncList, buildingId,
				floor, 0);
		data.put("dlcdf", dlcdf);
		data.put("ulcdf", ulcdf);
		return data;

	}

	public List<Map<String, Object>> getImsi(int building) {
		return inspectAdminMapper.selectImsi(building);

	}

	public List<Map<String, Object>> getImsiStatic(long startTime, long endTime, long imsi, int building) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		return inspectAdminMapper.selectImsiStatic(timeStartTS, timeEndTS, imsi, building);
	}

	public List<Map<String, Object>> getPositionCurve(long startTime, long endTime, long imsi, int building) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		return inspectAdminMapper.selectPositionByDate(timeStartTS, timeEndTS, imsi, building);
	}

	public List<Map<String, Object>> getPositionCurveBoxPlot(long startTime, long endTime, long imsi, int building) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		return inspectAdminMapper.selectPositionBoxPlotByDate(timeStartTS, timeEndTS, imsi, building);
	}

	public String getAuthenticatedPages(int role) {

		return userMapper.selectAuthenticatedPages(role);
	}

	public int findUserNameSet(String userNameSet) {
		List<Map<String, Object>> list = inspectAdminMapper.selectUserNameExist(userNameSet);
		return list.size();

	}

	public boolean insertInspector(Inspector inspector) {
		int num = androidMapper.insertInspector(inspector);
		return num == 1;
	}

	public Inspector getInspector(String username) {

		return androidMapper.selectInspector(username);
	}

	public void insertInspection(List<Inspection> inspectionList) {
		androidMapper.insertInspection(inspectionList);

	}

	/**
	 * 获取告警报表
	 * 
	 * @param regionMayDefault
	 */
	public List<WarningReport> getWarningReport(String username, long startTime, long endTime, int startPage,
			int pageSize, int isAll, String regionMayDefault) {
		// TODO Auto-generated method stub
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		return reportMapper.getWarningReport(username, timeStartTS, timeEndTS, (startPage - 1) * pageSize, pageSize,
				isAll, regionMayDefault);
	}

	/**
	 * 下载告警报表
	 */

	public String downWarningReport(String title, List sheetInf, String[] headers, List<WarningReport> warningReport) {

		ExportExcel<WarningReport> ex = new ExportExcel<WarningReport>();
		String url = "";
		String fileName = "warningReport" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		try {

			OutputStream out = new FileOutputStream("D://temp/excel/" + fileName + ".xls");
			ex.exportExcel(title, sheetInf, headers, warningReport, out);
			out.close();
			System.out.println("excel导出成功！");
			url = "/report/" + fileName + ".xls";
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return url;
	}

	/**
	 * 下载业务报表
	 */
	public String downBusinessReport(String title, List sheetInf, String[] headers, List<BusinessReport> Report) {
		ExportExcel<BusinessReport> ex = new ExportExcel<BusinessReport>();
		String url = "";
		String fileName = "businessReport" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		try {
			OutputStream out = new FileOutputStream("D://temp/excel/" + fileName + ".xls");
			ex.exportExcel(title, sheetInf, headers, Report, out);
			out.close();
			System.out.println("excel导出成功！");
			url = "/report/" + fileName + ".xls";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}

	public void insertKeepAliveLocation(KeepAliveLocation kal) {
		inspectAdminMapper.insertKeepAliveLocation(kal);

	}

	public int getTotalWarningCount(String username, long startTime, long endTime, String regionMayDefault) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		return reportMapper.getTotalCount(username, timeStartTS, timeEndTS, regionMayDefault);

	}

	/**
	 * 巡检范围报表
	 */

	public List<Map<String, Object>> getInspectReport(String province, String city, long startTime, long endTime) {

		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		List<InspectNumber> rawList = null;
		synchronized (this) {
			// 保护inspect_report_tmp数据表的一致性
			rawList = reportMapper.selectInspectNumber(province, city, timeStartTS, timeEndTS);
		}
		if (rawList == null)
			return null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String preInspector = null;
		String preBuilding = null;
		for (InspectNumber in : rawList) {
			if (in == null)
				continue;
			String inspector = in.getInspector();
			String building = in.getBuildingName();
			if (inspector == null || building == null)
				continue;
			// 把同一个人,同一栋楼的记录放在同一行
			if (preInspector == null || !preInspector.equals(inspector) || preBuilding == null
					|| !preBuilding.equals(building)) {
				// 加入一个新行
				preInspector = inspector;
				preBuilding = building;
				Map<String, Object> newItem = new HashMap<String, Object>();
				// 加入3列
				newItem.put("inspector", inspector);
				newItem.put("buildingName", building);
				newItem.put(in.getDate_string(), in.getNumber());
				list.add(newItem);
			} else {
				if (list.size() == 0)
					continue;
				// size -1 >=0
				Map<String, Object> sameItem = list.get(list.size() - 1);
				sameItem.put(in.getDate_string(), in.getNumber());
			}

		}
		return list;

	}

	public List<Map<String, Object>> getInspectTimesReport(String province, String city, long startTime, long endTime) {

		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		List<InspectTimes> rawList = null;
		synchronized (this) {
			// 保护inspect_report_tmp数据表的一致性
			rawList = reportMapper.selectInspectTimes(province, city, timeStartTS, timeEndTS);
		}
		if (rawList == null)
			return null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String preInspector = null;
		String preBuilding = null;
		for (InspectTimes in : rawList) {
			if (in == null)
				continue;
			String inspector = in.getInspector();
			String building = in.getBuildingName();
			if (inspector == null || building == null)
				continue;
			// 把同一个人的记录放在同一行
			if (preInspector == null || !preInspector.equals(inspector) || preBuilding == null
					|| !preBuilding.equals(building)) {
				// 加入一个新行
				preInspector = inspector;
				preBuilding = building;
				Map<String, Object> newItem = new HashMap<String, Object>();
				// 加入3列
				newItem.put("inspector", inspector);
				newItem.put("buildingName", building);
				newItem.put(in.getDate_string(), in.getDuration() + "分钟， 共" + in.getTimes() + "次巡检");
				list.add(newItem);
			} else {
				if (list.size() == 0)
					continue;
				// size -1 >=0
				Map<String, Object> sameItem = list.get(list.size() - 1);
				sameItem.put(in.getDate_string(), in.getDuration() + "分钟， 共" + in.getTimes() + "次巡检");
			}

		}
		return list;

	}

	public List<String> getDateStringList(long startTime, long endTime) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		return reportMapper.selectDateString(timeStartTS, timeEndTS);
	}

	public List<BusinessReport> getBusinessReport(String province, String city, long startTime, long endTime,
			int startPage, int pageSize, int isAll, String mnc, String wpNetwork, String wpRegion) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		String operator = getOperatorNameFromMnc(mnc);
		String[] d = getOperatprAndType(operator);
		String mncList = d[0];
		return reportMapper.getBusinessReport(province, city, timeStartTS, timeEndTS, (startPage - 1) * pageSize,
				pageSize, isAll, mncList, wpNetwork, wpRegion);
	}

	/**
	 * 下载巡检报表
	 *
	 * @param title
	 * @param sheetInf
	 * @param header
	 * @param inspectRageReport
	 * @param inspectTimeReport
	 * @return
	 */
	public String downInspectReport(String title, List sheetInf, String[] header,
			List<Map<String, Object>> inspectRageReport, List<Map<String, Object>> inspectTimeReport) {
		ExportExcel<BusinessReport> ex = new ExportExcel<BusinessReport>();
		String url = "";
		String fileName = "inspectReport" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		try {
			OutputStream out = new FileOutputStream("D://temp/excel/" + fileName + ".xls");
			ex.exportInspectExcel(title, sheetInf, header, inspectRageReport, inspectTimeReport, out);
			out.close();
			System.out.println("excel导出成功！");
			url = "/report/" + fileName + ".xls";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}

	public Map<String, Object> getNewApp() {

		Map<String, Object> newVersion = appMapper.getNewApp();

		return newVersion;
	}

	public List<InspectReport> getInspectReport(String province, String city, long startTime, long endTime,
			int startPage, int pageSize, int isAll, int buildingId) {
		// TODO Auto-generated method stub
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		return reportMapper.getInspectReport(buildingId, timeStartTS, timeEndTS, (startPage - 1) * pageSize, pageSize,
				isAll);
	}

	/**
	 * 下载巡检报表
	 */
	public String downInspectReport(String title, List sheetInf, String[] headers, List<InspectReport> Report) {
		ExportExcel<InspectReport> ex = new ExportExcel<InspectReport>();
		String url = "";
		String fileName = "inspectReport" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		try {
			OutputStream out = new FileOutputStream("D://temp/excel/" + fileName + ".xls");
			ex.exportExcel(title, sheetInf, headers, Report, out);
			out.close();
			System.out.println("excel导出成功！");
			url = "/report/" + fileName + ".xls";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}

	public List<Map<String, Object>> getCidCleanTable(long startTime, long endTime, int floor, int buildingId,
			String network, String mnc, String province, String city) {

		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		String[] d = getOperatprAndType(network);
		String networkType = d[1];
		return neighborMapper.selectCidAndNeighbor(province, city, timeStartTS, timeEndTS, networkType, buildingId,
				floor, mnc);
	}

	public int getUserRole(String username) {
		// TODO Auto-generated method stub
		return userMapper.getRole(username);

	}

	public void insertTrainDatas(List<Map<String, Object>> list, String username) {
		if (list.size() != 0)
			androidMapper.insertTrainDatas(list, username);
	}

	public void insertPositionXY(String marker, String X, String Y) {
		double x = Double.valueOf(X);
		double y = Double.valueOf(Y);
		androidMapper.insertTrainMap(marker, x, y);
	}

	public List<String> getWPRegionList(String province, String city, String networkMnc) {
		String operator = getOperatorNameFromMnc(networkMnc);
		return cellAdminMapper.selectWPRegionList(province, city, operator + "%");
	}

	public List<Map<String, String>> getBuildingRegionList(String province, String city, String regionLike) {
		// String operator = getOperatorNameFromMnc(networkMnc);
		return deviceAdminMapper.selectBuildingRegionList(province, city, regionLike + "%");
	}

	public List<Map<String, Object>> getIdOrBNameLike(String province, String city, String idOrCName) {
		return deviceAdminMapper.selectIdOrBNameLike(province, city, idOrCName);
	}

	public List<Map<String, Object>> getNeighborCurve(long startTime, long endTime, String position, String mnc,
			String network, int buildingId) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		String[] d = getOperatprAndType(network);
		String networkType = d[1];
		List<Map<String, Object>> list = neighborMapper.selectMaxNeighborDifferenceDaily(timeStartTS, timeEndTS,
				position, mnc, networkType, buildingId);
		List<Map<String, Object>> mergedList = new ArrayList<Map<String, Object>>(list.size());
		String preDate = null;
		// int preMainServingCid = -1;
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> item = list.get(i);
			String date = (String) item.get("date");
			// int mainServingCid = (Integer) item.get("mainServingCid");
			// sql结果已经排好序，只需取每组第一个
			if (preDate == null || !preDate.equals(date)) {
				mergedList.add(item);
				preDate = date;
			} else {
				continue;
			}
		}
		return mergedList;
	}

	public List<Map<String, Object>> getNeighborHist(long startTime, long endTime, String position, String mnc,
			String network, int buildingId) {
		Timestamp timeStartTS = new Timestamp(startTime);
		Timestamp timeEndTS = new Timestamp(endTime);
		String[] d = getOperatprAndType(network);
		String networkType = d[1];
		List<Map<String, Object>> list = neighborMapper.selectNeighborHist(timeStartTS, timeEndTS, position, mnc,
				networkType, buildingId);
		return list;
	}

}
