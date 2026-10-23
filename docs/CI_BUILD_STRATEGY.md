# Android Client Build Strategy

## Primary path: GitHub Actions

The authoritative reproducible build path is `.github/workflows/demo-client-apk.yml`.

Normal pushes to `demo-client-v1` run:
1. checkout with recursive submodules;
2. Java 17 setup;
3. Android SDK 36 / Build Tools 36 / NDK 29 setup;
4. Java + Android resource preflight.

An APK is packaged only when:
- the workflow is manually dispatched; or
- the pushed commit message contains `[build apk]`.

The preflight and conditional APK packaging intentionally run in the **same job/runner**. This avoids downloading/configuring the Android SDK/NDK twice for one explicit APK build and reduces the number of independent failure points.

## Artifact

Explicit package builds upload:
- artifact name: `cloud-gaming-demo-apk`;
- source path: `app/build/outputs/apk/nonRoot/debug/*.apk`;
- retention: 7 days.

For a release candidate, record the workflow run ID, source commit, artifact ID, artifact digest, APK version and—after extraction—the APK SHA-256 in the canonical handoff.

## Backup path: Google Colab

`tools/Cloud_Gaming_Android_Builder.ipynb` is the interactive backup builder.

It is useful when:
- a developer machine has slow Google SDK/NDK downloads;
- an interactive cloud build is useful;
- GitHub Actions is temporarily unavailable.

Colab is **not** the authoritative CI history.

## Windows development machine

The Windows laptop remains primarily a:
- source/debug station;
- ADB station;
- Android TV emulator station;
- Sunshine local host;
- physical-device installation station.

A full local NDK install is optional for day-to-day testing because CI/Colab can build the APK.

## Build safety

Do not put signing secrets, GitHub tokens, Sunshine credentials or operator secrets into:
- the repository;
- the Colab notebook;
- build logs;
- APK resources.

Production signing/release automation must use protected secret storage and is outside the demo build workflow.
