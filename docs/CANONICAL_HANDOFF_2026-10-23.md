# Cloud Gaming Client — Canonical Handoff (2026-10-23)

## Authority and rollback point

This document is the canonical continuation point for the Android/Android TV cloud-gaming client work as of 2026-10-23.

- Repository: `atifbukhari/Cloud-Gaming-Client`
- Active development branch: `demo-client-v1`
- Preserved pre-next-iteration checkpoint: `demo-client-v1-e2e-green-2026-10-23`
- Checkpoint source commit: `78c92b2ca05b5d59ae25c1b4f5d39db5b2c33fc9`
- Last proven APK build commit: `8b08d707922319ff2fa736590615b6fe641845c5`
- Proven APK version: `12.2-demo2`, versionCode `316`
- Proven APK SHA-256: `4570920deafc127e5274205a3bde9a947028863ba17de34787674bf8c9063878`
- GitHub Actions run 13: SUCCESS
- Run-13 artifact: `cloud-gaming-demo-apk`
- No paid AWS GPU worker is running.

Do not work directly on `master`. The checkpoint branch above must remain untouched so there is always a known rollback point.

## Product direction

Hero concept remains:

**One GPU. Any screen. No console.**

Primary consumer journey:

**Open branded client -> game-first catalogue -> select/resume game -> stream -> in-stream controls -> disconnect or end game.**

Engineering host/IP/Moonlight details should remain secondary. The same Android codebase should support:
- Android TV / Google TV as a primary large-screen client;
- Android phones/tablets as direct clients;
- a phone-as-controller path for TV using the demo QR/LAN controller;
- future operator/telco packaging and telemetry.

## What is now proven end-to-end

A full local Windows/Sunshine validation has been completed using the actual custom `12.2-demo2` APK, not only stock Moonlight.

### Local host
- Windows laptop in Pakistan.
- Sunshine installed and running as a Windows service.
- Sunshine fork created at `atifbukhari/Sunshine`.
- Sunshine upgraded during pairing diagnosis to `v2026.1006.15912`.
- SuperTuxKart 1.5 installed locally.
- SuperTuxKart added to Sunshine applications.
- Windows LAN address used by the Android emulator during the successful test: `192.168.18.26`.

### Android test client
- Android Studio installed.
- Android SDK command-line tools, ADB and emulator installed.
- Android TV AVD: `CloudGamingTV`.
- Custom APK package: `com.atifbukhari.cloudgaming.debug`.
- Official Moonlight 12.2 was also installed side-by-side as a control client during diagnosis.

### Successful custom-client journey
The following were verified:

1. Sunshine host manually added through the client.
2. Pairing completed successfully.
3. Custom game catalogue loaded.
4. SuperTuxKart was visible in the catalogue.
5. SuperTuxKart stream started/resumed through the custom client.
6. RTSP handshake completed.
7. Control/video/audio/input streams initialized and established.
8. First video packet was received and rendered.
9. Structured telemetry emitted real performance samples.
10. Visible in-stream Exit control worked.
11. **Disconnect** stopped only the client stream and left the host game running.
12. The same game session could be resumed.
13. **End Game** sent the host-side quit request.
14. Sunshine returned remote quit success.
15. SuperTuxKart process terminated on Windows.
16. Client returned to the catalogue.

This is the first complete local proof of the custom demo client lifecycle.

## Pairing diagnosis and important test rule

Several earlier pairing attempts failed with `unexpected end of stream`.

Two environmental problems were isolated:

1. The emulator-special host alias `10.0.2.2` was unreliable for the pairing path. The successful pairing used the Windows machine's real LAN IP.
2. More importantly, the Android emulator clock was approximately 32 minutes out of sync with Windows due to stale emulator snapshot state. Restarting the AVD with snapshots disabled synchronized the clock. After time synchronization, official Moonlight and the custom client paired successfully.

For future emulator pairing tests:
- use the host's real LAN address where practical;
- verify host/client clocks before debugging certificates or protocol code;
- boot the emulator without a stale snapshot if the clock has drifted.

Do not treat this environmental pairing failure as evidence of a defect in the custom Moonlight protocol core.

## Proven telemetry

Telemetry schema remains local-only NDJSON, schema version 2.

A real custom-client SuperTuxKart session emitted:
- `session_begin`
- connection stage start/complete events;
- `connection_started`
- repeated `perf_sample`
- `user_exit_requested`
- `session_end`
- `remote_quit_result`

Representative local-stream values:
- resolution: 1280x720;
- network drop: 0.00%;
- network latency: roughly 1–4 ms;
- host processing latency: generally single-digit to mid-teens milliseconds;
- decoder: `OMX.google.h264.decoder`.

The Android TV emulator uses Google's software H.264 path and is not representative of physical-device decode performance. Emulator FPS/decode latency must not be used as product performance targets. Physical Android hardware is the next important performance gate.

## Current implemented client features

### Catalogue
- branded **Cloud Gaming** TV layout;
- tagline **One GPU. Any screen. No console.**;
- TV-oriented large cards;
- demo ordering preference:
  1. SuperTuxKart
  2. Xonotic
  3. War Thunder
  4. Veloren
- Test Ball hidden from consumer demo.

### Session lifecycle
Visible demo-only Exit action and Android Back can open a leave-game flow:
- Disconnect;
- End Game;
- Cancel.

End Game uses the existing GameStream/Sunshine HTTP quit API and is now proven to work end-to-end.

### Telemetry
Structured local schema v2 with parsed:
- stream width/height/FPS;
- decoder;
- incoming/rendering FPS;
- network loss;
- RTT/variance;
- host processing min/max/average;
- average decode latency;
- session duration.

### TV QR -> phone controller
Android TV can expose **Use phone as controller**.
The TV runs a demo-only LAN HTTP controller page protected by a high-entropy token.
Current fixed controls:
- D-pad;
- A;
- B;
- Start.

The current implementation maps those controls through the Moonlight keyboard input path and is primarily aimed at the SuperTuxKart demo. Native analog/gamepad semantics remain future work.

## Build strategy

### GitHub Actions — primary
CI is the authoritative reproducible build path.

Current workflow:
- normal app/code pushes: compile/resource preflight only;
- APK packaging only for manual workflow dispatch or a commit containing `[build apk]`.

This avoids repeatedly packaging an APK during every development mutation.

### Windows laptop — test/development station
The Windows machine is now useful for:
- Android TV emulator;
- ADB install/launch;
- host/client E2E tests;
- Sunshine;
- local Windows games.

Full local compilation is not yet the preferred path because NDK `29.0.14206865` download throughput from Google's SDK servers was extremely poor.

### Google Colab — approved backup build concept
A Colab notebook can be added as an interactive/emergency builder:
- JDK 17;
- Android SDK 36;
- Build Tools 36;
- NDK `29.0.14206865`;
- recursive repo checkout;
- `:app:assembleNonRootDebug`;
- APK export to Drive/download.

GitHub Actions remains primary because it is easier to reproduce and audit. Colab is useful as a backup or interactive build machine, not a replacement for CI.

## Starwire / Moonlight-fork review

Reviewed on 2026-10-23:
- `SicarioGaming/Starwire`
- `MoreOrLessSoftware/moonlight-android` (Moonlight X)
- `ClassicOldSong/moonlight-android` (Artemis)
- `derflacco/moonlight-android` (Artemide)
- related Starwire-attributed work such as Moonlight V+, Moonlit Artemis and Moonlight X Turnip.

Starwire is GPL-3.0 and the current client repo also carries GPL-3.0 licensing inherited from Moonlight Android. Selective implementation reuse is feasible, but credits/notices and source-distribution obligations must be preserved for distributed derivatives.

Starwire is a useful engineering reference, but it is still a young integration project. Do **not** replace the now-proven client wholesale. Selectively port features behind reversible changes/feature flags.

### Adopt in the main client next
1. Game-first selected-game/hero UX.
2. Per-game stream profiles.
3. In-stream quick menu.
4. Lite/detailed performance display.
5. Controller / phone-controller entry point from the in-stream menu.
6. Touch/mouse UX improvements where they do not disturb TV behavior.
7. User-facing performance modes such as **Fast / Balanced / Quality** instead of exposing every Moonlight knob.

### Performance lab — isolate first
Create or use a separate performance branch for:
- direct/Vulkan renderer;
- Fast V-Sync / Nano Pacer;
- LFR;
- async decode;
- vendor-specific ultra-low-latency decoder flags;
- later PyroWave testing.

A/B test these features with the existing telemetry rather than promoting based only on subjective feel.

### Defer
- custom Turnip driver as a general default;
- PyroWave as a baseline codec;
- host-specific clipboard/command features;
- audio haptics;
- broad host replacement.

## Host strategy

Keep Sunshine as the default host because the custom client + Sunshine path is now proven.

Potential future host abstraction:

```
Host API
  ├── Sunshine   (default / proven)
  └── Apollo or other enhanced host (optional)
```

Apollo/Vibepollo features such as virtual display, clipboard, host commands or bitrate changes can be evaluated later without replacing the proven Sunshine baseline.

## Next development iteration

Target: **12.3 development line**

### Priority A — in-stream menu
Replace the single-purpose Exit presentation with a small game menu that can provide:
- Resume/close menu;
- Performance stats toggle;
- Phone Controller;
- Disconnect;
- End Game.

Preserve the already-proven disconnect/end-game implementation underneath.

### Priority B — game-first catalogue
Move toward a selected-game hero area:
- cover/art;
- title;
- Play/Resume;
- profile chips such as resolution/FPS/mode;
- horizontal/TV-friendly remaining catalogue.

Do not clone Starwire's visual design; use it as product inspiration and maintain a distinct Cloud Gaming identity.

### Priority C — per-game profiles
Introduce simple presets:
- Auto;
- Fast;
- Balanced;
- Quality.

The first version may map to existing Moonlight preferences without exposing advanced flags. Advanced pacing/decoder changes belong in the performance lab until proven.

### Priority D — physical Android device gate
Install `12.2-demo2` or the next green APK on a physical Android phone/tablet and validate:
- hardware decode;
- real Wi-Fi;
- controller/touch input;
- stable 60 FPS where supported;
- QR phone-controller journey.

### Priority E — performance lab
After physical baseline metrics exist, test renderer/pacing/async variants and compare structured telemetry.

## 12.3 progress completed after this handoff was created

The first Starwire-informed product iteration has now been implemented without replacing the proven Moonlight streaming core.

### 12.3-demo1 version
- versionName: `12.3-demo1`
- versionCode: `317`

### In-stream game menu
Implemented on `demo-client-v1`:
- top-right demo affordance renamed from **Exit** to **Menu**;
- Android Back during a stream opens the same game menu;
- Resume game;
- Show/Hide performance overlay;
- Disconnect;
- End Game;
- menu interactions emit structured demo telemetry.

The already-proven Disconnect and host-side End Game logic remains underneath unchanged.

CI checkpoint:
- run 17: SUCCESS
- workflow run id: `39892749869`
- APK artifact id: `12475806842`
- artifact digest: `sha256:13e9ea902674e6c47e69230c814366454d7e1497a0d35ecea90d13045015045d`

### Game-first catalogue hero
Implemented on `demo-client-v1`:
- focused/selected game drives a hero information area;
- game title;
- Play/Resume state indicator;
- resolution/FPS/bitrate summary;
- stream-mode label derived from the actual existing frame-pacing preference;
- TV D-pad focus updates the hero;
- `game_focused` telemetry.

Current product labels:
- minimum-latency frame pacing -> **Fast**;
- maximum-smoothness frame pacing -> **Quality**;
- other current modes -> **Balanced**.

The hero action is currently informational; card selection remains the proven launch path. Do not remove the existing card launch path until the hero action has its own runtime validation.

CI checkpoints:
- run 21: SUCCESS, hero preview
  - workflow run id: `39893276022`
  - artifact id: `12476060800`
  - artifact digest: `sha256:64a04700e10b28a90d2e704aa269bc9b538a31589dd5bdb9bcbb1b8fb4b269b3`
- run 23: SUCCESS, corrected real profile label
  - workflow run id: `39893635659`
  - build commit: `a11665236baf6de2a573c510219261468b1a10d6`
  - artifact id: `12476186135`
  - artifact digest: `sha256:a24fc282292f73bf46e3a4028580a6eea8f1647129d7c09608a59b0b475f9dab`

### Phone controller inside the active stream
Implemented and merged through PR #1.

Design requirement: do **not** launch `DemoControllerActivity` while streaming because `Game.onStop()` stops the active connection. The new path instead presents the QR as an `AlertDialog` inside the existing `Game` activity.

Implementation:
- shared `DemoQrCode` renderer;
- in-stream Phone controller action on Android TV / Leanback;
- controller QR dialog remains inside the active Game activity;
- reuses the existing tokenized LAN `DemoControllerServer`;
- reuses the active `Game` input listener set on `connectionStarted()`;
- no protocol, native decoder, or Moonlight core changes.

Feature-branch CI:
- run 24: SUCCESS
- workflow run id: `39894128881`
- tested app commit: `8a63a88b8d1eb73091b086ecc7c12325f1e07a43`
- APK artifact id: `12476107142`
- artifact digest: `sha256:ae19da40909326f76a0d46e9d99cb002afdb4a7c8c34d8b6eb11625937f4f233`
- PR #1 squash merge commit on `demo-client-v1`: `6c2bfaed45d2ebd484debf8e18732741353cad69`

### Per-game stream profiles and hero launch

Implemented and merged through PR #2.

Profiles are stored per Sunshine computer UUID + app ID:
- **Auto** — preserves the existing Moonlight preferences unchanged;
- **Fast** — maps only to the existing minimum-latency frame-pacing mode;
- **Balanced** — maps only to the existing balanced frame-pacing mode;
- **Quality** — maps only to the existing maximum-smoothness frame-pacing mode.

The initial profile layer deliberately does **not** change codec, resolution, FPS, bitrate, HDR, vendor decoder flags, Vulkan/direct rendering, PyroWave, or native protocol code.

Catalogue updates:
- focused game hero shows the selected profile and effective stream summary;
- profile chip opens an Auto/Fast/Balanced/Quality chooser;
- hero Play/Resume control now launches/resumes the focused game;
- existing card launch remains available as a fallback;
- `stream_profile_selected` and `stream_profile_applied` telemetry added.

Documentation:
- `docs/STREAM_PROFILES.md`

Feature-branch CI:
- run 26: SUCCESS
- workflow run id: `39894836154`
- tested app commit: `2cc435e20b81345ce623262cbc75d546b3537714`
- APK artifact id: `12475883400`
- artifact digest: `sha256:8f28b0df9bbb24f37aa22a078ee3a11937a7a29b44ba19b99ef5517f9bdcbb38`
- PR #2 squash merge commit on `demo-client-v1`: `1f37f20486c78f596c27453cd63ec227bf192709`

### Repository support added
- canonical rollback branch: `demo-client-v1-e2e-green-2026-10-23`;
- performance experiment branch: `perf-lab-v1`;
- `docs/PERF_LAB_V1.md`;
- `docs/UPSTREAM_FEATURE_REFERENCES.md`;
- Colab backup builder: `tools/Cloud_Gaming_Android_Builder.ipynb`;
- README points to this canonical handoff.

### Current next product slice
The next gate is **physical Android validation of 12.3**:
- hardware decoder path;
- Wi-Fi stream stability;
- hero Play/Resume;
- per-game profile selection;
- in-stream Phone controller QR;
- phone controller input while the game remains connected;
- Disconnect / Resume / End Game regression.

After the physical baseline, use `perf-lab-v1` for measured decoder/pacing experiments. Advanced renderer/decoder work remains isolated there until telemetry supports promotion.

## AWS guardrail

Do not restart paid AWS GPU infrastructure solely for client/UI work.

The previous Linux/AWS SuperTuxKart black-screen/SDL-Wayland problem remains a separate server-side issue. The successful Windows/Sunshine local test proves the custom Android client can render a real stream but does **not** by itself prove the previous Linux cloud host problem is resolved.

Only recreate a short-lived GPU worker after the physical client/controller gates are ready. Use SuperTuxKart first and terminate paid resources after the test.

## Continuation prompt

> Continue the Cloud Gaming client from `docs/CANONICAL_HANDOFF_2026-10-23.md` in `atifbukhari/Cloud-Gaming-Client`. Work on `demo-client-v1`; preserve `demo-client-v1-e2e-green-2026-10-23` as the immutable rollback checkpoint. The custom 12.2-demo2 baseline was proven end-to-end locally against Sunshine on Windows, including pairing, SuperTuxKart streaming, structured telemetry, Disconnect, Resume and End Game. The 12.3-demo1 code is now merged and CI-green with a controller-friendly in-stream Game Menu, game-first catalogue hero, in-stream TV phone-controller QR, functional hero Play/Resume, and per-game Auto/Fast/Balanced/Quality profiles where Auto preserves existing Moonlight behavior and the other profiles only map to existing frame-pacing modes. Do not rewrite the proven Moonlight streaming core. The next gate is physical Android validation of the 12.3 features and hardware decoder/Wi-Fi behavior. Keep advanced Starwire/Moonlight-X/Artemide renderer/decoder changes isolated in `perf-lab-v1` and promote only from measured telemetry. Do not start paid AWS GPU compute until the physical Android/client-controller gates are ready.
