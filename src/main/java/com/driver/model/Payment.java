package com.driver.model;

import javax.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    boolean paymentCompleted;

    PaymentMode paymentMode;

    @OneToOne
    @JoinColumn
    Reservation reservation;

    public Payment() {
    }

    public Payment(int id, boolean paymentCompleted, PaymentMode paymentMode, Reservation reservation) {
        this.id = id;
        this.paymentCompleted = paymentCompleted;
        this.paymentMode = paymentMode;
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getPaymentCompleted() {
        return paymentCompleted;
    }

    public void isPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
