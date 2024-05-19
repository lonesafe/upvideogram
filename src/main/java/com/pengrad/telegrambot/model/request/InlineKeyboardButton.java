package com.pengrad.telegrambot.model.request;

import java.io.Serializable;
import java.util.Objects;

import com.pengrad.telegrambot.model.WebAppInfo;

/**
 * Stas Parshin
 * 06 May 2016
 */
public class InlineKeyboardButton implements Serializable {
    private final static long serialVersionUID = 0L;

    private String text;
    private String url;
    private LoginUrl login_url;
    private String callback_data;
    private String switch_inline_query;
    private String switch_inline_query_current_chat;
    private SwitchInlineQueryChosenChat switch_inline_query_chosen_chat;
    private CallbackGame callback_game;
    private Boolean pay;
    private WebAppInfo web_app;

    private InlineKeyboardButton() {
    }

    //todo can use only one optional field, make different constructors or static methods

    public InlineKeyboardButton(String text) {
        this.text = text;
    }

    public InlineKeyboardButton url(String url) {
        this.url = url;
        return this;
    }

    public InlineKeyboardButton loginUrl(LoginUrl loginUrl) {
        login_url = loginUrl;
        return this;
    }

    public InlineKeyboardButton callbackData(String callbackData) {
        callback_data = callbackData;
        return this;
    }

    public InlineKeyboardButton switchInlineQuery(String switchInlineQuery) {
        switch_inline_query = switchInlineQuery;
        return this;
    }

    public InlineKeyboardButton switchInlineQueryCurrentChat(String switchInlineQueryCurrentChat) {
        switch_inline_query_current_chat = switchInlineQueryCurrentChat;
        return this;
    }

    public InlineKeyboardButton switchInlineQueryChosenChat(SwitchInlineQueryChosenChat switchInlineQueryChosenChat) {
        switch_inline_query_chosen_chat = switchInlineQueryChosenChat;
        return this;
    }

    public InlineKeyboardButton callbackGame(String callbackGame) {
        callback_game = new CallbackGame();
        return this;
    }

    public InlineKeyboardButton pay() {
        this.pay = true;
        return this;
    }

    public InlineKeyboardButton webApp(WebAppInfo webApp) {
        this.web_app = webApp;
        return this;
    } 

    public String text() {
        return text;
    }

    public String url() {
        return url;
    }

    public String callbackData() {
        return callback_data;
    }

    public String switchInlineQuery() {
        return switch_inline_query;
    }

    public String switchInlineQueryCurrentChat() {
        return switch_inline_query_current_chat;
    }

    public SwitchInlineQueryChosenChat switchInlineQueryChosenChat() {
        return switch_inline_query_chosen_chat;
    }

    public CallbackGame callbackGame() {
        return callback_game;
    }

    public boolean isPay() {
        return pay != null ? pay : false;
    }

    public WebAppInfo webApp() {
        return web_app;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InlineKeyboardButton that = (InlineKeyboardButton) o;
        return Objects.equals(text, that.text) &&
                Objects.equals(url, that.url) &&
                Objects.equals(login_url, that.login_url) &&
                Objects.equals(callback_data, that.callback_data) &&
                Objects.equals(switch_inline_query, that.switch_inline_query) &&
                Objects.equals(switch_inline_query_current_chat, that.switch_inline_query_current_chat) &&
                Objects.equals(switch_inline_query_chosen_chat, that.switch_inline_query_chosen_chat) &&
                Objects.equals(callback_game, that.callback_game) &&
                Objects.equals(pay, that.pay) &&
                Objects.equals(web_app, that.web_app);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, url, login_url, callback_data, switch_inline_query, switch_inline_query_current_chat, switch_inline_query_chosen_chat, callback_game, pay, web_app);
    }

    @Override
    public String toString() {
        return "InlineKeyboardButton{" +
                "text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", login_url=" + login_url +
                ", callback_data='" + callback_data + '\'' +
                ", switch_inline_query='" + switch_inline_query + '\'' +
                ", switch_inline_query_current_chat='" + switch_inline_query_current_chat + '\'' +
                ", switch_inline_query_chosen_chat='" + switch_inline_query_chosen_chat + '\'' +
                ", callback_game=" + callback_game +
                ", pay=" + pay +
                ", web_app=" + web_app +
                '}';
    }
}
