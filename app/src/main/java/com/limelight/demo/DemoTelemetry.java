package com.limelight.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;

import com.limelight.BuildConfig;
import com.limelight.LimeLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bounded, local-only telemetry recorder for the demo client.
 * No advertising ID, account identifier, or background upload is used.
 */
public final class DemoTelemetry {
    private static final String PREFS = "CloudGamingDemoTelemetry";
    private static final String KEY_EVENTS = "events";
    private static final int MAX_LOCAL_CHARS = 96 * 1024;
    private static final long PERF_MIN_INTERVAL_MS = 2000;
    private static final int SCHEMA_VERSION = 2;

    private static final Pattern STREAM = Pattern.compile("Video stream: (\\d+)x(\\d+) ([0-9.]+) FPS");
    private static final Pattern DECODER = Pattern.compile("Decoder: (.+)");
    private static final Pattern INCOMING = Pattern.compile("Incoming frame rate from network: ([0-9.]+) FPS");
    private static final Pattern RENDERING = Pattern.compile("Rendering frame rate: ([0-9.]+) FPS");
    private static final Pattern DROPS = Pattern.compile("Frames dropped by your network connection: ([0-9.]+)%");
    private static final Pattern LATENCY = Pattern.compile("Average network latency: (\\d+) ms \\(variance: (\\d+) ms\\)");
    private static final Pattern HOST = Pattern.compile("Host processing latency min/max/average: ([0-9.]+)/([0-9.]+)/([0-9.]+) ms");
    private static final Pattern DECODE = Pattern.compile("Average decoding time: ([0-9.]+) ms");

    private static final Object LOCK = new Object();
    private static String sessionId;
    private static long sessionStartElapsedMs;
    private static long lastPerfMs;

    private DemoTelemetry() {}

    public static void beginSession(Context context, String appName, String hostName) {
        synchronized (LOCK) {
            sessionId = UUID.randomUUID().toString();
            sessionStartElapsedMs = SystemClock.elapsedRealtime();
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
        long started;
        synchronized (LOCK) {
            if (sessionId == null) {
                return;
            }
            started = sessionStartElapsedMs;
        }

        JSONObject detail = new JSONObject();
        put(detail, "reason", safe(reason));
        put(detail, "duration_ms", Math.max(0, SystemClock.elapsedRealtime() - started));
        eventJson(context, "session_end", detail);

        synchronized (LOCK) {
            sessionId = null;
            sessionStartElapsedMs = 0;
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
        JSONObject metrics = parsePerf(text);
        put(metrics, "raw", safe(text));
        eventJson(context, "perf_sample", metrics);
    }

    public static void event(Context context, String name, String detail) {
        JSONObject payload = new JSONObject();
        try {
            payload.put("detail", safe(detail));
        }
        catch (JSONException ignored) {}
        eventJson(context, name, payload);
    }

    private static JSONObject parsePerf(String text) {
        JSONObject metrics = new JSONObject();
        if (text == null) {
            return metrics;
        }

        Matcher m = STREAM.matcher(text);
        if (m.find()) {
            put(metrics, "stream_width_px", intValue(m.group(1)));
            put(metrics, "stream_height_px", intValue(m.group(2)));
            put(metrics, "stream_fps", doubleValue(m.group(3)));
        }
        m = DECODER.matcher(text);
        if (m.find()) put(metrics, "decoder", m.group(1).trim());
        m = INCOMING.matcher(text);
        if (m.find()) put(metrics, "incoming_fps", doubleValue(m.group(1)));
        m = RENDERING.matcher(text);
        if (m.find()) put(metrics, "rendering_fps", doubleValue(m.group(1)));
        m = DROPS.matcher(text);
        if (m.find()) put(metrics, "network_drop_pct", doubleValue(m.group(1)));
        m = LATENCY.matcher(text);
        if (m.find()) {
            put(metrics, "network_latency_ms", intValue(m.group(1)));
            put(metrics, "network_latency_variance_ms", intValue(m.group(2)));
        }
        m = HOST.matcher(text);
        if (m.find()) {
            put(metrics, "host_processing_min_ms", doubleValue(m.group(1)));
            put(metrics, "host_processing_max_ms", doubleValue(m.group(2)));
            put(metrics, "host_processing_avg_ms", doubleValue(m.group(3)));
        }
        m = DECODE.matcher(text);
        if (m.find()) put(metrics, "decode_avg_ms", doubleValue(m.group(1)));
        return metrics;
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
            event.put("schema_version", SCHEMA_VERSION);
            event.put("ts_ms", System.currentTimeMillis());
            event.put("event", name);
            event.put("session_id", sessionId == null ? JSONObject.NULL : sessionId);
            event.put("detail", detail);
            event.put("device_model", Build.MANUFACTURER + " " + Build.MODEL);
            event.put("android_sdk", Build.VERSION.SDK_INT);
            event.put("client_version", BuildConfig.VERSION_NAME);
            event.put("form_factor",
                    context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK) ? "tv" : "android");
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

    private static void put(JSONObject object, String key, Object value) {
        try {
            object.put(key, value);
        } catch (JSONException ignored) {}
    }

    private static int intValue(String value) {
        try { return Integer.parseInt(value); }
        catch (NumberFormatException e) { return 0; }
    }

    private static double doubleValue(String value) {
        try { return Double.parseDouble(value); }
        catch (NumberFormatException e) { return 0; }
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}
