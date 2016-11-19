package com.dsile.ema.entity;

import java.math.BigInteger;

/**
 * Created by DeSile on 11/19/2016.
 */
public class TransferingAuthorship {

    String hash;
    BigInteger senderKey;
    BigInteger receiverAddress;


    public TransferingAuthorship(String hash, BigInteger senderKey, BigInteger receiverAddress) {
        this.hash = hash;
        this.senderKey = senderKey;
        this.receiverAddress = receiverAddress;
    }

    public TransferingAuthorship(){

    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public BigInteger getSenderKey() {
        return senderKey;
    }

    public void setSenderKey(BigInteger senderKey) {
        this.senderKey = senderKey;
    }

    public BigInteger getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(BigInteger receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
}
