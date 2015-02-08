package org.galaxyproject.sampletracker.ui.picker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.common.base.Preconditions;

import org.galaxyproject.sampletracker.R;

/**
 * Dialog for getting text inputs of defined format.
 *
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class TextEnterDialog extends DialogFragment implements DialogInterface.OnClickListener {

    public interface OnTextEnteredListener {
        public void onTextEntered(int requestCode, CharSequence enteredText);
    }

    public static final String TAG = "TextEnterDialog";

    private static final String ARG_TITLE = "TextEnterDialog_title";
    private static final String ARG_INPUT_TYPE = "TextEnterDialog_inputType";
    private static final String ARG_DEFAULT_VALUE = "TextEnterDialog_defaultValue";

    public static TextEnterDialog create(Fragment targetFragment, int requestCode, String title, int inputType, String defaultValue) {
        Preconditions.checkArgument(targetFragment instanceof OnTextEnteredListener);
        TextEnterDialog dialog = new TextEnterDialog();
        dialog.setTargetFragment(targetFragment, requestCode);
        Bundle args = new Bundle(3);
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_INPUT_TYPE, inputType);
        args.putString(ARG_DEFAULT_VALUE, defaultValue);
        dialog.setArguments(args);
        return dialog;
    }

    private EditText mField;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString(ARG_TITLE));
        builder.setPositiveButton(R.string.glb_set, this);

        mField = new EditText(getActivity());
        mField.setInputType(getArguments().getInt(ARG_INPUT_TYPE));
        mField.setText(getArguments().getString(ARG_DEFAULT_VALUE));
        mField.setSelection(mField.length());
        mField.selectAll();
        builder.setView(mField);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        ((OnTextEnteredListener) getTargetFragment()).onTextEntered(getTargetRequestCode(), mField.getText());
    }
}
