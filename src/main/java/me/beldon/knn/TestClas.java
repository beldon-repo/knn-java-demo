package me.beldon.knn;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Beldon
 * Copyright (c) 2017/11/25, All Rights Reserved.
 * https://beldon.me/
 */
public class TestClas {
    public static void main(String[] args) throws Exception {
        String[] fileNames = Paths.get("samples/testDigits/").toFile().list();
        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            String fileNameStr = fileName.split("\\.")[0];
            String classNumStr = fileNameStr.split("_")[0];
            String targetFileName = classNumStr + "_ab" + i + ".txt";
            Files.copy(Paths.get("samples/testDigits/", fileName), Paths.get("samples/set/", targetFileName));
        }
    }
}
