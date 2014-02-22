package org.galaxyproject.sampletracker.model.galaxy.specimen;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Preconditions;
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

    public static SampleData from(SampleData original) {
        Preconditions.checkNotNull(original);

        SampleData data = new SampleData();
        data.setParentId(original.getParentId());
        data.setState(original.getState());
        data.setType(SpecimenType.from(original.getType()));
        data.setLocation(SpecimenLocation.from(original.getLocation()));
        data.setGenotypeFlag(original.isGenotypeFlag());
        data.setHaplotypeFlag(original.isHaplotypeFlag());
        data.setSangerSeqFlag(original.isSangerSeqFlag());
        data.setNgsSegFlag(original.isNgsSegFlag());
        data.setDdPcrFlag(original.isDdPcrFlag());

        data.setFamily(original.getFamily());
        data.setSex(original.getSex());
        data.setParticipantRelationship(original.getParticipantRelationship());
        data.setParticipantDob(original.getParticipantDob());
        data.setSentDate(original.getSentDate());
        data.setCollectionDate(original.getCollectionDate());
        data.setNote(original.getNote());
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

    @SerializedName("family") private String family;
    @SerializedName("sex") private String sex;
    @SerializedName("participant_relationship") private String participantRelationship;
    @SerializedName("participant_dob") private String participantDob;
    @SerializedName("date_sent") private String sentDate;
    @SerializedName("date_of_collection") private String collectionDate;
    @SerializedName("note") private String note;

    public SampleData() {
    }

    public String getTypeFormatted() {
        return type == null ? null : type.format();
    }

    public String getLocationFormatted() {
        return location == null ? null : location.format();
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

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getParticipantRelationship() {
        return participantRelationship;
    }

    public void setParticipantRelationship(String participantRelationship) {
        this.participantRelationship = participantRelationship;
    }

    public String getParticipantDob() {
        return participantDob;
    }

    public void setParticipantDob(String participantDob) {
        this.participantDob = participantDob;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
        dest.writeString(this.family);
        dest.writeString(this.sex);
        dest.writeString(this.participantRelationship);
        dest.writeString(this.participantDob);
        dest.writeString(this.sentDate);
        dest.writeString(this.collectionDate);
        dest.writeString(this.note);
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
        this.family = in.readString();
        this.sex = in.readString();
        this.participantRelationship = in.readString();
        this.participantDob = in.readString();
        this.sentDate = in.readString();
        this.collectionDate = in.readString();
        this.note = in.readString();
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
