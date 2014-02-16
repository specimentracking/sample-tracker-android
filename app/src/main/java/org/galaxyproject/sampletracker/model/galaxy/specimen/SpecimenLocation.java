package org.galaxyproject.sampletracker.model.galaxy.specimen;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Wraps a logic of specimen location format.<br>
 * Supported format is 'fridge_X-shelf_X-rack_X-box_X-spot_X', all required.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class SpecimenLocation implements Parcelable {

    private static final String FRIDGE = "fridge";
    private static final String SHELF = "shelf";
    private static final String RACK = "rack";
    private static final String BOX = "box";
    private static final String SPOT = "spot";

    private static final Set<String> REQUIRED_KEYS = Sets.newHashSet(FRIDGE, SHELF, RACK, BOX, SPOT);

    /**
     * Creates new/empty {@link SpecimenLocation}.
     */
    public static SpecimenLocation create() {
        return new SpecimenLocation(null, null, null, null, null, null);
    }

    /**
     * Creates deep copy of {@link SpecimenLocation}.
     */
    public static SpecimenLocation from(SpecimenLocation location) {
        if (location == null) {
            return null;
        } else {
            return parse(location.format());
        }
    }

    /**
     * Creates a {@link SpecimenLocation} based on formatted string parsing.
     * 
     * @param formatLocation Type value to parse
     */
    @Nullable
    public static SpecimenLocation parse(String formatLocation) {
        if (TextUtils.isEmpty(formatLocation)) {
            return null;
        }

        // Split format string to values
        Map<String, String> values = Splitter.on('-').withKeyValueSeparator('_').split(formatLocation);

        // Check valid format
        Set<String> keys = values.keySet();
        for (String requiredKey : REQUIRED_KEYS) {
            if (!keys.contains(requiredKey)) {
                String msg = "Invalid location, key '" + requiredKey + "' is missing in  '" + formatLocation + "'";
                throw new IllegalStateException(msg);
            }
        }

        // TODO checked correct values

        String fridge = values.get(FRIDGE);
        String shelf = values.get(SHELF);
        String rack = values.get(RACK);
        String box = values.get(BOX);

        String[] spotValues = parseSpot(values.get(SPOT));
        return new SpecimenLocation(fridge, shelf, rack, box, spotValues[0], spotValues[1]);
    }

    @Nonnull
    private static String[] parseSpot(String spot) {
        if (TextUtils.isEmpty(spot)) {
            return new String[] {null, null};
        }

        if (spot.length() != 2) {
            throw new IllegalStateException("Invalid spot format: " + spot);
        }

        return new String[] {String.valueOf(spot.charAt(0)), String.valueOf(spot.charAt(1))};
    }

    private String mFridge;
    private String mShelf;
    private String mRack;
    private String mBox;
    private String mSpot1;
    private String mSpot2;

    public SpecimenLocation(String fridge, String shelf, String rack, String box, String spot1, String spot2) {
        mFridge = fridge;
        mShelf = shelf;
        mRack = rack;
        mBox = box;
        mSpot1 = spot1;
        mSpot2 = spot2;
    }

    public String format() {
        if (mFridge == null && mShelf == null && mRack == null && mBox == null && mSpot1 == null && mSpot2 == null) {
            return null;
        }

        HashMap<String, String> values = Maps.newHashMap();
        values.put(FRIDGE, mFridge);
        values.put(SHELF, mShelf);
        values.put(RACK, mRack);
        values.put(BOX, mBox);
        values.put(SPOT, (mSpot1 == null || mSpot2 == null) ? null : mSpot1 + mSpot2);
        return Joiner.on('-').withKeyValueSeparator("_").useForNull("").join(values);
    }

    public String getFridge() {
        return mFridge;
    }

    public void setFridge(String fridge) {
        mFridge = fridge;
    }

    public String getShelf() {
        return mShelf;
    }

    public void setShelf(String shelf) {
        mShelf = shelf;
    }

    public String getRack() {
        return mRack;
    }

    public void setRack(String rack) {
        mRack = rack;
    }

    public String getBox() {
        return mBox;
    }

    public void setBox(String box) {
        mBox = box;
    }

    public String getSpot1() {
        return mSpot1;
    }

    public void setSpot1(String spot1) {
        mSpot1 = spot1;
    }

    public String getSpot2() {
        return mSpot2;
    }

    public void setSpot2(String spot2) {
        mSpot2 = spot2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFridge);
        dest.writeString(this.mShelf);
        dest.writeString(this.mRack);
        dest.writeString(this.mBox);
        dest.writeString(this.mSpot1);
        dest.writeString(this.mSpot2);
    }

    private SpecimenLocation(Parcel in) {
        this.mFridge = in.readString();
        this.mShelf = in.readString();
        this.mRack = in.readString();
        this.mBox = in.readString();
        this.mSpot1 = in.readString();
        this.mSpot2 = in.readString();
    }

    public static final Creator<SpecimenLocation> CREATOR = new Creator<SpecimenLocation>() {
        public SpecimenLocation createFromParcel(Parcel source) {
            return new SpecimenLocation(source);
        }

        public SpecimenLocation[] newArray(int size) {
            return new SpecimenLocation[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || ((Object) this).getClass() != o.getClass())
            return false;

        SpecimenLocation that = (SpecimenLocation) o;

        if (mBox != null ? !mBox.equals(that.mBox) : that.mBox != null)
            return false;
        if (mFridge != null ? !mFridge.equals(that.mFridge) : that.mFridge != null)
            return false;
        if (mRack != null ? !mRack.equals(that.mRack) : that.mRack != null)
            return false;
        if (mShelf != null ? !mShelf.equals(that.mShelf) : that.mShelf != null)
            return false;
        if (mSpot1 != null ? !mSpot1.equals(that.mSpot1) : that.mSpot1 != null)
            return false;
        if (mSpot2 != null ? !mSpot2.equals(that.mSpot2) : that.mSpot2 != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mFridge != null ? mFridge.hashCode() : 0;
        result = 31 * result + (mShelf != null ? mShelf.hashCode() : 0);
        result = 31 * result + (mRack != null ? mRack.hashCode() : 0);
        result = 31 * result + (mBox != null ? mBox.hashCode() : 0);
        result = 31 * result + (mSpot1 != null ? mSpot1.hashCode() : 0);
        result = 31 * result + (mSpot2 != null ? mSpot2.hashCode() : 0);
        return result;
    }
}
