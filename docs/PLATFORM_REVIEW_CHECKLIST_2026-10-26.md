# Cloud Gaming Platform 2.0 — Review & Approval Checklist
**Review created:** 2026-10-26  
**Status:** Open for product, technical, commercial and security review. No implied approvals.

> GitHub Issues are disabled in this repository at this time, so this version-controlled file is the initial review tracker. When issue tracking is enabled or a project management system is designated, migrate open review items without losing history.

## Review documents
- [Master platform architecture](PLATFORM_MASTER_PLAN_2026-10-26.md)
- [Delivery WBS and gates](PLATFORM_DELIVERY_WBS_2026-10-26.md)
- [Canonical continuation/handover](PLATFORM_HANDOVER_RUNBOOK_2026-10-26.md)
- [Decisions, sources and risk register](PLATFORM_DECISIONS_AND_SOURCES_2026-10-26.md)
- [Existing Android client implementation handoff](CANONICAL_HANDOFF_2026-10-23.md)

## Business / market
- [ ] Accept operator-first platform positioning, differentiators and product lines.
- [ ] Identify pilot operator/territory, network capabilities and named partner owner.
- [ ] Choose pilot SKU/bundle/playtime/instant-trial business model.
- [ ] Identify authorized commercial game titles and written publisher/cloud-use terms.
- [ ] Approve target price, expected utilization and margin methodology.
- [ ] Identify approved data/billing/edge integration and responsible counterparties.

## Technical architecture
- [ ] Confirm Stage 1 Android TV/phone and timing of browser/desktop/iOS surfaces.
- [ ] Accept native Moonlight/GameStream path plus optional browser WebRTC path.
- [ ] Accept Host Agent abstraction and versioned capability/operation contract.
- [ ] Keep Sunshine as proven baseline while Vibeshine is benchmarked on an isolated lab machine.
- [ ] Defer LuminalShine and AGPL LuminalVGD integration pending license/build/security evidence.
- [ ] Approve SessionOS state machine, lease lifecycle and idempotent billing events.
- [ ] Approve dedicated per-user environments initially and explicit tests before GPU density claims.
- [ ] Approve data stores, telemetry envelope, tenant boundaries and retention policy.
- [ ] Approve deterministic first-phase QoE rules and performance lab promotion gate.

## QA / next execution gate
- [ ] Verify physical Android phone 12.3 install, decoder, stream and telemetry (EX-002/003).
- [ ] Verify TV QR→phone in-stream input (EX-004).
- [ ] Measure Auto/Fast/Balanced/Quality on physical hardware (EX-005).
- [ ] Verify repeated Disconnect/Resume/End cleanup (EX-006).
- [ ] Decide Windows-first versus Linux after Linux headless black-screen root cause is documented.
- [ ] Confirm measurable pilot SLOs and input-to-photon instrument methodology.

## Finance, security and legal
- [ ] Approve GPU test location/type, maximum spend and automatic TTL cleanup before provisioning.
- [ ] Legal review: publisher rights, OS/GPU licensing, Moonlight/Sunshine GPL obligations, relevant AGPL/commercial driver terms.
- [ ] Security review: session grants, companion controller identity, least privilege, tenant/user isolation, encrypted saves, audit.
- [ ] Privacy review: gameplay captures, telemetry retention, minors and data residency.
- [ ] Agree operator billing reconciliation, refund and attribution handling.
- [ ] Name incident/on-call owner and release/rollback approval owner.

## Review log (append entries, never silently rewrite old outcomes)

| Date | Reviewer / role | Section / ADR ID | Decision | Evidence | Next action |
|---|---|---|---|---|---|
| 2026-10-26 | Unassigned | Whole plan | **Pending** | Documents committed | Nominate reviewers and approve/reject sections |

## Review-outcome rules
- Accepted decisions must be mirrored with owner/date/evidence into [PLATFORM_DECISIONS_AND_SOURCES_2026-10-26.md](PLATFORM_DECISIONS_AND_SOURCES_2026-10-26.md).
- Work tickets must map to stable IDs in the WBS even before Issue tracking is enabled.
- A review checkbox does not certify production readiness or passing a technical gate.
- Next owner for development upon approval: begin EX-002/003 physical Android verification; no paid AWS GPU until G0 and approved spend cap.

## Reviewer handoff shortcut

> Read the 2026-10-26 master plan, the delivery WBS and decisions ledger. Start by commenting on commercial target, game rights, Windows/Linux host direction, GPU budget and tenant/entitlement architecture. Record accepted choices here and in the ADR table. For technical continuation use the handover runbook and the existing dated Android canonical handoff; do not confuse planned SessionOS or real-device tests with deployed code.
