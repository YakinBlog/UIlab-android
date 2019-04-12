package com.yakin.uilab;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.yakin.rtp.RTPManager;
import com.yakin.uilab.adjust.AdjustActivity;
import com.yakin.watchdog.WatchDog;
import com.yakin.watchdog.log.ILogger;
import com.yakin.watchdog.ui.CrashPanel;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WatchDog.getLogger().setPrintLog(ILogger.DEBUG, "UILab");
        WatchDog.getCrashHandler().registerHandler(new CrashPanel());

        RTPManager.getInstance().requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });
    }

    public void onClick(View view) {
        if(view.getId() == R.id.adjust) {
            AdjustActivity.startActivity(this);
        }
    }
}
