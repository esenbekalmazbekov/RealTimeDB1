package com.example.weathermap.Models.getlocationkey;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Elevation implements Parcelable {

    @SerializedName("Metric")
    @Expose
    private Metric metric;
    @SerializedName("Imperial")
    @Expose
    private Imperial imperial;

    protected Elevation(Parcel in) {
        metric = in.readParcelable(Metric.class.getClassLoader());
        imperial = in.readParcelable(Imperial.class.getClassLoader());
    }

    public static final Creator<Elevation> CREATOR = new Creator<Elevation>() {
        @Override
        public Elevation createFromParcel(Parcel in) {
            return new Elevation(in);
        }

        @Override
        public Elevation[] newArray(int size) {
            return new Elevation[size];
        }
    };

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Imperial getImperial() {
        return imperial;
    }

    public void setImperial(Imperial imperial) {
        this.imperial = imperial;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(metric, flags);
        dest.writeParcelable(imperial, flags);
    }
}
