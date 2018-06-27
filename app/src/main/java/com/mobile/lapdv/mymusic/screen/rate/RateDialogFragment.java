package com.mobile.lapdv.mymusic.screen.rate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.mobile.lapdv.mymusic.R;

/**
 * Created by lap on 17/05/2018.
 */

public class RateDialogFragment extends DialogFragment {

    public static void show(FragmentManager fragmentManager) {
        new RateDialogFragment().show(fragmentManager, null);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.string_title_rate)
                .setMessage(R.string.string_message_rate)
                .setIcon(R.drawable.ic_app)
                .setPositiveButton(R.string.string_btn_rate,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("https://play.google.com/store/apps/details")));
                                dismiss();
                            }
                        })
                .setNeutralButton(R.string.string_btn_rate_later
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        })
                .setNegativeButton(R.string.string_btn_no_thanks,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        }).create();
    }
}
