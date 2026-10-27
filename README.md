> **Chromebook education MVP (2026-10-27):** [AI Skills Lab for Pakistan's planned 500,000-device Chromebook program — focused MVP/demo plan](docs/CHROMEBOOK_AI_SKILLS_LAB_MVP_2026-10-27.md). This is **proposed**, not a deployed product or government agreement.

> **Hybrid Gaming + AI Desktop direction (2026-10-27):** [Co-funded Sky47/Jazz/platform partnership, AV1 bandwidth savings, CPU AI workspaces, GPU desktops and inference economics](docs/SHARED_FUNDING_AV1_AI_DESKTOP_STRATEGY_2026-10-27.md). The model and JV shareholding are **illustrative**, not agreed.

> **Pakistan commercial pilot model (2026-10-27):** [Platform + Sky47 + Jazz CAPEX/OPEX, partner margins, break-even and rollout gates](docs/PAKISTAN_SKY47_JAZZ_TRIPARTITE_ECONOMICS_2026-10-27.md). All numbers are editable economic scenarios, not negotiated terms.

**2026-10-27 commercial focus:** [Unit economics and lowest-CAPEX segment strategy](docs/UNIT_ECONOMICS_MARKET_FOCUS_2026-10-27.md) — prioritize partner-funded GPU operator software; use paid, capped playable demos as fast-revenue wedge. Pricing scenarios are hypotheses; see editable workbook supplied with review.

> **Cloud Gaming Platform 2.0 — program plan (2026-10-26):** [Master architecture](docs/PLATFORM_MASTER_PLAN_2026-10-26.md) · [Delivery roadmap/WBS](docs/PLATFORM_DELIVERY_WBS_2026-10-26.md) · [Handover runbook](docs/PLATFORM_HANDOVER_RUNBOOK_2026-10-26.md) · [Decisions, risks & scope sources](docs/PLATFORM_DECISIONS_AND_SOURCES_2026-10-26.md) · [Review & approvals](docs/PLATFORM_REVIEW_CHECKLIST_2026-10-26.md). These are **review proposals** unless a gate is evidenced as passed; the dated client handoff below remains authoritative for actually verified Android E2E.

> **Cloud Gaming demo fork:** active product work is on `demo-client-v1`. The canonical continuation point is [`docs/CANONICAL_HANDOFF_2026-10-23.md`](docs/CANONICAL_HANDOFF_2026-10-23.md). Preserve `demo-client-v1-e2e-green-2026-10-23` as the known-good local E2E rollback checkpoint. Advanced renderer/decoder experiments belong on `perf-lab-v1`.

# Moonlight Android

[![AppVeyor Build Status](https://ci.appveyor.com/api/projects/status/232a8tadrrn8jv0k/branch/master?svg=true)](https://ci.appveyor.com/project/cgutman/moonlight-android/branch/master)
[![Translation Status](https://hosted.weblate.org/widgets/moonlight/-/moonlight-android/svg-badge.svg)](https://hosted.weblate.org/projects/moonlight/moonlight-android/)

[Moonlight for Android](https://moonlight-stream.org) is an open source client for NVIDIA GameStream and [Sunshine](https://github.com/LizardByte/Sunshine).

Moonlight for Android will allow you to stream your full collection of games from your Windows PC to your Android device,
whether in your own home or over the internet.

Moonlight also has a [PC client](https://github.com/moonlight-stream/moonlight-qt) and [iOS/tvOS client](https://github.com/moonlight-stream/moonlight-ios).

You can follow development on our [Discord server](https://moonlight-stream.org/discord) and help translate Moonlight into your language on [Weblate](https://hosted.weblate.org/projects/moonlight/moonlight-android/).

## Downloads
* [Google Play Store](https://play.google.com/store/apps/details?id=com.limelight)
* [Amazon App Store](https://www.amazon.com/gp/product/B00JK4MFN2)
* [F-Droid](https://f-droid.org/packages/com.limelight)
* [APK](https://github.com/moonlight-stream/moonlight-android/releases)

## Building
* Install Android Studio and the Android NDK
* Run ‘git submodule update --init --recursive’ from within moonlight-android/
* In moonlight-android/, create a file called ‘local.properties’. Add an ‘ndk.dir=’ property to the local.properties file and set it equal to your NDK directory.
* Build the APK using Android Studio or gradle

## Authors

* [Cameron Gutman](https://github.com/cgutman)  
* [Diego Waxemberg](https://github.com/dwaxemberg)  
* [Aaron Neyer](https://github.com/Aaronneyer)  
* [Andrew Hennessy](https://github.com/yetanothername)

Moonlight is the work of students at [Case Western](http://case.edu) and was
started as a project at [MHacks](http://mhacks.org).
