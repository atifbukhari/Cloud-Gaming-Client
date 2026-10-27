# Cloud Gaming Platform 2.0 — Canonical Program Handover & Continuation Runbook

> **2026-10-27 economic/technology refinement:** [Joint venture/co-investment + AV1 savings + Cloud AI Desktop architecture](SHARED_FUNDING_AV1_AI_DESKTOP_STRATEGY_2026-10-27.md). Prior gaming-only financial models are baseline scenarios, **not** the target product boundary or agreed partner split. GPU desktop, CPU workspace and AI inference have separate metering and licensing.


**2026-10-27 commercial continuation:** Read [Unit Economics & Market Focus](UNIT_ECONOMICS_MARKET_FOCUS_2026-10-27.md) before committing GPU/consumer product spend. Primary target partner-funded operator SaaS; secondary paid interactive demo campaigns. All modeled pricing/margins are unvalidated hypotheses.
**Date:** 2026-10-26  
**Read with:** [Master plan](PLATFORM_MASTER_PLAN_2026-10-26.md), [Delivery WBS](PLATFORM_DELIVERY_WBS_2026-10-26.md), [Client canonical handoff](CANONICAL_HANDOFF_2026-10-23.md), [Decision/source ledger](PLATFORM_DECISIONS_AND_SOURCES_2026-10-26.md)

> **Read this first when resuming after context loss.** The authoritative **verified implementation** is the dated client canonical handoff and matching GitHub branch/build evidence; this document is the program continuation index and operational safety policy. The target architecture is **not** deployed simply because this plan is merged. Do not fabricate progress, financials, current AWS spend, operational access, licensing rights or acceptance results.

## 1. Canonical order of reading
1. [PLATFORM_HANDOVER_RUNBOOK_2026-10-26.md](PLATFORM_HANDOVER_RUNBOOK_2026-10-26.md) — how to resume, validate truth, protect state and assign next step.
2. [PLATFORM_MASTER_PLAN_2026-10-26.md](PLATFORM_MASTER_PLAN_2026-10-26.md) — target product architecture, commercial differentiation, constraints.
3. [PLATFORM_DELIVERY_WBS_2026-10-26.md](PLATFORM_DELIVERY_WBS_2026-10-26.md) — work-item IDs, gates, owners, tests and dependencies.
4. [PLATFORM_DECISIONS_AND_SOURCES_2026-10-26.md](PLATFORM_DECISIONS_AND_SOURCES_2026-10-26.md) — known vs assumptions, ADRs, source traceability and risk register.
5. [CANONICAL_HANDOFF_2026-10-23.md](CANONICAL_HANDOFF_2026-10-23.md) — actual client progress and tested technical evidence.
6. [PHYSICAL_ANDROID_VALIDATION.md](PHYSICAL_ANDROID_VALIDATION.md), [CI_BUILD_STRATEGY.md](CI_BUILD_STRATEGY.md), [STREAM_PROFILES.md](STREAM_PROFILES.md), [UPSTREAM_FEATURE_REFERENCES.md](UPSTREAM_FEATURE_REFERENCES.md).

All linked documents are in `atifbukhari/Cloud-Gaming-Client` / `demo-client-v1`. Plans are versioned and reviewable; a new engineer/LLM should not search an old conversation instead of checking repository HEAD.

## 2. Authority and provenance
- **GitHub client repo:** https://github.com/atifbukhari/Cloud-Gaming-Client
- **Active client branch:** `demo-client-v1`
- **Known-good pre-iteration rollback:** `demo-client-v1-e2e-green-2026-10-23`, source checkpoint `78c92b2ca05b5d59ae25c1b4f5d39db5b2c33fc9` as recorded in the dated handoff. **Never rewrite this branch.**
- **Experimental client performance branch:** `perf-lab-v1` (verify current presence/HEAD; do not claim experiments have been deployed).
- **Stable custom physical/hardware E2E:** **NOT YET VERIFIED** as of this handover. The demonstrated local gameplay validation was with the **Android TV emulator**, not a physical phone.
- **Proven local Android APK:** `12.2-demo2`, Android package `com.atifbukhari.cloudgaming.debug` for the custom demo.
- **Latest recorded 12.3 build:** `12.3-demo2`, versionCode 317, build commit `e86a72305a8e54897f19869e3315f1a3d0cbfd70`, Actions run `39895824937` SUCCESS. This confirms CI build, **not** on-device E2E.
- **Post-build docs/layout/harness changes:** 2026-10-23 follow-up, latest pre-plan HEAD `1771ea652d7f87b1b4a7ea326d2ee321894b3a44`. Repo HEAD changes with new documentation; always query GitHub live.
- **Sunshine Windows:** historical local test used `v2026.1006.15912`; do not assert current machine state without inspection.
- **Physical phone:** user previously reported an ADB-authorized device; **do not publish device serial/unique IDs** in docs. Device currently connected? Check at execution time.
- **Cloud host:** previous Linux/AWS black-screen/headless issue remains unresolved and separately gated.
- **Paid AWS GPU:** should remain off until approved scoped cloud test. Verify AWS resource state live; handoff cannot guarantee that resources are still off.

## 3. Re-establish repo/build state (no secrets)

```bash
git clone --recursive https://github.com/atifbukhari/Cloud-Gaming-Client.git
cd Cloud-Gaming-Client
git fetch --all --prune
git checkout demo-client-v1
git submodule update --init --recursive
git status --short
git log -5 --oneline
```

Validate active branch and CI before editing. Read `.github/workflows/demo-client-apk.yml`.
- Primary build: GitHub Actions; normal pushes use Java/Android preflight and only explicit workflow dispatch or `[build apk]` commits package an APK per [CI_BUILD_STRATEGY.md](CI_BUILD_STRATEGY.md).
- SDK/NDK: Java 17, Android SDK 36 / Build Tools 36 / NDK 29.x per workflow. Do **not** substitute guessed local versions.
- APK artifact name: `cloud-gaming-demo-apk`; normally retained for **7 days**. Old links/artifacts may expire. When expired, request a new dispatch, record new run SHA, artifact digest and extracted APK SHA.
- `tools/Cloud_Gaming_Android_Builder.ipynb`: secondary Colab builder when SDK/NDK local downloads are bottlenecked.
- `tools/validate-android-device.ps1`: physical phone ADB/clock/codec/logcat validation tool. Inspect device selection and avoid uploading unredacted logcat.

## 4. Local Windows + Android physical test recipe

### Preparation
- Windows test PC is a **known-good local Sunshine host** with SuperTuxKart configured from earlier E2E. Treat its service, pairing, game manifests and host settings as user state; back up before changes.
- Android ADB path historically: `%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe`. On PowerShell where `adb` is not in PATH, use the absolute path with `&`.
- On physical Android phone: developer options and USB debugging on, RSA approved, device unlocked; same LAN/Wi-Fi as host for the initial LAN test; use automatic device clock to avoid TLS certificate failures.
- Emulator-specific alias `10.0.2.2` was unreliable in earlier pairing and a stale emulator snapshot produced ~32-minute clock drift. Prefer verified LAN address and clock consistency. A prior LAN address (`192.168.18.26`) is **historical, not static**.

### Non-destructive discovery commands
```powershell
$adb = "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe"
& $adb devices -l
& $adb -s <YOUR_CURRENT_DEVICE_SERIAL> shell getprop ro.product.model
& $adb -s <YOUR_CURRENT_DEVICE_SERIAL> shell getprop ro.build.version.release
& $adb -s <YOUR_CURRENT_DEVICE_SERIAL> shell date "+%Y-%m-%dT%H:%M:%S%z"
Get-Date -Format o
Get-Service SunshineService -ErrorAction SilentlyContinue
Get-Process sunshine,supertuxkart -ErrorAction SilentlyContinue
```
The `<YOUR_CURRENT_DEVICE_SERIAL>` token must be replaced by the actual ADB serial; never commit it or private identifiers.

### Installation and validation
1. Resolve the **newest verified** artifact matching intended tested commit—not simply newest filename. If no artifact retained, dispatch CI build and confirm success.
2. Run `powershell -ExecutionPolicy Bypass -File .\tools\validate-android-device.ps1 -ApkPath <APK_PATH>` from the repo checkout. Write captures outside git and redact logs before sharing.
3. Confirm phone hardware MediaCodec decoder (vendor codec, not emulator `OMX.google.h264.decoder`).
4. Pair to Sunshine using appropriate host address and a fresh one-time PIN; do not print protocol URLs/certificates or personal authorization data in chat/log bundles.
5. Launch SuperTuxKart; verify first actual frame, audio, input, repeated telemetry, profile behavior and stream exit.
6. Test in-stream QR controller on **Android TV** with **another phone**. Check the QR dialog does not stop the stream; validate token expiry and accidental/unauthorized pairing.
7. Execute Disconnect (game retains state) → Resume → End Game (game closes, no orphan streams) and collect evidence.
8. Record actual device/host OS, display, network and run timestamp. Distinguish codec decode time from full input-to-photon.
9. Update physical test doc, handoff status and linked issue only when evidence has been captured.

### Safe troubleshooting
- `unauthorized` ADB: unlock and approve RSA dialog, do not recreate project.
- Pairing `unexpected end of stream`: verify clock, Sunshine process, real IP and certificate handshake; do not assume permanent protocol bug.
- Black stream: check actual game process, display topology, capture errors, encoder, first video packet and decodes; distinguish host from client.
- Android emulator stutter with software decoder: do not infer physical user QoE.
- End Game fails: check authenticated Sunshine quit, host process and agent/session ownership; never kill unrelated user processes.
- USB device absent: change to data cable, phone USB data mode and correct OEM drivers if needed.

## 5. Experimental host/testing procedure

Keep the proven host unchanged. Use a separate Windows worker/VM:
1. Take OS/system config snapshot/backup and document GPU/driver/Windows build.
2. Install pinned Vibeshine stable release after verifying authentic checksums and any display/gamepad driver implications; confirm license and rollback.
3. Pair the **same** known-good Android client and stream the same authorized title over the same route/profile.
4. Test virtual displays, clean gamepad ownership, reconnect, crash recovery, unattended boot and teardown.
5. Collect encode/capture/frame, GPU/VRAM, device, start/stop and host metrics.
6. Contrast with Sunshine in a signed A/B report. Select main host only on repeatable results.
7. Keep LuminalShine/LuminalVGD in separate research; no host-driver swapping on the test laptop. Its AGPL/commercial driver license requires review before commercial integration.
8. Linux host black-screen work is a separate investigation; record root cause or formal decision to make Windows-first pilot.

### Cloud GPU test authorization
Do **not** invoke AWS GPUs until all are true:
- physical client gate passed;
- explicitly approved GPU instance type/region, per-test cost cap and TTL;
- planned game rights and OS licensing checked;
- automatic tag, budget alarm, cleanup timer, session audit and failure shutdown implemented;
- explicit end-of-test verification that no billable GPU/volumes/addresses/relay resources remain;
- short-lived staging credentials only; never expose AWS credentials.

## 6. Architecture/API artifacts required for new services

When implementing new modules create their own repos or subdirectories with:
- `README`: purpose, deps, local setup, test command, owner and license.
- `openapi.yaml`: client-control/public APIs plus examples without secrets.
- `schema/`: session, host capabilities, entitlement, catalogue and event schemas with compatibility policy.
- `infra/`: versioned IaC with budget-tag & teardown.
- `docs/ADR-...`: significant decisions and rejected alternatives.
- `tests/`: unit, integration and fault-injection, including abandoned GPU session cleanup.
- `RUNBOOK.md`: start/recover/rollback/security/logging.
- CI + branch protection and secure release artifacts.

Proposed future components: `platform-control` (auth/rights/commerce/CMS), `sessionos` (state/leases/placement), `host-agent` (trusted machine worker), `web-player` (WebRTC), `operator-portal` (partner UI). **No claim these code repositories already exist**.

## 7. SessionOS disaster recovery / safety runbook
- First classify whether client disconnection, game process failure, capture/encoder failure, control-plane failure or host GPU/driver reset.
- Retry only idempotent actions with bounded count and wall-clock deadline. Never keep restarting the GPU worker indefinitely.
- On user disconnection: start grace timer, retain only according to entitlement; rotate reconnect grant.
- On host capture failure: attempt adapter-defined lightweight reset only when the player/session owns the resource; report capture-specific error.
- On host GPU/TDR or dead display stack: fail/reassign/terminate according to game save/reconnect policy; never claim seamless live GPU migration.
- On control-plane partition: hosts enforce lease expiry/max duration and cannot create unbounded sessions offline.
- On worker termination: run post-mortem, verify release of virtual displays, gamepads, user data, GPU and egress resources; confirm no billable leak.
- On billing uncertainty: ledger events are immutable; reconcile before charging, never double charge after API retries.
- Preserve redacted logs/trace IDs before destructive action when safe; escalate if rights/security/cross-tenant isolation could be compromised.

## 8. Operators, rights and commercial handover
Partner packet must include:
- tenant/operator ID, allowed regions, branding, identity protocol, commercial SKU maps, network data/QoD availability and support owner;
- signed game title rights manifest with publisher/storefront/cloud scope, permitted regions, validity and anticheat/gaming OS compatibility;
- billing reconciliation model and refund rules, gross/variable contribution assumptions and partner revenue share, approved spending caps;
- telemetry consent, retention, data residency, privacy and policy endpoints;
- regional GPU/relay routes, per-player capacity, SLO, known failure cases and escalation;
- support portal roles and partner-specific feature flags.

Do not treat ordinary customer game ownership as a license to provide commercial gaming VMs. Do not turn on wallet/staking/recordings/ads without legal/consumer/privacy approval.

## 9. Prioritized continuation queue

**Next work to execute in sequence**
1. `EX-002` verify the physical phone's identity, ABI, clock and actual installed version with `validate-android-device.ps1`.
2. `EX-003` run real hardware SuperTuxKart 720p60/1080p60 where supported.
3. `EX-004` exercise TV→phone QR control with stream live; `EX-005` per-profile baseline; `EX-006` lifecycle regression.
4. `HS-001` record/backup proven Sunshine host; build an **independent** Vibeshine lab before replacing anything.
5. `HS-011` draft Host Agent capability contract and error model (no GPU required).
6. `CP-001` draft SessionOS API, state machine, entitlement/rights schema (local mocks, no paid resources).
7. `CO-003` only after G0/G1 and spend authorization, run a short-lived GPU host proof, retain logs and tear down.
8. Open partner/publisher rights and billing/edge dependency tracking; never block no-cost local architecture work on these external approvals.

## 10. Work status record template (append per milestone)
```markdown
### YYYY-MM-DD / <WORK-ID> / <OWNER>
Decision and scope:
Branch/commit:
PR/issues:
Artifact/run SHA, build version:
Device/host/GPU/driver/network:
Rights/tenant authorization:
Test command and environment:
Observed metrics (p50/p95, sample N):
PASS / FAIL / BLOCKED and reason:
Open regression / security / cost risk:
Rollback point:
Cloud resources created / confirmed destroyed:
Next owner and next gate:
```

Do not record passwords, tokens, PINs, raw device IDs or private game captures in repo issues.

## 11. Handoff prompt for a new engineer or LLM

> We are building an operator-ready cloud gaming platform. Start with `docs/PLATFORM_HANDOVER_RUNBOOK_2026-10-26.md`, then the linked master plan, WBS, decisions/source ledger and `docs/CANONICAL_HANDOFF_2026-10-23.md` in `atifbukhari/Cloud-Gaming-Client` branch `demo-client-v1`. Verify GitHub branch HEAD and CI rather than trusting older conversational versions. Keep `demo-client-v1-e2e-green-2026-10-23` immutable. The actual known-good local proof is a custom Android TV **emulator** streaming SuperTuxKart from local Windows Sunshine with lifecycle/telemetry; 12.3 enhancements are CI-built but still need physical phone/TV test. An ADB phone had been authorized previously, but no physical streaming success has been recorded. Do not assume GPU workloads, SessionOS, WebRTC player or commercial platform are deployed. Next: EX-002 through EX-006 physical Android/QR/lifecycle; then isolated Windows Vibeshine host lab and SessionOS/API scaffolding. Do not start paid AWS GPU before physical gate and approved budget+TTL. Preserve game rights, license, isolation, secrets and cleanup requirements. Update measured proof and authoritative handoff with every accepted milestone.

---

**If a linked file is missing or a CI artifact expired, report the discrepancy, retrieve current GitHub source, and restore an equivalent tested artifact; never invent a successful test or silently choose unverified binaries.**
