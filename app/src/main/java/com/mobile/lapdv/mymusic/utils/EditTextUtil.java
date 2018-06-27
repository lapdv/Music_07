package com.mobile.lapdv.mymusic.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by lap on 14/05/2018.
 */

public class EditTextUtil {
    public static void fixFontPasswordType(EditText edt) {
        edt.setTypeface(Typeface.DEFAULT);
    }

    private static final int MAX_PASSWORD_LENGTH = 8;

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!$%@#*?&]{8,16}$";


    public static void hideSoftKeyboard(EditText edt, Context ctx) {
        if (edt == null || ctx == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
    }

    public static void showKeyboard(EditText edt, Context ctx) {
        if (edt == null || ctx == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void showKeyBoardRun(final EditText edt, final Activity ctx) {
        edt.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager) ctx
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);

            }
        }, 200);
    }

    private static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        hideKeyboard(activity);
    }

    public interface EnterListener {
        void onEnterPress();
    }

    public static void setOnEnterListener(EditText edt,
                                          final EnterListener enterListener) {
        edt.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (enterListener != null) {
                        enterListener.onEnterPress();
                    }
                }
                return false;
            }
        });
    }
}
