package org.galaxyproject.sampletracker.ui.specimen;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SampleData;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;
import org.galaxyproject.sampletracker.ui.core.BaseFragment;
import org.galaxyproject.sampletracker.util.DateTimeUtils;
import org.galaxyproject.sampletracker.util.ViewUtils;

import java.util.Date;

/**
 * Detailed read-only information about a specimen.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class SpecimenDetailsFragment extends BaseFragment {

    private static final String EXTRA_SPECIMEN = "specimen";

    protected static final SpecimenDetailsFragment create(Specimen specimen) {
        Preconditions.checkNotNull(specimen);

        SpecimenDetailsFragment fragment = new SpecimenDetailsFragment();
        Bundle args = new Bundle(1);
        args.putParcelable(EXTRA_SPECIMEN, specimen);
        fragment.setArguments(args);
        return fragment;
    }

    private Specimen mSpecimen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preconditions.checkArgument(getArguments() != null && getArguments().containsKey(EXTRA_SPECIMEN));
        mSpecimen = getArguments().getParcelable(EXTRA_SPECIMEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.frg_specimen_details, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(Menu.NONE, R.id.menu_close, Menu.NONE, R.string.glb_close).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_close:
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SampleData sampleData = mSpecimen.getSampleData();

        write(view, R.id.family, sampleData.getFamily());
        write(view, R.id.sex, sampleData.getSex());
        write(view, R.id.participant_relationship, sampleData.getParticipantRelationship());
        write(view, R.id.participant_dob, sampleData.getParticipantDob());
        write(view, R.id.derivative, sampleData.getParentId() == null ? R.string.glb_no : R.string.glb_yes);
        write(view, R.id.create_time, mSpecimen.getCreateTime());
        write(view, R.id.update_time, mSpecimen.getUpdateTime());
        write(view, R.id.collection_date, sampleData.getCollectionDate());
        write(view, R.id.sent_date, sampleData.getSentDate());
        write(view, R.id.note, sampleData.getNote());
    }

    private void write(View root, int viewId, int resId) {
        write(root, viewId, getString(resId));
    }

    private void write(View root, int viewId, Date date) {
        write(root, viewId, DateTimeUtils.formatAsDateTime(date));
    }

    private void write(View root, int viewId, String value) {
        TextView view = (TextView) root.findViewById(viewId);
        ViewUtils.conditionalGone(!TextUtils.isEmpty(value), (View) view.getParent());
        view.setText(value);
    }
}
