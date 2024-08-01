package com.roubsite.trans;

import com.pengrad.telegrambot.TelegramBot;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class BotConfigure {
    public static long chanelId = Long.parseLong("-1001871752681");
    public static TelegramBot BOT;
    private static final String BOT_TOKEN = "2103390029:AAE9vKe7Mpb9emegMlaAWmsm4ypR3XY-fFY";

//    private static final String BOT_UTL = "http://141.147.145.163:8081";
    private static final String BOT_UTL = "http://[2603:c023:1:9701:9217:b4c0:dc6b:b61d]:8081";


    static {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().
                connectTimeout(1000, TimeUnit.SECONDS).
                readTimeout(1, TimeUnit.HOURS).
                writeTimeout(1, TimeUnit.HOURS).
                callTimeout(1, TimeUnit.HOURS).build();
        BOT = new TelegramBot.Builder(BOT_TOKEN).apiUrl(BOT_UTL + "/bot")
                .okHttpClient(okHttpClient)
                .fileApiUrl(BOT_UTL + "/file/bot").build();

    }
}
