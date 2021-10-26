package bs.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EntryUtil implements Serializable {
	private static final long serialVersionUID = 4697743635389319682L;

	private EntryUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	/**
	 * Returns entry length
	 * 
	 */
	public static Integer entryLength(String[] entry) {
		Integer length = 0;
		try {
			
			if(ValidatorUtil.isNotEmpty(entry)) {
				length = entry.length;
			}
			
		} catch (Exception e) {
			log.error("UEntry#entryLength error {}", e);
		}
		return length;
	}
	
	/**
	 * Validates if an entry is empty <br>
	 * <b>NOTE:</b> Content begins at position 1 of the entry
	 * 
	 */
	public static boolean isEntryEmpty(String[] entry) {
		boolean isEmpty = false;
		try {
			
			Integer entryLength = entryLength(entry) - 1;
			
			if(entryLength <= 0) {
				return true;
			}
			
			for (int i = 1; i <= entryLength; i ++) {
				isEmpty = entry[i].isEmpty() ? true : false;
				if(!isEmpty) break;
			}
			
		} catch (Exception e) {
			log.error("UEntry#isEntryEmpty error {}", e);
		}
		return isEmpty;
	}
	
	/**
	 * Validates if an entry is empty <br>
	 * 
	 */
	public static boolean isEntryFullEmpty(String[] entry){
		boolean isEmpty = false;
		try {
			
			Integer entryLength = entryLength(entry);
			
			if(entryLength <= 0) {
				return true;
			}
			
			for (int i = 0; i <= entryLength; i ++) {
				isEmpty = entry[i].isEmpty() ? true : false;
				if(!isEmpty) break;
			}
			
		} catch (Exception e) {
			log.error("UEntry#isEntryFullEmpty error {}", e);
		}
		return isEmpty;
	}
	
	/**
	 * Deletes empty entries
	 * 
	 */
	public static String[] cleanerEntryByPipe(String[] entries) {
		try {
			
			if(ValidatorUtil.isNotEmpty(entries)) {
				if(entries.length > 0) {
					val target = Stream.of(entries)
									   .collect(Collectors.toList())
									   .stream()
									   .map(ValueUtil::str)
									   .filter(ValidatorUtil::isNotEmpty)
									   .map(i -> i.split(Pattern.quote("|")))
									   .filter(i -> isEntryEmpty(i))
									   .toArray(String[]::new);
					return target;
				}
			}
			
		} catch (Exception e) {
			log.error("UEntry#cleanerEntryByPipe error {}", e);
		}
		return null;
	}
	
	/**
	 * Converts List to String[]
	 * 
	 */
	public static String[] listToEntryConverter(List<String> sourceList) {
		try {
			
			return sourceList.toArray(new String[0]);
			
		} catch (Exception e) {
			log.error("UEntry#listToEntryConverter error {}", e);
		}
		return null;
	}
	
	/**
	 * Converts String[] to List
	 * 
	 */
	public static List<String> entryToListConverter(String[] source) {
		try {
			
			if(ValidatorUtil.isNotEmpty(source)) {
				return new ArrayList<>(Arrays.asList(source));
			}
			
		} catch (Exception e) {
			log.error("UEntry#entryToListConverter error {}", e);
		}
		return null;
	}
	
	/**
	 * Converts String[] to String
	 * 
	 */
	public static String entryToStringConverter(String[] entry, String delimiter) {
		try {
			
			if(ValidatorUtil.isNotEmpty(entry)) {
				if(ValidatorUtil.isEmpty(delimiter)) {
					delimiter = "\n";
				}
				
				return String.join(delimiter, entry);
			}
			
		} catch (Exception e) {
			log.error("UEntry#entryToStringConverter error {}", e);
		}
		return null;
	}
}
