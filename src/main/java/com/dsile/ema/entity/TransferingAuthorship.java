package com.dsile.ema.entity;

/**
 * Created by DeSile on 11/19/2016.
 */
public class TransferingAuthorship {

    String hash;
    Account account;


    public TransferingAuthorship(String hash, Account account) {
        this.hash = hash;
        this.account = account;
    }

    public TransferingAuthorship(){

    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
