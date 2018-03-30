package com.example.prashant.materialdesign.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by prashant on 30/3/18.
 */

public class Timeline implements Parcelable {
    String title;
    String subTitle;
    String time;

    protected Timeline(Parcel in) {
        title = in.readString();
        subTitle = in.readString();
        time = in.readString();
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(time);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Timeline> CREATOR = new Creator<Timeline>() {
        @Override
        public Timeline createFromParcel(Parcel in) {
            return new Timeline(in);
        }

        @Override
        public Timeline[] newArray(int size) {
            return new Timeline[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    int status;

    public Timeline() {
    }
}
