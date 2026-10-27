# Pakistan 3-Partner Cloud Gaming Economics: Platform + Sky47 + Jazz

> **2026-10-27 economic/technology refinement:** [Joint venture/co-investment + AV1 savings + Cloud AI Desktop architecture](SHARED_FUNDING_AV1_AI_DESKTOP_STRATEGY_2026-10-27.md). Prior gaming-only financial models are baseline scenarios, **not** the target product boundary or agreed partner split. GPU desktop, CPU workspace and AI inference have separate metering and licensing.

**Date:** 2026-10-27  
**Status:** Hypothesis model for negotiating a pilot. No quoted prices, actual Sky47 GPU inventory, signed Jazz participation, game licenses, QoS guarantees or confirmed partner agreements are assumed.  
**Companion strategy:** [Unit Economics & Market Focus](UNIT_ECONOMICS_MARKET_FOCUS_2026-10-27.md) and [Platform Master](PLATFORM_MASTER_PLAN_2026-10-26.md).

## 1. Business structure and risk ownership

**Platform company (our software/IP):** Moonlight-derived clients, SessionOS, player and controller experience, host orchestration, catalog/entitlements, safe billing events, quality telemetry and white-label operator experience. Recover incremental integration cost through one-time fees plus guaranteed recurring software fee.

**Sky47:** Gaming-suitable GPU/hardware procurement and operations, colocation, power, cooling, upstream interconnect, hardware maintenance, isolation and monitoring. Do not assume existing AI GPUs have NVENC or can run Windows games: NVIDIA H100/A100 do not have NVENC, unlike L4/L40/L40S/graphics cards. Gaming GPU procurement and commercial graphics/OS license must be verified.

**Jazz:** Subscriber identity/distribution, operator billing/JazzCash integrations as contracted, data bundle design, 4G/5G last-mile network, customer marketing/care and internal delivery cost. Carrier-neutral colocation does **not** make mobile access traffic free. Wholesale/on-net peering and network quality must be measured and quoted.

**Contract principle:** for a partner-owned hardware model, Jazz (or an operator-backed purchaser) guarantees minimum capacity revenue once the pilot graduates; Sky47 should not commit new fleet CAPEX solely in anticipation of retail take-up. Platform minimum fee protects engineering. Initial 10–20 seat demo phase should reuse approved existing equipment or be explicitly financed/sponsored.

## 2. Dated external reference anchors

- Sky47 official: https://sky47.com.pk/ — describes cloud, colocation and GPU-ready facilities, but does not publicly quote gaming seat GPUs/prices.
- Sky47 public operational launch: https://www.dawn.com/news/1968525 (2026-01-23) — reported cloud/AI infrastructure launch, not availability of gaming GPU fleet.
- Jazz/VEON 2Q26: https://www.sec.gov/Archives/edgar/data/1468091/000146809126000032/veon2q26earningsrelease.htm — 75.4m mobile, 58.1m 4G, ARPU PKR 328.6 in that quarter and 8.2GB data usage/user reported.
- NVENC codec support: https://developer.nvidia.com/video-codec-sdk and https://docs.dynamo.nvidia.com/dynamo/zh-CN/v1.5.1/multimodal/video-decode-gpu-requirements — confirm specific GPU.
- Approx USD/PKR reference 277.2 on 2026-10-27: https://hamariweb.com/finance/forex/inter_bank_rates.aspx — update before quote.
- Power cost is modeled, not Sky47's tariff. See industrial 2026 tariff context: https://pakera.pk/industrial-electricity-tariff-b1-b2-b3-b4-pakistan-2026/

## 3. Base-case editable model assumptions — **not agreed commercial rates**

| Driver | Assumption |
|---|---:|
| Independently playable GPU seats | 100 |
| Hours/calendar month | 730 |
| Sold/playtime utilization | 40% = 292h per seat/mo = about 9.6h per seat/day |
| **Net-of-indirect-tax** collected retail revenue | PKR 350 per played hour |
| Video payload bitrate | 8 Mbps = 3.6 decimal GB per played hour |
| Sky47 fully installed graphics-capable gaming GPU seat CAPEX | PKR 850,000 per seat |
| Sky47 asset straight-line life | 48 months |
| Sky47 contracted minimum seat lease | PKR 55,000/seat/month |
| Server power at play / idle | 0.45kW / 0.12kW |
| Sky47 facility PUE | 1.4 (illustrative) |
| Sky47 effective electricity price | PKR 35/kWh (illustrative) |
| Sky47 direct support + spares + upstream | PKR 8,500/seat/month |
| Platform fee | PKR 300,000/mo minimum + PKR 5,000/seat/mo |
| Platform direct ongoing cost | PKR 150,000/month + PKR 2/played hour |
| Jazz incremental data cost | **PKR 6/GB hypothetical internal economic cost; not a tariff** |
| Jazz payment cost | 2.5% of total recognized retail revenue |
| Jazz marketing cost | 5% of total recognized retail revenue |
| Jazz support | PKR 50,000 fixed + PKR 1,500 per seat/month |
| Game publisher cloud rights royalties | **PKR 0 baseline placeholder ONLY; commercial rights and fees unresolved** |
| Financing, income tax, indirect customer tax and R&D amortization | Excluded from direct-margin calculation |

**Important:** If PKR 350 is tax-inclusive consumer spend rather than **recognized net revenue**, replace the unit revenue with the net realized amount after applicable taxes/refunds/levies. Do not count network charges twice if billing bundles separately.

## 4. Base case monthly waterfall (100 seats @ 40% paid utilization)

- Paid gaming: **29,200 played hours/month**; ~**7,300 equivalent 4h/month paying customers**.
- Consumer recognized revenue: **PKR 10,220,000/month** (approx PKR 350/hour).
- **Sky47 revenue:** PKR **5,500,000** lease. Cash direct costs power/cooling ~**901,404**, support/upstream/spares **850,000**, straight-line depreciation **1,770,833**. After depreciation contribution **1,977,763**, **36.0% of Sky47 lease revenue**. Cash operating surplus before financing, tax, central overhead: **3,748,596/month**, implying **22.7 months illustrative simple CAPEX payback** on PKR 85m hardware (not IRR).
- **Platform revenue:** PKR **800,000**. Direct service/telemetry OPEX PKR **208,400**. Direct contribution PKR **591,600**, **74.0% of its revenue**, *before product R&D amortization / corporate/sales overhead*.
- **Jazz recognized residual:** PKR **3,920,000**. Last-mile data cost ~**630,720**, billing **255,500**, marketing **511,000**, support **200,000**. Contribution **2,322,780** = **59.3% of Jazz retained revenue**. Jazz bears fixed capacity downside under the proposed settlement.
- **Overall three-party modeled contribution** (including Sky47 depreciation) **PKR 4,892,143** = **47.9% of retail recognized revenue**.
- Monthly consumer video delivery: **105,120 decimal GB = ~105 TB**. Simultaneous 100-seat video payload ~**800 Mbps** excluding protocol overhead. This requires properly engineered regional networking and carrier routing.
- At 4h/user/month, gamer net spend **PKR 1,400/month** and data **14.4GB**; materially above reported average Jazz mobile ARPU and monthly average data usage—premium market, not low-cost mass consumer SKU.

## 5. Deployment CAPEX by stakeholder (hypothetical budgets, not quotes)

| CAPEX | 20-seat pilot | 100-seat rollout |
|---|---:|---:|
| Sky47 installed GPU fleet | PKR 17m | PKR 85m |
| Platform incremental implementation | PKR 5m | PKR 5m |
| Jazz app/charging/network integration | PKR 2m | PKR 2m |
| Shared interconnect/testing | PKR 2m | PKR 2m |
| **Total illustrative incremental CAPEX** | **PKR 26m** (~USD 93.8k) | **PKR 94m** (~USD 339.1k) |

The procurement cost is uncertain, potentially ranging **PKR 0.6m to 1.3m+ per delivered playable graphics seat**, depending on GPU, host, licensing, importer duties, warranty, density, game performance and encoder requirements. At 100 seats this alone ranges **PKR 60–130m** before setup expenses. Additional branding, content rights, marketing cash, working capital, true operator telecom upgrades, VAT/FED etc are not covered by the CAPEX estimate.

20-seat pilot @same assumptions: 5,840 played hours, PKR 2.044m revenue; lease to Sky47 PKR 1.1m; platform fee PKR 400k; Jazz residual PKR 544k; Jazz incremental contribution ~PKR 184,556 and Jazz break-even utilization **35.8%** (because operator/support and software minimums do not scale down proportionally). This is a risky pilot with a new fleet unless a sponsor covers minimum guarantees.

## 6. Utilization/pricing risk from Jazz perspective (100 seats)

With the modeled Sky47 and software monthly minimums, Jazz's own network+marketing/payment costs and **no game royalties**, the required **paid utilization** for Jazz to reach zero direct contribution:

| Net retail PKR/played hour | Jazz break-even paid utilization | Equivalent paid hours/day/seat |
|---|---:|---:|
| 150 | 76.0% | 18.2 |
| 200 | 54.5% | 13.1 |
| 250 | 42.5% | 10.2 |
| 300 | 34.8% | 8.4 |
| 350 | 29.5% | 7.1 |
| 450 | 22.6% | 5.4 |

At PKR 350, Jazz's modeled monthly direct contribution is **negative PKR 2.09m at 20%** utilization, about breakeven at 30%, **positive PKR 2.32m at 40%**.

**Structural risk:** if demand is concentrated into four evening hours a day, the maximum utilization without other buyer/dayparts is about **16.7%** regardless of subscriber count. Therefore 30–40% full-month utilization requires business-hours, educational/enterprise/venue demos, multi-time-zone buyers, weekends outside narrow evening peaks, or legal off-peak GPU workload substitution. A 20-seat pilot should measure **hour-of-day paid occupancy**, not just registered subscriber interest.

**Publisher content royalty sensitivity:** at PKR 50/played hour royalty (hypothetical), 29,200 hours adds PKR 1.46m/month, lowering Jazz's base-case contribution to PKR 0.863m if Jazz bears the charge; at PKR 100/hour it becomes negative. Negotiate rights and add true costs before announcing consumer SKUs.

## 7. Preferred JV terms to protect all three

1. **Joint steering committee** and clear IP ownership: our platform retains software IP and operator white-label distribution rights; Sky47 hardware IP/ownership; Jazz customer/billing/brand/network ownership.
2. **Pilot**: 10–20 seats; prefer existing compatible graphics GPUs or leased/test assets, no irreversible new GPU fleet. 60–90 day funded test with bounded usage, license/right and QoE evidence.
3. **Post-pilot take-or-pay capacity**: Jazz/enterprise buyer commits a 12–36 month minimum to Sky47 for dedicated seats, or find alternate contracted demand before Sky47 purchases hardware. A new GPU purchase without capacity guarantee breaks infra ROI.
4. **Platform minimum + seat fee** and separately billable integration/support enhancements; published client binaries must respect open-source licenses.
5. **Usage reconciliation waterfall**: only *recognized net-of-tax retail revenue*; Sky capacity reserved lease, platform invoice, Jazz distribution share/residual. Monthly paid hours independently measured and auditable by all parties.
6. **Risk boundaries**: service uptime and hardware failure Sky47/host SLO; software session/telemetry platform SLO; last-mile/charging/marketing Jazz; blame-free incident review, clear refund/credit policy.
7. **Power and FX escalators**, hardware replacement reserve, carrier backbone terms, minimum game-ready seats, peak concurrency service guarantee and exit/asset repurposing terms.
8. **Game rights** signed before paid consumer launch; title/OS/cloud/anti-cheat audits per game; publisher royalties assigned transparently to agreed party.
9. **QoS/data bundle** must include adequate quota and cost settlement; never advertise free traffic or guaranteed low-latency without carrier contract and measured capacity.
10. **Off-peak B2B scheduling** should be an explicit second product line; do not assume GPUs can concurrently earn AI and game revenue or that AI H100 hardware runs an NVENC cloud stream.

## 8. Architectural focus for economic validation

Before complex Vulkan/PyroWave/AI work build:
- 20-seat quota/lease calendar and hour-of-day occupancy dashboard;
- prepaid 1h / 4h / 10h packs, refundable trial logic, a net revenue ledger and wallet-independent Jazz entitlements;
- signed ready/launch/first-frame/end metering to ensure sessions are actually playable and the right party is paid;
- host APIs for Sky47 GPU fleet uptime, power, encoder, session readiness and cleanup;
- on-net traffic measurement, bitrate/codec tiers and last-mile cost telemetry from Jazz (requires permission);
- publisher-game rights registry and approved demo library;
- SLA dashboards by partner: platform software fault vs host failure vs carrier issue, plus a unified player support path.

**Commercial conclusion:** This 3-party JV is attractive mainly as a **premium / B2B-guaranteed-capacity product**, not a mass-market unlimited cheap cloud gaming bundle. Run a small sponsored/leased pilot, prove customers pay PKR300–450 net per hour or build a credible cheaper seat/low-royalty supply chain, then scale with an operator-backed minimum guarantee. Until then, target partner-funded SaaS and short paid demos as the cash-efficient wedge.

## 9. Workbook handoff
An editable companion `sky47_jazz_cloud_gaming_tripartite_economics_2026_10_27.xlsx` has been created as a chat attachment; it is **not** checked in to this repo. Sheets: Inputs, Economics, Sensitivity, Pilot CAPEX, Sources. Edit colored assumption cells and review calculated margins/thresholds. Future engineer should obtain the companion from the conversation/project files or recreate the model directly from formulas documented here.

### Model formulas
```text
played_hours = game_seats * 730 * paid_utilization
recognized_revenue = played_hours * net_price_per_hour
Sky_lease = game_seats * monthly_lease_per_seat
Platform_fee = monthly_platform_minimum + game_seats * per_seat_fee
Jazz_residual = recognized_revenue - Sky_lease - Platform_fee
Sky_power = game_seats * 730 * (u*active_kw+(1-u)*idle_kw) * PUE * power_tariff
Sky_profit = Sky_lease - Sky_power - Sky_ops - Sky_CAPEX/useful_life_months
Platform_profit = Platform_fee - fixed_direct_ops - played_hours*telemetry_unit_cost
Jazz_profit = Jazz_residual - played_hours*(Mbps*0.45*network_cost_per_GB+royalty_per_hour) - recognized_revenue*(billing_pct+marketing_pct) - Jazz_support_cost
Jazz_utilization_break_even = (Sky_lease+Platform_fee+Jazz_support_cost) /
  (game_seats*730*(net_price_per_hour*(1-billing_pct-marketing_pct) - Mbps*0.45*network_cost_per_GB - game_royalty_per_hour))
```

**All figures provisional.** Ask each party for a written BOM/hosting bill, per-GB network allocation, signed wholesale commercial terms, publisher game list, and pilot demand plan before using this in a financial pitch as an expectation.
