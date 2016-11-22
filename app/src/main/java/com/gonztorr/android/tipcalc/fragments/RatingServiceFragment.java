package com.gonztorr.android.tipcalc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.gonztorr.android.tipcalc.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingServiceFragment extends Fragment implements RatingServiceFragmentListener {
    @Bind(R.id.radioButtonSmm)  RadioButton radioButtonSmm;
    @Bind(R.id.radioButtonSm)   RadioButton radioButtonSm;
    @Bind(R.id.radioButtonSr)   RadioButton radioButtonSr;
    @Bind(R.id.radioButtonSb)   RadioButton radioButtonSb;
    @Bind(R.id.radioButtonSmb)  RadioButton radioButtonSmb;

    private String strRatingService = "init";

    public RatingServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating_service, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public String getRatingService() {
        //String strRatingFood = "Cr";

        return strRatingService;
    }

    @OnClick(R.id.radioButtonSmm)
    public void handleClickradioButtonSmm(){
        strRatingService = "Smm";
    }

    @OnClick(R.id.radioButtonSm)
    public void handleClickradioButtonSm(){
        strRatingService = "Sm";
    }

    @OnClick(R.id.radioButtonSr)
    public void handleClickradioButtonSr(){
        strRatingService = "Sr";
    }

    @OnClick(R.id.radioButtonSb)
    public void handleClickradioButtonSb(){
        strRatingService = "Sb";
    }

    @OnClick(R.id.radioButtonSmb)
    public void handleClickradioButtonSmb(){
        strRatingService = "Smb";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}