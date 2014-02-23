package org.galaxyproject.sampletracker.ui.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.ui.core.BaseFragment;

/**
 * Dim layer that pops back stack on click.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class DimFragment extends BaseFragment {

    public static final DimFragment create() {
        return new DimFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = new View(getActivity());
        view.setBackgroundColor(getResources().getColor(R.color.grey_light_semi));
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }
}
