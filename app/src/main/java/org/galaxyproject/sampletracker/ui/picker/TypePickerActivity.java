package org.galaxyproject.sampletracker.ui.picker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenType;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.util.List;

/**
 * Picker of specimen type divided to material type, acid type and acid sub type.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_picker_type)
public final class TypePickerActivity extends BaseActivity {

    public static final String EXTRA_TYPE = "type";

    public static Intent showIntent(String currentType) {
        Intent intent = new Intent(GalaxyApplication.get(), TypePickerActivity.class);
        if (!TextUtils.isEmpty(currentType)) {
            intent.putExtra(EXTRA_TYPE, currentType);
        }
        return intent;
    }

    @InjectExtra(value = EXTRA_TYPE, optional = true) private String mCurrentType;
    @InjectView(R.id.button_11) private CheckedTextView mButton11;
    @InjectView(R.id.button_12) private CheckedTextView mButton12;
    @InjectView(R.id.button_13) private CheckedTextView mButton13;
    @InjectView(R.id.button_21) private CheckedTextView mButton21;
    @InjectView(R.id.button_22) private CheckedTextView mButton22;
    @InjectView(R.id.button_23) private CheckedTextView mButton23;
    @InjectView(R.id.button_31) private CheckedTextView mButton31;
    @InjectView(R.id.button_32) private CheckedTextView mButton32;
    @InjectView(R.id.button_33) private CheckedTextView mButton33;

    private final List<CheckedTextView> mMaterialButtons = Lists.newArrayList();
    private String mCurrentMaterialType;
    private String mCurrentAcidType;
    private String mCurrentAcidSubType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        String[] currentTypeValues = SpecimenType.parse(mCurrentType);
        mCurrentMaterialType = currentTypeValues[0];
        mCurrentAcidType = currentTypeValues[1];
        mCurrentAcidSubType = currentTypeValues[2];

        initMaterialGroup(mCurrentMaterialType);

    }

    private void initMaterialGroup(String currentMaterialType) {
        mMaterialButtons.add(mButton11);
        mMaterialButtons.add(mButton12);
        mMaterialButtons.add(mButton13);
        mMaterialButtons.add(mButton21);
        mMaterialButtons.add(mButton22);
        mMaterialButtons.add(mButton23);
        mMaterialButtons.add(mButton31);
        mMaterialButtons.add(mButton32);
        mMaterialButtons.add(mButton33);

        List<String> mats = Lists.newArrayList(getResources().getStringArray(R.array.specimen_type_material_values));
        Preconditions.checkState(mMaterialButtons.size() == mats.size());

        for (int i = 0; i < mats.size(); i++) {
            CheckedTextView button = mMaterialButtons.get(i);
            String materialType = mats.get(i);
            button.setText(materialType);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (CheckedTextView button : mMaterialButtons) {
                        button.setChecked(false);
                    }
                    ((CheckedTextView) v).setChecked(true);
                }
            });

            if (materialType.equals(currentMaterialType)) {
                button.setChecked(true);
            }
        }
    }
}
