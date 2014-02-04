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
                upToParent();
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

    /**
     * Navigates up to parent activity. Activities above parent will be cleared but parent instance will be kept.
     */
    protected void upToParent() {
        Intent parentIntent = NavUtils.getParentActivityIntent(this);
        parentIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NavUtils.navigateUpTo(this, parentIntent);
    }
}
