package com.yakin.uilab.adjust;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yakin.uilab.R;

import androidx.appcompat.app.AppCompatActivity;

public class AdjustActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust);
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, AdjustActivity.class));
    }
}
