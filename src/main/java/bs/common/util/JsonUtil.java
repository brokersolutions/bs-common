package bs.common.util;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility which is up to transform json2object or Object2json
 * @author Maikel Guerra Ferrer mguerra@softixx.mx
 * 
 */
@Slf4j
public class JsonUtil implements Serializable {
	private static final long serialVersionUID = 7284025549866452865L;

	private JsonUtil() {
		throw new IllegalStateException("Utility class");
	}
	
    public static boolean isJSONValid(String jsonInString) {
        try {
            
        	final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
            
        } catch (IOException e) {
        	log.error("UJson#isJSONValid error {}", e.getMessage());
        }
        return false;
    }

    public static String serialize(final Object object) {
        try {
        	
        	ObjectMapper objMapper = new ObjectMapper();
            objMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            
            StringWriter sw = new StringWriter();
            objMapper.writeValue(sw, object);
            
            return sw.toString();
        	
		} catch (Exception e) {
			log.error("UJson#serialize error {}", e.getMessage());
		}
    	return null;
    }

    public static String serialize(final Object object, final boolean indent) {
    	try {
    		
    		ObjectMapper objMapper = new ObjectMapper();
            if (indent == true) {
                objMapper.enable(SerializationFeature.INDENT_OUTPUT);
                objMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            }

            StringWriter stringWriter = new StringWriter();
            objMapper.writeValue(stringWriter, object);
            return stringWriter.toString();
    		
		} catch (Exception e) {
			log.error("UJson#serialize error {}", e.getMessage());
		}
        return null;
    }

    public static <T> T jsonToObject(final String content, final Class<T> clazz) {
    	T obj = null;
        try {
        	
        	ObjectMapper objMapper = new ObjectMapper();
            obj = objMapper.readValue(content, clazz);
        	
		} catch (Exception e) {
			log.error("UJson#jsonToObject error {}", e.getMessage());
		}
        return obj;
    }

    public static <T> List<T> jsonToList(String content, Class<T> clazz) {
    	try {
    		
    		ObjectMapper mapper = new ObjectMapper();
    		List<T> list = mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        	//##### Otras variante
    		//List<T> list = Arrays.asList(mapper.readValue(content, clazz));
    		//List<T> list = mapper.readValue(content, new TypeReference<List<T>>(){});
        	return list;
    		
		} catch (Exception e) {
			log.error("UJson#jsonToList error {}", e.getMessage());
		}
    	return new ArrayList<T>();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> T jsonToObjectArray(String content) throws JsonParseException, JsonMappingException, IOException {
        T obj = null;
        try {
        	
        	ObjectMapper mapper = new ObjectMapper();
            obj = (T) mapper.readValue(content, new TypeReference<List>() {});
        	
		} catch (Exception e) {
			log.error("UJson#jsonToObjectArray error {}", e.getMessage());
		}
        return obj;
    }
    
    public static <T> T jsonToObjectArray(String content, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        T obj = null;
        try {
        	
        	ObjectMapper mapper = new ObjectMapper();
            mapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            obj = mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        	
		} catch (Exception e) {
			log.error("UJson#jsonToObjectArray error {}", e.getMessage());
		}
        return obj;
    }
}
    