# Demo Stream Profiles

The 12.3 profile layer is intentionally small and conservative.

## Profiles

- **Auto** — preserve the existing Moonlight preference configuration exactly.
- **Fast** — use the existing minimum-latency frame-pacing mode.
- **Balanced** — use the existing balanced frame-pacing mode.
- **Quality** — use the existing maximum-smoothness frame-pacing mode.

Profiles are stored per Sunshine computer UUID + app ID.

## What profiles do not change in 12.3

The initial profile layer does **not** change:
- codec preference;
- resolution;
- FPS target;
- bitrate;
- HDR;
- decoder vendor flags;
- Vulkan/direct renderer;
- PyroWave;
- network protocol behavior.

Those parameters stay governed by the existing Moonlight settings.

This keeps **Auto** fully backward-compatible and makes the first profile iteration a UI/product layer over a single existing, proven preference: frame pacing.

## Application point

The selected profile is applied to a fresh `PreferenceConfiguration` inside `Game.onCreate()` before orientation/renderer/stream setup.

The catalogue hero applies the same profile to a temporary preference object only to show the effective profile label. It does not mutate the global preference store.

## Telemetry

- `stream_profile_selected` records a catalogue selection.
- `stream_profile_applied` records the profile used when a stream activity starts.

## Future extension rule

Do not silently expand Fast/Balanced/Quality to change codec, bitrate, resolution, or experimental decoder behavior.

Any extension must:
1. remain Auto-safe;
2. be documented here;
3. be A/B tested using structured telemetry;
4. use physical Android hardware;
5. keep advanced renderer/decoder experiments in `perf-lab-v1` until promoted.
