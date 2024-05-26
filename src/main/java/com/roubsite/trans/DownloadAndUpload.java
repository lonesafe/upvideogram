package com.roubsite.trans;

import com.alibaba.fastjson2.JSON;
import okhttp3.*;
import sun.misc.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.lang.ref.Reference;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class DownloadAndUpload {
//    private static final String BASE_URL = "https://tgvideo.roubsite.com";
    private static final String BASE_URL = "http://127.0.0.1:8080";
    private static final String ENCRYPTION_KEY = "TKj1iBjFgJDRmEWY1SxrHw==";
    static OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) throws Exception {
        args = new String[]{"G:\\新建文件夹\\"};
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
                    downloadFile(file);
                }
            }
        }


    }

    public static void downloadFile(String path) throws Exception {
        String outPutPath = "D:/opt/download/" + new File(path).getName();
        new File("D:/opt/download/").mkdirs();

        String url = BASE_URL + "/api/v1/download/mp4";
        RequestBody requestBody = new FormBody.Builder()
                .add("path", path)
                .build(); // 如果需要发送POST数据

        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody) // 或者 "GET"，取决于API要求
//                .addHeader("Authorization", "Bearer your_token") // 添加认证信息，如果需要
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        byte[] iv = Base64.getDecoder().decode(response.header("X-Encryption-IV")); // 获取IV

        InputStream inputStream = response.body().byteStream();
        OutputStream outputStream = new FileOutputStream(outPutPath);

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();
        FileInputStream fileInputStream = new FileInputStream(outPutPath);
        OutputStream outputStream1 = new FileOutputStream(outPutPath+".dec.mp4");

        SecretKeySpec secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec); // 初始化加密器
        CipherInputStream cis = new CipherInputStream(fileInputStream, cipher);

        int len;
        while ((len = cis.read(buffer)) != -1) {
            outputStream1.write(buffer, 0, len);
        }
        outputStream1.flush();
        outputStream1.close();
        //上传到tg


    }

}
