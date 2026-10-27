# Hybrid Cloud Gaming + Cloud AI Desktop: Shared Funding, Codec Economics, and Product Strategy

> **SCOPE CLARIFICATION — 2026-10-27:** This file is a **future expansion and funding scenario**, not authorization to build four products simultaneously. First implement [Gaming-first MVP & Partner Demo](GAMING_FIRST_MVP_AND_DEMO_2026-10-27.md). AI desktop remains an optional downstream resource class; don't execute broad workspace features until the cloud gaming proof succeeds and buyer demand is established.

**Date:** 2026-10-27  
**Status:** Review hypothesis, not a JV agreement, price quotation, measured streaming result or guaranteed commercial return  
**Market:** Pakistan example, with Sky47 (infra), Jazz (network/distribution) and our platform company (software/IP) as **illustrative candidate parties**.

## Canonical links
- [Master platform architecture](PLATFORM_MASTER_PLAN_2026-10-26.md)
- [Tripartite GPU funding and operator margin base case](PAKISTAN_SKY47_JAZZ_TRIPARTITE_ECONOMICS_2026-10-27.md)
- [Commercial / lowest-CAPEX segment decision](UNIT_ECONOMICS_MARKET_FOCUS_2026-10-27.md)
- [Delivery WBS](PLATFORM_DELIVERY_WBS_2026-10-26.md)
- [Operational handover](PLATFORM_HANDOVER_RUNBOOK_2026-10-26.md)

## Executive decision

**Reposition target product to a multi-product Interactive GPU + AI Workspace Platform**, comprising:
1. Cloud Gaming (premium metered playtime, rights-cleared, low-latency video).
2. AI Workspace (CPU-backed remote desktop/browser/coding environment, with metered remote AI inference and enterprise policy).
3. GPU Creative Desktop (premium vGPU workstation for design/3D/creator AI).
4. Enterprise GPU Compute and sponsored playable demos (scheduled workloads, managed capacity, API-based inference, campaigns).

Do **not** promise every workload can run on the same GPU/OS or that 8 virtual monitors yield 8 secure users. Product segments need separate resource pools, admission policies, hardware/driver compatibility, and distinct commercial settlement. Build core session/identity/rights/telemetry/chargeback once and supply clients/workflows by product.

## 1. Three-way funding structures

### Option 1 — Jointly capitalized special-purpose venture
20 independently playable seat pilot's **hypothetical investment value PKR26m**, derived from prior model:
- PKR17m graphics gaming GPU servers/install;
- PKR5m platform implementation (new work, not automatically transfer of all background IP);
- PKR2m operator ID/billing integration;
- PKR2m local interconnect/testing.

Example contributed capital:
| Party | Value (PKR m) | Contribution | Illustrative economic stake |
|---|---:|---|---:|
| Sky47 | 12 | Cash and/or equipment, valued independently | 46.15% |
| Jazz | 9 | Cash, infrastructure/interconnect funding and working capital | 34.62% |
| Platform company | 5 | Defined new software work and non-exclusive licensable rights, independently valued | 19.23% |
| Total | 26 | Cash/equipment sources 21, in-kind new platform work 5 | 100% |

Do not claim an operator's future advertising spend, existing subscriber base or hypothetical future revenue is equivalent to cash paid into a JV. Contributions need contractual valuation; original platform IP stays owned by us unless explicitly sold. Prepaid capacity is a contract liability, **not** simultaneously equity capital; separate it from contribution math.

**Settlement priority:** collect revenue → pay direct external infra/network/AI compute and customer refunds → reimburse agreed fair-value operational services → maintenance replacement/interest/working capital reserves → dividendable free cash → distribute by negotiated equity, not arbitrary 30/30/40 **and** duplicate underlying lease profit.

At prior 20-seat 40% gaming utilization, **consolidated recognized net revenue PKR2.044m/mo**; direct cost incl GPU depreciation PKR1.226m; consolidated direct contribution **~PKR818,429/mo**. Amortizing platform+Jazz+interconnect setup PKR9m over illustrative 36 months subtracts PKR250k/mo; **~PKR568,429/mo contribution after amortization (~27.8%)**, **before loan interest, taxes, central R&D, royalties and any unpaid investment returns**. This is *not* an agreed JV dividend. A cash buffer still needed (~PKR486k/mo zero-revenue base cash running cost in model; 3-month minimum modeled cash reserve ~PKR1.46m, before broad contingencies). The PKR26m scope excludes such working capital reserves.

The earlier three-company margin waterfall (Sky lease + platform license + Jazz residual) is a separate **contracted-provider economic model**, not additive to the JV consolidated profit. Partners may additionally receive agreed service payments, but the JV must count those as expenses when determining dividends.

### Option 2 — Capital-light commercial alliance (preferred pilot)
- Sky47 owns/finances a handful of suitable existing graphics GPUs or leases verified capacity.
- Jazz contributes committed distribution, network/billing integration, pilot promotion, optional refundable **capacity purchase guarantee**.
- Platform company provides licensed software and implementation for a small minimum + usage-based upside.
- Each party funds its own preparation/operations. No immediate JV cap table. A later funded scale-up option can grant equity or convert limited prepaid credits subject to explicit contract.
- Reserve hardware procurement only after signed demand, proven GPU/game rights, uptime and utilization measurements.

### Option 3 — JV + vendor/equipment finance
- Three parties jointly fund a smaller equity tranche; third-party asset lender/vendor finances GPU hardware.
- Debt interest, security, asset title, expiry, FX and residual hardware value must be modeled separately.
- Do not equate availability of lease financing with lower **total** CAPEX; it lowers the *cash required up front* and adds financing expense/commitments.

## 2. AV1 codec savings and realistic limits

NVIDIA reports AV1 NVENC **up to approximately 40% lower bitrate than H.264** at comparable quality in 1080p60 test conditions. Source: https://developer.nvidia.com/blog/improving-video-quality-and-performance-with-av1-and-nvidia-ada-lovelace-architecture/ . This is not a guaranteed win over tuned HEVC, or at identical game low-latency encoding presets.

At 8 Mbps H.264 baseline, scenarios:
| Codec / target | Video Mbps | Decimal GB/h | 100-seat 29,200h/month network cost PKR6/GB | Monthly saving vs H.264 |
|---|---:|---:|---:|---:|
| H.264 reference | 8.0 | 3.60 | PKR630,720 | 0 |
| HEVC/tuned illustrative | 6.0 | 2.70 | PKR473,040 | PKR157,680 |
| AV1 30%-lower bitrate | 5.6 | 2.52 | PKR441,504 | PKR189,216 |
| AV1 40%-lower bitrate | 4.8 | 2.16 | PKR378,432 | PKR252,288 |

**All bitrate scenarios are targets; the exact device/game quality, AV1 motion, FEC, jitter, audio, encoder cost and physical decode must be measured.** The 100-seat 8Mbps model's network marginal cost is a hypothetical PKR6/GB from prior work, **not Jazz's internal tariff**. AV1 does not cure a bad GPU lease/utilization model: PKR189k saving is only 1.85% of modeled PKR10.22m monthly recognized revenue. It can, however, materially improve scarce data-bundle allowances and congestion.

### Codec implementation
- Hardware capability probe via `MediaCodecInfo` and performance points, not only Android version. Source: https://developer.android.com/media/optimize/performance/codec
- Host GPU hardware NVENC AV1 support (e.g. L4/L40S/appropriate Ada/Blackwell). Some high-end AI training GPUs including H100 have **no NVENC**, see https://docs.dynamo.nvidia.com/dynamo/dev/multimodal/video-decode-gpu-requirements
- Negotiate actual AV1/HEVC/H264 supported GameStream/WebRTC profile, 8/10-bit and hardware decoding; enforce graceful fallback.
- A/B target 720p60, 1080p60 with actual Android phone+TV, compare PSNR/VMAF + subjective motion/text, bitrate, dropped frames, decode time, input-to-photon p95, power/thermal and full actual GB delivered.
- Optimize at service level too: lower resolution when text/game content permits, game-aware capped FPS, adaptive network bitrate, error protection tuned to loss, no redundant spectator feed, on-net peering and realistic bundle economics.
- There is no such thing as universally zero-cost mobile bandwidth through the same operator.

## 3. AI desktop product design (three independent resource classes)

### Tier A — CPU AI Workspace (prioritize for reach and high margin)
- A per-tenant remote Linux/browser/desktop workspace for coding, office, terminals and tools.
- Local small CPU workloads; AI assistant using centrally metered model APIs or an enterprise model endpoint.
- No dedicated GPU required per interactive subscriber.
- Access via browser/mobile/Android TV/low-cost laptops, with multi-factor login, data retention, tenant isolation, and optional school/SMB admin.
- Pricing as monthly enterprise seat or time-limited student pack **plus separately metered token/AI usage allowance**; price must cover API spend.
- Bandwidth mostly low/variable. Microsoft RDP guidance: light 1.5Mbps, medium 3Mbps, heavy 5Mbps. Source: https://learn.microsoft.com/en-us/windows-server/remote/remote-desktop-services/network-guidance
- Avoid promising an AI cloud desktop can run private models on a CPU with gaming-grade responsiveness; route high-demand inference to shared compute.

### Tier B — GPU AI Creator Workstation (premium limited-capacity product)
- Isolated VM/profile on suitable graphics GPU; CAD, Blender, 3D, image/creator AI, video, design applications.
- Track actual graphics GPU compute and VRAM; a GPU workstation user is **not** equivalent to idle office AI user.
- Virtual GPU licenses and guest OS licensing can be material. NVIDIA suggested annual vPC subscription ~$50 per concurrent user; RTX vWS ~$250 per concurrent user (subject to actual contract). https://www.nvidia.com/en-us/data-center/buy-grid/
- Windows hosted desktops have independent licensing constraints. Confirm service-provider/MS agreement rather than copy a local Windows PC to many tenants. https://learn.microsoft.com/en-us/windows-server/remote/remote-desktop-services/desktop-hosting-reference-architecture
- Higher-priced hourly reservations or dedicated monthly seats with prepaid minimum and max VRAM/CPU time; bill AI tokens separately.

### Tier C — AI Inference / Batch Compute
- Managed AI agents, model endpoints, job and token execution. Client displays can be entirely local and use API calls rather than streamed video.
- Explicit vRAM/model sizes, batch/token rate, idle retention, concurrency and data residency. Use heterogeneous GPU pool; if sharing gaming GPUs, only after host reimaging/draining and compatible VRAM/driver/licensing verified.
- Target daytime/nighttime off-peak when game commitments allow, or provision separate inference-optimized GPUs. Different high-end AI GPU models may lack NVENC and are not interchangeable with gaming GPUs.

### Workload scheduling rules
```text
Product class CPU_WORKSPACE -> CPU/RAM host pool; AI_MODEL_API separate meter
Product class GPU_WORKSTATION -> vGPU-licensed graphics pool / isolated VM
Product class GAME_SESSION -> gaming-capable isolated graphics host with streaming hardware
Product class AI_INFERENCE -> memory/model-aligned compute pool, token/job billing
Product class PLAYABLE_DEMO -> reserved interactive media slot with budget/timeout
```
Jobs can share *capacity providers* and some physical GPU hardware, but never assume same Windows desktop can host a private gamer and private enterprise AI user simultaneously. A scheduled pool switch requires drain, wipe, re-image, driver/compliance validation and warmup. Workday vs evening demand complement is a hypothesis to test by hour-of-day bookings, not a claim of available utilization.

## 4. GPU-seat hybrid unit economics and break-even warning

Illustrative 20-seat model (same prior GPU fleet/accounting costs, excluding loan interest/taxes/game royalties):
- Gaming only: 20% of all hours = 2,920 paid gaming hours × PKR350 = PKR1.022m/mo recognized revenue.
- Additional GPU AI desktops: 20 seats × 8 eligible daytime hours × 22 weekdays × **50% paid occupancy** = 1,760 sold GPU desktop hours × PKR180 = PKR316,800/mo.
- Combined occupied hours: 4,680 of 14,600 monthly seat-hours = **32.1%**.
- Combined net recognized revenue **PKR1.339m/mo**.
- More active power, AI-specific support, AI video bandwidth at illustrative 1.5Mbps, AI variable API/storage cost PKR30/h, and fixed GPU vWS/desktop license/admin placeholder PKR120k/mo are included.
- Consolidated direct contribution (after GPU depreciation): **~PKR47,460/mo** (3.5%).
- Allocating remaining launch costs over 36 months deducts PKR250k/mo ⇒ **loss ~PKR202,540/mo** after that additional amortization.
- Break-even under these assumptions at the same bookings requires AI desk net price ~PKR305/h, or different cost structure/revenue/utilization. **AI Desktop does NOT automatically rescue weak gaming economics.**
- Critical sensitivity: CPU-based AI workspaces must be modeled separately with CPU VM/AI API costs and should not be charged against premium gaming GPU hours; higher concurrent user density only where isolation and licenses permit.

## 5. Architecture amendments to SessionOS
1. `capacity_provider` includes `Sky47`, cloud GPU, CPU VM/host, partner edge with unit costs, rights, SLA and billing owner.
2. `product_class` = `GAME_SESSION | CPU_AI_WORKSPACE | GPU_CREATIVE_DESKTOP | AI_INFERENCE | PLAYABLE_DEMO`.
3. Entitlement specifies session time, AI token allowance, GPU capacity, permitted software, age/tenant/territory policy, bandwidth and storage limits.
4. Billing ledger differentiates gaming playable hours, CPU desktop user-hours, GPU desktop allocated hours, AI tokens/jobs, streaming GB, support and advertising attribution.
5. Two-way billing: JV consolidated contribution vs arm's-length intercompany fees; **do not conflate**.
6. QoE policy choose codec + FPS/bitrate only after client capability probe and device/network testing.
7. GPU seat/calendar reservations, occupancy heat map by hour and resource class, preemptible off-peak workload with eviction policy, advance peak commitments.
8. Encrypted workspace profiles, per-tenant storage, identity, prompt/data policies and enterprise DLP; game recording and AI usage separate consent.
9. FinOps policy: all costs and revenues allocated to a product class, tenant, session/job and partner, including idle capacity and amortization.
10. Provider contracts and game/AI licenses authorize each usage mode; IP rights and software license distribution documented.

## 6. Next low-CAPEX proof
- In 2 weeks, gather real Sky47 BOM/GPU inventory, Jazz incremental per-GB/network pricing, vGPU/Windows licenses and two paid buyer proposals: premium gaming plus AI Workspace enterprise/schools/creators.
- Physical Android hardware decode and stream gate remains mandatory; implement H264/HEVC/AV1 **A/B only**, not new codec.
- Build **CPU AI Workspace prototype** (identity, remote desktop, API-based AI assistant, token metering) on cheap existing CPU infrastructure independently from game stream.
- Build small **GPU Creative Desktop proof** using a single suitable verified NVENC graphics GPU and valid virtual workstation/OS licenses; one approved creative app and explicit tenant separation.
- Measure 7/30-day seat bookings by hour, priced paid occupancy, average bandwidth, incremental AI API token cost, security and support.
- Start 5–10 graphics seats or leased capacity, not 100; seek commercial commitment and publish a 3-party pilot P&L before new permanent fleet CAPEX.
- Do not promise guaranteed QoD, cheap consumer GB, multi-session GPU sharing or commercial publisher rights until separately proven.

## 7. Approval requests
- Do partners want a ring-fenced SPV with equity, or a commercial alliance with conversion to JV after paid demand?
- Agree to fair market valuation of newly developed IP; distinguish historical IP license, partner future distribution and paid equity.
- GPU BOM and 3-year maintenance/finance quote; verify AV1 encode/desktop virtual licensing.
- Jazz wholesale/on-net data cost, charging integration and bundled allowance approvals.
- Approve Tier A AI Workspace as a low-CAPEX product and Tier B GPU creative desktop as controlled premium test.
- Set full cash budget including contingency/working capital, legal, marketing, taxes, financing and royalty assumptions.
- Design buyer validation and privacy/security rules, secure paid pilots before scale.

## 8. Workbook link
Companion editable XLSX created in the conversation: `cloud_gaming_ai_desktop_joint_venture_economics_2026_10_27.xlsx` (not automatically in GitHub). Extends previous 3-party workbook with `JV Funding`, `AV1 Bandwidth`, `AI Desktop Mix` and supporting sources, all with live spreadsheet formulas.

**Document purpose:** technical/commercial handover and review. All costs/returns are scenarios, not offered deals or executed investments.
