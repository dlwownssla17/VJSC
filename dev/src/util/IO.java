package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IO {
	public static String readFile(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		
		StringBuffer sb = new StringBuffer();
		String currentLine;
		while ((currentLine = br.readLine()) != null) {
			sb.append(currentLine).append('\n');
		}
		br.close();
		
		return sb.toString();
	}
	
	public static boolean writeFile(String filePath, String text) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
		
		bw.write(text);
		bw.close();
		
		return true;
	}
}
