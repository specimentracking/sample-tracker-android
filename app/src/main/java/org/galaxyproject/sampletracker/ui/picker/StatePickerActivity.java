package org.galaxyproject.sampletracker.ui.picker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * @author Pavel Sveda <pavel.sveda@cleverlance.com>
 */
@ContentView(R.layout.act_picker_state)
public final class StatePickerActivity extends BaseActivity implements OnItemClickListener {

    public static Intent showIntent() {
        return new Intent(GalaxyApplication.get(), StatePickerActivity.class);
    }

    @InjectView(android.R.id.list) private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        mList.setAdapter(ArrayAdapter.createFromResource(this, R.array.specimen_state_values,
                android.R.layout.simple_list_item_1));
        mList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
