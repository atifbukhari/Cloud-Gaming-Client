# Cloud Gaming Demo Client v1

Branch: `demo-client-v1`

## User journey
1. One-time host pairing uses the upstream Moonlight flow.
2. When exactly one paired online host exists, the demo build automatically enters the game catalogue.
3. The catalogue is branded **Cloud Gaming** with the tagline **One GPU. Any screen. No console.**
4. Selecting a game uses the upstream Moonlight direct-launch path.
5. Streaming continues to use Moonlight's existing protocol, decoder, input and controller stack.

## Demo telemetry
The demo build:
- forces Moonlight's real performance overlay visible during a stream;
- records session start, connection stages, status changes, failures, termination and performance updates;
- stores a bounded local NDJSON log;
- exposes the log from the catalogue overflow menu under **Demo telemetry**;
- does not upload telemetry in v1.

## Advertising
Google Mobile Ads is wired using Google's official test App ID and test banner unit.
- test ads only;
- catalogue screen only;
- hidden on Android TV / Leanback devices;
- never overlays gameplay.

Production AdMob IDs must not be inserted until the demo placements are approved.

## Identity
The demo application ID is:
`com.atifbukhari.cloudgaming`

The demo branch minimum Android version is API 24 (Android 7.0), matching the current Google Mobile Ads SDK requirement. Upstream/master compatibility is unchanged.

The Java namespace intentionally remains `com.limelight` in v1 to minimize risk to the upstream streaming implementation.

## Build
GitHub Actions builds `assembleNonRootDebug` on every push to this branch and uploads the APK as `cloud-gaming-demo-apk`.


## Client readiness iteration (2026-10-05)

### APK validation
The run-11 artifact was downloaded and integrity-checked against the canonical handoff digest:
`4a86ad56aa7f5c7f1045f2d87069ef7f10fd37f5161838102db76f0c04091184`.
The APK contains native Moonlight libraries for arm64-v8a, armeabi-v7a, x86 and x86_64.
A physical/emulator launch test is still required because the current automation workspace has no Android device or ADB target.

### In-session Exit / End Session
Demo streams now expose a visible **Exit** control and Android Back opens the same leave-game flow:
- **Disconnect** closes only the client stream;
- **End game** requests host-side application termination through the existing GameStream HTTPS API, then returns to the catalogue;
- result and exit intent are recorded in local demo telemetry.

### TV catalogue
The demo catalogue now uses a TV-oriented dark layout with larger focused game cards.
The preferred demo order is:
1. SuperTuxKart
2. Xonotic
3. War Thunder
4. Veloren

`Test Ball` is engineering-only and is hidden from the consumer demo catalogue.

### Structured local telemetry
Telemetry remains local-only NDJSON, now with schema version 2.
Performance samples are parsed into dashboard-ready fields including stream resolution/FPS, incoming FPS, rendering FPS, network drop percentage, RTT/variance, host-processing latency and average decode time.
Demo mode explicitly enables Moonlight performance-stat generation so metrics are produced even when the user's normal performance-overlay preference is disabled.

### TV QR -> phone controller
Android TV / Google TV shows **Use phone as controller**.
The TV starts a demo-only HTTP controller endpoint on the local LAN and renders a QR code containing a high-entropy pairing token.
A phone on the same Wi-Fi network opens a lightweight controller page with D-pad, A, B and Start controls; those fixed controls are translated through the existing Moonlight keyboard input path.
No cloud endpoint, account, app installation, shell access or arbitrary command execution is involved.

This first controller version is deliberately optimized for the SuperTuxKart demo. Native virtual-gamepad semantics and analog axes remain a later enhancement after the basic QR journey is proven.

### Server guardrail
No paid AWS GPU worker is required for any of the changes above. SuperTuxKart's prior black-screen / SDL Wayland failure remains unresolved until a new end-to-end GPU stream proves otherwise.
