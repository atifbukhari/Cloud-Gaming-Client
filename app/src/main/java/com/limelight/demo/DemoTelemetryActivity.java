package com.limelight.demo;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DemoTelemetryActivity extends Activity {
    private TextView telemetryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Demo Telemetry");

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 24, 24, 24);

        LinearLayout actions = new LinearLayout(this);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        actions.setGravity(Gravity.END);

        Button refresh = new Button(this);
        refresh.setText("Refresh");
        refresh.setOnClickListener(v -> refreshTelemetry());

        Button clear = new Button(this);
        clear.setText("Clear");
        clear.setOnClickListener(v -> {
            DemoTelemetry.clearLocalEvents(this);
            refreshTelemetry();
        });

        actions.addView(refresh);
        actions.addView(clear);
        root.addView(actions, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        telemetryText = new TextView(this);
        telemetryText.setTextIsSelectable(true);
        telemetryText.setTypeface(Typeface.MONOSPACE);
        telemetryText.setTextSize(12);

        ScrollView scroll = new ScrollView(this);
        scroll.addView(telemetryText, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(scroll, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1));

        setContentView(root);
        refreshTelemetry();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTelemetry();
    }

    private void refreshTelemetry() {
        String events = DemoTelemetry.getLocalEvents(this);
        telemetryText.setText(events.isEmpty()
                ? "No demo telemetry captured yet."
                : events);
    }
}
