package com.dsile.ema.entity;

import java.math.BigInteger;

/**
 * Created by DeSile on 11/18/2016.
 */
public class AudioDataForAdd {

    String audiohash;
    Account account;

    public AudioDataForAdd(String audiohash, Account account) {
        this.audiohash = audiohash;
        this.account = account;
    }

    public AudioDataForAdd(){

    }

    public String getAudiohash() {
        return audiohash;
    }

    public void setAudiohash(String audiohash) {
        this.audiohash = audiohash;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
