import {describe, expect, it} from "vitest";
import TicketPricingEngine from "../../src/pricing/ticket-pricing-engine";

describe("TicketPricingEngine", () => {
    const engine = new TicketPricingEngine();

    describe("concert", () => {

        it("applies weekend/holiday upcharge and basic discount rules", () => {
            const total = engine.calculateTotal(
                "concert",
                2,
                true,
                false,
                "regular",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(124);
        });

        it("applies senior discount on concerts", () => {
            const total = engine.calculateTotal(
                "concert",
                2,
                false,
                false,
                "senior",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(99);
        });

        it("applies student discount on concerts", () => {
            const total = engine.calculateTotal(
                "concert",
                2,
                false,
                false,
                "student",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(94);
        });

        it("applies WELCOME coupon on concerts", () => {
            const total = engine.calculateTotal(
                "concert",
                2,
                false,
                false,
                "regular",
                false,
                "WELCOME",
                "web"
            );

            expect(total).toBeCloseTo(94);
        });

        it("does not apply welcome coupon for more than 2 tickets", () => {
            const total = engine.calculateTotal(
                "concert",
                3,
                false,
                false,
                "regular",
                false,
                "WELCOME",
                "web"
            );

            expect(total).toBeCloseTo(156);
        });
    });

    describe("movie", () => {

        it("does not accept GROUP10 coupons for movies", () => {
            const total = engine.calculateTotal(
                "movie",
                10,
                true,
                false,
                "regular",
                false,
                "GROUP10",
                "web"
            );

            expect(total).toBeCloseTo(115);
        });

        it("applies weekday discount", () => {
            const total = engine.calculateTotal(
                "movie",
                3,
                false,
                false,
                "regular",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(28.5);
        });

        it("does not apply weekday discount on weekend", () => {
            const total = engine.calculateTotal(
                "movie",
                3,
                true,
                false,
                "regular",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(34.5);
        });

        it("applies student discount on movies", () => {
            const total = engine.calculateTotal(
                "movie",
                3,
                false,
                false,
                "student",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(23.7);
        });

        it("applies senior discount on movies", () => {
            const total = engine.calculateTotal(
                "movie",
                3,
                false,
                false,
                "senior",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(24.9);
        });

        it("applies VIP discount on movies", () => {
            const total = engine.calculateTotal(
                "movie",
                3,
                false,
                false,
                "regular",
                true,
                "",
                "web"
            );

            expect(total).toBeCloseTo(27.3);
        });

        it("applies COUPLE coupon (only if 2 tickets)", () => {
            const total = engine.calculateTotal(
                "movie",
                2,
                false,
                false,
                "regular",
                false,
                "COUPLE",
                "web"
            );

            expect(total).toBeCloseTo(17.4);

            const total3 = engine.calculateTotal(
                "movie",
                3,
                false,
                false,
                "regular",
                false,
                "COUPLE",
                "web"
            );

            expect(total3).toBeCloseTo(28.5);
        });
    });

    describe("theater", () => {

        it("applies weekend upcharge", () => {
            const total = engine.calculateTotal(
                "theater",
                2,
                true,
                false,
                "regular",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(138.6);
        });

        it("applies holiday upcharge", () => {
            const total = engine.calculateTotal(
                "theater",
                2,
                false,
                true,
                "regular",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(132.6);
        });

        it("applies both weekend and holiday upcharges", () => {

            const total = engine.calculateTotal(
                "theater",
                2,
                true,
                true,
                "regular",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(152.4);
        });

        it("applies student discount on theater", () => {
            const total = engine.calculateTotal(
                "theater",
                2,
                false,
                false,
                "student",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(108.6);
        });

        it("applies senior discount on theater", () => {
            const total = engine.calculateTotal(
                "theater",
                2,
                false,
                false,
                "senior",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(114.6);
        });

        it("applies WELCOME coupon (only for 1 or two tickets)", () => {
            const total = engine.calculateTotal(
                "theater",
                2,
                false,
                false,
                "regular",
                false,
                "GROUP10",
                "web"
            );

            expect(total).toBeCloseTo(120.6);

            const total9 = engine.calculateTotal(
                "theater",
                9,
                false,
                false,
                "regular",
                false,
                "GROUP10",
                "web"
            );

            expect(total9).toBeCloseTo(542.7);
        });

        it("applies promo channel fee (phone/walk‑in)", () => {
            const total = engine.calculateTotal(
                "theater",
                2,
                false,
                false,
                "regular",
                false,
                "",
                "phone"
            );

            expect(total).toBeCloseTo(121);

            const totalWeb = engine.calculateTotal(
                "theater",
                2,
                false,
                false,
                "regular",
                false,
                "",
                "web"
            );

            expect(totalWeb).toBeCloseTo(120.6);
        });
    });

    describe("sports", () => {

        it("applies weekend upcharge", () => {
            const total = engine.calculateTotal(
                "sports",
                2,
                true,
                false,
                "regular",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(206);
        });

        it("applies holiday upcharge", () => {
            const total = engine.calculateTotal(
                "sports",
                2,
                false,
                true,
                "regular",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(190);
        });

        it("applies both weekend and holiday upcharges", () => {
            const total = engine.calculateTotal(
                "sports",
                2,
                true,
                true,
                "regular",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(236);
        });

        it("applies senior discount on sports", () => {
            const total = engine.calculateTotal(
                "sports",
                2,
                false,
                false,
                "senior",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(154.8);
        });

        it("applies student discount on sports", () => {
            const total = engine.calculateTotal(
                "sports",
                2,
                false,
                false,
                "student",
                false,
                "",
                "web"
            );

            expect(total).toBeCloseTo(153.2);
        });

        it("applies GROUP10 coupon (sports, 15% for 10+)", () => {
            const total = engine.calculateTotal(
                "sports",
                10,
                false,
                false,
                "regular",
                false,
                "GROUP10",
                "web"
            );

            expect(total).toBeCloseTo(710);

            const total9 = engine.calculateTotal(
                "sports",
                9,
                false,
                false,
                "regular",
                false,
                "GROUP10",
                "web"
            );

            expect(total9).toBeCloseTo(747);
        });

        it("applies different promo channel fee (web vs other)", () => {
            const totalWeb = engine.calculateTotal(
                "sports",
                2,
                false,
                false,
                "regular",
                false,
                "",
                "web"
            );

            expect(totalWeb).toBeCloseTo(166);

            const totalOther = engine.calculateTotal(
                "sports",
                2,
                false,
                false,
                "regular",
                false,
                "",
                "phone"
            );

            expect(totalOther).toBeCloseTo(167);
        });
    });

});
