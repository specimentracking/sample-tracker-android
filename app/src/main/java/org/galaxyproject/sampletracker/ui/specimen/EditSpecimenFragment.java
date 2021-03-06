package org.galaxyproject.sampletracker.ui.specimen;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.logic.galaxy.SpecimenResourceController;
import org.galaxyproject.sampletracker.model.galaxy.AbstractResponse;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SampleData;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenLocation;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenType;
import org.galaxyproject.sampletracker.ui.component.DimFragment;
import org.galaxyproject.sampletracker.ui.component.PendingDialogFragment;
import org.galaxyproject.sampletracker.util.Toasts;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.util.Ln;

/**
 * Fragment for creating editing a specimen that already exists on server.
 *
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class EditSpecimenFragment extends AbstractSpecimenFragment implements Callback<Specimen> {

    public static AbstractSpecimenFragment create(Specimen specimen) {
        return create(new EditSpecimenFragment(), specimen);
    }

    @Inject private SpecimenResourceController mSpecimenController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.frg_specimen_edit, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Go to parent button only for specimens with an ancestor
        String parentId = getOriginalSpecimen().getSampleData().getParentId();
        menu.add(Menu.NONE, R.id.menu_parent, Menu.NONE, R.string.specimen_ancestor).setShowAsActionFlags(
                MenuItem.SHOW_AS_ACTION_ALWAYS).setVisible(!TextUtils.isEmpty(parentId));

        // Specimen details panel
        menu.add(Menu.NONE, R.id.menu_details, Menu.NONE, R.string.specimen_details).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_details:
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.setCustomAnimations(android.R.animator.fade_in, 0, 0, android.R.animator.fade_out);
                ft.add(android.R.id.content, DimFragment.create()).addToBackStack(null);

                SpecimenDetailsFragment fragment = SpecimenDetailsFragment.create(getOriginalSpecimen());
                ft.setCustomAnimations(R.animator.slide_in_right, 0, 0, R.animator.slide_out_right);
                ft.add(android.R.id.content, fragment).commit();

                return true;
            case R.id.menu_parent:
                String parentId = getOriginalSpecimen().getSampleData().getParentId();
                startActivity(SpecimenDetailActivity.queryIdIntent(parentId));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected boolean isModelValid(Specimen specimen) {
        // Only state or location may change
        SampleData sampleData = specimen.getSampleData();
        SpecimenLocation location = sampleData.getLocation();
        SpecimenType type = sampleData.getType();
        String state = sampleData.getState();
        if (state == null) {
            return false; // State is required
        }

        // Values must change
        SampleData originalSampleData = getOriginalSpecimen().getSampleData();
        SpecimenLocation originalLocation = originalSampleData.getLocation();
        SpecimenType originalType = originalSampleData.getType();
        String originalState = originalSampleData.getState();

        return !nullEq(location, originalLocation) || !nullEq(type, originalType) || !state.equals(originalState);
    }

    @Override
    protected void sendModel(Specimen specimen) {
        PendingDialogFragment.showPendingDialog(getFragmentManager());
        mSpecimenController.update(specimen, this);
    }

    @Override
    public void success(Specimen specimen, Response response) {
        PendingDialogFragment.hidePendingDialog(getFragmentManager());
        Toasts.showLong(R.string.net_specimen_updated);
        getActivity().onBackPressed();
    }

    @Override
    public void failure(RetrofitError error) {
        PendingDialogFragment.hidePendingDialog(getFragmentManager());
        try {
            if (error.getBody() instanceof AbstractResponse) {
                Toasts.showLong(((AbstractResponse) error.getBody()).getErrorMessage());
            } else {
                throw new IllegalStateException("Invalid response received");
            }
        } catch (Exception e) {
            Ln.w(error, "Unexpected error");
            String msg = error.getMessage();
            if (TextUtils.isEmpty(msg)) {
                Toasts.showLong(R.string.net_error_unknown);
            } else {
                Toasts.showLong(msg);
            }
        }
    }

    private boolean nullEq(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        } else {
            return o1.equals(o2);
        }
    }
}
