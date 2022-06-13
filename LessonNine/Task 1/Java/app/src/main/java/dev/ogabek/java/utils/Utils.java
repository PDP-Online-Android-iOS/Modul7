package dev.ogabek.java.utils;

import static android.content.Context.WINDOW_SERVICE;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import dev.ogabek.java.R;
import dev.ogabek.java.model.ScreenSize;

public interface Utils {

    public static void fireToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static ScreenSize screenSize(Application context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;
        return new ScreenSize(deviceWidth, deviceHeight);
    }

    public static void dialogDouble(Context context, String title, DialogListener callback) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_dialog_double);
        dialog.setCanceledOnTouchOutside(true);
        TextView d_title = dialog.findViewById(R.id.d_title);
        TextView d_confirm = dialog.findViewById(R.id.d_confirm);
        TextView d_cancel = dialog.findViewById(R.id.d_cancel);
        d_title.setText(title);
        d_confirm.setOnClickListener(view -> {
            dialog.dismiss();
            callback.onCallback(true);
        });
        d_cancel.setOnClickListener(view -> {
            dialog.dismiss();
            callback.onCallback(false);
        });
        dialog.show();
    }

    public static void dialogSingle(Context context, String title, DialogListener callback) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_dialog_single);
        dialog.setCanceledOnTouchOutside(true);
        TextView d_title = dialog.findViewById(R.id.d_title);
        TextView d_confirm = dialog.findViewById(R.id.d_confirm);
        d_title.setText(title);
        d_confirm.setOnClickListener(view -> {
            dialog.dismiss();
            callback.onCallback(true);
        });
        dialog.show();
    }
}

