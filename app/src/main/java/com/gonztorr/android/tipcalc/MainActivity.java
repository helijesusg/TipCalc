package com.gonztorr.android.tipcalc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gonztorr.android.tipcalc.fragments.RatingFoodFragment;
import com.gonztorr.android.tipcalc.fragments.RatingFoodFragmentListener;
import com.gonztorr.android.tipcalc.fragments.RatingServiceFragment;
import com.gonztorr.android.tipcalc.fragments.RatingServiceFragmentListener;
import com.gonztorr.android.tipcalc.fragments.TipHistoryListFragment;
import com.gonztorr.android.tipcalc.fragments.TipHistoryListFragmentListener;
import com.gonztorr.android.tipcalc.models.TipRecord;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import clases.clsAssess;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.inputTotal)      EditText    inputTotal;
    @Bind(R.id.inputPercentage) EditText    inputPercentage;
    @Bind(R.id.btnSubmit)       Button      btnSubmit;
    //@Bind(R.id.btnDelete)       Button      btnDelete;
    @Bind(R.id.btnIncrease)     Button      btnIncrease;
    @Bind(R.id.btnDecrease)     Button      btnDecrease;
    @Bind(R.id.txtTip)          TextView    txtTip;

    private boolean exit = false;
    Toast toastMessage;

    private RatingServiceFragmentListener   ServicefragmentListener;
    private RatingFoodFragmentListener      FoodfragmentListener;
    private TipHistoryListFragmentListener  fragmentListener;

    private String currentRatingService = "init";
    private String currentRatingFood    = "init";

    private final static int TIP_STEP_CHANGE = 1;           //Salto de Incremento/Decreto

    @SuppressWarnings({"deprecation", "RedundantCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Se establece el vínculo entre el .xml y el código java
        ButterKnife.bind(this);

        final int version = android.os.Build.VERSION.SDK_INT;

        if( version < 21 ){    //Lo utilizamos para configurar en tiempo de ejecución los botones de la APP
            btnSubmit.setTextColor( getResources().getColor(R.color.colorPrimaryText ) );
            btnIncrease.setTextColor( getResources().getColor(R.color.colorPrimaryText) );
            btnDecrease.setTextColor( getResources().getColor(R.color.colorPrimaryText) );
            //btnDelete.setTextColor( getResources().getColor(R.color.colorPrimaryText) );
        }

        TipHistoryListFragment  fragmentList    = (TipHistoryListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentList);
        RatingFoodFragment      fragmentFood    = (RatingFoodFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentRatingFood);
        RatingServiceFragment   fragmentService = (RatingServiceFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentRatingService);

        fragmentList.setRetainInstance(true);   //Permite mantener los valores así cambie de configuración del dispositivo
        fragmentListener = (TipHistoryListFragmentListener)fragmentList;

        fragmentFood.setRetainInstance(true);   //Permite mantener los valores así cambie de configuración del dispositivo
        FoodfragmentListener = (RatingFoodFragmentListener) fragmentFood;

        fragmentService.setRetainInstance(true);   //Permite mantener los valores así cambie de configuración del dispositivo
        ServicefragmentListener = (RatingServiceFragmentListener) fragmentService;

        inputTotal = (EditText) findViewById(R.id.inputTotal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);  //Se establece el vínculo entre el java y el menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem itemMenu) {
        if( itemMenu.getItemId() == R.id.action_createby ){    //Se identifica el Click
            CreateBy();
        }
        else if( itemMenu.getItemId() == R.id.action_about ){
            about();
        }
        return super.onOptionsItemSelected(itemMenu);
    }

    @OnClick(R.id.btnSubmit)
    public void handleClickSubmit(){
        String strInputTotal;

        Log.i( getLocalClassName(), "Click en Submit" ); //Forma de Debug... Lo muestra en el Log Trace de ejecución
        hideKeyboard();     //Metodo para ocultar el teclado

        strInputTotal = inputTotal.getText().toString().trim(); //Se captura la entrada del usuario.
            //Se valida que no esté vacio
        if( strInputTotal.isEmpty() ){  //Se valida en caso de esté vacio y se emite mensaje
            String strMessageEmpty = getResources().getString(R.string.field) + " " +
                                     getResources().getString(R.string.hint_total) + " " +  //Se construye el Mesaje a Partir de los Strings
                                     getResources().getString(R.string.message_notempty);

            toastMessage = Toast.makeText( getApplicationContext(),
                                           strMessageEmpty,
                                           Toast.LENGTH_LONG );
            toastMessage.show();    //Se muestra el mensaje
        }
        else{
            float   TotalConsumption = Float.parseFloat( strInputTotal );   //Se captura el valor introducido por el usuario
            int     tipPercentage = getTipPercentage();                     //Se determina y se asigna el valor de la propina

            //float   tip = TotalConsumption*(tipPercentage/100f);            //Se calcula el valor de la propina a calcelar

            TipRecord tipRecord = new TipRecord();

            tipRecord.setBill( TotalConsumption );
            tipRecord.setTipPercentage( tipPercentage );
            tipRecord.setTimeStamp( new Date() );

                //Se llena en TextView de salida
            String strTip = String.format( getString(R.string.message_tip), tipRecord.getTip() );

            fragmentListener.addToList( tipRecord );

            txtTip.setVisibility(View.VISIBLE);
            txtTip.setText(strTip);
        }
    }

    @OnClick(R.id.btnIncrease)
    public void handleClickIncrease(){
        hideKeyboard();
        handleTipChange( TIP_STEP_CHANGE );     //Se pasa positivo el salto de cambio en caso de incremento
    }

    @OnClick(R.id.btnDecrease)
    public void handleClickDecrease(){
        hideKeyboard();
        handleTipChange( -TIP_STEP_CHANGE );     //Se pasa negativo el salto de cambio en caso de decremento
    }

    /*@OnClick(R.id.btnDelete)
    public void handleClickDelete(){
        fragmentListener.clearList();
    }*/

    @SuppressWarnings("ConstantConditions")
    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        try{
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }catch (NullPointerException npe){
            Log.e( getLocalClassName(), Log.getStackTraceString(npe) );
        }
    }

    private int getTipPercentage(){
        clsAssess iclsAssess = new clsAssess(); //Clase de Lógica Difusa para calculo de propina
        int tipPercentage;

        String strRatingService   = ServicefragmentListener.getRatingService();
        String strRatingFood      = FoodfragmentListener.getRatingFood();

        if( strRatingService.equals("init") || strRatingFood.equals("init") ){
            strRatingService = "Smm";
            strRatingFood    = "Ch";
        }

        if( !currentRatingService.equals( strRatingService ) ){
            currentRatingService = strRatingService;
            inputPercentage.setText("");
        }

        if( !currentRatingFood.equals( strRatingFood ) ){
            currentRatingFood = strRatingFood;
            inputPercentage.setText("");
        }

        //int tipPercentage = DEAULT_TIP_PERCENTAGE;
        String strTipPercentage = inputPercentage.getText().toString().trim();

        if( !strTipPercentage.isEmpty() ){
            tipPercentage = Integer.parseInt(strTipPercentage);
        }
        else{
            iclsAssess.setStrValorServicio(strRatingService);   //Se establece la valoración del Servicio
            iclsAssess.setStrValorComida(strRatingFood);        //Se valora la Calidad de la Comida

            tipPercentage = (int) iclsAssess.obtenerPropina();  //La clase propone el porcentaje de la Propina

            inputPercentage.setText( String.valueOf(tipPercentage) );
        }

        return tipPercentage;
    }

    private void handleTipChange(int change) {
        int     tipPercentage = getTipPercentage();     //Se determina y se asigna el valor de la propina

        tipPercentage += change;    //Se suma algebraica dependiendo el signo con que se reciba
        if( tipPercentage > 0 ){
            inputPercentage.setText( String.valueOf(tipPercentage) );
        }
    }

    private void CreateBy() {
        //Acá vamos a obtener un Itent para que navegue a un URL
        TipCalcClass createbyTipCalc = (TipCalcClass)getApplication(); //Para importar la clase
        String strURL = createbyTipCalc.getCreatebyUrl();   //Obtenemos el URL

        Intent intent = new Intent(Intent.ACTION_VIEW);     //Se crea el Intent
        intent.setData(Uri.parse(strURL));                  //Se fija el URL
        startActivity(intent);                              //Se inicia... acá el Sistema sabe que va a abrir el Navegador.
    }


    private void about() {
        String version = getResources().getString(R.string.version) + ": " + BuildConfig.VERSION_NAME;
        AlertDialog.Builder alertDialogMsg = new AlertDialog.Builder(this);

        alertDialogMsg.setIcon( getResources().getDrawable(R.drawable.ic_information) )
                      .setTitle( getResources().getString(R.string.information) + "!!!" )
                      .setMessage( getResources().getString(R.string.message_about1 ) + "\n" +
                                   getResources().getString(R.string.message_about2) + "\n" + "\n" +
                                   version )
                      .setNeutralButton(getResources().getString(R.string.button_accept),    //setPositiveButton
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
        AlertDialog alertDialog = alertDialogMsg.create();
        alertDialog.show();
    }

    @Override
    public boolean onKeyDown( int keyCode, KeyEvent event)
    {
        if( keyCode == KeyEvent.KEYCODE_BACK )
        {
            exit = true;
            finish();   //Finaliza la APP y libera las variables.
            //onDestroy();
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if( exit )
            Toast.makeText( getApplicationContext(), getResources().getString(R.string.message_exit),
                            Toast.LENGTH_SHORT).show();
    }
}