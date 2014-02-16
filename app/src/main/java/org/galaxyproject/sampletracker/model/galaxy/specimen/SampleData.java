package org.galaxyproject.sampletracker.model.galaxy.specimen;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Model object of a single specimen.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class SampleData implements Parcelable {

    public static final SampleData from(String parentId) {
        SampleData data = new SampleData();
        data.setParentId(parentId);
        return data;
    }

    @SerializedName("parent_id") private String parentId;
    @SerializedName("state") private String state;
    @SerializedName("type") private SpecimenType type;
    @SerializedName("location") private SpecimenLocation location;
    @SerializedName("genotype_flag") private boolean genotypeFlag;
    @SerializedName("haplotype_flag") private boolean haplotypeFlag;
    @SerializedName("sanger_seq_flag") private boolean sangerSeqFlag;
    @SerializedName("ngs_seg_flag") private boolean ngsSegFlag;
    @SerializedName("dd_pcr_flag") private boolean ddPcrFlag;

    public SampleData() {
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public SpecimenType getType() {
        return type;
    }

    public void setType(SpecimenType type) {
        this.type = type;
    }

    public SpecimenLocation getLocation() {
        return location;
    }

    public void setLocation(SpecimenLocation location) {
        this.location = location;
    }

    public boolean isGenotypeFlag() {
        return genotypeFlag;
    }

    public void setGenotypeFlag(boolean genotypeFlag) {
        this.genotypeFlag = genotypeFlag;
    }

    public boolean isHaplotypeFlag() {
        return haplotypeFlag;
    }

    public void setHaplotypeFlag(boolean haplotypeFlag) {
        this.haplotypeFlag = haplotypeFlag;
    }

    public boolean isSangerSeqFlag() {
        return sangerSeqFlag;
    }

    public void setSangerSeqFlag(boolean sangerSeqFlag) {
        this.sangerSeqFlag = sangerSeqFlag;
    }

    public boolean isNgsSegFlag() {
        return ngsSegFlag;
    }

    public void setNgsSegFlag(boolean ngsSegFlag) {
        this.ngsSegFlag = ngsSegFlag;
    }

    public boolean isDdPcrFlag() {
        return ddPcrFlag;
    }

    public void setDdPcrFlag(boolean ddPcrFlag) {
        this.ddPcrFlag = ddPcrFlag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parentId);
        dest.writeString(this.state);
        dest.writeParcelable(this.type, flags);
        dest.writeParcelable(this.location, flags);
        dest.writeByte(genotypeFlag ? (byte) 1 : (byte) 0);
        dest.writeByte(haplotypeFlag ? (byte) 1 : (byte) 0);
        dest.writeByte(sangerSeqFlag ? (byte) 1 : (byte) 0);
        dest.writeByte(ngsSegFlag ? (byte) 1 : (byte) 0);
        dest.writeByte(ddPcrFlag ? (byte) 1 : (byte) 0);
    }

    private SampleData(Parcel in) {
        this.parentId = in.readString();
        this.state = in.readString();
        this.type = in.readParcelable(SpecimenType.class.getClassLoader());
        this.location = in.readParcelable(SpecimenLocation.class.getClassLoader());
        this.genotypeFlag = in.readByte() != 0;
        this.haplotypeFlag = in.readByte() != 0;
        this.sangerSeqFlag = in.readByte() != 0;
        this.ngsSegFlag = in.readByte() != 0;
        this.ddPcrFlag = in.readByte() != 0;
    }

    public static final Creator<SampleData> CREATOR = new Creator<SampleData>() {
        public SampleData createFromParcel(Parcel source) {
            return new SampleData(source);
        }

        public SampleData[] newArray(int size) {
            return new SampleData[size];
        }
    };
}
