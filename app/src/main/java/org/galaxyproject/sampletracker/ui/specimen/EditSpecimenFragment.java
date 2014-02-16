package org.galaxyproject.sampletracker.ui.specimen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.logic.galaxy.SpecimenResourceController;
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

    @Inject private SpecimenResourceController mSpecimenController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_specimen_edit, container, false);
    }

    @Override
    protected boolean isModelValid(Specimen specimen) {
        // TODO
        return false;
    }

    @Override
    protected void sendModel(Specimen specimen) {
        // TODO
    }
}
