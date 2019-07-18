package com.ioocllcdrdapp.iooc.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.utilities.Utilities;
import com.ioocllcdrdapp.iooc.views.UIEngine;

public class PopupDialog {

    private static AlertDialog alert;
    private ErrorDialogListener listener;

    public PopupDialog(ErrorDialogListener listener) {
        this.listener = listener;
    }

    public void showMessageDialog(final String title, final String message, final Activity activity) {
        if (activity == null)
            return;
        UIEngine.initialize(activity.getApplicationContext());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                if (!Utilities.isNullString(title))
                    dialog.setTitle(UIEngine.createSpannableString(title, UIEngine.Fonts.APP_FONT_LIGHT));
                dialog.setMessage(UIEngine.createSpannableString(message, UIEngine.Fonts.APP_FONT_LIGHT));
                dialog.setCancelable(false);

                dialog.setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        listener.onOkClick();
                    }
                });

                dialog.setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (alert != null && alert.isShowing())
                            alert.dismiss();
                        listener.onCancelClick();
                    }
                });

                if (alert != null && alert.isShowing())
                    alert.dismiss();
                alert = dialog.create();
                alert.show();
            }
        });
    }

    public interface ErrorDialogListener {
        void onOkClick();

        void onCancelClick();
    }
}
