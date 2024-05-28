package com.roubsite.trans;


import java.io.File;

public class UploadThread implements Runnable{
    private File videoFile;
    public UploadThread(String videoFile) {
        this.videoFile = new File(videoFile);
    }
    @Override
    public void run() {
//        UploadToTelegram.upload(videoFile, path);
    }
}
