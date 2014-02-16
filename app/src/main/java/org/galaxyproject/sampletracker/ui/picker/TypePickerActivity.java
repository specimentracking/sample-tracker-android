package org.galaxyproject.sampletracker.ui.picker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenType;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import roboguice.util.Ln;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Picker of specimen type divided to material type, acid type and acid sub type.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_picker_type)
public final class TypePickerActivity extends BaseActivity implements OnClickListener {

    public static final String EXTRA_TYPE = "type";

    public static Intent showIntent(@Nullable SpecimenType currentType) {
        if (currentType == null) {
            currentType = SpecimenType.create();
        }

        Intent intent = new Intent(GalaxyApplication.get(), TypePickerActivity.class);
        intent.putExtra(EXTRA_TYPE, currentType);
        return intent;
    }

    // TODO create a custom component for radios
    // TODO allow acid types to un-check
    @InjectExtra(EXTRA_TYPE) private SpecimenType mCurrentType;
    @InjectView(R.id.button_11) private CheckedTextView mButton11;
    @InjectView(R.id.button_12) private CheckedTextView mButton12;
    @InjectView(R.id.button_13) private CheckedTextView mButton13;
    @InjectView(R.id.button_21) private CheckedTextView mButton21;
    @InjectView(R.id.button_22) private CheckedTextView mButton22;
    @InjectView(R.id.button_23) private CheckedTextView mButton23;
    @InjectView(R.id.button_31) private CheckedTextView mButton31;
    @InjectView(R.id.button_32) private CheckedTextView mButton32;
    @InjectView(R.id.button_33) private CheckedTextView mButton33;
    @InjectView(R.id.acid_type) private RadioGroup mAcidTypeGroup;
    @InjectView(R.id.acid_sub_type) private RadioGroup mAcidSubTypeGroup;
    @InjectView(R.id.save) private Button mSaveButton;

    private final List<CheckedTextView> mMaterialButtons = Lists.newArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        initMaterialGroup();
        initAcidGroup();
        initAcidSubGroup();

        mSaveButton.setOnClickListener(this);

        updateComponentAvailability();
    }

    private void initMaterialGroup() {
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

            // Set button label
            button.setText(materialType);

            // Ensure only one button may be checked
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Un-check all buttons
                    for (CheckedTextView button : mMaterialButtons) {
                        button.setChecked(false);
                    }

                    // Check current one
                    ((CheckedTextView) v).setChecked(true);

                    // Set current value to model
                    mCurrentType.setMaterialType(((CheckedTextView) v).getText().toString());

                    // Enable elements based on material type set
                    updateComponentAvailability();
                }
            });

            // Check current selected material if any
            if (materialType.equals(mCurrentType.getMaterialType())) {
                button.setChecked(true);
            }
        }
    }

    private void initAcidGroup() {
        if (mCurrentType.getAcidType() != null) {
            findButtonWithText(mAcidTypeGroup, mCurrentType.getAcidType()).setChecked(true);
        }
        mAcidTypeGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedButton = (RadioButton) group.findViewById(checkedId);

                // Set current value to model
                mCurrentType.setAcidType(checkedButton.getText().toString());

                // Enable elements based on acid type set
                updateComponentAvailability();
            }
        });
    }

    private void initAcidSubGroup() {
        if (mCurrentType.getAcidSubType() != null) {
            findButtonWithText(mAcidSubTypeGroup, mCurrentType.getAcidSubType()).setChecked(true);
        }
        mAcidSubTypeGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedButton = (RadioButton) group.findViewById(checkedId);

                // Set current value to model
                mCurrentType.setAcidSubType(checkedButton.getText().toString());

                // Enable elements based on acid type set
                updateComponentAvailability();
            }
        });
    }

    private void updateComponentAvailability() {
        enableGroup(mAcidTypeGroup, mCurrentType.getMaterialType() != null); // Material type must be set
        enableGroup(mAcidSubTypeGroup, mCurrentType.getAcidType() != null); // Material type must be set
        mSaveButton.setEnabled(mCurrentType.getMaterialType() != null); // Material type must be set
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                Intent data = new Intent();
                data.putExtra(EXTRA_TYPE, mCurrentType);
                setResult(RESULT_OK, data);
                finish();
                break;
            default:
                Ln.w("Unknown view has been clicked");
        }
    }

    private void enableGroup(RadioGroup group, boolean enabled) {
        for (int i = 0; i < group.getChildCount(); i++) {
            ((RadioButton) group.getChildAt(i)).setEnabled(enabled);
        }
    }

    private RadioButton findButtonWithText(RadioGroup group, String text) {
        Preconditions.checkNotNull(text);

        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton button = (RadioButton) group.getChildAt(i);
            if (text.equals(button.getText())) {
                return button;
            }
        }

        throw new IllegalStateException("No button with text '" + text + "' found");
    }
}
