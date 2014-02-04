package org.galaxyproject.sampletracker.ui.specimen;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;

/**
 * Fragment for creating a completely new specimen based on scanned barcode.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class NewSpecimenFragment extends AbstractSpecimenFragment {

    public static final NewSpecimenFragment create(String barcode) {
        Preconditions.checkArgument(!TextUtils.isEmpty(barcode));

        Bundle args = new Bundle(1);
        args.putParcelable(EXTRA_SPECIMEN, Specimen.from(barcode));

        NewSpecimenFragment fragment = new NewSpecimenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView view = new TextView(getActivity());
        view.setText(mSpecimen.getBarcode());
        view.setBackgroundResource(R.color.result_points);
        return view;
    }
}
