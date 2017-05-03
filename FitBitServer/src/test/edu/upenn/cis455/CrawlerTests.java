package test.edu.upenn.cis455;

import edu.upenn.cis455.crawler.DatabaseProcessor;
import edu.upenn.cis455.crawler.XPathCrawler;
import junit.framework.TestCase;


/*
 * Tests for helper functions in the Crawler
 */
public class CrawlerTests extends TestCase {
	public void testCrawler1() {
		String test = XPathCrawler.processRootURL("http://www.google.com");
		assertTrue(test.equals("http://www.google.com/"));
	}
	
	public void testCrawler2() {
		boolean test = XPathCrawler.isContentTypeValid("text/xml");
		assertTrue(test);
	}
	
	public void testCrawler3() {
		boolean test = XPathCrawler.isContentTypeValid("text/html");
		assertTrue(test);
	}
	
	public void testCrawler4() {
		boolean test = XPathCrawler.isContentTypeValid("text/plain");
		assertFalse(test);
	}
	
	public void testCrawler5() {
		String test = XPathCrawler.getRootURL("http://www.google.com");
		assertTrue(test.equals("http://www.google.com/"));
	}
	
	public void testCrawler6() {
		String test = XPathCrawler.getRootURL("http://www.google.com/hello/more/content.html");
		assertTrue(test.equals("http://www.google.com/"));
	}
}
