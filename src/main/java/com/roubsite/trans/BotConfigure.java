package com.roubsite.trans;

import com.pengrad.telegrambot.TelegramBot;

public class BotConfigure {
	public static long chanelId = Long.parseLong("-1001871752681");
	public static TelegramBot BOT;
	private static String BOT_TOKEN = "2103390029:AAE9vKe7Mpb9emegMlaAWmsm4ypR3XY-fFY";

	static {
		BOT = new TelegramBot.Builder(BOT_TOKEN).apiUrl("http://152.69.194.94:8081/bot")
				.fileApiUrl("http://152.69.194.94:8081/file/bot").build();

	}
}
