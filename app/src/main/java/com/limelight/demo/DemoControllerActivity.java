package com.limelight.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
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
        try {
            qr.setImageBitmap(makeQr(url, 700));
        } catch (WriterException e) {
            qr.setVisibility(View.INVISIBLE);
            status.setText(url);
        }

        DemoTelemetry.event(this, "phone_controller_qr_opened", "tv");
    }

    private Bitmap makeQr(String value, int size) throws WriterException {
        BitMatrix matrix = new QRCodeWriter().encode(value, BarcodeFormat.QR_CODE, size, size);
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                bitmap.setPixel(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return bitmap;
    }
}
