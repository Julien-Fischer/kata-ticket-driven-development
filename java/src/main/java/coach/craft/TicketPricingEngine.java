package coach.craft;

public class TicketPricingEngine {

    class TicketData {
        String eventType;
        int numberOfTickets;
        boolean isWeekend;
        boolean isHoliday;
        String customerType;
        boolean vip;
        String couponCode;
        String promoChannel;
    }

    public double calculateTotal(
            String eventType,
            int numberOfTickets,
            boolean isWeekend,
            boolean isHoliday,
            String customerType,
            boolean vip,
            String couponCode,
            String promoChannel
    ) {
        double total = 0;
        double discount = 0;
        double fee = 0;

        double basePrice = 50;

        final boolean isWeekday = !isWeekend;
        boolean isStudent = "student".equals(customerType);
        boolean isSenior = false;

        if ("senior".equals(customerType)) {
            isSenior = true;
        }

        if ("concert".equals(eventType)) {
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

            if (couponCode != null) {
                if ("GROUP10".equals(couponCode)) {
                    if (numberOfTickets >= 10) {
                        discount += 0.15;
                    } else { }
                } else if ("WELCOME".equals(couponCode) && numberOfTickets <= 2) {
                    discount += 0.1;
                }
            } else if (couponCode == null) {
                discount = discount;
            }

            for (int i = 0; i < numberOfTickets; i++) {
                fee = fee + 2;
            }
        }

        if (eventType.equals("movie")) {
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

            if ("COUPLE".equals(couponCode)) {
                if (numberOfTickets == 2) {
                    discount += 0.1;
                }
            } else if ("WELCOME".equals(couponCode) && numberOfTickets <= 2) {
                discount += 0.1;
            }

            fee += 1.5 * numberOfTickets;
        }

        if ("theater".equals(eventType)) {
            basePrice = 60;
            total = basePrice * numberOfTickets;

            if (isWeekend) {
                total *= 1.15;
            }

            if (isHoliday) total *= 1.1;
            if (isStudent) discount += 0.1;

            discount = calculateFor(isSenior, discount, couponCode, numberOfTickets);

            if ("WELCOME".equals(couponCode) && numberOfTickets <= 2) {
                discount += 0.1;
            }

            if (promoChannel != null) {
                if ("phone".equals(promoChannel) || "walk-in".equals(promoChannel)) {
                    fee += 0.5 * numberOfTickets;
                }
            }

            if ("web".equals(promoChannel)) {
                fee += 0.3 * numberOfTickets;
            }
        }

        if ("sports".equals(eventType)) {
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

            if ("GROUP10".equals(couponCode) && numberOfTickets >= 10) {
                discount += 0.15;
            } else if ("WELCOME".equals(couponCode) && numberOfTickets <= 2) {
                discount += 0.1;
            }

            // Ticket [MTF-1495]
            if ("web".equals(promoChannel)) {
                fee += numberOfTickets * 3;
            } else {
                fee += numberOfTickets * 3.5;
            }
        }

        total = total * (1 - discount);
        total += fee;

        return total;
    }

    private double calculateFor(boolean isSenior, double discount, String couponCode, int numberOfTickets) {
        if (isSenior) {
            discount += 0.05;
        }

        if ("GROUP10".equals(couponCode) && numberOfTickets >= 10) {
            discount += 0.15;
        }
        return discount;
    }
}
