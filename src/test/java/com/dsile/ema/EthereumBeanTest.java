package com.dsile.ema;


import com.dsile.ema.entity.Account;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;


public class EthereumBeanTest {
    private EthereumBean ethereum = new EthereumBean();

    @Before
    public void startBlockchain() {
        ethereum.start();
    }

    @Test
    public void sequentAddTest() {
        Account account = ethereum.createNewAccount();

        byte[] bytes1 = "hash1".getBytes();
        byte[] bytes2 = "hash2".getBytes();
        byte[] bytes3 = "hash3".getBytes();
        byte[] bytes4 = "hash4".getBytes();

        ethereum.addTransaction(bytes1, account);
        assertEquals(account.getAddress().toString(), ethereum.isAudioDataUniq(bytes1));
        ethereum.addTransaction(bytes2, account);
        assertEquals(account.getAddress().toString(), ethereum.isAudioDataUniq(bytes2));
        ethereum.addTransaction(bytes3, account);
        assertEquals(account.getAddress().toString(), ethereum.isAudioDataUniq(bytes3));
        ethereum.addTransaction(bytes4, account);
        assertEquals(account.getAddress().toString(), ethereum.isAudioDataUniq(bytes4));
    }

    @Test
    public void shouldNotAddDuplicateTransaction() {
        Account account = ethereum.createNewAccount();

        byte[] bytes1 = "hash1".getBytes();

        assertEquals("success", ethereum.addTransaction(bytes1, account));
        assertEquals(account.getAddress().toString(), ethereum.isAudioDataUniq(bytes1));
        assertEquals("Audio already authorshiped by " + account.getAddress().toString(),
                ethereum.addTransaction(bytes1, account));
        assertEquals(account.getAddress().toString(), ethereum.isAudioDataUniq(bytes1));
    }

    @Test
    public void shouldNotFailOnInvalidAccount() {
        Account account1 = ethereum.createNewAccount();

        byte[] bytes1 = "hash1".getBytes();
        Account invalidAccount = account1;
        invalidAccount.setPrivateKey(new BigInteger("9375092345"));

        assertEquals("Invalid Account", ethereum.addTransaction(bytes1, invalidAccount));
    }

}
