# Cloud Gaming Platform 2.0 — Architecture Decisions, Risks & Source Traceability
**Date:** 2026-10-26  
**Status:** Review ledger. Entries marked `PROPOSED` are **not** approved decisions.  
**Canonical program entry:** [PLATFORM_HANDOVER_RUNBOOK_2026-10-26.md](PLATFORM_HANDOVER_RUNBOOK_2026-10-26.md)

## 1. Authority and evidence classes
- **CONFIRMED:** supported by repository code/CI evidence, in-session device/host test logs, or explicit signed contract at specified date.
- **SOURCE SCOPE:** a *requirement or proposal* described in the uploaded scoping documents, not a deployed feature.
- **PUBLIC REFERENCE:** upstream repository feature/claims; not a benchmark on our platform.
- **PROPOSED:** current architectural recommendation; requires implementation and review.
- **OPEN:** a dependency or business/legal/technical decision with insufficient evidence.

Only `CONFIRMED` evidence can be cited to promote project status from `NOT TESTED` to `PASS`.

## 2. Recorded ADR-style architectural decisions

| ID | Status | Decision / rationale | Alternative / disqualifier | Review gate |
|---|---|---|---|---|
| ADR-001 | PROPOSED | Differentiate via operator integrations, SessionOS and QoE; reuse media engines | Own end-to-end custom codec now: high maintenance and little measured moat | Product sponsor |
| ADR-002 | PROPOSED | Decouple control and media transport | Proxy H.264/WebRTC frames via ordinary API servers | Architecture |
| ADR-003 | PROPOSED | Android TV/mobile first, browser second, desktop/iOS in phases | Big-bang all-platform CEF port | Product/client |
| ADR-004 | PROPOSED | Native Android/iOS video + platform-appropriate UI; React/CMS shared concept | CEF on every platform | Client lead |
| ADR-005 | PROPOSED | Sunshine stable; isolated Vibeshine Windows host lab; LuminalShine research lane | Replace current known-good host with nightly fork | Host/perf |
| ADR-006 | PROPOSED | Host Agent with versioned capabilities and short-lived authorized grants | Host-specific arbitrary admin operations embedded in client | Security/backend |
| ADR-007 | PROPOSED | One independent isolated game environment per commercial user initially | Conflate 4–8 virtual monitors with 4–8 safe independent users | Security/FinOps |
| ADR-008 | PROPOSED | WebRTC for browser/instant trials; Moonlight for native | Replace native gaming path with browser for all experiences | Web/media A/B |
| ADR-009 | PROPOSED | SessionOS state machine + idempotent leases/reap/usage ledger | Best-effort task scripts and billing from wall time | Backend/QA |
| ADR-010 | PROPOSED | Rule-based closed-loop QoE first; ML/AI on collected evidence | Let an LLM execute privileged stream/host actions without policy | QA/security |
| ADR-011 | PROPOSED | Multi-tenant modular backend, PostgreSQL, durable outbox, separate GPU agent | Start with many independent K8s services without ops maturity | Cloud/backend |
| ADR-012 | PROPOSED | Optional esports/ads/rewards/wallet boundary | Require blockchain/staking to access gameplay | Product/compliance |
| ADR-013 | PROPOSED | Game rights registry, territory restrictions and deny-by-default | Assume personal game purchase permits commercial cloud-streaming | Legal/publisher |
| ADR-014 | PROPOSED | Explicit budget, GPU test TTL and automatic shutdown | Provision long-running spot/on-demand GPU “to see how it goes” | FinOps |
| ADR-015 | PROPOSED | Keep public GPL source/attribution obligations and separately review AGPL drivers | Treat all fork or driver licenses as identical | Legal |
| ADR-016 | PROPOSED | One shared commercial backend with branding/partner configuration | Rebuild a streaming app and backend per operator | Product/backend |

### Decision outcome format
When approved, change status to `ACCEPTED`; record approving role/person, date, selected option, evidence, consequences, rollback, implementation PR and downstream document revisions. When rejected, mark `REJECTED` with rationale. Do not remove historical decisions.

## 3. Upstream project selection and licensing facts

| Reference | Source | Claimed capabilities useful to us | What we have actually proven | Concern |
|---|---|---|---|---|
| Moonlight Android | https://github.com/moonlight-stream/moonlight-android | Native low-latency GameStream client | Our derived 12.2 client completed local emulator/Sunshine E2E | Maintain GPL notices, per-device test |
| Sunshine | https://github.com/LizardByte/Sunshine | Cross-platform host, H.264/HEVC/AV1 | Local Windows/SuperTuxKart path | Linux headless AWS issue not fixed |
| Moonlight X | https://github.com/MoreOrLessSoftware/moonlight-android | Direct/Vulkan, per-app stream settings, overlay | Concept review; no experimental mode certified in our APK | Port path and device safety |
| Artemis | https://github.com/ClassicOldSong/moonlight-android | Input/touch, pan/zoom, host integrations | Concept review | Upstream code provenance |
| Artemide | https://github.com/derflacco/moonlight-android | Nano Pacer/Fast VSync, LFR, async decode | Concept review | Hardware validation required |
| Starwire | https://github.com/SicarioGaming/Starwire | Integrated fork with game-first UI and pacing options | Product inspiration only; our 12.3 has independent UI/features | Young aggregate fork, license and regression risks |
| Vibeshine | https://github.com/Nonary/vibeshine | Per-client virtual displays, WGC, Playnite, scoped APIs, VHF driver, Linux beta | Repo/release analysis only; no live benchmark in our host lab | Complex fork, experimental Linux, driver/AV compatibility |
| LuminalShine | https://github.com/NortheBridge/luminalshine | Windows-only Direct-to-Encoder, virtual display, WebRTC | Repo/release analysis only; no local deployment | Driver provenance, signed drivers, independent hardware tests |
| LuminalVGD | https://github.com/NortheBridge/LuminalVGD | Custom HDR virtual display driver | No integration into our system | AGPL-3.0 or separate commercial license; validate actual release source |
| Nonary display/gamepad | https://github.com/Nonary/libvirtualdisplay and https://github.com/Nonary/libvirtualgamepad | Smaller host primitives | MIT repos inspected; no operational integration yet | Verify exact pinned version and dependencies before shipping |

Relevant pinned upstream claims at time of review:
- Vibeshine stable 2.0.1 (published 2026-10-14); its latest 2.0.2 nightlies are **not** selected for a pilot.
- LuminalShine 26.10.0 (2026-10-26) claims bundled LuminalVGD driver, VHF gamepad and WebRTC; its release CI advises 842 tests passed, 12 failed, 10 skipped and hardware verification remains necessary.
- LuminalVGD repository has inconsistent older pre-development text relative to host release; treat actual signed MSI/driver/hash and corresponding source correspondence as a **hard diligence task**.
- Repo-wide GPL-3.0 and separately-licensed AGPL-3.0/commercial driver do **not** automatically allow proprietary closed-source distribution.
- Any official game/publisher/cloud use or distribution rights must be separately evaluated.

## 4. Uploaded scope documents — mapping and deliberate architecture deviations

These six pre-existing Project scope files are **inputs to planning**. They are not deployment reports or proof of completed work. Their original names are preserved for traceability. If they are not present to a subsequent assistant/engineer, do not claim detailed sections were re-verified until the actual file can be read.

| Original source | Source-derived objective or area | Plan mapping | Deliberate modification (proposal) |
|---|---|---|---|
| **Multi-platform App Scope.docx** | Cross-platform native client; CEF/React shared UI; hardware decoding; host multi-app context; input | Master §§4–5; EX-008, EX-015 | CEF only where suitable, native hardware rendering on mobile; staged platforms rather than all-at-once |
| **custom webrtc player.docx** | Windows/AWS capture→NVENC→WebRTC browser streaming, authentication, isolated instances and telemetry | Master §§6–8; EX-010/011, CP/CO | Reuse existing WebRTC host/managed stacks first; WebRTC complements native Moonlight |
| **Tournament Module Scope.docx** | Tournament creation, match automation, rosters, brackets, results, leaderboards and dispute handling | Master §11; EG-001–004 | Defer full esports stack until play economics and rights proven; official APIs first |
| **Simplified Initial Advertisement Module.docx** | Ad schedule dispatcher, advertiser handler, media processing, dashboard and payments | Master §§10–11; EG-005 | No forced game pause/interruption for ads; opt-in rewarded/pre/post-session first |
| **Blockchain related scope.docx** | GMRT wallet/payment, on-chain outcomes, staking, rewards, token entitlements | Master §§11,13; EG-007 | Optional compliance-gated integration; never require wallet or staking for gaming MVP |
| **WBS- Front end part with CEF.docx** | React/CEF shell, WebRTC input player, account/catalog, payment, settings, gamepad, blockchain UI | Master §§4,10–12; EX/CP/OP | Operator/rights/billing core first; client UI native where needed; token features optional |

**Source-versus-design warning:** The target product layers (SessionOS, network-aware QoE controller, scoped Host Agent, telco edge placement) are current design recommendations evolved from those scope documents and our current technical research. They are not necessarily commitments stated in each original scope. Maintain this distinction in proposals and estimates.

## 5. Risk register (initial; assign named owners and severity after review)

| Risk | Initial impact | Mitigation / acceptance | Early signal / trigger |
|---|---|---|---|
| R-01 Commercial game/publisher rights unavailable | Critical | Approved title/region rights matrix; do not make game available without rights | No license document or incompatible anti-cheat |
| R-02 False assumption of GPU density | Critical | Dedicated environments first; verified VM/driver isolation before shared GPU | Cross-session audio/input/VRAM leak |
| R-03 Cloud GPU cost/idle burn | High | Per-test TTL, quotas, budgets, cost-per-successful-play-hour ledger | Unexplained running GPU/egress or idle surge |
| R-04 Client physical hardware differences | High | Android phone+TV matrix, decoder/device identification, p95 metrics | Software codec/stutter or unexpected frame pacing |
| R-05 Headless Linux capture black screens | High | Isolated repro; Windows-first pilot decision if unresolved | Process alive but no fresh frames |
| R-06 Virtual display recovery wedges | High | Pinned host and drivers, bounded recovery, tested rollback | Orphan displays, failed encoder probe |
| R-07 Unauthorized controller/session takeover | Critical | Signed expiring role grants, rate limits, short grace, revocation | Multiple concurrent unauthorized input sources |
| R-08 GPL/AGPL/driver distribution incompatibility | Critical | Exact version license review and commercial terms | Closed-source product bundling incompatible component |
| R-09 Tenant data and save-game contamination | Critical | Resource isolation and sanitization, encrypted saves, red-team tests | Other player's save/input/video visible |
| R-10 Telco QoD/edge claims unsupported | High | Integrate only on supported network routes and operator APIs; measure before claiming | No SLA/QoS capability on pilot network |
| R-11 WebRTC NAT/TURN egress cost | Medium–high | Usage metering, TURN quotas and regional relays | Relay ratio high or egress over budget |
| R-12 Multiplatform simultaneous build overrun | High | Android first, browser next, desktop/iOS staged | Multiple clients stuck without verified stream |
| R-13 Payment double-charge / dropped events | Critical | Immutable ledger, idempotent outbox and reconciliation | Duplicate client requests, billing mismatch |
| R-14 Anti-cheat, launcher, DRM breakage | High | Game compatibility manifests, controlled rights pilot | Launch failure after vendor update |
| R-15 Hardware/warm-start timeout | Medium–high | Instrument boot→ready→first frame, predictive warm pool | Abandoned pre-play sessions |
| R-16 GPU/host support burden | High | Health agent, safe rollback, fault injection and second-engineer runbook | Persistent manual recovery tickets |
| R-17 Personalized telemetry privacy | High | Pseudonymize, minimize, retention/consent and access audit | Sensitive URLs, keys, video or identities in logs |
| R-18 Partner-revenue cannibalization | Medium–high | Measure bundle substitution and net incremental profit | Attributed gross increases, operator net doesn't |
| R-19 Unproven AI control actions | High | Deterministic policy engine first, human-approved high-impact changes | Oscillation or AI-triggered destructive restart |
| R-20 Unclear live resource/account state | High | Verify every AWS resource, host version and CI artifact directly | Plan references old versions/expired artifacts |

### Initial controls / risk acceptance
- Any **Critical** risk blocks a production/pilot launch until an accountable security/legal/engineering approver signs off.
- Exceptions require dated written scope, expiry and mitigation; the project plan cannot itself approve production risk.
- At weekly checkpoint: add owner, status, likelihood, evidence URL, and next action for top risks.
- After each serious incident, update failure mode and gate tests.

## 6. Open decision and assumptions log

| Question | Status | Evidence required | Responsible role |
|---|---|---|---|
| Pilot partner/operator and country | OPEN | Commercial sponsor and technical access | Product/partnership |
| Launch SKU (bundle, pay-time, subscription, trial) | OPEN | Price/payments/attribution/business case | Product/FinOps |
| Approved pilot game titles | OPEN | Publisher/cloud-rights + game launch matrix | Content/legal |
| Paid GPU limit / region / target concurrency | OPEN | Approved budget and provider quote | FinOps/cloud |
| Windows-first vs Linux-first host | OPEN | Headless capture measurements and rights | Host/cloud |
| Stable host engine for pilot | OPEN | Controlled Sunshine/Vibeshine A/B plus rollback | Host |
| Browser WebRTC in v1 or v1.1 | OPEN | Customer needs, browser QoE and TURN costs | Product/client |
| Tenant/auth design | OPEN | Operator SSO and identity scope | Backend/security |
| Host driver/Luminal AGPL terms | OPEN | Counsel and vendor signed terms if needed | Legal |
| Default QoE policy thresholds | OPEN | Hardware/user/network traces, no arbitrary defaults | QE |
| Save-game persistence title rights | OPEN | Publisher title-by-title terms | Legal/backend |
| Support SLA, residency, telemetry retention | OPEN | Partner requirements and observed SLO | Operator/security |

## 7. Design-review agenda and signoff template

1. Confirm *scope tier*: Pilot V1, Vision 2.0 and explicitly deferred.
2. Verify available physical Android testing and CI state.
3. Approve host lab isolation and candidate release versions.
4. Select SessionOS/control plane language/ownership and first API contracts.
5. Confirm title rights and operator network/payment partner.
6. Approve a specific AWS GPU test cap and automatic cleanup procedure.
7. Record acceptance metrics including first playable frame and cost/play-hour.
8. Decide browser trial priority, TV companion-control policy, and recording consent.
9. Resolve licensing obligations across distributed binaries and hosted drivers.
10. Update WBS owner, start status, ADR status and handover.

### Signoff record
```markdown
Date:
Decision IDs:
Approved/Rejected/Deferred:
Approving role(s):
Evidence:
Scope change:
Risk acceptance (if any):
Budget authority:
Impacted components:
Rollback:
WBS issue/PR:
Next review date:
```

Never infer that the absence of objections constitutes approval of commercial/technical/legal risks.

---

**This ledger is the companion to the master plan and must be updated as experiments produce real evidence.** Architecture decisions are separate from claims that code is deployed, game rights exist or business assumptions are profitable.
