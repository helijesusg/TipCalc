package com.gonztorr.android.tipcalc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gonztorr.android.tipcalc.R;
import com.gonztorr.android.tipcalc.models.TipRecord;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Heli Gonzalez on 04/11/2016.
 */

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder> {
    private Context context;            //Para obtener el Contexto de donde se ejecuta la clase
    private List<TipRecord> dataset;    //Lista para el manejo de Datos
    private OnItemClickListener onItemClickListener;

    /*
     * Constructor de la Clase adaptadora. Simplemente seteamos las variables de la clase
     */
    public TipAdapter(Context context, List<TipRecord> dataset, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
    }

    public TipAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataset = new ArrayList<TipRecord>();
        this.onItemClickListener = onItemClickListener;
    }

    /*
     * Método para crear el ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Variable para definir la vista sobre la cual vamos a trabajar
        View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.item_row, parent, false); //La vista la Inflamos sobre le item_row
        return new ViewHolder(view);    //Retornamos el ViewHolder sobre item_row
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TipRecord   element = dataset.get(position);
        String      strTip = String.format( context.getString(R.string.message_tip), element.getTip() );

        holder.txtContent.setText(strTip);
        holder.setOnItemClickListener( element, onItemClickListener );
    }

    @Override
    public int getItemCount() { //Se emplea para retornar la cantidad de items en el dataset
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.txtContent)  TextView txtContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListener(final TipRecord element, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    onItemClickListener.onItemClick(element);
                }
            });
        }
    }

    public void add(TipRecord record){
        dataset.add(0, record);
        notifyDataSetChanged();
    }

    public void clear(){
        dataset.clear();
        notifyDataSetChanged();
    }
}