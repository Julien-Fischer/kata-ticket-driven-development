#include <gtest/gtest.h>
#include "ticket_pricing_engine.hpp"

class TicketPricingEngineTest : public ::testing::Test {
protected:
    TicketPricingEngine engine;
};

inline void assertClose(const double actual, const double expected, const double tolerance = 0.01) {
    EXPECT_NEAR(actual, expected, tolerance);
}

//-----------------------------------------------------------------------------
// Concert tests
//-----------------------------------------------------------------------------

TEST_F(TicketPricingEngineTest, CONCERT_applies_weekend_upcharge_and_basic_discount_rules) {
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

    assertClose(total, 124, 0.01);
}

TEST_F(TicketPricingEngineTest, CONCERT_applies_holiday_upcharge_and_basic_discount_rules) {
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

    assertClose(total, 124, 0.01);
}

TEST_F(TicketPricingEngineTest, CONCERT_applies_senior_discount_on_concerts) {
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

    assertClose(total, 99);
}

TEST_F(TicketPricingEngineTest, CONCERT_applies_student_discount_on_concerts) {
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

    assertClose(total, 94);
}

TEST_F(TicketPricingEngineTest, CONCERT_applies_welcome_coupon_on_concerts) {
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

    assertClose(total, 94);
}

TEST_F(TicketPricingEngineTest, CONCERT_does_not_apply_welcome_coupon_for_more_than_2_tickets) {
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

    assertClose(total, 156);
}

TEST_F(TicketPricingEngineTest, CONCERT_applies_group10_coupon_on_concerts) {
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

    assertClose(total, 445);
}

TEST_F(TicketPricingEngineTest, CONCERT_does_not_apply_group10_coupon_for_more_than_2_tickets) {
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

    assertClose(total, 468);
}

//-----------------------------------------------------------------------------
// Movie tests
//-----------------------------------------------------------------------------

TEST_F(TicketPricingEngineTest, MOVIE_does_not_accept_group_10_coupons_for_movies) {
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

    assertClose(total, 115);
}

TEST_F(TicketPricingEngineTest, MOVIE_applies_weekday_discount) {
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

    assertClose(total, 28.5);
}

TEST_F(TicketPricingEngineTest, MOVIE_does_not_apply_weekday_discount_on_weekend) {
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

    assertClose(total, 34.5);
}

TEST_F(TicketPricingEngineTest, MOVIE_applies_student_discount_on_movies) {
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

    assertClose(total, 23.7);
}

TEST_F(TicketPricingEngineTest, MOVIE_applies_senior_discount_on_movies) {
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

    assertClose(total, 24.9);
}

TEST_F(TicketPricingEngineTest, MOVIE_applies_vip_discount_on_movies) {
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

    assertClose(total, 27.3);
}

TEST_F(TicketPricingEngineTest, MOVIE_applies_couple_coupon_only_if_2_tickets) {
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

    assertClose(total, 17.4);

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

    assertClose(total3, 28.5);
}

//-----------------------------------------------------------------------------
// Theater tests
//-----------------------------------------------------------------------------

TEST_F(TicketPricingEngineTest, THEATER_applies_weekend_upcharge) {
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

    assertClose(total, 138.6);
}

TEST_F(TicketPricingEngineTest, THEATER_applies_holiday_upcharge) {
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

    assertClose(total, 132.6);
}

TEST_F(TicketPricingEngineTest, THEATER_applies_both_weekend_and_holiday_upcharges) {
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

    assertClose(total, 152.4);
}

TEST_F(TicketPricingEngineTest, THEATER_applies_student_discount_on_theater) {
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

    assertClose(total, 108.6);
}

TEST_F(TicketPricingEngineTest, THEATER_applies_senior_discount_on_theater) {
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

    assertClose(total, 114.6);
}

TEST_F(TicketPricingEngineTest, THEATER_applies_welcome_coupon_only_for_1_or_two_tickets) {
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

    assertClose(total, 54.3);

    double total10 = engine.calculateTotal(
        "theater",
        2,
        false,
        false,
        "regular",
        false,
        "WELCOME",
        "web"
    );

    assertClose(total10, 108.6);
}

TEST_F(TicketPricingEngineTest, THEATER_applies_group10_coupon_only_for_10_or_more_tickets) {
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

    assertClose(total, 542.7);

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

    assertClose(total10, 513);
}

TEST_F(TicketPricingEngineTest, THEATER_applies_promo_channel_fee_phone_walk_in) {
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

    assertClose(total, 121);

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

    assertClose(totalWeb, 120.6);
}

//-----------------------------------------------------------------------------
// Sports tests
//-----------------------------------------------------------------------------

TEST_F(TicketPricingEngineTest, SPORTS_applies_weekend_upcharge_sports) {
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

    assertClose(total, 206);
}

TEST_F(TicketPricingEngineTest, SPORTS_applies_holiday_upcharge_sports) {
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

    assertClose(total, 190);
}

TEST_F(TicketPricingEngineTest, SPORTS_applies_both_weekend_and_holiday_upcharges_sports) {
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

    assertClose(total, 236);
}

TEST_F(TicketPricingEngineTest, SPORTS_applies_senior_discount_on_sports) {
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

    assertClose(total, 154.8);
}

TEST_F(TicketPricingEngineTest, SPORTS_applies_student_discount_on_sports) {
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

    assertClose(total, 153.2);
}

TEST_F(TicketPricingEngineTest, SPORTS_applies_group_10_coupon_sports) {
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

    assertClose(total, 710);

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

    assertClose(total9, 747);
}

TEST_F(TicketPricingEngineTest, SPORTS_applies_different_promo_channel_fee_sports) {
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

    assertClose(totalWeb, 166);

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

    assertClose(totalOther, 167);
}
