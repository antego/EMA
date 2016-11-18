package com.dsile.ema;

import com.dsile.ema.entity.Account;
import com.google.gson.Gson;
import org.ethereum.config.SystemProperties;
import org.ethereum.config.blockchain.FrontierConfig;
import org.ethereum.core.Block;
import org.ethereum.core.Transaction;
import org.ethereum.crypto.ECKey;
import org.ethereum.util.blockchain.StandaloneBlockchain;
import org.spongycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class EthereumBean {

    private StandaloneBlockchain standAlone;
    private final Object lock = new Object();
    private long nonce = 2;


    public void start() {
        // need to modify the default Frontier settings to keep the blocks difficulty
        // low to not waste a lot of time for block mining
        SystemProperties.getDefault().setBlockchainConfig(new FrontierConfig(new FrontierConfig.FrontierConstants() {
            @Override
            public BigInteger getMINIMUM_DIFFICULTY() {
                return BigInteger.ONE;
            }
        }));

        // Creating a blockchain which generates a new block for each transaction
        // just not to call createBlock() after each call transaction
        synchronized (lock) {
            standAlone = new StandaloneBlockchain().withAutoblock(true);
        }
//            byte[] block1 = standAlone.getBlockchain().getBestBlockHash();
//            System.out.println("Creating first empty block (need some time to generate DAG)...");
//            // warning up the block miner just to understand how long
//            // the initial miner dataset is generated
//            standAlone.createBlock();
//            byte[] block2 = standAlone.getBlockchain().getBestBlockHash();
//            Transaction tx = standAlone.createTransaction(0, new byte[]{1, 2, 3, 4, 5}, 300, new byte[]{1, 1, 1, 1, 1, 11, 1, 1,});
//            standAlone.submitTransaction(tx);
//            byte[] block3 = standAlone.getBlockchain().getBestBlockHash();
//            //Transaction transaction = bc.createTransaction(key,)
//            ECKey ecKey = new ECKey();
//            byte[] addr = ecKey.getAddress();
//            byte[] pubk = ecKey.getPubKey();
//
//            BigInteger priv = ecKey.getPrivKey();
//            ECPoint ecPubKey = ecKey.getPubKeyPoint();
//            ecPubKey.getEncoded(false); //то же самое что и ecKey.getPubKey()
//
//            ECKey ecKeyRebirth = new ECKey(priv, ecPubKey);
//            ECKey ecKeyR2 = ECKey.fromPrivate(priv);

    }

    public String getBestBlock(){
        return standAlone.getBlockchain().getBestBlock().getNumber() + "";
    }

    public String addTransaction(byte[] audioData, Account account){
        byte[] addrBytes = account.getAddress().toByteArray();
        String authorship = findAudioAuthorship(audioData);
        if(authorship == null){
            try {
                synchronized (lock) {
                    Transaction tx = standAlone.createTransaction(ECKey.fromPrivate(account.getPrivateKey()), 0, addrBytes, null, audioData);
                    standAlone.submitTransaction(tx);
                }
                return "success";
            } catch (RuntimeException e){
                return "Invalid Block";
            }
        } else {
            return "Audio already authorshiped by " + authorship;
        }
    }

    private String findAudioAuthorship(byte[] data){
        Block currentBlock = standAlone.getBlockchain().getBestBlock();
        while(currentBlock != null){
            Optional<Transaction> result = currentBlock.getTransactionsList().stream().filter(t -> Arrays.equals(t.getData(),data)).findFirst();
            if(result.isPresent()){
                return new BigInteger(result.get().getSender()).toString();
            }
            currentBlock = standAlone.getBlockchain().getBlockByHash(currentBlock.getParentHash());
        }
        return null;
    }

    public String isAudioDataUniq(String data){
        return findAudioAuthorship(data.getBytes()) + "";
    }

    public Account createNewAccount(){
        ECKey ecKey = new ECKey();
        synchronized (lock){
            standAlone.sendEther(ecKey.getAddress(), new BigInteger("10000000000000000000000000000000000000000000000000000"));
        }

        return new Account(new BigInteger(ecKey.getAddress()),ecKey.getPrivKey());
    }

}
