package com.example.posapp.Accessories;

import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.posapp.R;

public class WindowDecoration implements IWindowDecoration {
    @Override
    public void hideBar(AppCompatActivity context) {
        context.getSupportActionBar().hide();
        context.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(context.getResources()
                .getColor(R.color.black)));
        Window window = context.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(context.getResources().getColor(R.color.black));
    }
}
