package org.galaxyproject.sampletracker.ui.specimen;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;
import org.galaxyproject.sampletracker.ui.core.BaseFragment;

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
    }

    private void bindModel(Specimen specimen) {
        mLocationValue.setText(specimen.getSampleData().getLocation());
        mTypeValue.setText(specimen.getSampleData().getType());
        mStateValue.setText(specimen.getSampleData().getState());
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
                // TODO
                break;
            case R.id.set_state:
                // TODO
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
