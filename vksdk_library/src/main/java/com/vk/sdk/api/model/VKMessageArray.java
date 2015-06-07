package com.vk.sdk.api.model;

import android.os.Parcel;

/**
 * Array of VKMessage
 * Created by alex_xpert on 29.01.14.
 */
public class VKMessageArray extends VKApiArray<VKMessage> {
    @Override
    protected VKMessage createObject() {
        return new VKMessage();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
