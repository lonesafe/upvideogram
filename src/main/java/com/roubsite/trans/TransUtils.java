package com.roubsite.trans;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.request.SendVideo;
import com.pengrad.telegrambot.response.SendResponse;
import com.roubsite.trans.utils.Encoder;
import com.roubsite.trans.utils.FFmpegUtils;
import com.roubsite.trans.utils.MultimediaInfo;
import com.roubsite.trans.utils.ZdyFFMPEGLocator;

import java.io.File;
import java.io.IOException;

import static com.roubsite.trans.BotConfigure.BOT;

public class TransUtils {
    private static Encoder encoder = new Encoder(new ZdyFFMPEGLocator());
    ;
    public static int COMPLETE = 0;// 转码进度
    public static String nowFile;

    public static void trans(File file) {

        Trans.incrementThreadNum();
        nowFile = file.getAbsolutePath();
        System.out.println("当前上传：" + file.getAbsolutePath());
        try {
            SendVideo sendVideo = uploadVideo(file.getName().substring(0, file.getName().lastIndexOf('.')), file.getAbsolutePath());
            BOT.execute(sendVideo, new Callback<SendVideo, SendResponse>() {

                @Override
                public void onResponse(SendVideo sendVideo, SendResponse sendResponse) {
                    if(file.delete()){
                        System.out.println("删除成功");
                    }else {
                        System.out.println("删除失败");
                    }
                    Trans.decrementThreadNum();
                }

                @Override
                public void onFailure(SendVideo sendVideo, IOException e) {
                    System.out.println("上传失败");
                    e.printStackTrace();
                    Trans.decrementThreadNum();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Trans.decrementThreadNum();
        }


    }

    /**
     * 上传视频到群组
     */
    public static SendVideo uploadVideo(String videoName, String videoPath) throws Exception {
        File videoFile = new File(videoPath);
        MultimediaInfo m = encoder.getInfo(videoFile);
        SendVideo request = new SendVideo(BotConfigure.chanelId, videoFile);
        request.height(m.getVideo().getSize().getHeight());
        request.width(m.getVideo().getSize().getWidth());
        try {
            request.duration(Integer.parseInt(Long.toString(m.getDuration() / 1000)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.supportsStreaming(true);
        try {
            FFmpegUtils.getThumb(videoPath, videoPath + ".jpeg", 0, 0, 1);
            request.thumb(new File(videoPath + ".jpeg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.caption("#"+videoFile.getParentFile().getName()+"\n"+videoName);
        return request;
    }


}
