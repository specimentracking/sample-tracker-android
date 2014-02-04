package org.galaxyproject.sampletracker.ui.core;

import android.app.Fragment;
import android.app.FragmentManager;
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

    /**
     * @see {@link BaseActivity#replaceFragment(int, Fragment, String)}
     */
    protected void replaceFragment(Fragment fragment) {
        replaceFragment(android.R.id.content, fragment, null);
    }

    /**
     * @see {@link BaseActivity#replaceFragment(int, Fragment, String)}
     */
    protected void replaceFragment(Fragment fragment, String tag) {
        replaceFragment(android.R.id.content, fragment, tag);
    }

    /**
     * @see {@link BaseActivity#replaceFragment(int, Fragment, String)}
     */
    protected void replaceFragment(int containerViewId, Fragment fragment) {
        replaceFragment(containerViewId, fragment, null);
    }

    /**
     * Convenience method for simple replacing fragment with fragment's {@link FragmentManager} and auto commit.
     * 
     * @param containerViewId Identifier of the container whose fragment(s) are to be replaced.
     * @param fragment The new fragment to place in the container.
     * @param tag Optional tag name for the fragment, to later retrieve the fragment with
     *            {@link FragmentManager#findFragmentByTag(String) FragmentManager.findFragmentByTag(String)}.
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
        getFragmentManager().beginTransaction().replace(containerViewId, fragment, tag).commit();
    }
}
