package com.TouristNest.travelGuide.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@Getter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_sequence")
    @SequenceGenerator(name = "booking_sequence", sequenceName = "booking_sequence", allocationSize = 1, initialValue = 1000)
    private Long bookingId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date checkIN;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date checkOUT;
    @OneToOne
    private User user;
    private int numberOfPersons;
    private String payableAmount;
    private String cardNumber;
    private String paymentMethod;
    @Transient
    private String cvvNumber;

    public Booking() {
        super();
    }

    public Booking(Date checkIN, Date checkOUT, User user,
                   int numberOfPersons, String payableAmount, String paymentMethod,
                   String cardNumber, String cvvNumber) {
        this.checkIN = checkIN;
        this.checkOUT = checkOUT;
        this.user = user;
        this.numberOfPersons = numberOfPersons;
        this.payableAmount = payableAmount;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.cvvNumber = cvvNumber;
    }

    public void setCheckIN(Date checkIN) {
        this.checkIN = checkIN;
    }

    public void setCheckOUT(Date checkOUT) {
        this.checkOUT = checkOUT;
    }

    public void setTotalPerson(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public void setPaidAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

}
