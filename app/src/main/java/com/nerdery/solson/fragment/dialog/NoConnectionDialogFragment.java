package com.nerdery.solson.fragment.dialog;

import com.nerdery.solson.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

/**
 * Created by kenton on 7/30/14.
 */
public class NoConnectionDialogFragment extends DialogFragment {

    @Inject
    public NoConnectionDialogFragment() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.no_connection_dialog_title)
                .setMessage(R.string.no_connection_dialog_message)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).create();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager != null) {
            if (manager.findFragmentByTag(tag) == null) {
                super.show(manager, tag);
            }
        }
    }
}
