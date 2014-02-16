package org.galaxyproject.sampletracker.ui.specimen;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SampleData;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenLocation;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenType;
import org.galaxyproject.sampletracker.ui.core.BaseFragment;
import org.galaxyproject.sampletracker.ui.picker.LocationPickerActivity;
import org.galaxyproject.sampletracker.ui.picker.StatePickerActivity;
import org.galaxyproject.sampletracker.ui.picker.TypePickerActivity;

import roboguice.inject.InjectResource;
import roboguice.util.Ln;

/**
 * Common logic for all specimen fragments - new, existing, derivative.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
abstract class AbstractSpecimenFragment extends BaseFragment implements OnClickListener {

    private static final String EXTRA_SPECIMEN = "specimen";

    protected static final AbstractSpecimenFragment create(AbstractSpecimenFragment fragment, Specimen specimen) {
        Preconditions.checkNotNull(fragment);
        Preconditions.checkNotNull(specimen);

        Bundle args = new Bundle(1);
        args.putParcelable(EXTRA_SPECIMEN, specimen);
        fragment.setArguments(args);
        return fragment;
    }

    @InjectResource(R.string.glb_no_value) private String mNoValue;

    private TextView mLocationValue;
    private TextView mTypeValue;
    private TextView mStateValue;
    private CheckedTextView mFlag1;
    private CheckedTextView mFlag2;
    private CheckedTextView mFlag3;
    private CheckedTextView mFlag4;
    private CheckedTextView mFlag5;
    private Button mSendButton;

    private Specimen mSpecimen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preconditions.checkArgument(getArguments() != null && getArguments().containsKey(EXTRA_SPECIMEN));

        mSpecimen = getArguments().getParcelable(EXTRA_SPECIMEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case R.id.request_specimen_state:
                if (resultCode == Activity.RESULT_OK) {
                    setNewState(data.getStringExtra(StatePickerActivity.EXTRA_STATE));
                }
                break;
            case R.id.request_specimen_type:
                if (resultCode == Activity.RESULT_OK) {
                    setNewType((SpecimenType) data.getParcelableExtra(TypePickerActivity.EXTRA_TYPE));
                }
                break;
            case R.id.request_specimen_location:
                if (resultCode == Activity.RESULT_OK) {
                    setNewLocation((SpecimenLocation) data.getParcelableExtra(LocationPickerActivity.EXTRA_LOCATION));
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        bindModel(mSpecimen);
    }

    private void bindViews(View view) {
        mLocationValue = (TextView) view.findViewById(R.id.location);
        mTypeValue = (TextView) view.findViewById(R.id.type);
        mStateValue = (TextView) view.findViewById(R.id.state);
        mSendButton = (Button) view.findViewById(R.id.send);

        mFlag1 = (CheckedTextView) view.findViewById(R.id.flag_1);
        mFlag2 = (CheckedTextView) view.findViewById(R.id.flag_2);
        mFlag3 = (CheckedTextView) view.findViewById(R.id.flag_3);
        mFlag4 = (CheckedTextView) view.findViewById(R.id.flag_4);
        mFlag5 = (CheckedTextView) view.findViewById(R.id.flag_5);

        bindOnClickListener(view, R.id.set_location);
        bindOnClickListener(view, R.id.set_type);
        bindOnClickListener(view, R.id.set_state);
        bindOnClickListener(view, R.id.derivative);
        mSendButton.setOnClickListener(this);
    }

    private void bindOnClickListener(View view, int buttonId) {
        View button = view.findViewById(buttonId);
        if (button != null) {
            button.setOnClickListener(this);
        }
    }

    private void bindModel(Specimen specimen) {
        SampleData sampleData = specimen.getSampleData();
        SpecimenLocation location = sampleData.getLocation();
        SpecimenType type = sampleData.getType();

        write(mLocationValue, location == null ? null : location.format());
        write(mTypeValue, type == null ? null : type.format());
        write(mStateValue, sampleData.getState());

        writeFlag(mFlag1, R.string.specimen_flag_gen, sampleData.isGenotypeFlag());
        writeFlag(mFlag2, R.string.specimen_flag_hap, sampleData.isHaplotypeFlag());
        writeFlag(mFlag3, R.string.specimen_flag_sgr, sampleData.isSangerSeqFlag());
        writeFlag(mFlag4, R.string.specimen_flag_ngs, sampleData.isNgsSegFlag());
        writeFlag(mFlag5, R.string.specimen_flag_pcr, sampleData.isDdPcrFlag());
    }

    private void write(TextView view, String value) {
        view.setText(TextUtils.isEmpty(value) ? mNoValue : value);
    }

    private void writeFlag(CheckedTextView flagView, int labelId, boolean value) {
        if (flagView != null) {
            flagView.setText(labelId);
            flagView.setChecked(value);
        }
    }

    protected void setNewLocation(SpecimenLocation location) {
        mSpecimen.getSampleData().setLocation(location);
        bindModel(mSpecimen);
        validateModel();
    }

    protected void setNewType(SpecimenType type) {
        mSpecimen.getSampleData().setType(type);
        bindModel(mSpecimen);
        validateModel();
    }

    protected void setNewState(String state) {
        mSpecimen.getSampleData().setState(state);
        bindModel(mSpecimen);
        validateModel();
    }

    private void validateModel() {
        mSendButton.setEnabled(isModelValid(mSpecimen));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_location:
                Intent locationIntent = LocationPickerActivity.showIntent(mSpecimen.getSampleData().getLocation());
                startActivityForResult(locationIntent, R.id.request_specimen_location);
                break;
            case R.id.set_type:
                Intent typeIntent = TypePickerActivity.showIntent(mSpecimen.getSampleData().getType());
                startActivityForResult(typeIntent, R.id.request_specimen_type);
                break;
            case R.id.set_state:
                Intent stateIntent = StatePickerActivity.showIntent(mSpecimen.getSampleData().getState());
                startActivityForResult(stateIntent, R.id.request_specimen_state);
                break;
            case R.id.derivative:
                Fragment fragment = CreateSpecimenFragment.createDerivative(mSpecimen.getBarcode(), mSpecimen.getId());
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(android.R.id.content, fragment).addToBackStack(null).commit();
                break;
            case R.id.send:
                Preconditions.checkState(isModelValid(mSpecimen));
                sendModel(mSpecimen);
                break;
            default:
                Ln.w("Unknown view has been clicked");
        }
    }

    /**
     * Check whether model is valid and can be sent.
     * 
     * @param specimen Model to check
     */
    protected abstract boolean isModelValid(Specimen specimen);

    /**
     * Send valid model to Galaxy server.
     * 
     * @param specimen Model to send
     */
    protected abstract void sendModel(Specimen specimen);
}
