package ru.myjino.zhalex.firerunner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


public class Success extends Activity {
/*
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;
    private int counter;
    private boolean gameStarted ;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try{

        TermikRest.lf( this+" pos 1 ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        TermikRest.lf( this + " pos 2 ");

        }catch (Throwable e) {
        }


/*
        //creating timer object to set interval for server start game request
        mTimer = new Timer();
        //Object of task for request about start game
        mMyTimerTask = new MyTimerTask();
        mTimer.schedule(mMyTimerTask, 1000, 2000);
        counter = 0;
        gameStarted = false;
        */
    }

/*

    public void startGame() {


        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        } else return;

        if (gameStarted) return;
        Intent intent = new Intent(Success.this, Start.class);

        startActivity(intent);

        gameStarted = true;
    }

    public void requestGameStart() {
        TermikRest.get("1_1/?game=4&action=isStarted", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    JSONObject json = response.getJSONObject("result");
                    if (json.getInt("isStarted") == 0) {

                        startGame();
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
