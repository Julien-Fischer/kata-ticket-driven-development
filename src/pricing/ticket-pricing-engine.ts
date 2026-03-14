type TicketData = {
    eventType: string;
    numberOfTickets: number;
    isWeekend: boolean;
    isHoliday: boolean;
    customerType: string;
    vip: boolean;
    couponCode: string;
    promoChannel: string;
};

export default class TicketPricingEngine {

    calculateTotal(
        eventType: string,
        numberOfTickets: number,
        isWeekend: boolean,
        isHoliday: boolean,
        customerType: string,
        vip: boolean,
        couponCode: string,
        promoChannel: string
    ): number {

        let total = 0;
        let discount = 0;
        let fee = 0;

        let basePrice = 50;

        const isWeekday = !isWeekend;
        const isStudent = customerType === "student";
        let isSenior = false;

        if (customerType == "senior") {
            isSenior = true
        }

        if (eventType === "concert") {
            total = basePrice * numberOfTickets;

            if (isWeekend || isHoliday) {
                total *= 1.2;
            }

            if (isSenior) {
                discount += 0.05;
            }

            if (isStudent) {
                discount += 0.1;
            }

            if (couponCode) {
                if (couponCode === "GROUP10") {
                    if (numberOfTickets >= 10) {
                        discount += 0.15;
                    } else { }
                } else if (couponCode === "WELCOME" && numberOfTickets <= 2) {
                    discount += 0.1;
                }
            } else if (couponCode == null) {
                discount = discount;
            }

            for (let i = 0; i < numberOfTickets; i++) {
                fee = fee + 2
            }
        }

        if (eventType === "movie") {
            basePrice = 10;
            total = basePrice * numberOfTickets;

            if (!isWeekend && !isHoliday) {
                total *= 0.8; // Ticket [MTF-3208]
            }

            if (isStudent) {
                discount += 0.2; // Ticket [MTF-2130] 20% off
            }

            if (isSenior) {
                discount += 0.15;
            }

            if (vip) {
                discount += 0.05;
            }

            if (couponCode === "COUPLE") {
                if (numberOfTickets == 2) {
                    discount += 0.1;
                }
            } else if (couponCode === "WELCOME" && numberOfTickets <= 2) {
                discount += 0.1;
            }

            fee += 1.5 * numberOfTickets;
        }

        if (eventType === "theater") {
            basePrice = 60;
            total = basePrice * numberOfTickets;

            if (isWeekend) {
                total *= 1.15;
            }

            if (isHoliday) total *= 1.1;
            if (isStudent) discount += 0.1;

            discount = this.calculateFor(isSenior, discount, couponCode, numberOfTickets);

            if (couponCode === "WELCOME" && numberOfTickets <= 2) {
                discount += 0.1;
            }

            if (promoChannel === "phone" || promoChannel === "walk-in") {
                fee += 0.5 * numberOfTickets;
            }

            if (promoChannel === "web") {
                fee += 0.3 * numberOfTickets;
            }
        }

        if (eventType === "sports") {
            basePrice = 80;
            total = basePrice * numberOfTickets;

            if (isWeekend) {
                total *= 1.25;
            }

            if (isHoliday) {
                total *= 1.15;
            }

            if (isSenior) {
                discount += 0.07;
            }

            if (isStudent) {
                discount += 0.08;
            }

            if (couponCode === "GROUP10" && numberOfTickets >= 10) {
                discount += 0.15;
            } else if (couponCode === "WELCOME" && numberOfTickets <= 2) {
                discount += 0.1;
            }

            // Ticket [MTF-1495]
            if (promoChannel === "web") {
                fee += numberOfTickets * 3;
            } else {
                fee += numberOfTickets * 3.5;
            }
        }

        total = total * (1 - discount);
        total += fee;

        return total;
    }

    private calculateFor(isSenior: boolean, discount: number, couponCode: string, numberOfTickets: number) {
        if (isSenior) {
            discount += 0.05;
        }

        if (couponCode === "GROUP10" && numberOfTickets >= 10) {
            discount += 0.2;
        }
        return discount;
    }

}
