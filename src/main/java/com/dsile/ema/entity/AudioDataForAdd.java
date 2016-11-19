package com.dsile.ema.entity;

/**
 * Created by DeSile on 11/18/2016.
 */
public class AudioDataForAdd {

    String hash;
    Account account;

    public AudioDataForAdd(String hash, Account account) {
        this.hash = hash;
        this.account = account;
    }

    public AudioDataForAdd(){

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
