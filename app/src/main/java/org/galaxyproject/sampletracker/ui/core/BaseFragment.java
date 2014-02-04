package org.galaxyproject.sampletracker.ui.core;

import android.app.Fragment;
import android.os.Bundle;

import roboguice.RoboGuice;

/**
 * Base fragment that handles RoboGuice injection and other common stuff.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectMembersWithoutViews(this);
    }
}
