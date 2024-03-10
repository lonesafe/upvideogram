package com.roubsite.trans;

import com.alibaba.fastjson2.JSON;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.request.SendVideo;
import com.pengrad.telegrambot.response.SendResponse;
import com.roubsite.trans.utils.Encoder;
import com.roubsite.trans.utils.FFmpegUtils;
import com.roubsite.trans.utils.MultimediaInfo;
import com.roubsite.trans.utils.ZdyFFMPEGLocator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.roubsite.trans.BotConfigure.BOT;

public class TransUtils {
    private static Encoder encoder = new Encoder(new ZdyFFMPEGLocator());
    ;
    public static int COMPLETE = 0;// 转码进度
    public static String nowFile;

    public static void trans(File file) {

        Trans.incrementThreadNum();
        nowFile = file.getAbsolutePath();
//		System.out.println("当前转码：" + file.getAbsolutePath());
//		List<String> command = new ArrayList<>();
//		command.add("/opt/trans");
//		command.add("-c:v");
//		command.add("h264_cuvid");
//		command.add("-i");
//		command.add(file.getAbsolutePath());
//		command.add("-c:v");
//		command.add("h264_nvenc");
//		command.add("-vf");
//		command.add("scale=iw:trunc(ow/a/2)*2");
//		command.add("-c:a");
//		command.add("aac");
//		command.add("-b:v");
//		command.add("0");
//		command.add("-y");
//		command.add(file.getParent() + "/trans_ok_" + file.getName());
//		ProcessBuilder processBuilder = new ProcessBuilder(command);
//		processBuilder.redirectErrorStream(true);
//		try {
//			// 读取命令的输出信息
//			COMPLETE = 0;
//			Process p = processBuilder.start();
//			doWaitPro(p, file);
//			p.destroy();
//		} catch (Exception error) {
//		}
//		file = new File(file.getParent() + "/trans_ok_" + file.getName());
//		nowFile = file.getAbsolutePath();
        System.out.println("当前上传：" + file.getAbsolutePath());
        try {
            SendVideo sendVideo = uploadVideo(file.getName().substring(0, file.getName().lastIndexOf('.')), nowFile);
            File finalFile = file;
            BOT.execute(sendVideo, new Callback<SendVideo, SendResponse>() {

                @Override
                public void onResponse(SendVideo sendVideo, SendResponse sendResponse) {
                    finalFile.delete();
                    System.out.println(JSON.toJSONString(sendResponse.message()));
                    Trans.decrementThreadNum();
                }

                @Override
                public void onFailure(SendVideo sendVideo, IOException e) {
                    e.printStackTrace();
                    Trans.decrementThreadNum();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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

    private static void doWaitPro(Process p, File file) {
        try {
            String errorMsg = readInputStream(p.getErrorStream(), "error");
            String outputMsg = readInputStream(p.getInputStream(), "out");
            int c = p.waitFor();
            COMPLETE = 0;
            if (c != 0) {// 如果处理进程在等待
                Trans.fileList.add(file);
                System.err.println("处理失败稍后重试：" + errorMsg);
            } else {
                // 转码成功删除文件
                System.out.println("转码成功：" + file.getAbsolutePath());
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static String readInputStream(InputStream is, String f) throws IOException {
        // 将进程的输出流封装成缓冲读者对象
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer lines = new StringBuffer();// 构造一个可变字符串
        long totalTime = 0;

        // 对缓冲读者对象进行每行循环
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            lines.append(line);// 将每行信息字符串添加到可变字符串中
            int positionDuration = line.indexOf("Duration:");// 在当前行中找到第一个"Duration:"的位置
            int positionTime = line.indexOf("time=");
            if (positionDuration > 0) {// 如果当前行中有"Duration:"
                String dur = line.replace("Duration:", "");// 将当前行中"Duration:"替换为""
                dur = dur.trim().substring(0, 8);// 将替换后的字符串去掉首尾空格后截取前8个字符
                int h = Integer.parseInt(dur.substring(0, 2));// 封装成小时
                int m = Integer.parseInt(dur.substring(3, 5));// 封装成分钟
                int s = Integer.parseInt(dur.substring(6, 8));// 封装成秒
                totalTime = h * 3600 + m * 60 + s;// 得到总共的时间秒数
            }
            if (positionTime > 0) {// 如果所用时间字符串存在
                // 截取包含time=的当前所用时间字符串
                String time = line.substring(positionTime, line.indexOf("bitrate") - 1);
                time = time.substring(time.indexOf("=") + 1, time.indexOf("."));// 截取当前所用时间字符串
                int h = Integer.parseInt(time.substring(0, 2));// 封装成小时
                int m = Integer.parseInt(time.substring(3, 5));// 封装成分钟
                int s = Integer.parseInt(time.substring(6, 8));// 封装成秒
                long hasTime = h * 3600 + m * 60 + s;// 得到总共的时间秒数
                float t = (float) hasTime / (float) totalTime;// 计算所用时间与总共需要时间的比例
                COMPLETE = (int) Math.ceil(t * 100);// 计算完成进度百分比
            }
        }
        br.close();// 关闭进程的输出流
        return lines.toString();
    }

}
