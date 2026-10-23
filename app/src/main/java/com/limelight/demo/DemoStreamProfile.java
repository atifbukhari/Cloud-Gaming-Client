package com.limelight.demo;

import android.content.Context;
import android.content.SharedPreferences;

import com.limelight.R;
import com.limelight.preferences.PreferenceConfiguration;

/**
 * Small product-facing profile layer for demo builds.
 *
 * Auto intentionally preserves the existing Moonlight preference configuration.
 * The first iteration only changes the already-supported frame-pacing mode.
 * Codec, bitrate, resolution, decoder flags, and native streaming code are not
 * modified by these profiles.
 */
public final class DemoStreamProfile {
    public static final String AUTO = "auto";
    public static final String FAST = "fast";
    public static final String BALANCED = "balanced";
    public static final String QUALITY = "quality";

    private static final String PREFS = "DemoStreamProfiles";

    private DemoStreamProfile() {
    }

    private static String key(String computerUuid, int appId) {
        return (computerUuid == null ? "unknown" : computerUuid) + ":" + appId;
    }

    public static String get(Context context, String computerUuid, int appId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String value = prefs.getString(key(computerUuid, appId), AUTO);
        if (FAST.equals(value) || BALANCED.equals(value) || QUALITY.equals(value)) {
            return value;
        }
        return AUTO;
    }

    public static void set(Context context, String computerUuid, int appId, String profile) {
        String value = profile;
        if (!FAST.equals(value) && !BALANCED.equals(value) && !QUALITY.equals(value)) {
            value = AUTO;
        }

        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(key(computerUuid, appId), value)
                .apply();
    }

    public static void apply(Context context, String computerUuid, int appId,
                             PreferenceConfiguration config) {
        if (config == null) {
            return;
        }

        switch (get(context, computerUuid, appId)) {
            case FAST:
                config.framePacing = PreferenceConfiguration.FRAME_PACING_MIN_LATENCY;
                break;
            case BALANCED:
                config.framePacing = PreferenceConfiguration.FRAME_PACING_BALANCED;
                break;
            case QUALITY:
                config.framePacing = PreferenceConfiguration.FRAME_PACING_MAX_SMOOTHNESS;
                break;
            case AUTO:
            default:
                // Deliberately preserve the global/current Moonlight preference.
                break;
        }
    }

    public static int labelRes(String profile) {
        switch (profile) {
            case FAST:
                return R.string.demo_profile_fast;
            case BALANCED:
                return R.string.demo_profile_balanced;
            case QUALITY:
                return R.string.demo_profile_quality;
            case AUTO:
            default:
                return R.string.demo_profile_auto;
        }
    }

    public static int effectiveLabelRes(String selectedProfile,
                                        PreferenceConfiguration effectiveConfig) {
        if (!AUTO.equals(selectedProfile)) {
            return labelRes(selectedProfile);
        }

        if (effectiveConfig == null) {
            return R.string.demo_profile_auto;
        }

        switch (effectiveConfig.framePacing) {
            case PreferenceConfiguration.FRAME_PACING_MIN_LATENCY:
                return R.string.demo_profile_fast;
            case PreferenceConfiguration.FRAME_PACING_MAX_SMOOTHNESS:
                return R.string.demo_profile_quality;
            default:
                return R.string.demo_profile_balanced;
        }
    }

    public static String[] values() {
        return new String[] { AUTO, FAST, BALANCED, QUALITY };
    }
}
