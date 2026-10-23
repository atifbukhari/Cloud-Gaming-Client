# Performance Lab v1

Branch: `perf-lab-v1`

This branch is deliberately isolated from the product branch. It exists to evaluate performance changes from Moonlight X / Artemis / Artemide / Starwire without destabilizing the proven `12.2-demo2` streaming core.

## Baseline

Start point: `demo-client-v1-e2e-green-2026-10-23`.

Known-good local Windows/Sunshine behavior:
- pairing works when host/client clocks are synchronized;
- SuperTuxKart launches and renders;
- structured telemetry works;
- Disconnect, Resume and End Game work;
- local network loss observed at 0% in the validation session.

The Android TV emulator's software H.264 decoder is **not** a physical-device performance reference.

## Upstream references

Primary reference repositories:
- `MoreOrLessSoftware/moonlight-android` / Moonlight X
- `ClassicOldSong/moonlight-android` / Artemis
- `derflacco/moonlight-android` / Artemide
- `SicarioGaming/Starwire`

Starwire is useful as an integration map, not as a new wholesale upstream.

## Candidate sequence

Evaluate one family at a time:

1. vendor low-latency MediaCodec flags;
2. LFR / lower-delay behavior;
3. async decode;
4. Fast V-Sync / Nano Pacer;
5. direct/Vulkan rendering path;
6. later PyroWave;
7. custom Turnip driver only as a narrow device experiment.

Do not combine multiple unproven pacing/decoder changes in the first test.

## Required A/B metrics

For every candidate run capture:
- stream resolution;
- requested FPS;
- incoming FPS;
- rendered FPS;
- incoming/render variance;
- network drop percentage;
- RTT and variance;
- host processing min/max/average;
- average decode latency;
- first-video-packet time;
- connection-stage timings;
- crash/decoder-reset events;
- device model, SoC/decoder name and Android version.

Existing `DemoTelemetry` schema v2 already captures most of these.

## Initial test modes

Use the product labels only after measurements support them:

- **Fast**: minimize response latency;
- **Balanced**: stable default;
- **Quality**: favor frame consistency/image quality.

The product branch should expose simple profiles. The lab may expose detailed toggles.

## Promotion rule

A performance change can move to `demo-client-v1` only when:
1. baseline and candidate both build green;
2. physical Android hardware has been tested;
3. no regression in pairing/session lifecycle;
4. no increase in crashes/decoder resets;
5. improvement is measurable in telemetry or materially improves frame consistency;
6. unsupported devices fall back safely.

## First physical-device matrix

At minimum:
- one mainstream Qualcomm Android phone;
- one Android TV / Google TV class device if available;
- one lower-cost/older Android device if commercially relevant.

Do not infer hardware-decoder performance from the x86 Android emulator.

## Host scope

Keep Sunshine unchanged during client performance A/B tests unless the tested feature explicitly requires Apollo/Vibepollo. Host changes and client changes should not be mixed in a single experiment.
