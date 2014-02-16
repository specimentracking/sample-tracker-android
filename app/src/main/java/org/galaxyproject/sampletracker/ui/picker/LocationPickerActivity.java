package org.galaxyproject.sampletracker.ui.picker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;

/**
 * Picker of specimen location.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_picker_location)
public final class LocationPickerActivity extends BaseActivity implements OnItemClickListener {

    public static final String EXTRA_STATE = "state";

    public static Intent showIntent(String currentState) {
        Intent intent = new Intent(GalaxyApplication.get(), LocationPickerActivity.class);
        if (!TextUtils.isEmpty(currentState)) {
            intent.putExtra(EXTRA_STATE, currentState);
        }
        return intent;
    }

    @InjectExtra(value = EXTRA_STATE, optional = true) private String mCurrentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        // // Initialize list view
        // int valuesResource = R.array.specimen_state_values;
        // mList.setAdapter(ArrayAdapter.createFromResource(this, valuesResource, R.layout.list_item));
        // mList.setOnItemClickListener(this);
        //
        // // Select current state if set
        // int currentIndex = Lists.newArrayList(getResources().getStringArray(valuesResource)).indexOf(mCurrentState);
        // if (currentIndex >= 0) {
        // mList.setItemChecked(currentIndex, true);
        // }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // String selectedState = (String) mList.getAdapter().getItem(position);
        // Intent data = new Intent();
        // data.putExtra(EXTRA_STATE, selectedState);
        // setResult(RESULT_OK, data);
        // finish();
    }
}
