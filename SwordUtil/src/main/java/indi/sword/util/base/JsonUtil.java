package indi.sword.util.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.Map.Entry;
 
/**
 * @ClassName JsonFormateUtil  
 * @Description 辅助转化Json，防止出现空指针
 * @author WangYanan 347576073@qq.com  
 * @date 2015年6月25日 上午11:31:39 
 */
public class JsonUtil {

	public static Date getDate(JsonObject JO,String name){
		
		Date value;
		try{
			value = DateUtils.toTime(JO.get(name).getAsString());
			return value;
		} catch(Exception e) {
			System.out.println("变量"+name+"为空");
			return null;
		}
	}
	
	public static Integer getInteger(JsonObject JO,String name){
		Integer value; 
		try{
			value = JO.get(name).getAsInt();
			return value;
		} catch(Exception e) {
			System.out.println("变量"+name+"为空");
			return null;
		}
	}

	public static String getString(JsonObject JO,String name){
		String value; 
		try{
			value = JO.get(name).getAsString();
			return value;
		} catch(Exception e) {
			System.out.println("变量"+name+"为空");
			return null;
		}
	}	

	/**
	 * @param jsonElement
	 */
	public static Map<String, Object> convertJsonToMap(JsonElement jsonElement) {
		Map<String, Object> result_map = new HashMap<>();
		if (jsonElement.isJsonObject()) {
			JsonObject JO = jsonElement.getAsJsonObject();
			Set<Entry<String, JsonElement>> entrySet = JO.entrySet();

			for (Entry<String, JsonElement> entry : entrySet) {
				JsonElement value = entry.getValue();
				if (value.isJsonNull()) {
					continue;
				}
				if (value.isJsonObject()) {
					result_map.put(entry.getKey(), convertJsonToMap(value));
				} else if (value.isJsonArray()) {
					JsonArray json_array = value.getAsJsonArray();
					List<Object> list = new ArrayList<>();
					for (JsonElement json_element_in_array : json_array) {
						list.add(convertJsonToMap(json_element_in_array));
					}
					result_map.put(entry.getKey(), list);
				} else {
					try {
						result_map.put(entry.getKey(), value.getAsString());
					} catch (Exception e) {
						System.out.println(value.toString());
						e.printStackTrace();
					}
				}
			}
		}
		return result_map;
	}
	
	private final static ObjectMapper om = new ObjectMapper();

    public static <E> Map<String, Object> objectToMap(E e) {
        return om.convertValue(e, Map.class);
    }

    public static <E> E mapToObject(Map map, Class<E> clazz) {
        return om.convertValue(map, clazz);
    }

    public static <E> E readValue(String string, TypeReference<E> typeReference) throws IOException {
        return om.readValue(string, typeReference);
    }

    /**
	 * 通过Json 格式的str 返回 相应的对象
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @param clazz 
	 * @Date Oct 20, 2017 8:32:43 PM
	 */
	private static <clazz> clazz getResultByJsonStr(String jsonStr, Class<?> clazz) {
		Object result = null;
		try {
			Gson gson = new Gson();
			if (!StringUtils.isEmpty(jsonStr) && !jsonStr.equals("ERROR")) {
				JsonElement JE = null;
				JsonReader reader;
				reader = new JsonReader(new StringReader(jsonStr));
				reader.setLenient(true);
				JE = gson.fromJson(reader, JsonElement.class);
				if (null != JE && JE.isJsonObject()) {
					result =  gson.fromJson(JE, clazz);
				}
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (clazz)result;
	}
	
	/**
	 * 通过Json格式的Object 返回 相应的对象
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @param clazz 
	 * @Date Oct 20, 2017 8:32:43 PM
	 */
	public static <clazz> clazz getResultByJsonObject(Object object, Class<?> clazz) {
		Object result = null;
		try {
			String str = null;
			if(!(object instanceof String)){
				Gson gson = new Gson();
				str = gson.toJson(object);
			}else{
				str = (String)object;
			}
			result = getResultByJsonStr(str,clazz);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (clazz)result;
	}
	public static void main(String[] args){
	}
}
