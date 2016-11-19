package com.dsile.ema.entity;

import org.ethereum.crypto.ECKey;

import java.math.BigInteger;
import java.util.Arrays;

public class Account {

    BigInteger address;
    BigInteger privateKey;

    public Account(BigInteger address, BigInteger privateKey){
        this.address = address;
        this.privateKey = privateKey;
    }

    public Account(){

    }

    public BigInteger getAddress() {
        return address;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public void setAddress(BigInteger address) {
        this.address = address;
    }

    public void setPrivateKey(BigInteger privateKey) {
        this.privateKey = privateKey;
    }

    public boolean validate() {
        ECKey key = ECKey.fromPrivate(privateKey);
        byte[] actualAddress = key.getAddress();
        return Arrays.equals(actualAddress, this.address.toByteArray());
    }
}
