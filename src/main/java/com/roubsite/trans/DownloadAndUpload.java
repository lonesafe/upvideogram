package com.roubsite.trans;

import com.alibaba.fastjson2.JSON;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class DownloadAndUpload {
    static Logger logger = LogManager.getLogger(DownloadAndUpload.class);
    public static final String BASE_URL = "https://tgvideo.roubsite.com";
    //    private static final String BASE_URL = "http://127.0.0.1:8080";
    private static final String ENCRYPTION_KEY = "TKj1iBjFgJDRmEWY1SxrHw==";
    public static OkHttpClient client = new OkHttpClient();
//        public static final String BASE_PATH="D:/download/";
    public static final String BASE_PATH = "/data/download/";

    public static void main(String[] args) throws Exception {
//        args = new String[]{"/opt/video"};
        if (args.length <= 0) {
            System.out.println("Please provide a folder in the parameters.");
            return;
        }
        String url = BASE_URL + "/api/v1/getFileList";
        RequestBody requestBody = new FormBody.Builder()
                .add("path", args[0])
                .build(); // 如果需要发送POST数据

        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody) // 或者 "GET"，取决于API要求
//                .addHeader("Authorization", "Bearer your_token") // 添加认证信息，如果需要
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        if (response.body() != null) {
            String json = response.body().string();
            Map map = JSON.parseObject(json, Map.class);
            if (map.get("code").equals(200)) {
                for (String file : (List<String>) map.get("data")) {
                    try {
                        downloadFile(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                        String outPutPath = BASE_PATH + new File(file).getName();
                        if (new File(outPutPath).delete()) {
                            System.out.println("删除成功:" + outPutPath);
                        } else {
                            System.out.println("删除失败:" + outPutPath);
                        }
                    }
                }
            }
        }
//        String outPutPath = BASE_PATH+"trans_ok_2023.06.24_20.43.57_afroditha_.mp4";
//        UploadToTelegram.upload(new File(outPutPath),"/asdf/asdf");
        System.out.println("上传完成");

    }

    //    public static void downloadFile(String path) throws Exception {
//        System.out.println("当前下载：" + path);
//
//        String outPutPath = BASE_PATH + new File(path).getName();
//        new File(BASE_PATH).mkdirs();
//
//        String url = BASE_URL + "/api/v1/download/mp4";
//        RequestBody requestBody = new FormBody.Builder()
//                .add("path", path)
//                .build(); // 如果需要发送POST数据
//
//        Request request = new Request.Builder()
//                .url(url)
//                .method("POST", requestBody) // 或者 "GET"，取决于API要求
////                .addHeader("Authorization", "Bearer your_token") // 添加认证信息，如果需要
//                .build();
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//        System.out.println("请求完成：");
//        byte[] iv = Base64.getDecoder().decode(response.header("X-Encryption-IV")); // 获取IV
//
//        InputStream inputStream = response.body().byteStream();
//        OutputStream outputStream = new FileOutputStream(outPutPath);
//
//        byte[] buffer = new byte[1024];
//        int bytesRead;
//
//        while ((bytesRead = inputStream.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, bytesRead);
//        }
//
//        inputStream.close();
//        outputStream.close();
//        FileInputStream fileInputStream = new FileInputStream(outPutPath);
//        OutputStream outputStream1 = new FileOutputStream(outPutPath + ".dec.mp4");
//
//        SecretKeySpec secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        IvParameterSpec ivSpec = new IvParameterSpec(iv);
//        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec); // 初始化加密器
//        CipherInputStream cis = new CipherInputStream(fileInputStream, cipher);
//
//        int len;
//        while ((len = cis.read(buffer)) != -1) {
//            outputStream1.write(buffer, 0, len);
//        }
//        outputStream1.flush();
//        outputStream1.close();
//        fileInputStream.close();
//        //上传到tg
//        new File(outPutPath).delete();
//        new File(outPutPath + ".dec.mp4").renameTo(new File(outPutPath));
////        new Thread(new UploadThread(outPutPath+".dec.mp4")).start();
//        UploadToTelegram.upload(new File(outPutPath), path);
//    }
    public static void downloadFile(String path) throws Exception {
        String url = BASE_URL + "/api/v1/download/mp4";
        String outPutPath = BASE_PATH + new File(path).getName();
        Request request = new Request.Builder()
                .url(url)
                .post(new FormBody.Builder().add("path", path).build())
                .build();

        Response response = client.newCall(request).execute();
        InputStream encryptedStream = response.body().byteStream();
        CipherInputStream decryptedStream = initDecryptionStream(encryptedStream, response);
        FileOutputStream outputStream = new FileOutputStream(outPutPath);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = decryptedStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        UploadToTelegram.upload(new File(outPutPath), path);
    }

    private static CipherInputStream initDecryptionStream(InputStream encryptedStream, Response response) throws Exception {
        byte[] iv = Base64.getDecoder().decode(response.header("X-Encryption-IV"));
        SecretKeySpec secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        return new CipherInputStream(encryptedStream, cipher);

    }
}
