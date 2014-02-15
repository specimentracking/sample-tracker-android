package org.galaxyproject.sampletracker.ui.specimen;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Preconditions;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;

/**
 * Fragment for creating a completely new specimen based on scanned barcode or a derivative based on parent specimen.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class CreateSpecimenFragment extends AbstractSpecimenFragment {

    public static AbstractSpecimenFragment createNew(String barcode) {
        Preconditions.checkArgument(!TextUtils.isEmpty(barcode));
        return create(new CreateSpecimenFragment(), Specimen.from(barcode));
    }

    public static AbstractSpecimenFragment createDerivative(String barcode, String parentId) {
        Preconditions.checkArgument(!TextUtils.isEmpty(barcode));
        Preconditions.checkArgument(!TextUtils.isEmpty(parentId));
        return create(new CreateSpecimenFragment(), Specimen.from(barcode, parentId));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_specimen_create, container, false);
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
