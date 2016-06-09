package ru.myjino.zhalex.firerunner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Brifing extends Activity {

    String field="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        TermikRest.lf(  " brifing on create before ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brifing);

        TermikRest.lf(" brifing on create middle ");



        onNewIntent(getIntent());
        }catch (Throwable e) {
        }

    }


    @Override
    protected void onNewIntent(Intent intent) {
        try{
        super.onNewIntent(intent);

        TermikRest.lf(" onNewIntent on create beofre  " + field + "\n\n");


        field=intent.getExtras().getString("field");
        TermikRest.BASE_URL="http:/"+intent.getExtras().getString("SERVER");

        TermikRest.lf(  " onNewIntent on create after  "+field+"\n\n");
        }catch (Throwable e) {
        }
    }


    public void onClick(View view) {

        try{


        TermikRest.lf(  " brifing on click before ");
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("field", field);
        TermikRest.lf(  " brifing on click middle  ");
        startActivity(intent);
        TermikRest.lf(  " brifing on click after");
        }catch (Throwable e) {
        }
    }
}
