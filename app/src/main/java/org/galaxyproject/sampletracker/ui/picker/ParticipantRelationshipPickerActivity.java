package org.galaxyproject.sampletracker.ui.picker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.common.collect.Lists;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import javax.annotation.Nullable;

/**
 * Picker of participant relationship.
 *
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_picker_relationship)
public final class ParticipantRelationshipPickerActivity extends BaseActivity implements OnItemClickListener {

    public static final String EXTRA_RELATIONSHIP = "relationship";

    public static Intent showIntent(@Nullable String currentRelationship) {
        Intent intent = new Intent(GalaxyApplication.get(), ParticipantRelationshipPickerActivity.class);
        if (!TextUtils.isEmpty(currentRelationship)) {
            intent.putExtra(EXTRA_RELATIONSHIP, currentRelationship);
        }
        return intent;
    }

    @InjectView(android.R.id.list) private ListView mList;
    @InjectExtra(value = EXTRA_RELATIONSHIP, optional = true) private String mCurrentRelationship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        // Initialize list view
        int valuesResource = R.array.specimen_participant_relationship_values;
        mList.setAdapter(ArrayAdapter.createFromResource(this, valuesResource, R.layout.list_item));
        mList.setOnItemClickListener(this);

        // Select current state if set
        int currentIndex = Lists.newArrayList(getResources().getStringArray(valuesResource)).indexOf(mCurrentRelationship);
        if (currentIndex >= 0) {
            mList.setItemChecked(currentIndex, true);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedState = (String) mList.getAdapter().getItem(position);
        Intent data = new Intent();
        data.putExtra(EXTRA_RELATIONSHIP, selectedState);
        setResult(RESULT_OK, data);
        finish();
    }
}
