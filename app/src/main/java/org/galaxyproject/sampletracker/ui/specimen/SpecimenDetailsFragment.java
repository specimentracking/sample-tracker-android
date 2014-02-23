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
import org.galaxyproject.sampletracker.util.ViewUtils;

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

    private TextView mFamilyValue;

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
        bindViews(view);
        bindModel(mSpecimen);
    }

    private void bindViews(View view) {
        mFamilyValue = (TextView) view.findViewById(R.id.family);
    }

    private void bindModel(Specimen specimen) {
        SampleData sampleData = specimen.getSampleData();

        write(mFamilyValue, sampleData.getFamily());
    }

    private void write(TextView view, String value) {
        ViewUtils.conditionalGone(!TextUtils.isEmpty(value), (View) view.getParent());
        view.setText(value);
    }
}
