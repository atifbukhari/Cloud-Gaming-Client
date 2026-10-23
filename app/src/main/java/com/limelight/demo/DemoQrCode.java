package com.limelight.demo;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Shared QR renderer for the demo phone-controller journey.
 *
 * Kept separate from the controller Activity so the active Game Activity can
 * show the same pairing QR in an AlertDialog without launching another
 * Activity (which would stop the Moonlight stream).
 */
public final class DemoQrCode {
    private DemoQrCode() {
    }

    public static Bitmap create(String value, int size) {
        if (value == null || value.isEmpty() || size <= 0) {
            return null;
        }

        try {
            BitMatrix matrix = new QRCodeWriter().encode(
                    value, BarcodeFormat.QR_CODE, size, size);
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    bitmap.setPixel(x, y,
                            matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            return null;
        }
    }
}
