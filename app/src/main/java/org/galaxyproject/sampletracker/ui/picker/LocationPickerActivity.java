package org.galaxyproject.sampletracker.ui.picker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.logic.preference.PreferenceController;
import org.galaxyproject.sampletracker.logic.preference.UserPreference;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenLocation;
import org.galaxyproject.sampletracker.ui.component.AlphaNumericFilter;
import org.galaxyproject.sampletracker.ui.component.EmptyTextWatcher;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import roboguice.util.Ln;

import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

/**
 * Picker of specimen location.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_picker_location)
public final class LocationPickerActivity extends BaseActivity implements OnClickListener {

    public static final String EXTRA_LOCATION = "location";

    public static Intent showIntent(@Nullable SpecimenLocation currentLocation) {
        Intent intent = new Intent(GalaxyApplication.get(), LocationPickerActivity.class);
        if (currentLocation != null) {
            intent.putExtra(EXTRA_LOCATION, currentLocation);
        }
        return intent;
    }

    @Inject private PreferenceController mPreferenceController;
    @InjectExtra(value = EXTRA_LOCATION, optional = true) private SpecimenLocation mCurrentLocation;
    @InjectView(R.id.fridge) private EditText mFridgeField;
    @InjectView(R.id.shelf) private EditText mShelfField;
    @InjectView(R.id.rack) private EditText mRackField;
    @InjectView(R.id.box) private EditText mBoxField;
    @InjectView(R.id.spot_1) private ListView mSpot1List;
    @InjectView(R.id.spot_2) private ListView mSpot2List;
    @InjectView(R.id.save) private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        // If no location set, use previously used one or empty location
        String lastLocation = mPreferenceController.getString(UserPreference.LAST_LOCATION_USED, null);
        if (lastLocation != null) {
            mCurrentLocation = SpecimenLocation.parse(lastLocation);
        } else {
            mCurrentLocation = SpecimenLocation.create();
        }

        initField(mFridgeField, mCurrentLocation.getFridge());
        initField(mShelfField, mCurrentLocation.getShelf());
        initField(mRackField, mCurrentLocation.getRack());
        initField(mBoxField, mCurrentLocation.getBox());
        initList(mSpot1List, R.array.specimen_location_spot1_values, mCurrentLocation.getSpot1());
        initList(mSpot2List, R.array.specimen_location_spot2_values, mCurrentLocation.getSpot2());

        mSaveButton.setOnClickListener(this);
        checkFormValidity();
    }

    private void initField(EditText field, String initValue) {
        addInputFilter(field, new AlphaNumericFilter());
        field.setText(initValue);
        field.addTextChangedListener(new EmptyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                checkFormValidity();
            }
        });
    }

    private void initList(ListView list, int valuesResource, String initValue) {
        // Initialize list view
        list.setAdapter(ArrayAdapter.createFromResource(this, valuesResource, R.layout.list_item_location_spot));
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkFormValidity();
            }
        });

        // Select current state if set
        if (!TextUtils.isEmpty(initValue)) {
            initValue = initValue.toUpperCase(Locale.US);
            int currentIndex = Lists.newArrayList(getResources().getStringArray(valuesResource)).indexOf(initValue);
            if (currentIndex >= 0) {
                list.setItemChecked(currentIndex, true);
            }
        }
    }

    private void checkFormValidity() {
        boolean fridgeOk = !TextUtils.isEmpty(mFridgeField.getText());
        boolean shelfOk = !TextUtils.isEmpty(mShelfField.getText());
        boolean rackOk = !TextUtils.isEmpty(mRackField.getText());
        boolean boxOk = !TextUtils.isEmpty(mBoxField.getText());
        boolean spot1Ok = readList(mSpot1List) != null;
        boolean spot2Ok = readList(mSpot2List) != null;

        mSaveButton.setEnabled(fridgeOk && shelfOk && rackOk && boxOk && spot1Ok && spot2Ok);
    }

    private void updateModel() {
        mCurrentLocation.setFridge(readField(mFridgeField));
        mCurrentLocation.setShelf(readField(mShelfField));
        mCurrentLocation.setRack(readField(mRackField));
        mCurrentLocation.setBox(readField(mBoxField));
        mCurrentLocation.setSpot1(readList(mSpot1List));
        mCurrentLocation.setSpot2(readList(mSpot2List));
    }

    private String readField(EditText field) {
        return field.getText().toString().toLowerCase(Locale.US);
    }

    private String readList(ListView list) {
        try {
            return (String) list.getAdapter().getItem(list.getCheckedItemPosition());
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                updateModel();

                mPreferenceController.putString(UserPreference.LAST_LOCATION_USED, mCurrentLocation.format());

                Intent data = new Intent();
                data.putExtra(EXTRA_LOCATION, mCurrentLocation);
                setResult(RESULT_OK, data);
                finish();
                break;
            default:
                Ln.w("Unknown view has been clicked");
        }
    }

    /**
     * Adds given {@link InputFilter} to the already added filters.
     * 
     * @param view View to add filter to
     * @param newFilter Filter to add
     */
    private void addInputFilter(TextView view, InputFilter newFilter) {
        InputFilter[] oldFilters = view.getFilters();
        List<InputFilter> list = Lists.newArrayList(oldFilters);
        list.add(newFilter);
        InputFilter[] newFilters = list.toArray(new InputFilter[list.size()]);
        view.setFilters(newFilters);
    }
}
