package com.gonztorr.android.tipcalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class TipDetailActivity extends AppCompatActivity {
    @Bind(R.id.txtBillTotal)    TextView txtBillTotal;
    @Bind(R.id.txtTip)          TextView txtTip;
    @Bind(R.id.txtTimeStamp)    TextView txtTimeStamp;

    public final static String TIP_KEY = "Tip";
    public final static String TIME_KEY = "timeStamp";
    public final static String TOTAL_KEY = "Total";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_detail);
        ButterKnife.bind(this);
        //getActionBar().setDisplayHomeAsUpEnabled(true);   //Ya se tiene desde al MainActivity

        //Este Intent, es para poder recibir los elementos de datos que envía la Actividad que llama a esta.
        Intent intent   = getIntent();
        String strTotal = String.format( getString(R.string.message_total),
                                         intent.getDoubleExtra(TOTAL_KEY, 0d) );
        String strTip   = String.format( getString(R.string.message_tip),
                                         intent.getDoubleExtra(TIP_KEY, 0d) );


        txtBillTotal.setText( strTotal );
        txtTip.setText( strTip );
        txtTimeStamp.setText( intent.getStringExtra(TIME_KEY) );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //SNavUtils.navigateUpFromSameTask(this);   //La navegación simplemente limpia en Form del MainActivity
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
