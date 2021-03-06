package androidServer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
	public static final ObjectMapper mapper = new ObjectMapper();

	public static <T> List<T> jsonToJavaList(String json, Class<?> tClass) {
		JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, tClass);
		List<T> returnValue = null;
		try {
			returnValue = mapper.readValue(json, type);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	public static <T> T jsonToObject(String json, Class<?> tClass) {
		ObjectMapper mapper = new ObjectMapper();
		T o = null;
		try {
			o = (T) mapper.readValue(json, tClass);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	public static String ObjectToJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public static void main(String[] args) {
		String json = "{\"hel\":1}";

		Map<String, Object> map = jsonToObject(json, HashMap.class);

		if (map == null) {
			System.out.println("null");
			return;
		}
		for (String key : map.keySet()) {
			System.out.println(key + " : " + map.get(key));
		}
	}

}
