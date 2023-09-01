package com.laptop.rfid_innotek2.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class UtilService {

	public String readFile(String filePath) throws IOException {

		StringBuilder content = new StringBuilder();
		if (isFilePathValid(filePath)) {

			int fileLength = fileLength(filePath);
			if (fileLength < 11) {
				FileReader file = new FileReader(filePath);
				BufferedReader reader = new BufferedReader(file);
				String line;
				while ((line = reader.readLine()) != null) {
					content.append(line).append("\n");
				}
				reader.close();
				return content.toString();
			} else {
				return "파일 크기가 10MB 이상입니다. 다운로드 받아서 확인해주세요.";
			}
		} else {
			return "파일이 존재하지 않습니다. 파일 경로를 확인해주세요 - " + filePath;
		}
	}

	public static boolean isFilePathValid(String filePath) {
		File file = new File(filePath);
		return file.exists() && file.isFile();
	}

	public static int fileLength(String filePath) {
		File file = new File(filePath);
		return (int) (file.length() / 1024 / 1024);
	}

}
