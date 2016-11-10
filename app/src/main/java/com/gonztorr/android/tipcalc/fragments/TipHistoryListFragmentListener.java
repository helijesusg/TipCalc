package com.gonztorr.android.tipcalc.fragments;

import com.gonztorr.android.tipcalc.models.TipRecord;

/**
 * Created by Heli Gonzalez on 02/11/2016.
 */

public interface TipHistoryListFragmentListener {
    void addToList(TipRecord record);
    void clearList();
}
