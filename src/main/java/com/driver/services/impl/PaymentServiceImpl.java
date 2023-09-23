package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.Spot;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        PaymentMode paymentMode;
        try {
            paymentMode = PaymentMode.valueOf(mode);
        } catch (IllegalArgumentException e) {
            throw new Exception("Payment mode not detected");
        }

        Optional<Reservation> optionalReservation = reservationRepository2.findById(reservationId);

        if (!optionalReservation.isPresent()) {
            throw new Exception("ReservationId Does not exist");
        }

        Reservation reservation = optionalReservation.get();
        Spot spot = reservation.getSpot();

        int totalPrice = spot.getPricePerHour() * reservation.getNumberOfHours();

        if (totalPrice < amountSent) {
            throw new Exception("Insufficient Amount");
        }

        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setPaymentMode(paymentMode);
        payment.isPaymentCompleted(true);

        reservation.setPayment(payment);

        return paymentRepository2.save(payment);
    }
}
