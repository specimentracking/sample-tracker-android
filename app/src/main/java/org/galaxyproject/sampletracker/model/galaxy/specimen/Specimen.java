package org.galaxyproject.sampletracker.model.galaxy.specimen;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;

import org.galaxyproject.sampletracker.model.galaxy.AbstractResponse;

/**
 * Model object of a single specimen.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class Specimen extends AbstractResponse implements Parcelable {

    public static final Specimen from(String barcode) {
        Specimen specimen = new Specimen();
        specimen.setBarcode(barcode);
        specimen.setSampleData(SampleData.from((String) null));
        return specimen;
    }

    public static final Specimen from(String barcode, String parentId) {
        Specimen specimen = new Specimen();
        specimen.setBarcode(barcode);
        specimen.setSampleData(SampleData.from(parentId));
        return specimen;
    }

    public static final Specimen from(Specimen original) {
        Preconditions.checkNotNull(original);

        Specimen specimen = new Specimen();
        specimen.setId(original.getId());
        specimen.setBarcode(original.getBarcode());
        specimen.setSampleData(SampleData.from(original.getSampleData()));
        return specimen;
    }

    @SerializedName("id") private String id;
    @SerializedName("bar_code") private String barcode;
    @SerializedName("sample_data") private SampleData sampleData;

    public Specimen() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public SampleData getSampleData() {
        return sampleData;
    }

    public void setSampleData(SampleData sampleData) {
        this.sampleData = sampleData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.barcode);
        dest.writeParcelable(this.sampleData, flags);
    }

    private Specimen(Parcel in) {
        this.id = in.readString();
        this.barcode = in.readString();
        this.sampleData = in.readParcelable(SampleData.class.getClassLoader());
    }

    public static final Creator<Specimen> CREATOR = new Creator<Specimen>() {
        public Specimen createFromParcel(Parcel source) {
            return new Specimen(source);
        }

        public Specimen[] newArray(int size) {
            return new Specimen[size];
        }
    };
}
