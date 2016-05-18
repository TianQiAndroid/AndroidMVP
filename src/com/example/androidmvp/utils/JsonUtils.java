package com.example.androidmvp.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * 不支持数组类型
 * 
 * @author Cao Mingming
 * @date 2016-5-10 16:14:50
 */
public class JsonUtils {

	private final static String TYPE_LIST = "java.util.List";
	private final static String TYPE_STRING = "java.lang.String";
	private final static String TYPE_INTEGER = "java.lang.Integer";
	private final static String TYPE_INT = "int";
	private final static String TYPE_DOUBLE = "java.lang.Double";
	private final static String TYPE_DOUBLE2 = "double";
	private final static String TYPE_FLOAT = "java.lang.Float";
	private final static String TYPE_FLOAT2 = "float";
	private final static String TYPE_LONG = "java.lang.Long";
	private final static String TYPE_LONG2 = "long";
	private final static String TYPE_SHORT = "java.lang.Short";
	private final static String TYPE_SHORT2 = "short";
	private final static String TYPE_BYTE = "java.lang.Byte";
	private final static String TYPE_BYTE2 = "byte";
	private final static String TYPE_CHAR = "java.lang.Char";
	private final static String TYPE_CHAR2 = "char";
	private final static String TYPE_BOOLEAN = "java.lang.Boolean";
	private final static String TYPE_BOOLEAN2 = "boolean";

	private JsonUtils() {

	}

	public static <T> String toJson(T src) {
		return toJson(src, src.getClass());

	}

	/**
	 * 对象转换成json字符串
	 * 
	 * @param src
	 * @param classOfT
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static <T> String toJson(T src, Class classOfT) {
		if (src == null || classOfT == null) {
			return null;
		}
		if (classOfT.getGenericInterfaces() != null
				&& classOfT.getGenericInterfaces().length > 0
				&& classOfT.getGenericInterfaces()[0].toString().contains(
						TYPE_LIST)) {
			// 传递进来的是list集合
			return listToJson(src, classOfT);

		} else {
			// 传递进来的是非集合对象
			return objectToJson(src, classOfT);
		}

	}

	/**
	 * 将集合转换成json字符串
	 * 
	 * @param src
	 * @param classOfT
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static <T> String listToJson(T src, Class classOfT) {
		StringBuffer stringBuffer = new StringBuffer("[");
		List list = (List) src;
		int length = list.size();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(objectToJson(list.get(i), list.get(i)
					.getClass()));
			if (i != length - 1) {
				stringBuffer.append(",");
			}
		}
		stringBuffer.append("]");
		return stringBuffer.toString();

	}

	/**
	 * 将非集合对象转换成json字符串
	 * 
	 * @param src
	 * @param classOfT
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static <T> String objectToJson(T src, Class classOfT) {
		StringBuffer stringBuffer = new StringBuffer("{");
		Field[] fields = classOfT.getDeclaredFields();
		int lengthFields = fields.length;
		for (int i = 0; i < lengthFields; i++) {
			try {
				if (fields[i].get(src) == null) {
					continue;
				}
				stringBuffer.append("\"");
				stringBuffer.append(fields[i].getName());
				stringBuffer.append("\":");

				if (TYPE_STRING.equals(fields[i].getType().getName())) {
					stringBuffer.append("\"");
					stringBuffer.append((String) fields[i].get(src));
					stringBuffer.append("\"");
				} else if (TYPE_INTEGER.equals(fields[i].getType().getName())
						|| TYPE_INT.equals(fields[i].getType().getName())) {
					stringBuffer.append((Integer) fields[i].get(src));
				} else if (TYPE_DOUBLE.equals(fields[i].getType().getName())
						|| TYPE_DOUBLE2.equals(fields[i].getType().getName())) {
					stringBuffer.append((Double) fields[i].get(src));
				} else if (TYPE_FLOAT.equals(fields[i].getType().getName())
						|| TYPE_FLOAT2.equals(fields[i].getType().getName())) {
					stringBuffer.append((Float) fields[i].get(src));
				} else if (TYPE_LONG.equals(fields[i].getType().getName())
						|| TYPE_LONG2.equals(fields[i].getType().getName())) {
					stringBuffer.append((Long) fields[i].get(src));
				} else if (TYPE_SHORT.equals(fields[i].getType().getName())
						|| TYPE_SHORT2.equals(fields[i].getType().getName())) {
					stringBuffer.append((Short) fields[i].get(src));
				} else if (TYPE_BYTE.equals(fields[i].getType().getName())
						|| TYPE_BYTE2.equals(fields[i].getType().getName())) {
					stringBuffer.append((Byte) fields[i].get(src));
				} else if (TYPE_CHAR.equals(fields[i].getType().getName())
						|| TYPE_CHAR2.equals(fields[i].getType().getName())) {
					stringBuffer.append(fields[i].get(src).toString()
							.toCharArray()[0]);
				} else if (TYPE_BOOLEAN.equals(fields[i].getType().getName())
						|| TYPE_BOOLEAN2.equals(fields[i].getType().getName())) {
					stringBuffer.append((Boolean) fields[i].get(src));
				} else {
					// 集合类型
					if (TYPE_LIST.equals(fields[i].getType().getName())) {
						Class clazzGenericType = (Class) ((ParameterizedType) fields[i]
								.getGenericType()).getActualTypeArguments()[0];
						List list = (List) fields[i].get(src);
						stringBuffer.append("[");
						int lengthList = list.size();
						for (int j = 0; j < lengthList; j++) {
							stringBuffer.append(toJson(list.get(j),
									clazzGenericType));
							if (j != lengthList - 1) {
								stringBuffer.append(",");
							}
						}
						stringBuffer.append("]");
					} else {
						// 对象类型
						stringBuffer.append(toJson(fields[i].get(src),
								fields[i].getType()));
					}

				}
			} catch (Exception e) {
				stringBuffer.append("");
			}

			if (i != lengthFields - 1) {
				stringBuffer.append(",");
			}
		}
		if (stringBuffer.charAt(stringBuffer.length() - 1) == ',') {
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		}
		stringBuffer.append("}");
		return stringBuffer.toString();
	}

	/**
	 * 获取value
	 * 
	 * @param key
	 * @param json
	 * @return
	 */
	private static String getValue(String key, String json) {
		int index = json.indexOf(key + ":");
		if (index == -1) {
			return null;
		}
		int length = json.length();
		StringBuffer stringBuffer = new StringBuffer("");
		for (int i = index + key.length() + 1; i < length; i++) {
			if (json.charAt(i) == ',') {
				break;
			} else {
				stringBuffer.append(json.charAt(i));
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * 获取json value
	 * 
	 * @param key
	 * @param json
	 * @return
	 */
	private static String getJsonValue(String key, String json) {
		int index = json.indexOf(key + ":");
		if (index == -1) {
			return null;
		}
		int length = json.length();
		StringBuffer stringBuffer = new StringBuffer("");
		for (int i = index + key.length() + 1; i < length; i++) {
			stringBuffer.append(json.charAt(i));
			if (json.charAt(i) == '}') {
				break;
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * 获取json array value
	 * 
	 * @param key
	 * @param json
	 * @return
	 */
	private static String getJsonAryValue(String key, String json) {
		int index = json.indexOf(key + ":");
		if (index == -1) {
			return null;
		}
		int length = json.length();
		StringBuffer stringBuffer = new StringBuffer("");
		for (int i = index + key.length() + 1; i < length; i++) {
			stringBuffer.append(json.charAt(i));
			if (json.charAt(i) == ']') {
				break;
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * json字符串转集合对象
	 * 
	 * @param json
	 * @param classOfT
	 *            泛型参数的字节码
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List fromJsonList(String json, Class<T> classOfT) {
		List t = null;
		if (isEmpty(json) || classOfT == null) {
			return t;
		}
		try {
			t = new ArrayList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 空json字符串{}
		if (json.length() == 2) {
			return t;
		}

		String[] split = json.substring(1, json.length() - 1).split("},");
		int lengthAry = split.length;
		for (int j = 0; j < lengthAry; j++) {
			String vaule = split[j];
			if (j != lengthAry - 1) {
				vaule += "}";
			}
			t.add(fromJsonObject(vaule, classOfT));
		}

		return t;

	}

	/**
	 * json字符串转非集合对象
	 * 
	 * @param json
	 * @param classOfT
	 *            对象字节码
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T fromJsonObject(String json, Class<T> classOfT) {

		T t = null;
		if (isEmpty(json) || classOfT == null) {
			return t;
		}
		try {
			t = classOfT.newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 空json字符串{}
		if (json.length() == 2) {
			return t;
		}

		String temp = json.substring(1, json.length() - 1);
		temp = temp.replace("\"", "");
		Field[] fields = classOfT.getDeclaredFields();
		int lengthFields = fields.length;
		for (int i = 0; i < lengthFields; i++) {
			try {
				if (TYPE_STRING.equals(fields[i].getType().getName())) {
					String value = getValue(fields[i].getName(), temp);
					if (!isEmpty(value)) {
						fields[i].set(t, value);
					}
				} else if (TYPE_INTEGER.equals(fields[i].getType().getName())
						|| TYPE_INT.equals(fields[i].getType().getName())) {
					String value = getValue(fields[i].getName(), temp);
					if (!isEmpty(value)) {
						fields[i].set(t, Integer.parseInt(value));
					}
				} else if (TYPE_DOUBLE.equals(fields[i].getType().getName())
						|| TYPE_DOUBLE2.equals(fields[i].getType().getName())) {
					String value = getValue(fields[i].getName(), temp);
					if (!isEmpty(value)) {
						fields[i].set(t, Double.parseDouble(value));
					}
				} else if (TYPE_FLOAT.equals(fields[i].getType().getName())
						|| TYPE_FLOAT2.equals(fields[i].getType().getName())) {
					String value = getValue(fields[i].getName(), temp);
					if (!isEmpty(value)) {
						fields[i].set(t, Float.parseFloat(value));
					}
				} else if (TYPE_LONG.equals(fields[i].getType().getName())
						|| TYPE_LONG2.equals(fields[i].getType().getName())) {
					String value = getValue(fields[i].getName(), temp);
					if (!isEmpty(value)) {
						fields[i].set(t, Long.parseLong(value));
					}
				} else if (TYPE_SHORT.equals(fields[i].getType().getName())
						|| TYPE_SHORT2.equals(fields[i].getType().getName())) {
					String value = getValue(fields[i].getName(), temp);
					if (!isEmpty(value)) {
						fields[i].set(t, Short.parseShort(value));
					}
				} else if (TYPE_BYTE.equals(fields[i].getType().getName())
						|| TYPE_BYTE2.equals(fields[i].getType().getName())) {
					String value = getValue(fields[i].getName(), temp);
					if (!isEmpty(value)) {
						fields[i].set(t, Byte.parseByte(value));
					}
				} else if (TYPE_CHAR.equals(fields[i].getType().getName())
						|| TYPE_CHAR2.equals(fields[i].getType().getName())) {
					String value = getValue(fields[i].getName(), temp);
					if (!isEmpty(value)) {
						fields[i].set(t, value.toCharArray()[0]);
					}
				} else if (TYPE_BOOLEAN.equals(fields[i].getType().getName())
						|| TYPE_BOOLEAN2.equals(fields[i].getType().getName())) {
					String value = getValue(fields[i].getName(), temp);
					if (!isEmpty(value)) {
						fields[i].set(t, Boolean.parseBoolean(value));
					}
				} else {

					if (TYPE_LIST.equals(fields[i].getType().getName())) {
						Class clazzGenericType = (Class) ((ParameterizedType) fields[i]
								.getGenericType()).getActualTypeArguments()[0];
						String jsonAry = getJsonAryValue(fields[i].getName(),
								temp);
						if (!isEmpty(jsonAry)) {

							String[] split = jsonAry.substring(1,
									jsonAry.length() - 1).split("},");
							int lengthAry = split.length;
							List list = new ArrayList();
							for (int j = 0; j < lengthAry; j++) {
								String vaule = split[j];
								if (j != lengthAry - 1) {
									vaule += "}";
								}
								list.add(fromJsonObject(vaule, clazzGenericType));
							}
							fields[i].set(t, list);
						}

					} else {
						// 对象类型
						String jsonValue = getJsonValue(fields[i].getName(),
								temp);
						if (!isEmpty(jsonValue)) {
							fields[i].set(
									t,
									fromJsonObject(jsonValue,
											fields[i].getType()));
						}
					}

				}
			} catch (Exception e) {

			}

		}

		return t;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isEmpty(CharSequence str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}
}
