package com.roubsite.trans;

import com.alibaba.fastjson2.JSON;
import io.netty.util.internal.StringUtil;
import okhttp3.*;
import org.apache.commons.cli.*;
import org.apache.commons.exec.util.StringUtils;
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
import java.util.concurrent.TimeUnit;

public class DownloadAndUpload {
    static Logger logger = LogManager.getLogger(DownloadAndUpload.class);
    //    public static final String BASE_URL = "https://tgvideo.roubsite.com";
    public static String BASE_URL = "http://10.254.254.254:8081";
    private static final String ENCRYPTION_KEY = "TKj1iBjFgJDRmEWY1SxrHw==";
    public static OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(1000, TimeUnit.SECONDS).
            readTimeout(1, TimeUnit.HOURS).
            writeTimeout(1, TimeUnit.HOURS).
            callTimeout(1, TimeUnit.HOURS).build();
    //        public static final String BASE_PATH="D:/download/";
    public static final String BASE_PATH = "/data/download/";

    public static void main(String[] args) throws Exception {
//        UploadToTelegram.upload1(new File("/data/download/trans_ok_2024.04.20_20.33.37_evalewis-2.mp4"),"evalewis");

        Options options = new Options();
        Option pathOption = Option.builder("path")
                .hasArg()
                .desc("目录路径")
                .required(true)
                .build();
        Option baseUrlOption = Option.builder("base_url")
                .hasArg()
                .desc("服务器地址")
                .required(false)
                .build();

        options.addOption(pathOption);
        options.addOption(baseUrlOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("程序名称", options);
            System.exit(1);
            return;
        }

        String path = cmd.getOptionValue("path");
        String baseUrl = cmd.getOptionValue("base_url");
        if (!StringUtil.isNullOrEmpty(BASE_URL)) {
            BASE_URL = baseUrl;
        }

        RequestBody requestBody = new FormBody.Builder()
                .add("path", path)
                .build(); // 如果需要发送POST数据


        String url = BASE_URL + "/api/v1/getFileList";
        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody) // 或者 "GET"，取决于API要求
//                .addHeader("Authorization", "Bearer your_token") // 添加认证信息，如果需要
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        if (response.body() != null) {
            String json = response.body().string();
            System.out.println(json);
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
        System.out.println("全部上传完成");

    }

    public static void downloadFile(String path) throws Exception {
        System.out.println("start download：" + path);
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
        System.out.println("download completed.\nstart upload：" + outPutPath);
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
