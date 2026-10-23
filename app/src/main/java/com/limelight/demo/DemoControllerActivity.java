package com.limelight.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.limelight.R;
import com.limelight.utils.UiHelper;

public class DemoControllerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiHelper.setLocale(this);
        setContentView(R.layout.activity_demo_controller);

        DemoControllerServer server = DemoControllerServer.ensureStarted(getApplicationContext());
        String url = server.getPairingUrl();

        ImageView qr = findViewById(R.id.demoControllerQr);
        TextView urlText = findViewById(R.id.demoControllerUrl);
        TextView status = findViewById(R.id.demoControllerStatus);

        if (url == null) {
            qr.setVisibility(View.INVISIBLE);
            urlText.setText("");
            status.setText(R.string.demo_controller_unavailable);
            return;
        }

        urlText.setText(url);
        status.setText(R.string.demo_controller_waiting);
        Bitmap bitmap = DemoQrCode.create(url, 700);
        if (bitmap != null) {
            qr.setImageBitmap(bitmap);
        } else {
            qr.setVisibility(View.INVISIBLE);
            status.setText(url);
        }

        DemoTelemetry.event(this, "phone_controller_qr_opened", "tv");
    }
}
