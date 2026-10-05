package com.limelight.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;

import com.limelight.BuildConfig;
import com.limelight.LimeLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Bounded, local-only telemetry recorder for the demo client.
 * No advertising ID, account identifier, or background upload is used.
 */
public final class DemoTelemetry {
    private static final String PREFS = "CloudGamingDemoTelemetry";
    private static final String KEY_EVENTS = "events";
    private static final int MAX_LOCAL_CHARS = 64 * 1024;
    private static final long PERF_MIN_INTERVAL_MS = 2000;

    private static final Object LOCK = new Object();
    private static String sessionId;
    private static long lastPerfMs;

    private DemoTelemetry() {}

    public static void beginSession(Context context, String appName, String hostName) {
        synchronized (LOCK) {
            sessionId = UUID.randomUUID().toString();
            lastPerfMs = 0;
        }

        JSONObject detail = new JSONObject();
        try {
            detail.put("app", safe(appName));
            detail.put("host_name", safe(hostName));
        }
        catch (JSONException ignored) {}
        eventJson(context, "session_begin", detail);
    }

    public static void endSession(Context context, String reason) {
        event(context, "session_end", reason);
        synchronized (LOCK) {
            sessionId = null;
            lastPerfMs = 0;
        }
    }

    public static void perf(Context context, String text) {
        long now = SystemClock.elapsedRealtime();
        synchronized (LOCK) {
            if (now - lastPerfMs < PERF_MIN_INTERVAL_MS) {
                return;
            }
            lastPerfMs = now;
        }
        event(context, "perf", text);
    }

    public static void event(Context context, String name, String detail) {
        JSONObject payload = new JSONObject();
        try {
            payload.put("detail", safe(detail));
        }
        catch (JSONException ignored) {}
        eventJson(context, name, payload);
    }

    public static String getLocalEvents(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(KEY_EVENTS, "");
    }

    public static void clearLocalEvents(Context context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .remove(KEY_EVENTS)
                .apply();
    }

    private static void eventJson(Context context, String name, JSONObject detail) {
        if (!BuildConfig.DEMO_MODE) {
            return;
        }

        JSONObject event = new JSONObject();
        try {
            event.put("ts_ms", System.currentTimeMillis());
            event.put("event", name);
            event.put("session_id", sessionId == null ? JSONObject.NULL : sessionId);
            event.put("detail", detail);
            event.put("device_model", Build.MANUFACTURER + " " + Build.MODEL);
            event.put("android_sdk", Build.VERSION.SDK_INT);
            event.put("client_version", BuildConfig.VERSION_NAME);
        }
        catch (JSONException e) {
            LimeLog.warning("Demo telemetry serialization failed: " + e.getMessage());
            return;
        }

        String line = event.toString();
        appendLocal(context.getApplicationContext(), line);
        LimeLog.info("DEMO_TELEMETRY " + line);
    }

    private static void appendLocal(Context context, String line) {
        synchronized (LOCK) {
            SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            String previous = prefs.getString(KEY_EVENTS, "");
            String combined = previous + line + "\n";

            if (combined.length() > MAX_LOCAL_CHARS) {
                combined = combined.substring(combined.length() - MAX_LOCAL_CHARS);
                int firstNewline = combined.indexOf('\n');
                if (firstNewline >= 0 && firstNewline + 1 < combined.length()) {
                    combined = combined.substring(firstNewline + 1);
                }
            }

            prefs.edit().putString(KEY_EVENTS, combined).apply();
        }
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}
