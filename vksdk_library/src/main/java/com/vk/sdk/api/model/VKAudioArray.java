package com.vk.sdk.api.model;

import android.os.Parcel;

/**
 * Array of VKAudio
 * Created by alex_xpert on 28.01.14.
 */
public class VKAudioArray extends VKApiArray<VKAudio> {
    @Override
    protected VKAudio createObject() {
        return new VKAudio();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
