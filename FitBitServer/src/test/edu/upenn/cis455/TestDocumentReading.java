package test.edu.upenn.cis455;

import edu.upenn.cis455.crawler.PageInformation;
import junit.framework.TestCase;

/*
 * Tests for the crawler parsing documents
 */
public class TestDocumentReading extends TestCase {
	public void testDocument1() {
		PageInformation p = new PageInformation("http://crawltest.cis.upenn.edu/");
		p.orriginalUrls.add("/test/");
		p.makeLinksAbsolute();
		p.addForwardSlashForDirectories();
		assertTrue(p.urls.get(0).equals("http://crawltest.cis.upenn.edu/test/"));
	}
	
	public void testDocument2() {
		PageInformation p = new PageInformation("http://crawltest.cis.upenn.edu/more/");
		p.orriginalUrls.add("test/");
		p.makeLinksAbsolute();
		p.addForwardSlashForDirectories();
		assertTrue(p.urls.get(0).equals("http://crawltest.cis.upenn.edu/more/test/"));
	}
	
	public void testDocument3() {
		PageInformation p = new PageInformation("http://crawltest.cis.upenn.edu/more/");
		p.orriginalUrls.add("/test/");
		p.makeLinksAbsolute();
		p.addForwardSlashForDirectories();
		assertTrue(p.urls.get(0).equals("http://crawltest.cis.upenn.edu/test/"));
	}
}
