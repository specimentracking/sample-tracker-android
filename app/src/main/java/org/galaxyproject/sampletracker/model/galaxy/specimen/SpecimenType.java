package org.galaxyproject.sampletracker.model.galaxy.specimen;

import android.os.Parcel;
import android.text.TextUtils;

import com.google.common.base.Splitter;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Wraps a logic of specimen type format.<br>
 * Supported format is 'material-acid-sub_acid', only first 'material' value is required.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class SpecimenType implements android.os.Parcelable {

    /**
     * Creates new/empty {@link SpecimenType}.
     */
    public static SpecimenType create() {
        return new SpecimenType(null, null, null);
    }

    /**
     * Creates deep copy of {@link SpecimenType}.
     */
    public static SpecimenType from(SpecimenType type) {
        if (type == null) {
            return null;
        } else {
            return parse(type.format());
        }
    }

    /**
     * Creates a {@link SpecimenType} based on formatted string parsing.
     * 
     * @param formatType Type value to parse
     */
    @Nullable
    public static SpecimenType parse(String formatType) {
        if (TextUtils.isEmpty(formatType)) {
            return null;
        }

        // Split format string to values
        List<String> values = Splitter.on('-').splitToList(formatType);
        int size = values.size();

        // Check valid format
        if (size > 3) {
            throw new IllegalStateException("Invalid specimen type format: " + formatType);
        }

        // Fill single types based on order
        String materialType = null;
        String acidType = null;
        String acidSubType = null;

        // TODO checked correct values
        if (size >= 1) {
            materialType = values.get(0);
        }
        if (size >= 2) {
            acidType = values.get(1);
        }
        if (size >= 3) {
            acidSubType = values.get(2);
        }

        return new SpecimenType(materialType, acidType, acidSubType);
    }

    private String mMaterialType;
    private String mAcidType;
    private String mAcidSubType;

    private SpecimenType(String materialType, String acidType, String acidSubType) {
        mMaterialType = materialType;
        mAcidType = acidType;
        mAcidSubType = acidSubType;
    }

    public String format() {
        if (TextUtils.isEmpty(mMaterialType)) {
            return null;
        }

        StringBuilder formatType = new StringBuilder(mMaterialType);
        if (TextUtils.isEmpty(mAcidType)) {
            return formatType.toString();
        }

        formatType.append('-').append(mAcidType);
        if (TextUtils.isEmpty(mAcidSubType)) {
            return formatType.toString();
        }

        formatType.append('-').append(mAcidSubType);
        return formatType.toString();
    }

    public String getMaterialType() {
        return mMaterialType;
    }

    public void setMaterialType(String materialType) {
        mMaterialType = materialType;
    }

    public void clearMaterialType() {
        mMaterialType = null;
    }

    public String getAcidType() {
        return mAcidType;
    }

    public void setAcidType(String acidType) {
        mAcidType = acidType;
    }

    public void clearAcidType() {
        mAcidType = null;
    }

    public String getAcidSubType() {
        return mAcidSubType;
    }

    public void setAcidSubType(String acidSubType) {
        mAcidSubType = acidSubType;
    }

    public void clearAcidSubType() {
        mAcidSubType = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mMaterialType);
        dest.writeString(this.mAcidType);
        dest.writeString(this.mAcidSubType);
    }

    private SpecimenType(Parcel in) {
        this.mMaterialType = in.readString();
        this.mAcidType = in.readString();
        this.mAcidSubType = in.readString();
    }

    public static final Creator<SpecimenType> CREATOR = new Creator<SpecimenType>() {
        public SpecimenType createFromParcel(Parcel source) {
            return new SpecimenType(source);
        }

        public SpecimenType[] newArray(int size) {
            return new SpecimenType[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || ((Object) this).getClass() != o.getClass())
            return false;

        SpecimenType that = (SpecimenType) o;

        if (mAcidSubType != null ? !mAcidSubType.equals(that.mAcidSubType) : that.mAcidSubType != null)
            return false;
        if (mAcidType != null ? !mAcidType.equals(that.mAcidType) : that.mAcidType != null)
            return false;
        if (mMaterialType != null ? !mMaterialType.equals(that.mMaterialType) : that.mMaterialType != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mMaterialType != null ? mMaterialType.hashCode() : 0;
        result = 31 * result + (mAcidType != null ? mAcidType.hashCode() : 0);
        result = 31 * result + (mAcidSubType != null ? mAcidSubType.hashCode() : 0);
        return result;
    }
}
