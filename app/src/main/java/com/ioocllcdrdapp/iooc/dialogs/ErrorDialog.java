package com.ioocllcdrdapp.iooc.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.utilities.Utilities;
import com.ioocllcdrdapp.iooc.views.UIEngine;

public class ErrorDialog {

    private static AlertDialog alert;

    public static void showMessageDialog(final String title, final String message, final Activity activity) {
        showMessageDialog(title, message, activity, null, false);
    }

    public static void showMessageDialog(final String title, final String message, final Activity activity, final Runnable runnable, final boolean isShowCancelButton) {
        if (activity == null)
            return;
        UIEngine.initialize(activity.getApplicationContext());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                if (!Utilities.isNullString(title))
                    // dialog.setTitle(UIEngine.createSpannableString(title, UIEngine.Fonts.APP_FONT_BOOK));
                    dialog.setTitle(null);

                dialog.setMessage(UIEngine.createSpannableString(message, UIEngine.Fonts.APP_FONT_BOOK));
                dialog.setCancelable(false);

                dialog.setPositiveButton(activity.getString(R.string.ok), (dialog1, whichButton) -> {
                    if (runnable != null) {
                        runnable.run();
                    }
                });

                if (isShowCancelButton) {
                    dialog.setNegativeButton(activity.getString(R.string.cancel), (dialog12, whichButton) -> {
                        if (alert != null && alert.isShowing())
                            alert.dismiss();
                    });
                }

                if (alert != null && alert.isShowing())
                    alert.dismiss();
                alert = dialog.create();
                try {
                    alert.show();
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        });
    }
}
