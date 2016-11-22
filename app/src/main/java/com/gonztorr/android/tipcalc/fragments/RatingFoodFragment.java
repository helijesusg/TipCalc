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
public class RatingFoodFragment extends Fragment implements RatingFoodFragmentListener{
    @Bind(R.id.radioButtonCh)   RadioButton radioButtonCh;
    @Bind(R.id.radioButtonCm)   RadioButton radioButtonCm;
    @Bind(R.id.radioButtonCr)   RadioButton radioButtonCr;
    @Bind(R.id.radioButtonCb)   RadioButton radioButtonCb;
    @Bind(R.id.radioButtonCd)   RadioButton radioButtonCd;

    private String strRatingFood = "init";

    public RatingFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating_food, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public String getRatingFood() {
        //String strRatingFood = "Cr";

        return strRatingFood;
    }

    @OnClick(R.id.radioButtonCh)
    public void handleClickradioButtonCh(){
        strRatingFood = "Ch";
    }

    @OnClick(R.id.radioButtonCm)
    public void handleClickradioButtonCm(){
        strRatingFood = "Cm";
    }

    @OnClick(R.id.radioButtonCr)
    public void handleClickradioButtonCr(){
        strRatingFood = "Cr";
    }

    @OnClick(R.id.radioButtonCb)
    public void handleClickradioButtonCb(){
        strRatingFood = "Cb";
    }

    @OnClick(R.id.radioButtonCd)
    public void handleClickradioButtonCd(){
        strRatingFood = "Cd";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
