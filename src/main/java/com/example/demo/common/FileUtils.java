package com.example.demo.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    public final static String FILETYPE = ".mp4";

    public static File createDirectoryIfNotExists(String piName, String hlsOutputPath) {
        try {
            System.out.println(hlsOutputPath);
            File output = new File(hlsOutputPath + piName);
            System.out.println(output.getAbsolutePath());
            if (!output.exists()) {
                output.mkdirs();
            }

            return output;
        } catch (Exception e) {
            System.err.println("Error creating directory: " + e.getMessage());
            return null;
        }

    }

//    public static boolean writeMsgToFile(byte[] content, File filePath, int index) {
//
//        try (BufferedOutputStream bos = new BufferedOutputStream(
//                new FileOutputStream(filePath.getAbsoluteFile() + MIDDLE + index + FILETYPE, false))) {
//            bos.write(content);
//            return true;
//        } catch (IOException e) {
//            System.err.println("Error writing content to file: " + e.getMessage());
//            return false;
//        }
//    }

    public static File writeBufferToTempFile(byte[] buffer) {
        try {
            File tempFile = File.createTempFile("temp", FILETYPE);
            tempFile.deleteOnExit(); // 프로그램 종료 후 임시 파일 삭제

            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile, false))) {
//            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                bos.write(buffer);
            }

            return tempFile;
        } catch (IOException e) {
            System.err.println("Error writing content to temparary file: " + e.getMessage());
//            log.error("Error writing buffer to temp file: {}", e.getMessage());
            return null;
        }
    }
}
