
package com.example.weathermap.Models.getfromapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day implements Parcelable {

    @SerializedName("Icon")
    @Expose
    private Integer icon;
    @SerializedName("IconPhrase")
    @Expose
    private String iconPhrase;
    @SerializedName("HasPrecipitation")
    @Expose
    private Boolean hasPrecipitation;
    @SerializedName("PrecipitationType")
    @Expose
    private String precipitationType;
    @SerializedName("PrecipitationIntensity")
    @Expose
    private String precipitationIntensity;

    private Day(Parcel in) {
        if (in.readByte() == 0) {
            icon = null;
        } else {
            icon = in.readInt();
        }
        iconPhrase = in.readString();
        byte tmpHasPrecipitation = in.readByte();
        hasPrecipitation = tmpHasPrecipitation == 0 ? null : tmpHasPrecipitation == 1;
        precipitationType = in.readString();
        precipitationIntensity = in.readString();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }

    public Boolean getHasPrecipitation() {
        return hasPrecipitation;
    }

    public void setHasPrecipitation(Boolean hasPrecipitation) {
        this.hasPrecipitation = hasPrecipitation;
    }

    public String getPrecipitationType() {
        return precipitationType;
    }

    public void setPrecipitationType(String precipitationType) {
        this.precipitationType = precipitationType;
    }

    public String getPrecipitationIntensity() {
        return precipitationIntensity;
    }

    public void setPrecipitationIntensity(String precipitationIntensity) {
        this.precipitationIntensity = precipitationIntensity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (icon == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(icon);
        }
        parcel.writeString(iconPhrase);
        parcel.writeByte((byte) (hasPrecipitation == null ? 0 : hasPrecipitation ? 1 : 2));
        parcel.writeString(precipitationType);
        parcel.writeString(precipitationIntensity);
    }
}
