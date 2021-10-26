package bs.common.test.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import lombok.val;
import bs.common.util.ListUtil;

/**
 * {@link ListUtil} unit test
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@TestInstance(value = Lifecycle.PER_CLASS)
class ListUtilTest {

	@Test
	void listToStringTest1() {
		val l = List.of("home", "sweet", "home");
		val result = ListUtil.toString(l);
		assertEquals("home,sweet,home", result);
	}
	
	@Test
	void listToStringTest2() {
		val l = List.of("home", "sweet", "home");
		val delimiter = "|";
		val result = ListUtil.toString(l, delimiter);
		assertEquals("home|sweet|home", result);
	}
	
	@Test
	void stringToListTest1() {
		val source = "a,b,c,d,e";
		val l = ListUtil.toList(source);
		assertEquals(5, l.size());
	}
	
	@Test
	void stringToListTest2() {
		val source = "a|b|c|d|e";
		val delimiter = "|";
		val l = ListUtil.toList(source, delimiter);
		assertEquals(5, l.size());
	}
	
	@Test
	void arrayToListTest() {
		val arr = new String[] {"a", "b", "c"};
		val l = ListUtil.toList(arr);
		assertEquals(3, l.size());
	}
	
	@Test
	void listToArryTest() {
		val l = List.of("a", "b", "c");
		val arr = ListUtil.toArray(l);
		assertEquals(3, arr.length);
	}
	
	@Test	
	void concatMultipleTest() {
		val l1 = List.of(1, 2);
		val l2 = List.of(3, 4);
		val l3 = List.of(5, 6);
		val result = ListUtil.concat(l1, l2, l3); // [1, 2, 3, 4, 5, 6]
		assertEquals(6, result.size());
	}
	
	@Test	
	void concatTest() {
		val l1 = List.of(1, 2, 1);
		val l2 = List.of(4, 5, 3);
		val result = ListUtil.concat(l1, l2); // [1, 2, 1, 4, 5, 3]
		assertEquals(6, result.size());
	}
	
	@Test
	void meregeMultipleTest() {
		val l1 = List.of(1, 2, 1, 4, 5);
		val l2 = List.of(1, 1, 3, 4, 1);
		val l3 = List.of(1, 2, 3, 5, 1);
		val l4 = List.of(6, 1, 3, 4, 1);
		val result = ListUtil.merge(l1, l2, l3, l4); // [1, 2, 3, 4, 5, 6]
		assertEquals(6, result.size());
	}
	
	@Test
	void mergeTest() {
		val l1 = List.of(1, 2, 1, 4, 5);
		val l2 = List.of(1, 1, 3, 4, 1);
		val result = ListUtil.merge(l1, l2); // [1, 2, 3, 4, 5]
		assertEquals(5, result.size());
	}
	
	@Test
	void intersectionTest() {
		val l1 = List.of(1, 2, 1, 4, 5);
		val l2 = List.of(1, 1, 3, 4, 1);
		val result = ListUtil.intersection(l1, l2); // [1, 4]
		assertEquals(2, result.size());
	}
	
	@Test
	void differenceTest() {
		val l1 = List.of(1, 2, 1, 4, 5);
		val l2 = List.of(1, 1, 3, 4, 1);
		val result = ListUtil.difference(l1, l2); // [2, 5]
		assertEquals(2, result.size());
	}
	
	@Test
	void fullDifferenceTest() {
		val l1 = List.of(1, 2, 1, 4, 5);
		val l2 = List.of(1, 1, 3, 4, 1);
		val result = ListUtil.fullDifference(l1, l2); // [2, 3, 5]
		assertEquals(3, result.size());
	}
	
}