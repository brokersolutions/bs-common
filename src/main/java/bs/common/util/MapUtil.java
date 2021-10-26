package bs.common.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import one.util.streamex.EntryStream;

/**
 * Utility class to operate with a {@code Map}
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
public class MapUtil implements Serializable {
	private static final long serialVersionUID = -7360511403340200538L;

	private MapUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	/**
	 * Merge two {@code java.util.Map<K, V>} using {@code StreamEx} <br><br>
	 * If map1 and map2 are empty or null, returns {@code Collections.emptyMap()}<br>
	 * If map1 is null or empty, return map2<br>
	 * If map2 is null or empty, return map1<br>
	 * @param <K> Generic key
	 * @param <V> Generic value
	 * @param map1 {@code Map<K, V>}
	 * @param map2 {@code Map<K, V>}
	 * @return the result of merging map1 with map2
	 */
	public static <K, V> Map<K, V> merge(Map<K, V> map1, Map<K, V> map2) {
		if(ValidatorUtil.isEmpty(map1) && ValidatorUtil.isEmpty(map2)) {
			return Collections.emptyMap();
		}
		
		if(ValidatorUtil.isEmpty(map1)) {
			return map2;
		}
		
		if(ValidatorUtil.isEmpty(map2)) {
			return map1;
		}

		return EntryStream.of(map1)
				  		  .append(EntryStream.of(map2))
				  		  .toMap((e1, e2) -> e2);
	}
	
	/**
	 * Merge multiples {@code java.util.Map<K, V>}
	 * 
	 * @param <K> Generic key
	 * @param <V> Generic value
	 * @param maps {@code Map<K, V>...}
	 * @return the result of merging the maps provided
	 */
	@SafeVarargs
	public static <K, V> Map<K, V> merge(Map<K, V>... maps) {
		return Stream.of(maps)
					 .filter(Objects::nonNull)
					 .flatMap(m -> m.entrySet().stream())
					 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2));
	}
	
}