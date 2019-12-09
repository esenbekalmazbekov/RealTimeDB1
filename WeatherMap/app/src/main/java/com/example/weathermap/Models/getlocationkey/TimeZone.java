package com.example.weathermap.Models.getlocationkey;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeZone implements Parcelable {


    @SerializedName("Name")
    @Expose
    private String name;

    protected TimeZone(Parcel in) {
        name = in.readString();
    }

    public static final Creator<TimeZone> CREATOR = new Creator<TimeZone>() {
        @Override
        public TimeZone createFromParcel(Parcel in) {
            return new TimeZone(in);
        }

        @Override
        public TimeZone[] newArray(int size) {
            return new TimeZone[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}