package org.galaxyproject.sampletracker.ui.specimen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;

/**
 * Fragment for creating editing a specimen that already exists on server.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class EditSpecimenFragment extends AbstractSpecimenFragment {

    public static AbstractSpecimenFragment create(Specimen specimen) {
        return create(new EditSpecimenFragment(), specimen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView view = new TextView(getActivity());
        view.setText(mSpecimen.getBarcode());
        view.setBackgroundResource(R.color.possible_result_points);
        return view;
    }
}
