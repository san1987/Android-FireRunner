package ru.myjino.zhalex.firerunner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Failed extends Activity {
    String field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        TermikRest.lf( " failed screen on create before ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failed);

        field=getIntent().getExtras().getString("field");
        TermikRest.lf( " failed screen on create after ");
        }catch(Throwable e){

        }

    }

    public void onClick(View view) {
        try{
        TermikRest.lf( " failed screen on click b ");
        Intent intent = new Intent(Failed.this, Game.class);
        intent.putExtra("field", field);
        startActivity(intent);
        TermikRest.lf( " failed screen on click a ");
        }catch(Throwable e){

        }
    }
}
