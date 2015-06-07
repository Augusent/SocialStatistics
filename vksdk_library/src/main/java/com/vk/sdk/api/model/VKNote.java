package com.vk.sdk.api.model;

import android.os.Parcel;

/**
 * Note
 * Created by alex_xpert on 29.01.14.
 */
public class VKNote extends VKApiModel {
    public long id; // идентификатор заметки
    public long user_id; // идентификатор владельца заметки
    public String title; // заголовок заметки
    public String text; // текст заметки
    public long date; // дата создания заметки в формате unixtime
    public int comments; // количество комментариев
    public int read_comments; // количество прочитанных комментариев (только при запросе информации о заметке текущего пользователя).

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
