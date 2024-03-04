package com.roubsite.trans;

import com.roubsite.trans.server.Server;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Trans {
    private static int threadNum = 0;
    public static final Vector<File> fileList = new Vector<>();
    private static List<String> allowedExtensions = Arrays.asList("wmv", "asf", "asx", "rm", "rmvb", "mpg", "mpeg", "mpe", "3gp", "mov", "mp4", "m4v", "avi", "dat", "mkv", "flv", "vob");

    public synchronized static int getThreadNum() {
        return threadNum;
    }

    public synchronized static void incrementThreadNum() {
        threadNum++;
    }

    public synchronized static void decrementThreadNum() {
        threadNum--;
    }

    public static void main(String[] args) {
        if (args.length <= 0) {
            System.out.println("Please provide a folder in the parameters.");
            return;
        }

        if (args.length > 1) {
            allowedExtensions = Arrays.asList(args[1].split(","));
        }

        System.out.println("Scanning folder: " + args[0]);

        new Thread(() -> {
            try {
                Server.start(2469);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        scanDirectory(new File(args[0]));
        System.out.println("Scan completed. Found " + fileList.size() + " files.");

        for (File file : fileList) {
            TransUtils.trans(file);
            while (getThreadNum() > 10) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void scanDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(file);
            } else {
                if (file.getName().startsWith("trans_ok_") && isAllowedExtension(file.getName())) {
                    fileList.add(file);
                    System.out.println("File found: " + file.getAbsolutePath());
                }
            }
        }
    }

    public static boolean isAllowedExtension(String fileName) {
        for (String extension : allowedExtensions) {
            if (fileName.toUpperCase().endsWith(extension.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
