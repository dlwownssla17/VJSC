package util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import util.Util;

public class UtilTest {

	@Test
	public void isNumericTrue() {
		assertTrue(Util.isNumeric("2312"));
	}
	
	@Test
	public void isNumericFalse() {
		assertFalse(Util.isNumeric("23 12"));
	}
	
	@Test
	public void containsNullTrue() {
		List<Integer> l = new ArrayList<>();
		l.add(1);
		l.add(2);
		l.add(null);
		System.out.println(Util.containsNull(l));
	}
	
	@Test
	public void containsNullFalse() {
		List<Integer> l = new ArrayList<>();
		l.add(1);
		l.add(2);
		System.out.println(!Util.containsNull(l));
	}

}
