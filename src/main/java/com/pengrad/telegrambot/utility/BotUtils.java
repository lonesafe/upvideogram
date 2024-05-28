package com.pengrad.telegrambot.utility;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.chatboost.source.ChatBoostSource;
import com.pengrad.telegrambot.model.message.MaybeInaccessibleMessage;
import com.pengrad.telegrambot.model.message.origin.MessageOrigin;
import com.pengrad.telegrambot.model.reaction.ReactionType;
import com.pengrad.telegrambot.utility.gson.ChatBoostSourceTypeAdapter;
import com.pengrad.telegrambot.utility.gson.MaybeInaccessibleMessageTypeAdapter;
import com.pengrad.telegrambot.utility.gson.MessageOriginTypeAdapter;
import com.pengrad.telegrambot.utility.gson.ReactionTypeAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * stas
 * 11/1/15.
 */
public class BotUtils {

    private BotUtils() {}

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ReactionType.class, new ReactionTypeAdapter())
            .registerTypeAdapter(MessageOrigin.class, new MessageOriginTypeAdapter())
            .registerTypeAdapter(ChatBoostSource.class, new ChatBoostSourceTypeAdapter())
            .registerTypeAdapter(MaybeInaccessibleMessage.class, new MaybeInaccessibleMessageTypeAdapter())
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public static Update parseUpdate(String update) {
        return GSON.fromJson(update, Update.class);
    }

    public static Update parseUpdate(Reader reader) {
        return GSON.fromJson(reader, Update.class);
    }

    public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xFFFF];
        for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }

    public static <R> R fromJson(String jsonString, Class<R> resClass) {
        return GSON.fromJson(jsonString,resClass);
    }

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }
}
