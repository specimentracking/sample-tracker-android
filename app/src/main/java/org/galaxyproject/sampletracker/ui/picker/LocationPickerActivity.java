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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
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

/**
 * Picker of specimen location.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_picker_location)
public final class LocationPickerActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

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
    @InjectView(R.id.save) private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        initField(mFridgeField, mCurrentLocation.getFridge());
        initField(mShelfField, mCurrentLocation.getShelf());
        initField(mRackField, mCurrentLocation.getRack());
        initField(mBoxField, mCurrentLocation.getBox());

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

    private void checkFormValidity() {
        boolean fridgeOk = !TextUtils.isEmpty(mFridgeField.getText());
        boolean shelfOk = !TextUtils.isEmpty(mShelfField.getText());
        boolean rackOk = !TextUtils.isEmpty(mRackField.getText());
        boolean boxOk = !TextUtils.isEmpty(mBoxField.getText());

        boolean allOk = fridgeOk && shelfOk && rackOk && boxOk;
        mSaveButton.setEnabled(allOk);
    }

    private void updateModel() {
        mCurrentLocation.setFridge(mFridgeField.getText().toString().toLowerCase(Locale.US));
        mCurrentLocation.setShelf(mShelfField.getText().toString().toLowerCase(Locale.US));
        mCurrentLocation.setRack(mRackField.getText().toString().toLowerCase(Locale.US));
        mCurrentLocation.setBox(mBoxField.getText().toString().toLowerCase(Locale.US));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // String selectedState = (String) mList.getAdapter().getItem(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                updateModel();

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
