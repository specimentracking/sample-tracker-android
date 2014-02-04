package org.galaxyproject.sampletracker.ui.component;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import org.galaxyproject.sampletracker.R;

/**
 * Fragment filling the activity, so that its content is not clickable, showing pending icon.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public class PendingDialogFragment extends DialogFragment {

    public static final String DIALOG_FRAGMENT_TAG = "PendingDialogFragment";

    public static PendingDialogFragment find(FragmentManager fragmentManager) {
        return (PendingDialogFragment) fragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG);
    }

    public static PendingDialogFragment showPendingDialog(FragmentManager fragmentManager) {
        return showPendingDialog(fragmentManager, android.R.id.content);
    }

    public static PendingDialogFragment showPendingDialog(FragmentManager fragmentManager, int viewId) {
        // hide previously shown dialog
        hidePendingDialog(fragmentManager);

        final PendingDialogFragment pendingDialogFragment = new PendingDialogFragment();

        // The device is smaller, so show the fragment full screen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // For a little polish, specify a transition animation
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        // To make it full screen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity
        transaction.add(viewId, pendingDialogFragment, DIALOG_FRAGMENT_TAG).commitAllowingStateLoss();

        return pendingDialogFragment;
    }

    public static void hidePendingDialog(FragmentManager fragmentManager) {
        final PendingDialogFragment pendingDialogFragment = PendingDialogFragment.find(fragmentManager);
        if (pendingDialogFragment == null) {
            return;
        }

        // The device is smaller, so show the fragment full screen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // For a little polish, specify a transition animation
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        // To make it full screen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity
        transaction.remove(pendingDialogFragment).commitAllowingStateLoss();
    }

    /**
     * The system calls this to get the DialogFragment's layout, regardless of whether it's being displayed as a dialog
     * or an embedded fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_pending, container, false);
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
