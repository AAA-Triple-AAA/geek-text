package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

public class CreditCard {
    private int id;
    private String cardHolder;
    private String number;
    private String cvv;
    private String zip;
    private int userID;

    public CreditCard(int id, String cardHolder, String number, String cvv, String zip, int userID) {
        this.id = id;
        this.cardHolder = cardHolder;
        this.number = number;
        this.cvv = cvv;
        this.zip = zip;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public static final RowMapper<CreditCard> CREDIT_CARD_MAPPER = (rs, rowNum) -> {
        return new CreditCard(rs.getInt("id"),
                rs.getString("card_hold"),
                rs.getString("number"),
                rs.getString("cvv"),
                rs.getString("zip"),
                rs.getInt("user_id"));
    };
}
