package com.vk.sdk.api.model;

import android.os.Parcel;

/**
 * Array of VKChat
 * Created by alex_xpert on 29.01.14.
 */
public class VKChatArray extends VKApiArray<VKChat> {
    @Override
    protected VKChat createObject() {
        return new VKChat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
