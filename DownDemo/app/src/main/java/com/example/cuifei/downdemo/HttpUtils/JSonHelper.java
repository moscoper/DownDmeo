package com.example.cuifei.downdemo.HttpUtils;

import android.content.Context;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.util.EncodingUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;


public class JSonHelper {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static void init() {
		objectMapper.configure(
				DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper
				.configure(
						DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
						true);
	}

	public static <T> T DeserializeJsonToObject(Class<T> valueType,
			String rawJsonData) {

		try {

			JsonFactory factory = objectMapper.getFactory();

			JsonParser parse = factory.createParser(rawJsonData);
			T t = parse.readValueAs(valueType);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static <T> T DeserializeJsonToObjectFromAsset(Class<T> valueType,
			String assetsPath, Context context) {

		T result = null;
		try {

			InputStream is = context.getAssets().open(assetsPath);
			StringBuilder sbcf = new StringBuilder();
			byte[] buffer = new byte[1024];
			while (is.read(buffer) != -1) {
				sbcf.append(new String(buffer));
			}

			result = JSonHelper.DeserializeJsonToObject(valueType,
					sbcf.toString());
			is.close();
		} catch (Exception e) {

			e.printStackTrace();
			result = null;
		}

		return result;
	}

	@SuppressWarnings("deprecation")
	public static String SerializeToJson(Object obj) {

		String result = "";
		JsonFactory factory = new JsonFactory();

		StringWriter sw = new StringWriter();
		JsonGenerator generator;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();//向OutPutStream中写入，如 message.writeTo(baos);  
		try {
			generator = factory.createGenerator(baos, JsonEncoding.UTF8);
			objectMapper.writeValue(generator, obj);

			result = baos.toString();  

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static boolean SaveToFile(String fileName, Object value) {
		return SaveToFile(null, fileName, value);
	}

	public static boolean SaveToFile(Context c, String fileName,
			Object value) {
		try {

			int i = fileName.lastIndexOf("/");
			String folderName = fileName.substring(0, i);

			File folderFile = new File(folderName);
			if (!folderFile.exists()) {
				folderFile.mkdirs();
			}

			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = new FileOutputStream(file);

			objectMapper.writeValue(fos, value);

			fos.close();

			return true;
		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}
	}
	
	public static <T> T LoadFromFile(String fileName,
			Class<T> valueType) {
		return LoadFromFile(null, fileName, valueType);
	}

	public static <T> T LoadFromFile(Context context, String fileName,
			Class<T> valueType) {

		try {
			File file = new File(fileName);
			if (!file.exists()) {
				return null;
			}
			FileInputStream fis = new FileInputStream(file);

			int length = fis.available();

			byte[] bytes = new byte[length];
			fis.read(bytes);

			String result = EncodingUtils.getString(bytes, "UTF-8");

			T value = objectMapper.readValue(result, valueType);

			fis.close();

			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> readJsonToList(String json) {
		List<Map<String, Object>> list;
		try {
			list = objectMapper.readValue(json, List.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static JavaType getCollectionType(Class<?> collectionClass,
			Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametricType(
				collectionClass, elementClasses);
	}
}