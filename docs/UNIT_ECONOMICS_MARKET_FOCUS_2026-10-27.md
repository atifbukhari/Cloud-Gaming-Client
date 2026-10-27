# Cloud Gaming Unit Economics & Lowest-Capex Market Focus
**Date:** 2026-10-27  
**Status:** Economics hypothesis to validate with paid contracts and provider quotes; **NOT** observed sales/SLAs  
**Companion:** [Master platform plan](PLATFORM_MASTER_PLAN_2026-10-26.md) · [Delivery WBS](PLATFORM_DELIVERY_WBS_2026-10-26.md) · [Program handover](PLATFORM_HANDOVER_RUNBOOK_2026-10-26.md)

## 1. Decision: which segment should dictate the product design?

**Primary segment:** GPU-owner / GPU-funding operators, regional data centers, ISPs, gaming venue chains, and telecom partners who want a **white-label commercial gaming service** using GPU capacity they own or pay for directly. We sell the software layer—catalogue/rights policy, controlled session management, native client, phone companion controller, white-label entitlements, analytics, support automation and optimizations—through setup + fixed retainer + usage tiers/minimum guarantee.

**Second, fast-to-revenue segment:** **paid instant playable demos and remote playtests** for studios, campaigns, gaming publishers and game-marketing partners using the studio's authorized DRM-free demonstration build and cloud GPU rented only for booked play windows. Can use GameLift Streams / hosted WebRTC rather than completing every Moonlight platform feature first. Short duration caps reduce uncontrolled GPU liability. Sponsor/client pays for distribution/media separately.

**Avoid as an initial venture-funded product:** ourselves selling unlimited consumer AAA gaming subscriptions while owning GPU runtime, external egress, game licensing and customer acquisition expense. That has the least predictable direct margin and biggest working-capital exposure.

**Important distinction:** Software SaaS maximizes achievable *recurring direct gross margin* but has longer sales cycles and integration labor. Productized studio/demo work could bring paid validation faster with near-zero equipment CAPEX but margins depend heavily on setup effort and sponsor acquisition. Neither target pricing nor sales demand is yet proven.

## 2. Market segment comparison (assessment, not researched average industry margins)

| Segment | Customer pays GPU/egress? | Revenue design | Indicative direct-margin potential | Cash CAPEX | Main risk | Priority |
|---|---|---|---|---|---|---|
| White-label for partner-funded GPU operator | **Partner** | Setup + monthly minimum + per played hour/seat | High if service is standardized and minimum fees accepted | Minimal equipment CAPEX | Enterprise sales cycle, customization and capacity/rights ownership | **#1** |
| Paid instant interactive demos & remote playtests | We rent for booked capped windows, bill buyer | Paid campaign/setup + quota/usage overage | High after templating, moderate in first custom projects | Small variable rental, no GPU purchase | Difficult demos, sales/CAC, existing cloud competitors | **#2 fast wedge** |
| Developer QA/playtest private streaming with partner-owned GPUs | **Studio** or reimbursed | Per project + managed test slots | Medium-high | Low | IP security, studios may self-host | **#3** |
| Cybercafe / GPU lab management SaaS | Venue | Venue seat licensing + monitoring | Medium | Low | Low willingness-to-pay, local support | Niche channel |
| Telco consumer offering with revenue share and vendor-funded GPU | **Partner** | Guarantee + management fee/revshare | High for software provider if structured well | Low | Partner has no GPU budget, rights and integration | Scale extension of #1 |
| Direct consumer GPU-owned subscription | **Us** | Subscription/paytime | Low/negative under weak utilization | High working-capital + CAC, possibly no hardware purchase | Egress, utilization, rights, churn | **Defer** |

## 3. External cost anchors, October 2026

These are **reference prices only**, not a quote, reservation, or measured compatibility result:
- AWS EC2 `g6f.xlarge` Linux us-east-1 ~$0.2375/allocated hour from a 2026-10-22 independent pricing snapshot; 1/8 NVIDIA L4, 3 GB VRAM. Source: https://ec2calc.cloud/instances/g6f.xlarge ; AWS G6f specs: https://aws.amazon.com/ec2/instance-types/accelerated-computing/
- AWS EC2 `g6.xlarge` Linux US-East approximate ~$0.80/allocated hour (rounded independently listed rate); dedicated GPU, not the same workload as g6f. Source: https://www.economize.cloud/resources/aws/pricing/ec2/family/g6/
- AWS standard EC2 public Internet transfer US first tier ~$0.09/GB after the 100 GB shared monthly free allowance, with volume tiers and regional variations: https://aws.amazon.com/ec2/pricing/on-demand/
- Sustained 1 Mbps streaming for an hour ~0.45 decimal GB (arithmetic); 8 Mbps → 3.6 GB/hour → ~$0.324/hour egress at $0.09/GB; 15 Mbps → 6.75 GB/hour → ~$0.6075/hour. This excludes codec variation, FEC, relay, TCP control and taxes.
- AWS GameLift Streams offers `gen6n_small` at **$0.16 per allocated capacity-hour in us-east-2** as of 2025-12 release announcement. This is **not** an all-title / all-region quote and is a separately billed product; capacity is billed while allocated and idle. Sources: https://aws.amazon.com/about-aws/whats-new/2025/12/aws-gameliftstream-gen6-enhanced-autoscaling/ and https://aws.amazon.com/gamelift/streams/pricing/
- G6f uses a specific NVIDIA GRID driver rather than the EC2 gaming driver: https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/install-nvidia-driver.html . Test game compatibility; do NOT price an AAA commercial product solely on g6f.

## 4. Modeled segment economics (USD; illustrative contract terms / not verified sales)

### A. Partner-funded white-label platform SaaS (per partner, per month)
- **Customer commercial model:** $2,500 monthly platform minimum + $0.25 per played hour × 4,000 eligible partner-paid hours = **$3,500 monthly revenue**.
- **Who bears GPU/egr/rights:** partner owns/leasing provider, or partner pays cloud provider directly. Our technical costs exclude their usage costs.
- Our **direct monthly COGS:** $180 API/monitoring + 9 direct-support hours × $40 = $360 + $0.03 telemetry/use cost × 4,000 = $120 → **$660**.
- **Gross contribution:** $2,840 per month = **81.1% direct margin**. Excludes partner acquisition, product engineering/R&D, wider overhead and tax.
- Hypothetical one-time implementation fee **$7,500** with 100 engineer hours × $40 + $800 other direct cost => $2,700 setup contribution. Year-1 (12 steady monthly invoices + setup): $49,500 revenue - $12,720 direct COGS = **74.3%** direct margin.
- Pricing is an **offer hypothesis**; do not list it in sales collateral as an accepted operator contract.

**Risk:** A partner may ask for unpaid integrations, no fixed retainer or us to bear GPU/egress. Explicitly refuse unlimited-cost commitments, set a billable customization SOW, minimum guarantee and client-funded capacity before accepting margin claims.

### B. Productized playable studio demo campaign (per campaign)
- Contract scenario: **$5,000 sponsor fee**, 1,200 completed demos × 10 minutes = **200 delivered playable hours**.
- GPU: `g6f.xlarge` $0.2375 allocated hour at 65% utilized capacity = 307.7 allocated hours × $0.2375 = **$73.08**.
- Egress: 200 × (8 Mbps × 0.45 GB/Mbps-h) × $0.09/GB = **$64.80** (EC2 framework assumption; GameLift Streams separate quote if used).
- Onboarding: 25 hours × $40 = $1,000; direct campaign ops: 8 hours × $40 = $320; other direct media/analytics: $160.
- **Total direct COGS $1,617.88**, contribution **$3,382.12** = **67.6%**. Modeled cloud GPU+egress cash exposure only **$137.88**, but person-hours dominate.
- Excludes paid advertising/media, customer acquisition, taxes, custom game-build development and corporate R&D. Client/publisher supplies and authorizes streaming of their game build. **Do not promise 1,200 qualified leads or sales**.
- At 100 hours of game-onboarding labor instead of 25, profitability drops substantially; require asset-readiness audit and scoped SOW before fixed fee.
- Add an approved campaign quota, daily capacity cap, maximum minute/stream, per-day spend limit, overage pricing and explicit data/privacy/marketing attribution.

### C. Consumer-owned GPU subscriptions (per 1,000 subscribers per month)
- Pricing scenario: $12/subscriber/month, 12 play hours each, all subscribers active → **$12,000 monthly revenue**, **12,000 hours delivered**.
- Dedicated GPU baseline: $0.80 per allocated hour at 55% utilization → **$17,454.55 compute**.
- Delivery egress: 12,000 × 8 Mbps × 0.45 × $0.09 = **$3,888**.
- Other direct cost $0.15/played hour = $1,800; fixed platform cost $500; payment charges 3% revenue = $360; **no game royalties modeled**.
- **Total direct COGS ~$24,002.55** → **loss of ~$12,002.55/month**, direct margin ~**-100.0%**.
- Approximate break-even subscription at fixed 12 played hours = **$24.37/user/month**, *before game royalties, consumer acquisition, marketing and R&D*.
- A hypothetical g6f-compatible light game would improve this if fractional GPU operation were technically/legal validated; it is not a valid assumption for a full AAA catalogue.
- Avoid an uncapped $10–15 monthly consumer plan funded by our own GPU/egress before measured player usage, game licensing and committed edge network economics.

## 5. Package and pricing design (not validated selling prices)

### Product A — Operator Cloud GamingOS
- **Required:** tenant and rights configuration, player auth/entitlement, stream/session API, host capacity adapter, session leases, telemetry/support and usage ledger.
- **Offer structure:** one-time integration/onboarding SOW **$5,000–$15,000** (estimate/offer to test); monthly minimum **$2,000–$5,000**; per-playhour or per seat billing based on usage/commitment; separately priced network/operator billing integration and SLA tiers.
- **Pricing guardrails:** partner pays GPU + egress + game license directly or prefunds cost with a contracted markup. Enforce usage caps and billable paid customization; no unlimited users at fixed low fee.
- **Go-to-market:** operator/data center/ISP with demonstrated GPU procurement and a legal game-content partner. A telco that wants us to buy GPUs is a *different* economic segment and should be priced separately.
- **Competitive advantage:** local/regional network/QoE awareness, cost and session automation, partner CMS, accessible Android TV/phone experience, QR gamepad and gaming ecosystem connectors—not just Moonlight host.

### Product B — Instant Play CampaignOS
- **Required:** publisher/studio-supplied ready-to-play build, campaign config, access tokens/guest flow, short WebRTC plays, conversion/CTA analytics, capacity and budget caps, client branding/UTM/pixel where compliant.
- **Offer structure:** fixed creative+technical onboarding fee plus a prepaid play quota, optional overage. For initial validation, test packages in roughly **$3k–$10k** range *subject to actual client feedback*. Do not include ad buying by default.
- **Go-to-market:** publishers, marketing studios, influencer/event campaigns and existing telco gaming partners who can provide distribution. Aim for recognizable, compelling game content with explicit authorized streaming rights.
- **Caution/competition:** AWS GameLift Streams already provides playable stream URLs and publisher instant-play demos, so our moat cannot be “a game opens in a browser.” Differentiators must be telco distribution, conversion analytics, operator activation and productized integration. References: https://aws.amazon.com/about-aws/whats-new/2026/08/amazon-gamelift-streams/ and https://aws.amazon.com/blogs/gametech/best-practices-for-instant-play-demos-powered-by-amazon-gamelift-streams/

### Product C — Private Remote Playtesting (optional)
- Studio funds capacity or reimburses GPU use; we monetize authenticated build distribution, invite-only play slots, collected telemetry, survey/video feedback with explicit consent and reproducibility.
- It is closer to enterprise software delivery and lower game cloud distribution risk for the studio's own authorized builds, but high IP security requirements.
- Can share the CampaignOS session engine while avoiding marketing/analytics modules.

## 6. Design the MVP around this economics (not a consumer AAA mega-platform)

### Build ONLY common capabilities first
```text
Partner / Studio / Operator portal
  ├── Tenant + branded campaign/catalog config
  ├── Game rights/build and allowed territory
  ├── Capacity provider connection + budget/TTL
  ├── Guest/demo or logged-in subscription entitlement
  ├── Start / Play / End API + short-lived grants
  ├── Per-session played minutes and attributable usage ledger
  ├── First-frame/QoE/CTA conversion metrics
  ├── Safe stop, cleanup, anti-abuse, support logs
  └── Reporting, exports, invoices
                    │
                    ▼
     SessionOS (minimal state, leases, cap, throttle)
                    │
       ┌────────────┴────────────┐
       ▼                         ▼
Provider-funded Windows/       Managed browser stream provider
Linux Sunshine/Vibeshine       GameLift Streams / WebRTC host
and our Android native client  and simple browser/guest link
```

### Do not build yet
- Our own new codec or proprietary virtual display driver.
- Unlimited consumer subscriptions and AAA catalogue purchasing.
- Full multi-client Windows sharing or console farms.
- Blockchain wallet/staking as a gameplay requirement.
- Multi-region Kubernetes operating platform.
- Complex AI agents before measured quality/cost data.
- Full esports/creator video production stack unless a paying pilot needs it.

### Product sequencing
1. **2 weeks business validation:** request three types of buyer signals: (a) infrastructure-funded operator with identified GPU+rights owner; (b) studio/agency with authorized playable build and funded campaign; (c) game studio with remote QA demand. Record actual budget, deployment constraints and WTP.
2. **2–4 weeks technical pilot:** use existing client and low-code Control Plane/managed provider to demonstrate capped session start→play→stop→report, 1 demo or authorized game; no new streaming codec.
3. **First revenue gate:** get 1 signed paid campaign or an operator paid integration/retainer with cost cap. Do not assume unpaid showcase is market proof.
4. **Repeatability:** productize the second deployment so game/tenant onboarding is measured under 15–25 hours; otherwise margins erode.
5. **Scale recurring B2B:** deliver Operator GamingOS module using tested session/capacity abstraction; expand to telco network signals only when partner can expose them.
6. **Consumer gaming only as a partner-funded downstream channel** with usage limits, game rights, approved margin and session/egress metering.

## 7. Decision/validation metrics

| Metric | Decision threshold / target proposed | Notes |
|---|---|---|
| Operator commercial validation | ≥2 budget-owning buyers interested, ≥1 paid pilot or signed minimum guarantee | Not yet achieved |
| Demo commercial validation | ≥1 paid campaign, repeat buyer signal and lead-to-play measurements | Not yet achieved |
| Operator software direct margin | Target >70% after direct ops/support and per-partner hosting | Not equivalent to company EBIT |
| Productized campaign direct margin | Target >50% including allocated GPU/egress + implementation labor | Quote must include overage |
| Campaign integration time | Target ≤25 hours per ordinary compatible title after tooling matures | First title may take longer |
| GPU spend control | Every session/campaign TTL, quota, billing trace, kill-switch | Mandatory |
| Stream quality | Hardware mobile baseline with first-frame and p95 metrics | Local emulator ≠ customer test |
| License/rights gate | 100% permitted user/game/territory combinations | Legal approvals |
| Consumer viability | Contribution positive under observed utilization+subscriber hours before product launch | Avoid subsidized unlimited risk |
| R&D spend | Approve short proof milestones; do not build multi-year platform pre-contract | Founder-led stage gates |

## 8. Sales and go/no-go questions
1. Can we identify **two operators with real GPU budgets**? Many telcos have network distribution but do not own enough gaming GPUs; that disqualifies the software-only economic assumption.
2. Does the client value *white-label gaming platform operations* enough to pay a monthly minimum in addition to its own GPU costs?
3. Can we get a studio/publisher to supply an authorized cloud-play demo build and pay for a campaign **separately from media buying**?
4. Are we willing to run a paid trial through a managed host (GameLift Streams) to avoid making the proprietary Moonlight path a blocker?
5. Which costs are passed through, reimbursed, marked up, or fixed under each contract? Require explicit termination and refunds.
6. Is the territory network cost lower via operator peering? If yes, request a real tariff/commitment rather than assuming free bandwidth.
7. How much engineer onboarding labor does the second tenant/game actually require? Revenue without repeatability does not equal a high-margin platform.

## 9. Model, formulas and handover

Editable 2026-10-27 economics workbook (distributed with the review, not checked into source until approved):
`cloud_gaming_unit_economics_2026_10_27.xlsx`.

Tabs:
- Dashboard (3 scenarios and qualitative priority).
- Operator SaaS (monthly + year-1 setup costs).
- Instant Demos (per-campaign played hours, capacity, egress, labor).
- Consumer Cloud (subscription break-even and losses).
- Sensitivity (GPU utilization/rate; demo onboarding effort/price).
- Sources (dated source URLs and hypothesis vs external facts).

**How to extend:** replace assumptions with written prospect budget/pricing, chosen cloud region, game/OS rights and actual input-to-photon + GPU/egress billing logs. Run pessimistic/base/optimistic cases and include CAC, founder pay, insurance, legal, reserves, taxes and central engineering amortization before an investment decision.

**Current recommendation:** pursue Partner-funded Cloud GamingOS recurring SaaS, prove low-capex productized demos for quick paid validation, and stop treating GPU-owned retail consumer cloud gaming as the anchor model.
