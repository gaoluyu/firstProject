package androidServer.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import androidServer.bean.HeatData;
import androidServer.mapper.OutdoorMapper;

@Service
public class HeatService {
	@Autowired
	OutdoorMapper outdoorMapper;

	public static int STR_SCALE_TOP_DBM = -60;// -50
	public static int STR_SCALE_BOTTOM_DBM = -110;// -100

	/**
	 * 更新某城市的热力图(heatresult)数据表，同时返回数据
	 * 
	 * @param province
	 * @param city
	 * @return
	 */
	public List<HeatData> updateHeatData(String province, String city) {
		System.out.println("更新热力图数据\n" + province + " " + city);
		List<HeatData> list = outdoorMapper.selectHeatData(province, city);
		if (list.size() == 0)
			return list;
		format(list);
		List<HeatData> result = displayDisposal(list);
		Timestamp updateTime = new Timestamp(System.currentTimeMillis());
		outdoorMapper.updateHeatResult(result, province, city, updateTime);
		return result;
	}

	public List<HeatData> getHeatData(String province, String city) {
		List<HeatData> list = outdoorMapper.selectHeatResult(province, city);
		if (list.size() == 0)
			list = updateHeatData(province, city);
		return list;

	}

	/**
	 * 剔除坏点，并归一化
	 * 
	 * @param list
	 */
	public void format(List<HeatData> list) {
		// find max and min
		int maxValue = Integer.MIN_VALUE;
		int minValue = Integer.MAX_VALUE;

		for (HeatData hd : list) {
			int value = hd.getValue();
			if (isBad(value)) {
				continue;
			}
			if (maxValue < value)
				maxValue = value;
			if (minValue > value)
				minValue = value;
		}

		System.out.println("maxValue:" + maxValue);
		System.out.println("minValue:" + minValue);

		int diff = maxValue - minValue;

		scale(list, maxValue, minValue);

	}

	private boolean isBad(int value) {
		if (value < -200 || value >= 0)
			return true;
		return false;
	}

	public void scale(List<HeatData> datas, int maxValue, int minValue) {
		// dbm=2*asu-113
		// topDbm~100
		// bottomDbm~1
		// (x-bottomDbm)*99/(topDbm-bottomDbm)+1
		int diff = STR_SCALE_TOP_DBM - STR_SCALE_BOTTOM_DBM;
		for (int i = 0; i < datas.size(); i++) {
			HeatData item = datas.get(i);
			if (isBad(item.getValue()))
				bad2Good(item, maxValue, minValue);

			int v = item.getValue();
			// int dbm = 2 * v - 113;
			int dbm = v;
			if (dbm >= STR_SCALE_TOP_DBM) {
				// System.out.println("lager dbm " + dbm);
				v = 100;
			} else if (dbm <= STR_SCALE_BOTTOM_DBM) {
				// System.out.println("smaller dbm " + dbm);
				v = 1;
			} else {

				v = (int) (99 * ((double) dbm - STR_SCALE_BOTTOM_DBM) / diff + 1);
			}
			item.setValue(v);
		}
	}

	private void bad2Good(HeatData data, int max, int min) {
		int good = (int) (Math.random() * (max - min)) + min;
		data.setValue(good);
	}

	public List<HeatData> displayDisposal(List<HeatData> list) {
		List<HeatData> rawDataList = list;
		double minLng = 0, minLat = 0, maxLng = 0, maxLat = 0;
		double width_lng = 0.0002;
		double height_lat = 0.0001;
		double delt_lng = width_lng;// 0.00025;//0.0003
		double delt_lat = height_lat;// 0.00015;//0.0002

		if (rawDataList.size() > 0) {
			HeatData first = rawDataList.get(0);
			minLng = maxLng = first.lng;
			minLat = maxLat = first.lat;
		}
		for (HeatData data : rawDataList) {
			if (minLng > data.lng)
				minLng = data.lng;
			if (maxLng < data.lng)
				maxLng = data.lng;
			if (minLat > data.lat)
				minLat = data.lat;
			if (maxLat < data.lat)
				maxLat = data.lat;
		}

		minLng -= width_lng / 2;
		minLat -= height_lat / 2;
		maxLng += width_lng / 2;
		maxLat += height_lat / 2;

		// this.aggregate();
		HashMap<String, ArrayList<HeatData>> table = new HashMap<String, ArrayList<HeatData>>();
		for (int i = 0; i < rawDataList.size(); i++) {
			double longitude = rawDataList.get(i).lng;
			double latitude = rawDataList.get(i).lat;
			String coord = getCenterIdToString(longitude, latitude, minLng, minLat, delt_lng, delt_lat);
			if (table.containsKey(coord)) {
				// area is already not empty
				ArrayList<HeatData> _list = table.get(coord);
				_list.add(rawDataList.get(i));
			} else {
				ArrayList<HeatData> _list = new ArrayList<HeatData>();
				_list.add(rawDataList.get(i));
				table.put(coord, _list);
			}
		}
		Set<Map.Entry<String, ArrayList<HeatData>>> entrySet = table.entrySet();
		Iterator<Map.Entry<String, ArrayList<HeatData>>> iter = entrySet.iterator();
		List<HeatData> out = new ArrayList<HeatData>();
		for (; iter.hasNext();) {
			Map.Entry<String, ArrayList<HeatData>> entry = iter.next();
			String coord = entry.getKey();
			ArrayList<HeatData> _list = entry.getValue();
			String[] elements = coord.split(",");
			double[] coordDouble = getCenter(Long.parseLong(elements[0]), Long.parseLong(elements[1]), minLng, minLat,
					delt_lng, delt_lat);
			int value = 0;
			for (HeatData d : _list) {
				value += d.value;
			}
			value = value / _list.size();
			out.add(new HeatData(coordDouble[0], coordDouble[1], value));
		}
		return out;
	}

	/*
	 * return string ，order to calculate hash
	 */
	private String getCenterIdToString(double longitude, double latitude, double minLng, double minLat, double delt_lng,
			double delt_lat) {
		long[] longs = getCenterId(longitude, latitude, minLng, minLat, delt_lng, delt_lat);
		return new String(longs[0] + "," + longs[1]);

	}

	private long[] getCenterId(double longitude, double latitude, double minLng, double minLat, double delt_lng,
			double delt_lat) {
		long dLong = (long) ((longitude - minLng) / delt_lng);
		long dLat = (long) ((latitude - minLat) / delt_lat);
		return new long[] { dLong, dLat };
	}

	/*
	 * return new double[]{longitude,latitude};
	 */
	private double[] getCenter(long dLong, long dLat, double minLng, double minLat, double delt_lng, double delt_lat) {
		double longitudeMin = minLng + delt_lng * dLong;
		double longitudeMax = longitudeMin + delt_lng;
		double latitudeMin = minLat + delt_lat * dLat;
		double latitudeMax = latitudeMin + delt_lat;
		return new double[] { (longitudeMin + longitudeMax) / 2, (latitudeMin + latitudeMax) / 2 };
	}

	/**
	 * 获取地标的中心坐标
	 * @param province
	 * @param city
	 * @return
	 */
	public Map<String, Object> getCenterCoordinate(String province, String city) {
		return outdoorMapper.selectCenterCoordinate(province, city);
	}

}
