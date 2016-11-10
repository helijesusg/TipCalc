package com.gonztorr.android.tipcalc;

import android.app.Application;

/**
 * Created by Heli Gonzalez on 28/10/2016.
 */

public class TipCalcClass extends Application {
    private final static String CREATEBY_URL = "https://ve.linkedin.com/in/heli-jesús-gonzález-torres-18179586";

    public String getCreatebyUrl() {
        return CREATEBY_URL;
    }
}
