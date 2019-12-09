package com.example.weathermap.Models.getlocationkey;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityData implements Parcelable {

    @SerializedName("Key")
    @Expose
    private String key;

    @SerializedName("TimeZone")
    @Expose
    private TimeZone timeZone;

    private CityData(Parcel in) {
        key = in.readString();
        timeZone = in.readParcelable(TimeZone.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeParcelable(timeZone, flags);
    }

    public static final Creator<CityData> CREATOR = new Creator<CityData>() {
        @Override
        public CityData createFromParcel(Parcel in) {
            return new CityData(in);
        }

        @Override
        public CityData[] newArray(int size) {
            return new CityData[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
