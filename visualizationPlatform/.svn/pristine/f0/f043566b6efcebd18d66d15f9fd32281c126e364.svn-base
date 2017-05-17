package androidServer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class StatisticService {

	private static final int GET_MAX = 1;
	private static final int GET_MIN = 2;

	/**
	 * in order to draw 3 cdf line on one plot,this function is needed to align
	 * data the input list has already been sorted
	 * 
	 * @param cdfList_2G
	 * @param cdfList_3G
	 * @param cdfList_4G
	 */
	@SuppressWarnings("unchecked")
	public void cdfAlignment(Map<String, Object> data) {

		List<Map<String, Object>> _2G = (List<Map<String, Object>>) data.get("cdfList_2G");
		List<Map<String, Object>> _3G = (List<Map<String, Object>>) data.get("cdfList_3G");
		List<Map<String, Object>> _4G = (List<Map<String, Object>>) data.get("cdfList_4G");

		int min2G, min3G, min4G;
		int max2G, max3G, max4G;
		min2G = getFirstOrLastInCDFList(_2G, GET_MIN);
		max2G = getFirstOrLastInCDFList(_2G, GET_MAX);
		min3G = getFirstOrLastInCDFList(_3G, GET_MIN);
		max3G = getFirstOrLastInCDFList(_3G, GET_MAX);
		min4G = getFirstOrLastInCDFList(_4G, GET_MIN);
		max4G = getFirstOrLastInCDFList(_4G, GET_MAX);

		int min = min2G < min3G ? (min2G < min4G ? min2G : min4G) : (min3G < min4G ? min3G : min4G);
		int max = max2G > max3G ? (max2G > max4G ? max2G : max4G) : (max3G > max4G ? max3G : max4G);

		_2G = fillMinElement(_2G, min);
		_2G = fillMaxElement(_2G, max);

		_3G = fillMinElement(_3G, min);
		_3G = fillMaxElement(_3G, max);

		_4G = fillMinElement(_4G, min);
		_4G = fillMaxElement(_4G, max);

		data.put("cdfList_2G", _2G);
		data.put("cdfList_3G", _3G);
		data.put("cdfList_4G", _4G);
	}

	/**
	 * 返回最前或最后的元素的信号强度值
	 * 
	 * @param list
	 * @param type
	 * @return
	 */
	private int getFirstOrLastInCDFList(List<Map<String, Object>> list, int type) {
		int returnDefault = type == GET_MAX ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		if (list == null || list.size() == 0)
			return returnDefault;
		if (type == GET_MIN)
			return (Integer) list.get(0).get("signalStrength");
		if (type == GET_MAX)
			return (Integer) list.get(list.size() - 1).get("signalStrength");
		return 0;
	}

	private List<Map<String, Object>> fillMinElement(List<Map<String, Object>> list, int min) {
		if (list == null || list.size() == 0 || min == Integer.MAX_VALUE)
			return list;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int firstValue = (int) list.get(0).get("signalStrength");
		while (min++ < firstValue) {
			Map<String, Object> alignElement = new HashMap<String, Object>();
			alignElement.put("signalStrength", min);
			alignElement.put("per", 0);
			result.add(alignElement);
		}
		result.addAll(list);
		return result;
	}

	private List<Map<String, Object>> fillMaxElement(List<Map<String, Object>> list, int max) {
		if (list == null || list.size() == 0 || max == Integer.MIN_VALUE)
			return list;

		int lastValue = (int) list.get(list.size() - 1).get("signalStrength");
		while (max > lastValue++) {
			Map<String, Object> alignElement = new HashMap<String, Object>();
			alignElement.put("signalStrength", lastValue);
			alignElement.put("per", 1);
			list.add(alignElement);
		}
		return list;
	}

}
