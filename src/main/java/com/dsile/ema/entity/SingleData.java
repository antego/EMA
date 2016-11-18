package com.dsile.ema.entity;

/**
 * Created by DeSile on 11/18/2016.
 */
public class SingleData {

    String status;
    String data;

    public SingleData(String data){
        this.data = data;
    }

    public SingleData(){

    }

    public String getData(){
        return data;
    }


    public void setData(String data) {
        this.data = data;
    }
}
