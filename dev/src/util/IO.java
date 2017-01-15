package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IO {
	public static String readFile(String filePath) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				sb.append(currentLine).append('\n');
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static boolean writeFile(String filePath, String text) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(filePath));
			bw.write(text);
			return true;
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) bw.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
