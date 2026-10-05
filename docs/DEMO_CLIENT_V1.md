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
