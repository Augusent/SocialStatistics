package com.vk.sdk.api.model;

import android.os.Parcel;

/**
 * Array of VKVideo
 * Created by alex_xpert on 29.01.14.
 */
public class VKVideoArray extends VKApiArray<VKVideo> {
    @Override
    protected VKVideo createObject() {
        return new VKVideo();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
