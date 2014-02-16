package org.galaxyproject.sampletracker.ui.specimen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.common.base.Preconditions;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;

import roboguice.inject.InjectExtra;

/**
 * Detail of single derivative specimen.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class SpecimenDerivativeActivity extends BaseActivity {

    /** Barcode value of the specimen */
    private static final String EXTRA_BARCODE = "barcode";

    /** ID of parent specimen */
    private static final String EXTRA_PARENT = "parent";

    public static final Intent showIntent(String barcode, String parentId) {
        Preconditions.checkArgument(!TextUtils.isEmpty(barcode));
        Preconditions.checkArgument(!TextUtils.isEmpty(parentId));

        Intent intent = new Intent(GalaxyApplication.get(), SpecimenDerivativeActivity.class);
        intent.putExtra(EXTRA_BARCODE, barcode);
        intent.putExtra(EXTRA_PARENT, parentId);
        return intent;
    }

    @InjectExtra(EXTRA_BARCODE) private String mBarcode;
    @InjectExtra(EXTRA_PARENT) private String mParentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        AbstractSpecimenFragment fragment = CreateSpecimenFragment.createDerivative(mBarcode, mParentId);
        getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }
}
