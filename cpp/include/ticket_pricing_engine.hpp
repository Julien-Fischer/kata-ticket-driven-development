#pragma once

#include <string>

class TicketPricingEngine {
public:
    double calculateTotal(
        const std::string& eventType,
        int numberOfTickets,
        bool isWeekend,
        bool isHoliday,
        const std::string& customerType,
        bool vip,
        const std::string& couponCode,
        const std::string& promoChannel
    );

private:
    static double calculateFor(
        bool isSenior,
        double discount,
        const std::string& couponCode,
        int numberOfTickets
    ) ;
};
