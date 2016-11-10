package com.gonztorr.android.tipcalc.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Heli Gonzalez on 04/11/2016.
 */

public class TipRecord {
    private double Bill;        //Total de Consumo
    private int TipPercentage;  //Porcentaje de Propina
    private Date timeStamp;     //Para la fecha y hora del consumo

    public double getBill() {
        return Bill;
    }

    public void setBill(double bill) {
        Bill = bill;
    }

    public int getTipPercentage() {
        return TipPercentage;
    }

    public void setTipPercentage(int tipPercentage) {
        TipPercentage = tipPercentage;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getTip(){
        return  Bill*(TipPercentage/100d);
    }

    public String getDateFormatted(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy. hh:mm a");
        return simpleDateFormat.format(timeStamp);
    }
}