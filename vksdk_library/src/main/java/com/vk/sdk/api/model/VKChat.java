package com.vk.sdk.api.model;

import android.os.Parcel;

/**
 * Chat
 * Created by alex_xpert on 29.01.14.
 */
public class VKChat extends VKApiModel {
    public long id; // идентификатор беседы
    public String type; // тип диалога
    public String title; // название беседы
    public long admin_id; // идентификатор пользователя, который является создателем беседы
    public String users; // список идентификаторов участников беседы

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
