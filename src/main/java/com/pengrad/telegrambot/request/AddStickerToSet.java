package com.pengrad.telegrambot.request;

import com.pengrad.telegrambot.model.MaskPosition;
import com.pengrad.telegrambot.model.request.InputSticker;
import com.pengrad.telegrambot.response.BaseResponse;

/**
 * Stas Parshin
 * 23 July 2017
 */
public class AddStickerToSet extends AbstractUploadRequest<AddStickerToSet, BaseResponse> {

    /**
     * @deprecated Use constructor with the InputSticker type (since API v6.6)
     */
    @Deprecated
    public static AddStickerToSet tgsSticker(Long userId, String name, String emojis, Object tgsSticker) {
        return new AddStickerToSet(userId, name, emojis, "tgs_sticker", tgsSticker);
    }

    /**
     * @deprecated Use constructor with the InputSticker type (since API v6.6)
     */
    @Deprecated
    public static AddStickerToSet pngSticker(Long userId, String name, String emojis, Object pngSticker) {
        return new AddStickerToSet(userId, name, emojis, "png_sticker", pngSticker);
    }

    /**
     * @deprecated Use constructor with the InputSticker type (since API v6.6)
     */
    @Deprecated
    public static AddStickerToSet webmSticker(Long userId, String name, String emojis, Object webmSticker) {
        return new AddStickerToSet(userId, name, emojis, "webm_sticker", webmSticker);
    }

    /**
     * @deprecated Use static methods according to sticker set type - {@link #pngSticker(Long, String, String, Object) for png}, {@link #tgsSticker(Long, String, String, Object) for tgs} and {@link #webmSticker(Long, String, String, Object) for webm}
     */
    @Deprecated
    public AddStickerToSet(Long userId, String name, Object pngSticker, String emojis) {
        this(userId, name, emojis, "png_sticker", pngSticker);
    }

    /**
     * @deprecated Use constructor with the InputSticker type (since API v6.6)
     */
    @Deprecated
    private AddStickerToSet(Long userId, String name, String emojis, String stickerParam, Object sticker) {
        super(BaseResponse.class, stickerParam, sticker);
        add("user_id", userId);
        add("name", name);
        add("emojis", emojis);
    }

    public AddStickerToSet(Long userId, String name, InputSticker sticker) {
        super(BaseResponse.class, attachName(sticker), attachment(sticker));
        add("user_id", userId);
        add("name", name);
        add("sticker", sticker);
    }

    private static String attachName(InputSticker sticker) {
        return sticker.getAttachment() != null ? sticker.getAttachName() : "sticker_id";
    }

    private static Object attachment(InputSticker sticker) {
        return sticker.getAttachment() != null ? sticker.getAttachment() : "sticker_id";
    }

    public AddStickerToSet maskPosition(MaskPosition maskPosition) {
        return add("mask_position", maskPosition);
    }
}
