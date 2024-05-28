package com.roubsite.trans;

import cn.hutool.crypto.SecureUtil;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.request.SendVideo;
import com.pengrad.telegrambot.response.SendResponse;
import com.roubsite.trans.utils.Encoder;
import com.roubsite.trans.utils.FFmpegUtils;
import com.roubsite.trans.utils.MultimediaInfo;
import com.roubsite.trans.utils.ZdyFFMPEGLocator;
import io.netty.util.internal.StringUtil;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.util.StringUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.roubsite.trans.BotConfigure.BOT;
import static com.roubsite.trans.DownloadAndUpload.BASE_URL;
import static com.roubsite.trans.DownloadAndUpload.client;

public class UploadToTelegram {
    private static Encoder encoder = new Encoder(new ZdyFFMPEGLocator());

    public static void upload(File file, String path) {
        String mo = new File(path).getParentFile().getName();
        String url = BASE_URL + "/api/v1/deleteFile";
        RequestBody requestBody = new FormBody.Builder()
                .add("path", path)
                .add("c", SecureUtil.md5(path + "shuijing1A").toUpperCase())
                .build(); // 如果需要发送POST数据

        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody) // 或者 "GET"，取决于API要求
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.length() / 1024.0 / 1024 / 1024 > 1.9) {
            String command = "/data/split-video.sh " + file.getAbsolutePath() + " 2000000000";
            try {
                //接收正常结果流
                ByteArrayOutputStream susStream = new ByteArrayOutputStream();
                //接收异常结果流
                ByteArrayOutputStream errStream = new ByteArrayOutputStream();
                CommandLine commandLine = CommandLine.parse(command);
                DefaultExecutor exec = new DefaultExecutor();
                PumpStreamHandler streamHandler = new PumpStreamHandler(susStream, errStream);
                exec.setStreamHandler(streamHandler);
                int code = exec.execute(commandLine);
                System.out.println("result code: " + code);
                // 不同操作系统注意编码，否则结果乱码
                String suc = susStream.toString();
                String err = errStream.toString();
                System.out.println("suc: " + suc);
                System.out.println("err: " + err);
                if (!suc.isEmpty()) {
                    String[] files = suc.split("\n");
                    for (String s : files) {
                        if (!StringUtil.isNullOrEmpty(s)) {
                            upload1(new File(s), mo);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            upload1(file, mo);
        }
    }

    public static void upload1(File file, String mo) {

        System.out.println("当前上传：" + file.getAbsolutePath());

        try {
            SendVideo sendVideo = uploadVideo(file.getName().substring(0, file.getName().lastIndexOf('.')), file.getAbsolutePath(), mo);
            BOT.execute(sendVideo, new Callback<SendVideo, SendResponse>() {

                @Override
                public void onResponse(SendVideo sendVideo, SendResponse sendResponse) {
//                    System.out.println(sendResponse.toString());
                    new File(file.getAbsolutePath()+".jpg").delete();
                    if (file.delete()) {
                        System.out.println("删除成功");
                    } else {
                        System.out.println("删除失败");
                    }
                }

                @Override
                public void onFailure(SendVideo sendVideo, IOException e) {
                    System.out.println("上传失败");
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传视频到群组
     */
    public static SendVideo uploadVideo(String videoName, String videoPath, String mo) throws Exception {
        File videoFile = new File(videoPath);
        MultimediaInfo m = encoder.getInfo(videoFile);
        SendVideo request = new SendVideo(BotConfigure.chanelId, videoFile);
        System.out.println("视频宽度："+m.getVideo().getSize().getWidth());
        System.out.println("视频高度："+m.getVideo().getSize().getHeight());
        request.height(m.getVideo().getSize().getHeight());
        request.width(m.getVideo().getSize().getWidth());
        try {
            request.duration(Integer.parseInt(Long.toString(m.getDuration() / 1000)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.supportsStreaming(true);
        try {
            System.out.println("图片路径：" + videoPath + ".jpg");
            System.out.println("视频路径：" + videoPath);
            FFmpegUtils.getThumb(videoPath, videoPath + ".jpg", 0, 0, 1);
//            byte[] jpg = IOUtils.toByteArray(Files.newInputStream(Paths.get(videoPath + ".jpg")));
//            System.out.println("图片大小：" + jpg.length);
            request.thumbnail(new File(videoPath + ".jpg"));


        } catch (Exception e) {
            e.printStackTrace();
        }

        request.caption("#" + mo + "\n" + videoName.replace("trans_ok_",""));
        System.out.println("请求封装完成");
        return request;
    }


}
