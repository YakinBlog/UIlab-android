package com.yakin.uilab.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yakin.uilab.R;

/**
 * ****************************************************************************
 * Copyright (C) 2005-2016 UCWEB Corporation. All rights reserved
 * File        : AdjustActivity.java
 * <p>
 * Description : AdjustActivity
 * <p>
 * Creation    : 2019/3/27
 * Author      : mailto:wyj80283@alibaba-inc.com
 * History     : Creation, 2019/3/27, WYJ create the file
 * ****************************************************************************
 */
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
