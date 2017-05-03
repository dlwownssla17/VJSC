package test.edu.upenn.cis455;

import edu.upenn.cis455.crawler.DatabaseProcessor;
import junit.framework.TestCase;

/*
 * Tests for the database wrapper class
 */
public class DatabaseTests extends TestCase {
	public void testDatabase1() {
		DatabaseProcessor db = new DatabaseProcessor("/home/cis455/workspace/HW2/Database/");
		assertTrue(db.isValid());
		db.finish();
	}
	
	public void testDatabase2() {
		DatabaseProcessor db = new DatabaseProcessor("/home/cis455/workspace/HW2/Database/");
		assertTrue(db.isValid());
		db.addAccount("Bob", "hello");
		assertTrue(db.isValidUser("Bob", "hello"));
		db.finish();
	}
	
	public void testDatabase3() {
		DatabaseProcessor db = new DatabaseProcessor("/home/cis455/workspace/HW2/Database/");
		assertTrue(db.isValid());
		db.addAccount("Bob", "hello");
		assertTrue(db.isValidUser("Bob", "hello"));
		db.finish();
		DatabaseProcessor db2 = new DatabaseProcessor("/home/cis455/workspace/HW2/Database/");
		assertTrue(db2.isValid());
		assertTrue(db2.isValidUser("Bob", "hello"));
		db2.finish();
	}
	
	public void testDatabase4() {
		DatabaseProcessor db = new DatabaseProcessor("/home/cis455/workspace/HW2/Database/");
		assertTrue(db.isValid());
		db.addChannel("test", "/file[@test=\"something\"]", "test.xsl", "Bob");
		System.out.println(db.getChannelNames().get(0));
		assertTrue(db.getChannelNames().get(0).equals("test"));
		db.finish();
	}
}
