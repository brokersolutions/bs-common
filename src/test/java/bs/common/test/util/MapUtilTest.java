package bs.common.test.util;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import bs.common.util.MapUtil;
import lombok.val;

/**
 * {@link MapUtil} unit test
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@TestInstance(value = Lifecycle.PER_CLASS)
class MapUtilTest {
	
	@Test
	void mergeTwoMapsTest1() {
		val map1 = Map.of("k1", 1, "k2", 2, "k3", 3);
		val map2 = Map.of("k4", 4, "k5", 5, "k6", 6);
		val spectedMap = MapUtil.merge(map1, map2);
		
		assertEquals(1, spectedMap.get("k1"));
		assertEquals(5, spectedMap.get("k5"));
	}
	
	@Test
	void mergeTwoMapsTest2() {
		val map1 = Map.of("k1", 1, "k2", 2, "k3", 3);
		val map2 = Map.of("k1", 4, "k2", 5, "k3", 6);
		val spectedMap = MapUtil.merge(map1, map2);
		
		assertEquals(4, spectedMap.get("k1"));
		assertEquals(5, spectedMap.get("k2"));
		assertEquals(6, spectedMap.get("k3"));
	}
	
	@Test
	void mergeTwoMapsTest3() {
		val map1 = Map.of("k1", 1, "k2", 2, "k3", 3);
		val spectedMap = MapUtil.merge(map1, null);
		
		assertEquals(1, spectedMap.get("k1"));
		assertEquals(2, spectedMap.get("k2"));
		assertEquals(3, spectedMap.get("k3"));
	}
	
	@Test
	void mergeTwoMapsTest4() {
		val map2 = Map.of("k1", 4, "k2", 5, "k3", 6);
		val spectedMap = MapUtil.merge(null, map2);
		
		assertEquals(4, spectedMap.get("k1"));
		assertEquals(5, spectedMap.get("k2"));
		assertEquals(6, spectedMap.get("k3"));
	}
	
	@Test
	void mergeTwoMapsTest5() {
		val spectedMap = MapUtil.merge(null, null);
		assertTrue(spectedMap.isEmpty());
	}
	
	@Test
	void mergeMultiplesMapsTest1() {
		val map1 = Map.of("k1", 1, "k2", 2, "k3", 3);
		val map2 = Map.of("k4", 4, "k5", 5, "k6", 6);
		val map3 = Map.of("k7", 7, "k8", 8, "k9", 9);
		val spectedMap = MapUtil.merge(map1, map2, map3);
		
		assertEquals(1, spectedMap.get("k1"));
		assertEquals(5, spectedMap.get("k5"));
		assertEquals(9, spectedMap.get("k9"));
	}
	
	@Test
	void mergeMultiplesMapsTest2() {
		val map1 = Map.of("k1", 1, "k2", 2, "k3", 3);
		val map2 = Map.of("k1", 4, "k2", 5, "k3", 6);
		val map3 = Map.of("k1", 7, "k2", 8, "k3", 9);
		val spectedMap = MapUtil.merge(map1, map2, map3);
		
		assertEquals(7, spectedMap.get("k1"));
		assertEquals(8, spectedMap.get("k2"));
		assertEquals(9, spectedMap.get("k3"));
	}
	
	@Test
	void mergeMultiplesMapsTest3() {
		val map1 = Map.of("k1", 1, "k2", 2, "k3", 3);
		val map3 = Map.of("k7", 7, "k8", 8, "k9", 9);
		val spectedMap = MapUtil.merge(map1, null, map3);
		
		assertEquals(3, spectedMap.get("k3"));
		assertEquals(7, spectedMap.get("k7"));
	}
	
	@Test
	void mergeMultiplesMapsTest4() {
		val spectedMap = MapUtil.merge(null, null, null);
		assertTrue(spectedMap.isEmpty());
	}
	
}