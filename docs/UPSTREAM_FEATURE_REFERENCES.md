# Upstream Feature References and Porting Notes

This file tracks external Moonlight-derived projects reviewed for the Cloud Gaming client. It is an engineering/attribution aid, not a claim that every listed feature has been imported.

## Baseline

The product client is derived from Moonlight Android and carries GPL-3.0 licensing. The proven product baseline remains the Cloud Gaming fork; external forks are references for selective feature work rather than wholesale replacements.

## Reviewed projects

### Moonlight Android
Repository: `moonlight-stream/moonlight-android`

Role: protocol/client baseline and primary upstream ancestry.

### Moonlight X
Repository: `MoreOrLessSoftware/moonlight-android`

Relevant reviewed areas:
- quick-launch applications;
- per-app stream settings;
- in-stream overlay menu;
- performance/stat overrides;
- ultra-low-latency decoder flags;
- direct/Vulkan/PyroWave work in its extended video path.

Moonlight X's `AppPreferences` / `AppStreamSettings` model is the principal reference for future per-game configuration. The Cloud Gaming product should expose simpler presets first rather than clone the entire enthusiast settings surface.

### Artemis
Repository: `ClassicOldSong/moonlight-android`

Relevant reviewed areas:
- touch and trackpad modes;
- pan/zoom;
- custom virtual controls;
- in-game/back menu concepts;
- virtual display and host integrations when paired with Apollo;
- controller/input quality-of-life features.

### Artemide
Repository: `derflacco/moonlight-android`

Relevant reviewed areas:
- MediaTek low-latency experimentation;
- lower-delay/LFR behavior;
- Fast V-Sync / Nano Pacer;
- async decode experimentation.

Treat these as performance-lab candidates until validated on physical hardware.

### Starwire
Repository: `SicarioGaming/Starwire`

Starwire is an integration reference that documents provenance for features it combines from Moonlight X, Artemis, Artemide, Moonlight V+, Moonlit Artemis and Moonlight X Turnip.

Relevant product ideas:
- selected-game hero catalogue;
- cover-driven/game-first presentation;
- in-stream control grid;
- understandable settings categories;
- predicted-impact UX around advanced stream settings.

Relevant engineering ideas:
- Vulkan/direct rendering;
- PyroWave;
- Fast V-Sync;
- LFR;
- async decoding;
- device-specific low-latency behavior.

Do not make Starwire the new upstream. It is a young aggregate fork and should be used as a map to the original implementation/provenance wherever possible.

## Product-branch policy

Safe candidates for `demo-client-v1`:
- UI/catalogue presentation;
- in-stream menu;
- QR phone-controller access;
- local telemetry presentation;
- simple per-game profiles built on existing stable Moonlight settings;
- input/touch UX that does not replace core streaming behavior.

Performance candidates belong in `perf-lab-v1` first:
- decoder vendor flags;
- LFR;
- async decode;
- Fast V-Sync;
- Vulkan/direct renderer;
- PyroWave;
- custom Turnip driver.

## Licensing / attribution rule

Before copying implementation code from a fork:
1. identify the originating repository/author when the fork itself attributes an older source;
2. preserve required GPL notices and copyright headers;
3. record the source commit/path in the implementing commit or accompanying documentation;
4. keep the distributable client's corresponding source available as required by GPL-3.0;
5. review any bundled third-party native libraries separately because their licenses may differ from the application GPL license.

Ideas and UX concepts may be independently implemented, but direct code ports must retain provenance.

## Current Cloud Gaming differentiation

Do not lose the product-specific work already implemented:
- operator-oriented branded catalogue;
- local structured telemetry;
- explicit Disconnect vs End Game semantics;
- TV QR -> phone controller without phone app installation;
- future automatic profile/device tuning;
- host abstraction while keeping Sunshine as the proven default.

The goal is not to become an enthusiast Moonlight settings fork. The goal is to use the best proven streaming techniques behind a simple cloud-gaming product UX.
