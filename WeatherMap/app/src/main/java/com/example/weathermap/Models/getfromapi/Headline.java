
package com.example.weathermap.Models.getfromapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Headline implements Parcelable {

    @SerializedName("EffectiveDate")
    @Expose
    private String effectiveDate;
    @SerializedName("EffectiveEpochDate")
    @Expose
    private Integer effectiveEpochDate;
    @SerializedName("Severity")
    @Expose
    private Integer severity;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("EndEpochDate")
    @Expose
    private Integer endEpochDate;
    @SerializedName("MobileLink")
    @Expose
    private String mobileLink;
    @SerializedName("Link")
    @Expose
    private String link;

    private Headline(Parcel in) {
        effectiveDate = in.readString();
        if (in.readByte() == 0) {
            effectiveEpochDate = null;
        } else {
            effectiveEpochDate = in.readInt();
        }
        if (in.readByte() == 0) {
            severity = null;
        } else {
            severity = in.readInt();
        }
        text = in.readString();
        category = in.readString();
        endDate = in.readString();
        if (in.readByte() == 0) {
            endEpochDate = null;
        } else {
            endEpochDate = in.readInt();
        }
        mobileLink = in.readString();
        link = in.readString();
    }

    public static final Creator<Headline> CREATOR = new Creator<Headline>() {
        @Override
        public Headline createFromParcel(Parcel in) {
            return new Headline(in);
        }

        @Override
        public Headline[] newArray(int size) {
            return new Headline[size];
        }
    };

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Integer getEffectiveEpochDate() {
        return effectiveEpochDate;
    }

    public void setEffectiveEpochDate(Integer effectiveEpochDate) {
        this.effectiveEpochDate = effectiveEpochDate;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getEndEpochDate() {
        return endEpochDate;
    }

    public void setEndEpochDate(Integer endEpochDate) {
        this.endEpochDate = endEpochDate;
    }

    public String getMobileLink() {
        return mobileLink;
    }

    public void setMobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(effectiveDate);
        if (effectiveEpochDate == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(effectiveEpochDate);
        }
        if (severity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(severity);
        }
        parcel.writeString(text);
        parcel.writeString(category);
        parcel.writeString(endDate);
        if (endEpochDate == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(endEpochDate);
        }
        parcel.writeString(mobileLink);
        parcel.writeString(link);
    }
}
