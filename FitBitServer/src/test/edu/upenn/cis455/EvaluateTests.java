package test.edu.upenn.cis455;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import edu.upenn.cis455.xpathengine.XPathEngineImpl;
import junit.framework.TestCase;

/*
 * Tests the evaluate() function on an xml document.  Valid XPaths are
 * passed in, but only half of them match the document.  
 */
public class EvaluateTests extends TestCase {
	public void testEvaluate1() {
		String url = "http://crawltest.cis.upenn.edu/misc/weather.xml";
		int port = 80;
		try {
			URI urlActual = null;
			try {
				urlActual = new URI(url);
			} catch (URISyntaxException e) {
				System.out.println("Url Error");
			}
			
			// Set the port
			String auth = urlActual.getAuthority();
			String path = urlActual.getPath();
			Socket serverConnection = new Socket(InetAddress.getByName(auth), port);
			PrintWriter write = new PrintWriter(serverConnection.getOutputStream());
			write.print("GET " + path + " HTTP/1.1\r\n");
			write.print("Host: " + auth + ":" + port + "\r\n");
			write.println("Connection: close\r\n");
			write.flush();
			
			InputStream in = serverConnection.getInputStream();

			// Parse the document and convert it to a DOM
			Tidy parser = new Tidy();
			parser.setXHTML(true);
			parser.setXmlTags(true);

			Document doc = parser.parseDOM(in, null);
			
			
			String[] paths = {"/dwml/data/time-layout/layout-key[text()=\"k-p24h-n7-1\"][contains(text(), \"n7-\")]"};
			XPathEngineImpl t = new XPathEngineImpl();
			t.setXPaths(paths);
			assertTrue(t.evaluate(doc)[0]);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testEvaluate2() {
		String url = "http://crawltest.cis.upenn.edu/misc/weather.xml";
		int port = 80;
		try {
			URI urlActual = null;
			try {
				urlActual = new URI(url);
			} catch (URISyntaxException e) {
				System.out.println("Url Error");
			}
			
			// Set the port
			String auth = urlActual.getAuthority();
			String path = urlActual.getPath();
			Socket serverConnection = new Socket(InetAddress.getByName(auth), port);
			PrintWriter write = new PrintWriter(serverConnection.getOutputStream());
			write.print("GET " + path + " HTTP/1.1\r\n");
			write.print("Host: " + auth + ":" + port + "\r\n");
			write.println("Connection: close\r\n");
			write.flush();
			
			InputStream in = serverConnection.getInputStream();

			// Parse the document and convert it to a DOM
			Tidy parser = new Tidy();
			parser.setXHTML(true);
			parser.setXmlTags(true);

			Document doc = parser.parseDOM(in, null);
			
			
			String[] paths = {"/dwml/data/extra/time-layout/layout-key[text()=\"k-p24h-n7-1\"][contains(text(), \"n7-\")]"};
			XPathEngineImpl t = new XPathEngineImpl();
			t.setXPaths(paths);
			assertFalse(t.evaluate(doc)[0]);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testEvaluate3() {
		String url = "http://crawltest.cis.upenn.edu/misc/weather.xml";
		int port = 80;
		try {
			URI urlActual = null;
			try {
				urlActual = new URI(url);
			} catch (URISyntaxException e) {
				System.out.println("Url Error");
			}
			
			// Set the port
			String auth = urlActual.getAuthority();
			String path = urlActual.getPath();
			Socket serverConnection = new Socket(InetAddress.getByName(auth), port);
			PrintWriter write = new PrintWriter(serverConnection.getOutputStream());
			write.print("GET " + path + " HTTP/1.1\r\n");
			write.print("Host: " + auth + ":" + port + "\r\n");
			write.println("Connection: close\r\n");
			write.flush();
			
			InputStream in = serverConnection.getInputStream();

			// Parse the document and convert it to a DOM
			Tidy parser = new Tidy();
			parser.setXHTML(true);
			parser.setXmlTags(true);

			Document doc = parser.parseDOM(in, null);
			
			
			String[] paths = {"/dwml/data/time-layout/layout-key[text()=\"k-p24h-n7-1\"][contains(text(), \"n7-c\")]"};
			XPathEngineImpl t = new XPathEngineImpl();
			t.setXPaths(paths);
			assertFalse(t.evaluate(doc)[0]);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testEvaluate4() {
		String url = "http://crawltest.cis.upenn.edu/misc/weather.xml";
		int port = 80;
		try {
			URI urlActual = null;
			try {
				urlActual = new URI(url);
			} catch (URISyntaxException e) {
				System.out.println("Url Error");
			}
			
			// Set the port
			String auth = urlActual.getAuthority();
			String path = urlActual.getPath();
			Socket serverConnection = new Socket(InetAddress.getByName(auth), port);
			PrintWriter write = new PrintWriter(serverConnection.getOutputStream());
			write.print("GET " + path + " HTTP/1.1\r\n");
			write.print("Host: " + auth + ":" + port + "\r\n");
			write.println("Connection: close\r\n");
			write.flush();
			
			InputStream in = serverConnection.getInputStream();

			// Parse the document and convert it to a DOM
			Tidy parser = new Tidy();
			parser.setXHTML(true);
			parser.setXmlTags(true);

			Document doc = parser.parseDOM(in, null);
			
			
			String[] paths = {"/dwml/data/time-layout[@time-coordinate=\"local\"]"};
			XPathEngineImpl t = new XPathEngineImpl();
			t.setXPaths(paths);
			assertTrue(t.evaluate(doc)[0]);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
