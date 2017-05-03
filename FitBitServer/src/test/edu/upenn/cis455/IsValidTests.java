package test.edu.upenn.cis455;

import edu.upenn.cis455.xpathengine.XPathEngineImpl;
import junit.framework.TestCase;

/*
 * A collection of tests that check a variety of XPaths.  The first half are valid, and
 * the last half are invalid.  At the end there are also tests for invalid characters in 
 * names based on xml rules.  Nested structures and concatenation is covered.  
 */
public class IsValidTests extends TestCase {
	public void testXPath1() {
		String[] paths = {"/a"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath2() {
		String[] paths = {"/a/b"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath3() {
		String[] paths = {"/a/b/c"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath4() {
		String[] paths = {"/a/b/cs/d/es/f/g/h"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath5() {
		String[] paths = {"/a/b /cs   /d/es/  f   /g/h"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath6() {
		String[] paths = {"/a[s]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath7() {
		String[] paths = {"/a[s][d]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath8() {
		String[] paths = {"/a[s][d][rr]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath9() {
		String[] paths = {"/a[s][d][rr][r][t][y]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath10() {
		String[] paths = {"/a/f/g/t/y[s][d][rr][r][t][y]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath11() {
		String[] paths = {"/test[ a/b1[ c1[p]/d[p] ] /n1[a]/n2 [c2/d[p]/e[text()=\"/asp[&123(123*/]\"]]]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath12() {
		String[] paths = {"/a[b]/c[d]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath13() {
		String[] paths = {"/a[b][t]/c[d]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath14() {
		String[] paths = {"/a[b][t][r]/c[d][y][u]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath15() {
		String[] paths = {"/a[b][t][f]/c[d][y][g][y]/d[t]/f[d][f][r][e]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath16() {
		String[] paths = {"/a/f/g[b][t][f]/c/r/t/y[d][y][g][y]/d[t]/f/dd/rr/t/ee[d][f][r][e]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath17() {
		String[] paths = {"/a/f/g[b][t][f]/c/r/t/y[d][y][g][y]/d[t]/f/dd/rr/t/ee[d][f][r][e]/f"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath18() {
		String[] paths = {"/a/f/g[b][t][f]/c/r/t/y[d][y][g][y]/d[t]/f/dd/rr/t/ee[d][f][r][e]/f/v/f"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath19() {
		String[] paths = {"/a/f/gff[b][t][f]/c/r/t/yff[dff][y][g][yff]/d[t]/f/dd/rr/t/ee[dff][f][r][e]/f/v/f"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath20() {
		String[] paths = {"/a/f/gff[b][t][ffff]/c/r/t/yff[dff][y][gff][yff]/d[t]/f/dd/rr/t/ee[dff][f][r][e]/f/v/f"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath21() {
		String[] paths = {"/a[a/d/f]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath22() {
		String[] paths = {"/a[a/d/f/f/g/gh]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath23() {
		String[] paths = {"/a[a/d/f/f/g/gh][a/d/f/f/g/gh]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath24() {
		String[] paths = {"/a[a/d/f/f/g/gh][a/d/f/f/g/gh][a/d/f/f/g/gh]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath25() {
		String[] paths = {"/a/f/g[a/d/f/f/g/gh][a/d/f/f/g/gh][a/d/f/f/g/gh]/a/f/g[a/d/f/f/g/gh][a/d/f/f/g/gh][a/d/f/f/g/gh]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath26() {
		String[] paths = {"/a[s[r[t]]]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath27() {
		String[] paths = {"/a[s[r[t[y[u]]]]]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath28() {
		String[] paths = {"/a/g[s[r[t[y[u]]]]]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath29() {
		String[] paths = {"/a/g[s[r[t[y[u]]]][u]][i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath30() {
		String[] paths = {"/a/g[s[r[t[y[u][t][y][d]]]][u]][i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath31() {
		String[] paths = {"/a/g[s[r[t[y[u][t][y][d]][t][y]][y][t]][u]][i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath32() {
		String[] paths = {"/a/g[s[r[t[y[u][t][y][d]][t][y]][y][t]][u]][i]/a/g[s[r[t[y[u][t][y][d]][t][y]][y][t]][u]][i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath33() {
		String[] paths = {"/a/g[s/g/t[r[t[y[u/g/t][t][y][d/g[u]/t/y]][t/g/h][y]][y][t]][u]][i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath34() {
		String[] paths = {"/a/g[s/g/t[r[t[y[u/g/t][t][y][d/g[u]/t/y]][t/g/h][y/g/r]][y][t/g/t]][u]][i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath35() {
		String[] paths = {"/a[text() = \"hello\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath36() {
		String[] paths = {"/a[text() = \"hello\"][text() = \"hello\"][text() = \"hello\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath37() {
		String[] paths = {"/a/g[s[r[t[y[text() = \"hello\"]]]][text() = \"hello\"]][i][text() = \"hello\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath38() {
		String[] paths = {"/a[@val=\"text1\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath39() {
		String[] paths = {"/a[@val=\"text1\"][@val=\"text1\"][@val=\"text1\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath40() {
		String[] paths = {"/a/g[s[r[t[y[@val=\"text1\"]]]][@val=\"text1\"]][i][@val=\"text1\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath41() {
		String[] paths = {"/a[contains(text(),\"someSubstring\")]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath42() {
		String[] paths = {"/a[contains(text(),\"someSubstring\")][contains(text(),\"someSubstring\")][contains(text(),\"someSubstring\")]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath43() {
		String[] paths = {"/a/g[s[r[t[y[contains(text(),\"someSubstring\")]]]][contains(text(),\"someSubstring\")]][i][contains(text(),\"someSubstring\")]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath44() {
		String[] paths = {"/a[text() = \"hello\"][@val=\"text1\"][contains(text(),\"someSubstring\")]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath45() {
		String[] paths = {"/a/g[s[r[t[y[text() = \"hello\"]]]][contains(text(),\"someSubstring\")]][i][@val=\"text1\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath46() {
		String[] paths = {"/foo/bar/xyz"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath47() {
		String[] paths = {"/foo/bar[@att=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath48() {
		String[] paths = {"/xyz/abc[contains(text(),\"someSubstring\")]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath49() {
		String[] paths = {"/a/b/c[text()=\"theEntireText\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath50() {
		String[] paths = {"/blah[anotherElement]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath51() {
		String[] paths = {"/this/that[something/else]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath52() {
		String[] paths = {"/d/e/f[foo[text()=\"something\"]][bar]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath53() {
		String[] paths = {"/a/b/c[text() =   \"whiteSpacesShouldNotMatter\"] "};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath54() {
		String[] paths = {"  /a/b/c[text() =   \"whiteSpacesShouldNotMatter\"] "};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath55() {
		String[] paths = {"/a/b[foo[text()=\"#$(/][]\"]][bar]/hi[@asdf=\"#$(&[]\"][this][is][crazy]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath56() {
		String[] paths = {"/test[ a/b1[ c1[p]/d[p] ] /n1[a]/n2 [c2/d[p]/e[text()=\"/asp[&123(123*/]\"]]]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath57() {
		String[] paths = {"/note/hello4/this[@val=\"text1\"]/that[@val=\"text2\"][something/else]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath58() {
		String[] paths = {"/note/hello1/to[text()=\"text2\"][@vp=\"text1\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath59() {
		String[] paths = {"/foo/bar[@abc=\"This is a \\\"quoted\\\" test\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath60() {
		String[] paths = {"/a/"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath61() {
		String[] paths = {"a/"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath62() {
		String[] paths = {"/a[s][d][]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath63() {
		String[] paths = {"/a[s][d]d]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath64() {
		String[] paths = {"/a[s][d][d"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath65() {
		String[] paths = {"/a//g[s/g/t[r[t[y[u/g/t][t][y][d/g[u]/t/y]][t/g/h][y]][y][t]][u]][i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath66() {
		String[] paths = {"/a/[s/g/t[r[t[y[u/g/t][t][y][d/g[u]/t/y]][t/g/h][y]][y][t]][u]][i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath67() {
		String[] paths = {"/a/d[s/g/t[r[t[y[u/g/t/][t][y][d/g[u]/t/y]][t/g/h][y]][y][t]][u]][i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath68() {
		String[] paths = {"/a/d[s/g/t[r[t[y[u/g/t][t][y][d/[g[u]/t/y]][t/g/h][y]][y][t]][u]][i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath69() {
		String[] paths = {"/a/d[s/g/t[r[t[y[u/g/t][t][y][d/g[u]/t/y]][t/g/h][y]][y][t]][u]]/[i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath70() {
		String[] paths = {"/a g/d[s/g/t[r[t[y[u/g/t][t][y][d/g[u]/t/y]][t/g/h][y]][y][t]][u]][i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath71() {
		String[] paths = {"/a/g/d[s/g/t[r[t[y [u/g/t][t] [y][d/g[u t]/t/y]][t/g/h][y]][y][t]][u]][i]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath72() {
		String[] paths = {"//"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath73() {
		String[] paths = {"//s d"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath74() {
		String[] paths = {"/d/f/"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath75() {
		String[] paths = {"[d]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath76() {
		String[] paths = {"/d[d]d/s"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath77() {
		String[] paths = {"/d[d]/f,s"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath78() {
		String[] paths = {",d[d]/f/s"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath79() {
		String[] paths = {"(d[d]/f/s"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath80() {
		String[] paths = {"/d[d]/f(s"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath81() {
		String[] paths = {"/d/f/text()/f/g"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath82() {
		String[] paths = {"/d/\"hello\"/g"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath83() {
		String[] paths = {"/xyz/abc[contains(  text()  ,   \"someSubstring\"  )  ]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath84() {
		String[] paths = {"/xyz/abc[contains(  text(  )  ,   \"someSubstring\"  )  ]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath85() {
		String[] paths = {"/xyz/abc[contains(  text()     \"someSubstring\"  )  ]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath86() {
		String[] paths = {"/xyz/abc[condtains(  text()     \"someSubstring\"  )  ]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath87() {
		String[] paths = {"/xyz/abc[contains(  text()     test  )  ]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath88() {
		String[] paths = {"/xyz/abc[contains(  text()   ,  test  )  ]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath89() {
		String[] paths = {"/xyz/abc[contains(  text()   ,  \"test  )  ]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath90() {
		String[] paths = {"/xyz/abc[contains(  text()   ,  \"test\"   ]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath91() {
		String[] paths = {"/a/b/@c/d"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath92() {
		String[] paths = {"/a/b/@c/d"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath93() {
		String[] paths = {"/foo/bar[att=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath94() {
		String[] paths = {"/foo/bar[@att\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath95() {
		String[] paths = {"/foo/bar[@att=hello]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath96() {
		String[] paths = {"/foo/bar[@att][tt]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath97() {
		String[] paths = {"/foo/@/bar[@att][tt]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath98() {
		String[] paths = {"/foo/bar[@att=\"123\""};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath99() {
		String[] paths = {"/foo/bar@att=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath100() {
		String[] paths = {"/foo/bar[@rr==\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath101() {
		String[] paths = {"/foo/bar[@rr,\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath102() {
		String[] paths = {"/foo/bar[,=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath103() {
		String[] paths = {"/foo/bar[,=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath104() {
		String[] paths = {"/fo_o/bar"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath105() {
		String[] paths = {"/fo-o/bar"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath106() {
		String[] paths = {"/fo1234o/bar"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath107() {
		String[] paths = {"/fo.o/bar"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath108() {
		String[] paths = {"/_foo/bar"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath109() {
		String[] paths = {"/.foo/bar"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath110() {
		String[] paths = {"/3foo/bar"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath111() {
		String[] paths = {"/f$oo/bar"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath112() {
		String[] paths = {"/f*oo/bar"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath113() {
		String[] paths = {"/xmlfoo/bar"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath114() {
		String[] paths = {"/f@oo/bar"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath115() {
		String[] paths = {"/foo/bar[@a-tt=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath116() {
		String[] paths = {"/foo/bar[@a_tt=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath117() {
		String[] paths = {"/foo/bar[@a.tt=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath118() {
		String[] paths = {"/foo/bar[@a1234tt=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath119() {
		String[] paths = {"/foo/bar[@_att=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath120() {
		String[] paths = {"/foo/bar[@4att=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath121() {
		String[] paths = {"/foo/bar[@4at%t=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath122() {
		String[] paths = {"/foo/bar[@=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath123() {
		String[] paths = {"/foo/bar[@s=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertTrue(t.isValid(0));
	}
	
	public void testXPath124() {
		String[] paths = {"/foo/bar[@xmlabc=\"123\"]"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
	
	public void testXPath125() {
		String[] paths = {"/foo/bar[h]d"};
		XPathEngineImpl t = new XPathEngineImpl();
		t.setXPaths(paths);
		assertFalse(t.isValid(0));
	}
}
