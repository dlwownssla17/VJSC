package test.edu.upenn.cis455;

import edu.upenn.cis455.servlet.XPathServletHandler;
import edu.upenn.cis455.xpathengine.XPathEngineImpl;
import junit.framework.TestCase;

/*
 * These tests test the functionality of the content-type detection of the 
 * servlet handler.  This is used to initialize the correct version of Tidy 
 * and to throw an error for invalid types.  
 */
public class ServletTest extends TestCase {
	public void testServlet1() {
		XPathServletHandler h = new XPathServletHandler();
		String content = h.getContentType("Content-Type: text/html");
		assertTrue(content.equals("text/html"));
	}
	
	public void testServlet2() {
		XPathServletHandler h = new XPathServletHandler();
		String content = h.getContentType("Content-Type: text/xml");
		assertTrue(content.equals("text/xml"));
	}
	
	public void testServlet3() {
		XPathServletHandler h = new XPathServletHandler();
		String content = h.getContentType("Content-Type: text/xml;ISO-8859-1");
		assertTrue(content.equals("text/xml"));
	}
	
	public void testServlet4() {
		XPathServletHandler h = new XPathServletHandler();
		String content = h.getContentType("Content-Type: text/html;ISO-8859-1");
		assertTrue(content.equals("text/html"));
	}
	
	public void testServlet5() {
		XPathServletHandler h = new XPathServletHandler();
		String content = h.getContentType("Content-Type: text/plain;ISO-8859-1");
		assertTrue(content.equals("text/plain"));
	}
}
