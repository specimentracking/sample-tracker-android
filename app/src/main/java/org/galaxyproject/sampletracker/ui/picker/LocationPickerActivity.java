package org.galaxyproject.sampletracker.ui.picker;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenLocation;
import org.galaxyproject.sampletracker.ui.component.AlphaNumericFilter;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.util.List;

/**
 * Picker of specimen location.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_picker_location)
public final class LocationPickerActivity extends BaseActivity implements OnItemClickListener {

    public static final String EXTRA_LOCATION = "location";

    public static Intent showIntent(SpecimenLocation currentLocation) {
        Preconditions.checkNotNull(currentLocation);

        Intent intent = new Intent(GalaxyApplication.get(), LocationPickerActivity.class);
        intent.putExtra(EXTRA_LOCATION, currentLocation);
        return intent;
    }

    @InjectExtra(EXTRA_LOCATION) private SpecimenLocation mCurrentLocation;
    @InjectView(R.id.fridge) private EditText mFridgeField;
    @InjectView(R.id.shelf) private EditText mShelfField;
    @InjectView(R.id.rack) private EditText mRackField;
    @InjectView(R.id.box) private EditText mBoxField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        initFields();

        // // Initialize list view
        // int valuesResource = R.array.specimen_state_values;
        // mList.setAdapter(ArrayAdapter.createFromResource(this, valuesResource, R.layout.list_item));
        // mList.setOnItemClickListener(this);
        //
        // // Select current state if set
        // int currentIndex =
        // Lists.newArrayList(getResources().getStringArray(valuesResource)).indexOf(mCurrentLocation);
        // if (currentIndex >= 0) {
        // mList.setItemChecked(currentIndex, true);
        // }
    }

    private void initFields() {
        addInputFilter(mFridgeField, new AlphaNumericFilter());
        mFridgeField.setText(mCurrentLocation.getFridge());

        addInputFilter(mShelfField, new AlphaNumericFilter());
        mShelfField.setText(mCurrentLocation.getShelf());

        addInputFilter(mRackField, new AlphaNumericFilter());
        mRackField.setText(mCurrentLocation.getRack());

        addInputFilter(mBoxField, new AlphaNumericFilter());
        mBoxField.setText(mCurrentLocation.getBox());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // String selectedState = (String) mList.getAdapter().getItem(position);
        // Intent data = new Intent();
        // data.putExtra(EXTRA_LOCATION, selectedState);
        // setResult(RESULT_OK, data);
        // finish();
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
