package com.dsile.ema;

import com.dsile.ema.entity.Account;
import com.dsile.ema.entity.AudioDataForAdd;
import com.dsile.ema.entity.SingleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @Autowired
    EthereumBean ethereumBean;

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/best")
    String bestBlock() {
        return ethereumBean.getBestBlock();
    }

    @RequestMapping(value = "/isaudiouniq", method = RequestMethod.POST)
    @ResponseBody
    SingleData checkAudio(@RequestBody SingleData audioHash){
        return new SingleData(ethereumBean.isAudioDataUniq(audioHash.getData()));
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ResponseBody
    Account register(){
        return ethereumBean.createNewAccount();
    }

    @RequestMapping(value = "/addaudio", method = RequestMethod.POST)
    @ResponseBody
    SingleData addAudio(@RequestBody AudioDataForAdd audioDataForAdd){
        return new SingleData(ethereumBean.addTransaction(audioDataForAdd.getHash().getBytes(), audioDataForAdd.getAccount()));
    }

}
