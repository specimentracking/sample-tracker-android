package org.galaxyproject.sampletracker.ui.core;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import roboguice.activity.RoboActivity;

/**
 * Parent activity for all others in the app.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public abstract class BaseActivity extends RoboActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(this, parentIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Shows the ActionBar home button for UP navigation.
     */
    protected void showHomeButton() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }
}
