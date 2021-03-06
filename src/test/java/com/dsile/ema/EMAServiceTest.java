package com.dsile.ema;

import com.dsile.ema.entity.Account;
import com.dsile.ema.entity.AudioDataForAdd;
import com.dsile.ema.entity.SingleData;
import com.dsile.ema.entity.TransferingAuthorship;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class EMAServiceTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private HttpHeaders httpHeaders;

    @Before
    public void before(){
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testAudioReadd() throws Exception {
        ResponseEntity<Account> accountEntity = this.testRestTemplate.getForEntity("/register", Account.class);
        ResponseEntity<Account> accountEntity2 = this.testRestTemplate.getForEntity("/register", Account.class);
        assertThat(accountEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(accountEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);

        String audioHash = new String(new byte[]{1,2,3,4,5,6,7,8,9});
        AudioDataForAdd adfa = new AudioDataForAdd(audioHash,accountEntity.getBody());
        AudioDataForAdd adfa2 = new AudioDataForAdd(audioHash,accountEntity2.getBody());

        JSONObject audioHashJson = new JSONObject(adfa);
        JSONObject audioHashJson2 = new JSONObject(adfa2);

        HttpEntity<String> entity = new HttpEntity<>(audioHashJson.toString(), httpHeaders);
        HttpEntity<String> entity2 = new HttpEntity<>(audioHashJson2.toString(), httpHeaders);

        ResponseEntity<SingleData> resultAddAudio = this.testRestTemplate.postForEntity("/addaudio", entity , SingleData.class);
        assertThat(resultAddAudio.getBody().getData()).isEqualTo("success");

        ResponseEntity<SingleData> resultAddAudio2 = this.testRestTemplate.postForEntity("/addaudio", entity2 , SingleData.class);
        assertThat(resultAddAudio2.getBody().getData()).contains("Audio already authorshiped by");
    }

    @Test
    public void testAudioAdd() throws Exception {
        ResponseEntity<Account> accountEntity = this.testRestTemplate.getForEntity("/register", Account.class);
        assertThat(accountEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        String audioHash = new String(new byte[]{1,2,3,4,5,6,7,8,9});
        AudioDataForAdd adfa = new AudioDataForAdd(audioHash,accountEntity.getBody());

        JSONObject audioHashJson = new JSONObject(adfa);

        HttpEntity<String> entity = new HttpEntity<>(audioHashJson.toString(), httpHeaders);

        ResponseEntity<SingleData> resultAddAudio = this.testRestTemplate.postForEntity("/addaudio", entity , SingleData.class);
        assertThat(resultAddAudio.getBody().getData()).isEqualTo("success");
    }

    @Test
    public void testAudioUniq() throws Exception {
        SingleData audioHash = new SingleData(new String(new byte[]{1,2,3,4,5,6,7,8,9}));
        JSONObject audioHashJson = new JSONObject(audioHash);
        HttpEntity<String> entity = new HttpEntity<>(audioHashJson.toString(), httpHeaders);

        ResponseEntity<SingleData> resultCheckAudio = this.testRestTemplate.postForEntity("/isaudiouniq", entity , SingleData.class);
        assertThat(resultCheckAudio.getBody().getData()).isEqualTo("null");
    }

    @Test
    public void testTransferingAuthorship() throws Exception {
        ResponseEntity<Account> firstAccount = this.testRestTemplate.getForEntity("/register", Account.class);
        ResponseEntity<Account> seccondAccount = this.testRestTemplate.getForEntity("/register", Account.class);
        assertThat(firstAccount.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(seccondAccount.getStatusCode()).isEqualTo(HttpStatus.OK);

        String audioHash = new String(new byte[]{1,2,3,4,5,6,7,8,9});
        AudioDataForAdd dataForAddForFirstAccount = new AudioDataForAdd(audioHash,firstAccount.getBody());
        Account fromSecondToFirstAccount = new Account(seccondAccount.getBody().getAddress(),
                firstAccount.getBody().getPrivateKey());
        TransferingAuthorship ta = new TransferingAuthorship(audioHash, fromSecondToFirstAccount);

        JSONObject adfaJson = new JSONObject(dataForAddForFirstAccount);
        JSONObject transferJson = new JSONObject(ta);

        JSONObject audioHashJson = new JSONObject(new SingleData(audioHash));
        HttpEntity<String> audioEntity = new HttpEntity<>(audioHashJson.toString(), httpHeaders);

        HttpEntity<String> entity = new HttpEntity<>(adfaJson.toString(), httpHeaders);
        HttpEntity<String> entity2 = new HttpEntity<>(transferJson.toString(), httpHeaders);

        ResponseEntity<SingleData> resultAddAudio = this.testRestTemplate.postForEntity("/addaudio", entity , SingleData.class);
        assertThat(resultAddAudio.getBody().getData()).isEqualTo("success");

        ResponseEntity<SingleData> resultCheckAudio = this.testRestTemplate.postForEntity("/isaudiouniq", audioEntity , SingleData.class);
        assertThat(resultCheckAudio.getBody().getData()).contains(firstAccount.getBody().getAddress().toString());

        ResponseEntity<SingleData> transferResponce = this.testRestTemplate.postForEntity("/transfer-authorship", entity2 , SingleData.class);
        assertThat(transferResponce.getBody().getData()).contains("Authorship successful transfered");

        ResponseEntity<SingleData> resultCheckAudio2 = this.testRestTemplate.postForEntity("/isaudiouniq", audioEntity , SingleData.class);
        assertThat(resultCheckAudio2.getBody().getData()).contains(seccondAccount.getBody().getAddress().toString());
    }
}
