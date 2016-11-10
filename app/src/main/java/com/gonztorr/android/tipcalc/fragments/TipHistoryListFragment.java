package com.gonztorr.android.tipcalc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gonztorr.android.tipcalc.R;
import com.gonztorr.android.tipcalc.TipDetailActivity;
import com.gonztorr.android.tipcalc.adapters.OnItemClickListener;
import com.gonztorr.android.tipcalc.adapters.TipAdapter;
import com.gonztorr.android.tipcalc.models.TipRecord;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class TipHistoryListFragment extends Fragment implements TipHistoryListFragmentListener, OnItemClickListener{
    @Bind(R.id.recyclerView)    RecyclerView recyclerView;

    private TipAdapter adapter;

    public TipHistoryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tip_history_list, container, false);
        ButterKnife.bind(this, view);

        initAdapter();      //Se inicializa el Adapter
        initRecyclerView(); //Método para inicializar el RecyclerView

        // Inflate the layout for this fragment
        return view;
    }

    private void initAdapter() {
        if( adapter == null ){
            adapter = new TipAdapter(getActivity().getApplicationContext(), this);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addToList(TipRecord record) {
        adapter.add( record );
    }

    @Override
    public void clearList() {
        adapter.clear();
    }

    @Override
    public void onItemClick(TipRecord tipRecord) {

        Intent intent = new Intent( getActivity(), TipDetailActivity.class );

            //Se pasan los datos como parámetros.
        intent.putExtra( TipDetailActivity.TOTAL_KEY, tipRecord.getBill() );
        intent.putExtra( TipDetailActivity.TIP_KEY, tipRecord.getTip() );
        intent.putExtra( TipDetailActivity.TIME_KEY, tipRecord.getDateFormatted() );

        startActivity(intent);  //se inicia la Actividad
    }
}
