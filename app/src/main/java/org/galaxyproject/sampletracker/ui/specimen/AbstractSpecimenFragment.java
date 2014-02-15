package org.galaxyproject.sampletracker.ui.specimen;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

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

    protected Specimen mSpecimen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preconditions.checkArgument(getArguments() != null && getArguments().containsKey(EXTRA_SPECIMEN));

        mSpecimen = getArguments().getParcelable(EXTRA_SPECIMEN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.project_id: // TODO
                break;
            default:
                Ln.w("Unknown view has been clicked");
        }
    }
}
