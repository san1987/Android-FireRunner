package ru.myjino.zhalex.firerunner;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


public class Start extends Activity {
    /*
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;
    private int counter;
    private boolean gameStarted ;

*/





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{

        TermikRest.lf( " start 1 ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        TermikRest.lf( " start 2 ");


        new WatchSocket().execute(this);

            WatchDog wd = new WatchDog();
            wd.mTimer.scheduleAtFixedRate(wd, 1000, 1000);


        TermikRest.lf( " start 3 ");

        }catch (Throwable e) {
        }
/*

        //creating timer object to set interval for server start game request
        mTimer = new Timer();
        //Object of task for request about start game
        mMyTimerTask = new MyTimerTask();
        mTimer.schedule(mMyTimerTask, 1000, 2000);
        counter = 0;
        gameStarted = false;*/


    }/*

    public void startGame(String field) {


        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        } else return;

        if (gameStarted) return;
        Intent intent = new Intent(Start.this, Game.class);
        intent.putExtra("field", field);
        startActivity(intent);

        gameStarted = true;
    }

    public void requestGameStart() {
        TermikRest.get("1_1/?game=4&action=isStarted", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    JSONObject json = response.getJSONObject("result");
                    if (json.getInt("isStarted") == 1) {

                        startGame(json.getString("field"));
                    }
                } catch (JSONException e) {

                }

            }

        });

        counter++;
    }



    class MyTimerTask extends TimerTask {

        @Override
        public void run() {


            runOnUiThread(new Runnable() {

                @Override
                public void run() {


                    requestGameStart();


                }
            });
        }
    }

*/

}
