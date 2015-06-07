package com.vk.sdk.api.model;

import android.os.Parcel;

/**
 * Array of VKGroup
 * Created by alex_xpert on 28.01.14.
 */
public class VKGroupArray extends VKApiArray<VKGroup> {
    @Override
    protected VKGroup createObject() {
        return new VKGroup();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
