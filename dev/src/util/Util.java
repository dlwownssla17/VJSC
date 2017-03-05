package util;

import java.util.Iterator;

public class Util {
	public static boolean isNumeric(String s) {
		if (s == null || s.isEmpty()) { return false; }
		for (int i = 0; i < s.length(); i++) {
			int diff = s.charAt(i) - '0';
			if (diff < 0 || diff > 9) { return false; }
		}
		return true;
	}
	
	public static boolean containsNull(@SuppressWarnings("rawtypes") Iterable collec) {
		@SuppressWarnings("rawtypes")
		Iterator iter = collec.iterator();
		if (iter == null) { throw new RuntimeException("containsNull: iter is null"); }
		while (iter.hasNext()) {
			if (iter.next() == null) { return true; }
		}
		return false;
	}
}
