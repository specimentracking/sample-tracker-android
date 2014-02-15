package org.galaxyproject.sampletracker.model.galaxy.specimen;

import android.os.Parcel;
import android.os.Parcelable;

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
        return specimen;
    }

    public static final Specimen from(String barcode, String parentId) {
        Specimen specimen = new Specimen();
        specimen.setBarcode(barcode);
        specimen.setParentId(parentId);
        return specimen;
    }

    private String id;
    private String barcode;
    private String parent_id;
    private String state;
    private String type;
    private String location;
    private boolean genotype_flag;
    private boolean haplotype_flag;
    private boolean sanger_seq_flag;
    private boolean nqs_seg_flag;
    private boolean dd_pcr_flag;

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

    public String getParentId() {
        return parent_id;
    }

    public void setParentId(String parentId) {
        this.parent_id = parentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isGenotype() {
        return genotype_flag;
    }

    public void setGenotype(boolean genotype) {
        this.genotype_flag = genotype;
    }

    public boolean isHaplotype() {
        return haplotype_flag;
    }

    public void setHaplotype(boolean haplotype) {
        this.haplotype_flag = haplotype;
    }

    public boolean isSangerSeq() {
        return sanger_seq_flag;
    }

    public void setSangerSeq(boolean sangerSeq) {
        this.sanger_seq_flag = sangerSeq;
    }

    public boolean isNqsSeg() {
        return nqs_seg_flag;
    }

    public void setNqsSeg(boolean nqsSeg) {
        this.nqs_seg_flag = nqsSeg;
    }

    public boolean isDdPcr() {
        return dd_pcr_flag;
    }

    public void setDdPcr(boolean ddPcr) {
        this.dd_pcr_flag = ddPcr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.barcode);
        dest.writeString(this.parent_id);
        dest.writeString(this.state);
        dest.writeString(this.type);
        dest.writeString(this.location);
        dest.writeByte(genotype_flag ? (byte) 1 : (byte) 0);
        dest.writeByte(haplotype_flag ? (byte) 1 : (byte) 0);
        dest.writeByte(sanger_seq_flag ? (byte) 1 : (byte) 0);
        dest.writeByte(nqs_seg_flag ? (byte) 1 : (byte) 0);
        dest.writeByte(dd_pcr_flag ? (byte) 1 : (byte) 0);
    }

    private Specimen(Parcel in) {
        this.id = in.readString();
        this.barcode = in.readString();
        this.parent_id = in.readString();
        this.state = in.readString();
        this.type = in.readString();
        this.location = in.readString();
        this.genotype_flag = in.readByte() != 0;
        this.haplotype_flag = in.readByte() != 0;
        this.sanger_seq_flag = in.readByte() != 0;
        this.nqs_seg_flag = in.readByte() != 0;
        this.dd_pcr_flag = in.readByte() != 0;
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
