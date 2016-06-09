package ru.myjino.zhalex.firerunner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Game extends Activity  implements View.OnTouchListener {

    GameSurface surf=null;
    Timer t;
  //  int width, height;
    String field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        TermikRest.lf( " game class on create b ");
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);

        onNewIntent( getIntent());
        }catch(Exception e){

        }
    }





    @Override
    protected void onNewIntent(Intent intent) {
        try{
        super.onNewIntent(intent);


        field=intent.getExtras().getString("field");

        //this.getWindowManager().getDefaultDisplay().getHeight();
        //this.getWindowManager().getDefaultDisplay().getWidth();

        TermikRest.lf( " game class onNewIntent surf create b ");


        surf = new GameSurface(this,this,  field         );

        surf.width= 800 ;
        surf.height= 480 ;

        TermikRest.lf( " onNewIntent game class surf create a ");



        this.setContentView(surf);
        surf.setOnTouchListener(this);


        TermikRest.lf( " onNewIntent game class timer create b ");


        t = new Timer();

        t.scheduleAtFixedRate(new GraphUpdater(), 40, 40);
        t.scheduleAtFixedRate(new GameUpdater(), 40, 40);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        TermikRest.lf( " onNewIntent game class on create a ");
        }catch(Throwable e){

        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent evt) {
          //if(evt.getAction() == MotionEvent.ACTION_UP)
        try
          {
           // evt.getX(), evt.getY() ((int) evt.getX()),
             //           scrYToGame((int) evt.getY())))

              if (surf==null) return true;
              if (!surf.inited) return true;

              if (surf.player==null) return true;







              Log.v("touch", Integer.toString(evt.getAction()));
              int dx=(int) (surf.player.x-evt.getX());
              int dy=(int) (surf.player.y-evt.getY());

              if (Math.abs(dx)>Math.abs(dy)) {
                  int new_dir=dx<0?CircleObject.DIR_EAST:CircleObject.DIR_WEST;
                  if (
                          (new_dir == CircleObject.DIR_WEST && surf.player.direction!=CircleObject.DIR_EAST)
                      ||
                                  (new_dir == CircleObject.DIR_EAST && surf.player.direction!=CircleObject.DIR_WEST)
                          )
                  surf.player.direction = new_dir;
              }else{


                  int new_dir=dy<0?CircleObject.DIR_SOUTH:CircleObject.DIR_NORTH;
                  if (
                          (new_dir == CircleObject.DIR_SOUTH && surf.player.direction!=CircleObject.DIR_NORTH)
                                  ||
                                  (new_dir == CircleObject.DIR_NORTH && surf.player.direction!=CircleObject.DIR_SOUTH)
                          )
                      surf.player.direction = new_dir;
              }
          }
        catch(Throwable e){

        }

        return true;
    }


    public class GraphUpdater extends TimerTask {


        @Override
        public void run() {
            try {
                Canvas c = surf.getHolder().lockCanvas();
                if (c != null) {
                    c.drawColor(Color.BLACK);
                    surf.DrawGame(c);
                    surf.getHolder().unlockCanvasAndPost(c);
                }
            }catch (Throwable e) {
            }

        }
    }



    public class GameUpdater extends TimerTask {


        @Override
        public void  run() {



            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try{

                    int step_res = surf.Step();

                        if (step_res == GameSurface.GAME_WIN) {

                            TermikRest.lf( " game class WIN 1 ");


                            t.cancel();

                            TermikRest.lf( " game class WIN 2 ");

                            WatchSocket.state=3;
                            TermikRest.get(getApplicationContext(), "/0/1/0", null, null);

                            TermikRest.lf( " game class WIN 3 ");

                            Intent intent = new Intent(Game.this, Success.class);
                            startActivity(intent);

                            TermikRest.lf( " game class WIN 4 ");
                        }

                        if (step_res == GameSurface.GAME_OVER) {

                            TermikRest.lf( " game class GOVER 1 ");
                            t.cancel();

                            WatchSocket.state=2;

                            TermikRest.lf( " game class GOVER 2 ");

                            Intent intent = new Intent(Game.this, Failed.class);
                            intent.putExtra("field", field);
                            startActivity(intent);

                            TermikRest.lf( " game class GOVER 3 ");
                        }
                    }catch (Throwable e) {
                    }

                }
            });

        }
    }


}
