package org.galaxyproject.sampletracker.ui.specimen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;
import org.galaxyproject.sampletracker.ui.core.BaseFragment;
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
                    setNewType(data.getStringExtra(TypePickerActivity.EXTRA_TYPE));
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

        view.findViewById(R.id.set_location).setOnClickListener(this);
        view.findViewById(R.id.set_type).setOnClickListener(this);
        view.findViewById(R.id.set_state).setOnClickListener(this);
        mSendButton.setOnClickListener(this);
    }

    private void bindModel(Specimen specimen) {
        write(mLocationValue, specimen.getSampleData().getLocation());
        write(mTypeValue, specimen.getSampleData().getType());
        write(mStateValue, specimen.getSampleData().getState());
    }

    private void write(TextView view, String value) {
        view.setText(TextUtils.isEmpty(value) ? mNoValue : value);
    }

    protected void setNewLocation(String location) {
        mSpecimen.getSampleData().setLocation(location);
        bindModel(mSpecimen);
        validateModel();
    }

    protected void setNewType(String type) {
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
                // TODO
                break;
            case R.id.set_type:
                Intent typeIntent = TypePickerActivity.showIntent(mSpecimen.getSampleData().getType());
                startActivityForResult(typeIntent, R.id.request_specimen_type);
                break;
            case R.id.set_state:
                Intent stateIntent = StatePickerActivity.showIntent(mSpecimen.getSampleData().getState());
                startActivityForResult(stateIntent, R.id.request_specimen_state);
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
