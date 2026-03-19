package coach.craft;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class TicketPricingEngineTest {

    private TicketPricingEngine engine;

    @BeforeEach
    void setUp() {
        engine = new TicketPricingEngine();
    }

    @Nested
    class ConcertTests {

        @Test
        void applies_weekend_upcharge_and_basic_discount_rules() {
            double total = engine.calculateTotal(
                    "concert",
                    2,
                    true,
                    false,
                    "regular",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(124, within(0.01));
        }

        @Test
        void applies_holiday_upcharge_and_basic_discount_rules() {
            double total = engine.calculateTotal(
                    "concert",
                    2,
                    false,
                    true,
                    "regular",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(124, within(0.01));
        }

        @Test
        void applies_senior_discount_on_concerts() {
            double total = engine.calculateTotal(
                    "concert",
                    2,
                    false,
                    false,
                    "senior",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(99, within(0.01));
        }

        @Test
        void applies_student_discount_on_concerts() {
            double total = engine.calculateTotal(
                    "concert",
                    2,
                    false,
                    false,
                    "student",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(94, within(0.01));
        }

        @Test
        void applies_welcome_coupon_on_concerts() {
            double total = engine.calculateTotal(
                    "concert",
                    2,
                    false,
                    false,
                    "regular",
                    false,
                    "WELCOME",
                    "web"
            );

            assertThat(total).isCloseTo(94, within(0.01));
        }

        @Test
        void does_not_apply_welcome_coupon_for_more_than_2_tickets() {
            double total = engine.calculateTotal(
                    "concert",
                    3,
                    false,
                    false,
                    "regular",
                    false,
                    "WELCOME",
                    "web"
            );

            assertThat(total).isCloseTo(156, within(0.01));
        }

        @Test
        void applies_group10_coupon_on_concerts() {
            double total = engine.calculateTotal(
                    "concert",
                    10,
                    false,
                    false,
                    "regular",
                    false,
                    "GROUP10",
                    "web"
            );

            assertThat(total).isEqualTo(445);
        }

        @Test
        void does_not_apply_group10_coupon_for_less_than_10_tickets() {
            double total = engine.calculateTotal(
                    "concert",
                    9,
                    false,
                    false,
                    "regular",
                    false,
                    "GROUP10",
                    "web"
            );

            assertThat(total).isEqualTo(468);
        }
    }

    @Nested
    class MovieTests {

        @Test
        void does_not_accept_group_10_coupons_for_movies() {
            double total = engine.calculateTotal(
                    "movie",
                    10,
                    true,
                    false,
                    "regular",
                    false,
                    "GROUP10",
                    "web"
            );

            assertThat(total).isCloseTo(115, within(0.01));
        }

        @Test
        void applies_weekday_discount() {
            double total = engine.calculateTotal(
                    "movie",
                    3,
                    false,
                    false,
                    "regular",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(28.5, within(0.01));
        }

        @Test
        void does_not_apply_weekday_discount_on_weekend() {
            double total = engine.calculateTotal(
                    "movie",
                    3,
                    true,
                    false,
                    "regular",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(34.5, within(0.01));
        }

        @Test
        void applies_student_discount_on_movies() {
            double total = engine.calculateTotal(
                    "movie",
                    3,
                    false,
                    false,
                    "student",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(23.7, within(0.01));
        }

        @Test
        void applies_senior_discount_on_movies() {
            double total = engine.calculateTotal(
                    "movie",
                    3,
                    false,
                    false,
                    "senior",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(24.9, within(0.01));
        }

        @Test
        void applies_vip_discount_on_movies() {
            double total = engine.calculateTotal(
                    "movie",
                    3,
                    false,
                    false,
                    "regular",
                    true,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(27.3, within(0.01));
        }

        @Test
        void applies_couple_coupon_only_if_2_tickets() {
            double total = engine.calculateTotal(
                    "movie",
                    2,
                    false,
                    false,
                    "regular",
                    false,
                    "COUPLE",
                    "web"
            );

            assertThat(total).isCloseTo(17.4, within(0.01));

            double total3 = engine.calculateTotal(
                    "movie",
                    3,
                    false,
                    false,
                    "regular",
                    false,
                    "COUPLE",
                    "web"
            );

            assertThat(total3).isCloseTo(28.5, within(0.01));
        }
    }

    @Nested
    class TheaterTests {

        @Test
        void applies_weekend_upcharge() {
            double total = engine.calculateTotal(
                    "theater",
                    2,
                    true,
                    false,
                    "regular",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(138.6, within(0.01));
        }

        @Test
        void applies_holiday_upcharge() {
            double total = engine.calculateTotal(
                    "theater",
                    2,
                    false,
                    true,
                    "regular",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(132.6, within(0.01));
        }

        @Test
        void applies_both_weekend_and_holiday_upcharges() {
            double total = engine.calculateTotal(
                    "theater",
                    2,
                    true,
                    true,
                    "regular",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(152.4, within(0.01));
        }

        @Test
        void applies_student_discount_on_theater() {
            double total = engine.calculateTotal(
                    "theater",
                    2,
                    false,
                    false,
                    "student",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(108.6, within(0.01));
        }

        @Test
        void applies_senior_discount_on_theater() {
            double total = engine.calculateTotal(
                    "theater",
                    2,
                    false,
                    false,
                    "senior",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(114.6, within(0.01));
        }

        @Test
        void applies_group10_coupon_only_for_10_or_more_tickets() {
            double total = engine.calculateTotal(
                    "theater",
                    9,
                    false,
                    false,
                    "regular",
                    false,
                    "GROUP10",
                    "web"
            );

            assertThat(total).isCloseTo(542.7, within(0.01));

            double total10 = engine.calculateTotal(
                    "theater",
                    10,
                    false,
                    false,
                    "regular",
                    false,
                    "GROUP10",
                    "web"
            );

            assertThat(total10).isEqualTo(513);
        }

        @Test
        void applies_welcome_coupon_only_for_1_or_two_tickets() {
            double total = engine.calculateTotal(
                    "theater",
                    1,
                    false,
                    false,
                    "regular",
                    false,
                    "WELCOME",
                    "web"
            );

            assertThat(total).isCloseTo(54.3, within(0.01));

            double total9 = engine.calculateTotal(
                    "theater",
                    2,
                    false,
                    false,
                    "regular",
                    false,
                    "WELCOME",
                    "web"
            );

            assertThat(total9).isCloseTo(108.6, within(0.01));
        }

        @Test
        void applies_promo_channel_fee_phone_walk_in() {
            double total = engine.calculateTotal(
                    "theater",
                    2,
                    false,
                    false,
                    "regular",
                    false,
                    "",
                    "phone"
            );

            assertThat(total).isCloseTo(121, within(0.01));

            double totalWeb = engine.calculateTotal(
                    "theater",
                    2,
                    false,
                    false,
                    "regular",
                    false,
                    "",
                    "web"
            );

            assertThat(totalWeb).isCloseTo(120.6, within(0.01));
        }
    }

    @Nested
    class SportsTests {

        @Test
        void applies_weekend_upcharge_sports() {
            double total = engine.calculateTotal(
                    "sports",
                    2,
                    true,
                    false,
                    "regular",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(206, within(0.01));
        }

        @Test
        void applies_holiday_upcharge_sports() {
            double total = engine.calculateTotal(
                    "sports",
                    2,
                    false,
                    true,
                    "regular",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(190, within(0.01));
        }

        @Test
        void applies_both_weekend_and_holiday_upcharges_sports() {
            double total = engine.calculateTotal(
                    "sports",
                    2,
                    true,
                    true,
                    "regular",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(236, within(0.01));
        }

        @Test
        void applies_senior_discount_on_sports() {
            double total = engine.calculateTotal(
                    "sports",
                    2,
                    false,
                    false,
                    "senior",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(154.8, within(0.01));
        }

        @Test
        void applies_student_discount_on_sports() {
            double total = engine.calculateTotal(
                    "sports",
                    2,
                    false,
                    false,
                    "student",
                    false,
                    "",
                    "web"
            );

            assertThat(total).isCloseTo(153.2, within(0.01));
        }

        @Test
        void applies_group_10_coupon_sports() {
            double total = engine.calculateTotal(
                    "sports",
                    10,
                    false,
                    false,
                    "regular",
                    false,
                    "GROUP10",
                    "web"
            );

            assertThat(total).isCloseTo(710, within(0.01));

            double total9 = engine.calculateTotal(
                    "sports",
                    9,
                    false,
                    false,
                    "regular",
                    false,
                    "GROUP10",
                    "web"
            );

            assertThat(total9).isCloseTo(747, within(0.01));
        }

        @Test
        void applies_different_promo_channel_fee_sports() {
            double totalWeb = engine.calculateTotal(
                    "sports",
                    2,
                    false,
                    false,
                    "regular",
                    false,
                    "",
                    "web"
            );

            assertThat(totalWeb).isCloseTo(166, within(0.01));

            double totalOther = engine.calculateTotal(
                    "sports",
                    2,
                    false,
                    false,
                    "regular",
                    false,
                    "",
                    "phone"
            );

            assertThat(totalOther).isCloseTo(167, within(0.01));
        }
    }
}
