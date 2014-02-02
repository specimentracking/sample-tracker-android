package org.galaxyproject.sampletracker.ui.scan;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.android.CaptureActivity;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.util.Toasts;

import roboguice.util.Ln;

/**
 * Activity for scanning Aztec code.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class ScanActivity extends CaptureActivity {

    /** Scanned data as a result for calling activity */
    public static final String EXTRA_SCAN_DATA = "galaxy:scan_data";

    public static final Intent showIntent() {
        return new Intent(GalaxyApplication.get(), ScanActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_scan);

        // Rotate hint text so it looks like portrait
        RotateAnimation animation = new RotateAnimation(-90, -90, //
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(0);
        animation.setFillAfter(true);
        findViewById(R.id.status_view).startAnimation(animation);
    }

    @Override
    protected void onScanComplete(BarcodeFormat codeType, String codeData) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SCAN_DATA, codeData);
        Toasts.showShort(codeData);
    }

    @Override
    protected void onError(Exception e) {
        Ln.w(e, "Error while scanning");
        Toasts.showLong(R.string.scan_error);
        finish();
    }
}
