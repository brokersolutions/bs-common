package bs.common.util;

import java.io.Serializable;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapperUtil implements Serializable {
	private static final long serialVersionUID = -906996110484411143L;

	private MapperUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static <T> T readValue(final String dataJson, final Class<T> clazz) {
		val mapper = new ObjectMapper();
		try {

			return mapper.readValue(dataJson, clazz);

		} catch (Exception e) {
			log.info("MapperUtil readValue error: " + e.getMessage());
		}
		return null;
	}
	
}