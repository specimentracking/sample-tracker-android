package org.galaxyproject.sampletracker.ui.specimen;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_TEXT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.logic.preference.PreferenceController;
import org.galaxyproject.sampletracker.logic.preference.UserPreference;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SampleData;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenLocation;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenType;
import org.galaxyproject.sampletracker.ui.core.BaseFragment;
import org.galaxyproject.sampletracker.ui.picker.LocationPickerActivity;
import org.galaxyproject.sampletracker.ui.picker.ParticipantRelationshipPickerActivity;
import org.galaxyproject.sampletracker.ui.picker.StatePickerActivity;
import org.galaxyproject.sampletracker.ui.picker.TextEnterDialog;
import org.galaxyproject.sampletracker.ui.picker.TypePickerActivity;
import org.galaxyproject.sampletracker.ui.scan.ScanActivity;

import roboguice.inject.InjectResource;
import roboguice.util.Ln;

/**
 * Common logic for all specimen fragments - new, existing, derivative.
 *
 * @author Pavel Sveda <xsveda@gmail.com>
 */
abstract class AbstractSpecimenFragment extends BaseFragment implements OnClickListener, TextEnterDialog.OnTextEnteredListener {

    private static final String EXTRA_SPECIMEN = "specimen";

    protected static final AbstractSpecimenFragment create(AbstractSpecimenFragment fragment, Specimen specimen) {
        Preconditions.checkNotNull(fragment);
        Preconditions.checkNotNull(specimen);

        Bundle args = new Bundle(1);
        args.putParcelable(EXTRA_SPECIMEN, specimen);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject private PreferenceController mPreferenceController;
    @InjectResource(R.string.glb_no_value) private String mNoValue;

    private TextView mLocationValue;
    private TextView mTypeValue;
    private TextView mStateValue;
    private TextView mFamilyValue;
    private TextView mParticipantRelationshipValue;
    private TextView mNoteValue;
    private CheckedTextView mFlag1;
    private CheckedTextView mFlag2;
    private CheckedTextView mFlag3;
    private CheckedTextView mFlag4;
    private CheckedTextView mFlag5;
    private Button mSendButton;

    private Specimen mOriginalSpecimen;
    private Specimen mSpecimen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preconditions.checkArgument(getArguments() != null && getArguments().containsKey(EXTRA_SPECIMEN));

        mSpecimen = getArguments().getParcelable(EXTRA_SPECIMEN);
        mOriginalSpecimen = Specimen.from(mSpecimen);
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
            case R.id.request_participant_relationship:
                if (resultCode == Activity.RESULT_OK) {
                    setNewParticipantRelationship(data.getStringExtra(ParticipantRelationshipPickerActivity.EXTRA_RELATIONSHIP));
                }
                break;
            case R.id.request_scan:
                if (resultCode == Activity.RESULT_OK) {
                    String derivativeBarcode = data.getStringExtra(ScanActivity.EXTRA_SCAN_DATA);
                    String parentId = mSpecimen.getId();
                    startActivity(SpecimenDerivativeActivity.showIntent(derivativeBarcode, parentId));
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onTextEntered(int requestCode, CharSequence enteredText) {
        String enteredString = enteredText.toString();
        switch (requestCode) {
            case R.id.request_specimen_family:
                mPreferenceController.putString(UserPreference.LAST_FAMILY_USED, enteredString);
                setNewFamily(enteredString);
                break;
            case R.id.request_note:
                setNewNote(enteredString);
                break;
            default:
                throw new IllegalStateException("Unknown request code");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        bindModel(mSpecimen);
        validateModel();
    }

    private void bindViews(View view) {
        mLocationValue = (TextView) view.findViewById(R.id.location);
        mTypeValue = (TextView) view.findViewById(R.id.type);
        mStateValue = (TextView) view.findViewById(R.id.state);
        mFamilyValue = (TextView) view.findViewById(R.id.family);
        mParticipantRelationshipValue = (TextView) view.findViewById(R.id.participant_relationship);
        mNoteValue = (TextView) view.findViewById(R.id.note);
        mSendButton = (Button) view.findViewById(R.id.send);

        mFlag1 = (CheckedTextView) view.findViewById(R.id.flag_1);
        mFlag2 = (CheckedTextView) view.findViewById(R.id.flag_2);
        mFlag3 = (CheckedTextView) view.findViewById(R.id.flag_3);
        mFlag4 = (CheckedTextView) view.findViewById(R.id.flag_4);
        mFlag5 = (CheckedTextView) view.findViewById(R.id.flag_5);

        bindOnClickListener(view, R.id.set_location);
        bindOnClickListener(view, R.id.set_type);
        bindOnClickListener(view, R.id.set_state);
        bindOnClickListener(view, R.id.set_family);
        bindOnClickListener(view, R.id.set_participant_relationship);
        bindOnClickListener(view, R.id.set_note);
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

        write(mLocationValue, sampleData.getLocationFormatted());
        write(mTypeValue, sampleData.getTypeFormatted());
        write(mStateValue, sampleData.getState());
        write(mFamilyValue, sampleData.getFamily());
        write(mParticipantRelationshipValue, sampleData.getParticipantRelationship());
        write(mNoteValue, sampleData.getNote());

        writeFlag(mFlag1, R.string.specimen_flag_gen, sampleData.isGenotypeFlag());
        writeFlag(mFlag2, R.string.specimen_flag_hap, sampleData.isHaplotypeFlag());
        writeFlag(mFlag3, R.string.specimen_flag_sgr, sampleData.isSangerSeqFlag());
        writeFlag(mFlag4, R.string.specimen_flag_ngs, sampleData.isNgsSegFlag());
        writeFlag(mFlag5, R.string.specimen_flag_pcr, sampleData.isDdPcrFlag());
    }

    private void write(TextView view, String value) {
        if (view != null) {
            view.setText(TextUtils.isEmpty(value) ? mNoValue : value);
        }
    }

    private void writeFlag(CheckedTextView flagView, int labelId, boolean value) {
        if (flagView != null) {
            flagView.setText(labelId);
            flagView.setChecked(value);
        }
    }

    protected Specimen getOriginalSpecimen() {
        return mOriginalSpecimen;
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

    protected void setNewFamily(String family) {
        mSpecimen.getSampleData().setFamily(family);
        bindModel(mSpecimen);
        validateModel();
    }

    protected void setNewParticipantRelationship(String relationship) {
        mSpecimen.getSampleData().setParticipantRelationship(relationship);
        bindModel(mSpecimen);
        validateModel();
    }

    protected void setNewNote(String note) {
        mSpecimen.getSampleData().setNote(note);
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
            case R.id.set_family:
                String lastFamilyValue = mPreferenceController.getString(UserPreference.LAST_FAMILY_USED, null);
                TextEnterDialog familyDialog = TextEnterDialog.create(this, R.id.request_specimen_family,
                        getResources().getString(R.string.specimen_family_title), TYPE_CLASS_NUMBER, lastFamilyValue);
                familyDialog.show(getFragmentManager(), TextEnterDialog.TAG);
                break;
            case R.id.set_participant_relationship:
                Intent relIntent = ParticipantRelationshipPickerActivity.showIntent(mSpecimen.getSampleData().getParticipantRelationship());
                startActivityForResult(relIntent, R.id.request_participant_relationship);
                break;
            case R.id.set_note:
                TextEnterDialog noteDialog = TextEnterDialog.create(this, R.id.request_note,
                        getResources().getString(R.string.specimen_note_title), TYPE_CLASS_TEXT, null);
                noteDialog.show(getFragmentManager(), TextEnterDialog.TAG);
                break;
            case R.id.derivative:
                startActivityForResult(ScanActivity.showIntent(), R.id.request_scan);
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
