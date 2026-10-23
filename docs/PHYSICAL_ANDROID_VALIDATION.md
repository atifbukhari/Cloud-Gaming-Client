# Physical Android Validation Gate

This is the required client gate before promoting experimental renderer/decoder work or restarting paid AWS GPU infrastructure.

## Purpose

Validate the Cloud Gaming client on real Android hardware rather than an emulator. The emulator is useful for UI/regression testing but its software decoder and snapshot clock behavior are not representative of production devices.

## Preparation

1. Connect the Android phone/tablet to the Windows test laptop by USB.
2. Enable Developer options and USB debugging.
3. Approve the RSA debugging prompt on the Android device.
4. Keep the Android device and the Sunshine host on the same LAN/Wi-Fi for the first physical test.
5. Ensure automatic date/time is enabled on Android.

Run:

```powershell
powershell -ExecutionPolicy Bypass -File .\tools\validate-android-device.ps1 -ApkPath C:\path\to\app-nonRoot-debug.apk
```

The script:
- selects an authorized physical Android device when exactly one is connected;
- records manufacturer/model/Android SDK/ABI/build;
- checks host/device clock skew;
- warns if clock skew is greater than 120 seconds;
- installs the APK when `-ApkPath` is supplied;
- launches the Cloud Gaming demo client;
- captures a MediaCodec dump and baseline logcat.

The clock check is mandatory because stale emulator time caused Sunshine certificate/pairing failures during the 2026-10-23 local E2E test.

## Functional acceptance

Use Sunshine + SuperTuxKart first.

Required pass:
1. Host discovery/manual add.
2. Pairing.
3. Game-first hero renders correctly.
4. Hero Play starts SuperTuxKart.
5. Profile selector works for Auto/Fast/Balanced/Quality.
6. Stream reaches connected state.
7. Video/audio/input work.
8. Performance telemetry produces samples.
9. In-stream Menu opens without disconnecting.
10. Phone Controller QR opens while the stream remains active.
11. Phone controller input reaches the host/game.
12. Disconnect returns to catalogue and leaves the game running.
13. Resume reconnects to the same game.
14. End Game terminates the remote game and returns to catalogue.

## Performance baseline

Record at least one stable sample for each profile:
- incoming FPS;
- rendering FPS;
- network loss percentage;
- average network latency and variance;
- host-processing min/max/average;
- average decode latency;
- decoder name;
- stream resolution/FPS.

Do not compare emulator decode latency against the physical device.

## Promotion rule

Do not merge Fast V-Sync, LFR, async decode, Vulkan/direct-renderer, vendor decoder flags, PyroWave, or Turnip-driver experiments into `demo-client-v1` until:
- the physical baseline above is complete;
- the experiment is isolated in `perf-lab-v1`;
- telemetry shows a repeatable improvement without unacceptable stability/device-compatibility regressions.

## AWS guardrail

Do not start paid AWS GPU compute solely for this client validation. After the physical client/controller gate is green, a short-lived GPU worker may be recreated for the separate Linux/Sunshine cloud-host test, beginning with SuperTuxKart.
