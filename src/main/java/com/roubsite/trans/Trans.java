package com.roubsite.trans;

import com.roubsite.trans.server.Server;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Trans {
    public static Vector<File> list = new Vector<>();
    public static List<String> ends = Arrays.asList(new String[]{"wmv", "asf", "asx", "rm", "rmvb", "mpg", "mpeg", "mpe", "3gp", "mov", "mp4", "m4v", "avi", "dat", "mkv", "flv", "vob"});

    public static void main(String[] args) {
        if (args.length <= 0) {
            System.out.println("请在参数中添加文件夹");
            return;
        }
        if (args.length > 1) {
            ends = Arrays.asList(args[1].split(","));
        }
        System.out.println("扫描文件夹" + args[0]);
        new Thread(() -> {
            try {
                Server.start(2469);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        getDirectory(new File(args[0]), list);
        System.out.println("扫描完成，共" + list.size() + "个文件");
        for (int i = 0; i < list.size(); i++) {
            TransUtils.trans(list.get(i));
        }
//        TransUtils.trans(new File(args[0]));
    }

    private static void getDirectory(File file, Vector<File> list) {
        File[] fileList = file.listFiles();
        if (fileList == null || fileList.length == 0) {
            return;
        }
        for (File f : fileList) {
            if (f.isDirectory()) {
                getDirectory(f, list);
            } else {
                if (f.getName().startsWith("trans_ok_")) {
                    if (isEnd(f.getName())) {
                        list.add(f);
                        System.out.println("file==>" + f.getAbsolutePath());
                    }
                }
            }

        }
    }

    public static boolean isEnd(String s) {
        for (String end : ends) {
            if (s.toUpperCase().endsWith(end.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
