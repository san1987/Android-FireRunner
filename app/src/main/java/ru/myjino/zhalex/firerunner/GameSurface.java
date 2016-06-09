package ru.myjino.zhalex.firerunner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by san on 24.05.2015.
 */
public class GameSurface extends SurfaceView {

    public ArrayList<CircleObject> circleObjects;
    public ArrayList<LineObject> lineObjects;

    Bitmap bmp_finish, bmp_player, bmp_opponent, bmp_background;





    CircleObject player=null, finish=null;

    public Boolean inited=false;


    public static final int GAME_OVER = 1;
    public static final int GAME_WIN = 2;

    public float finish_x, finish_y, finish_radius;

    String lines_str;

    int width, height;

    public GameSurface(Activity a, Context context, String field) {
        super(context);
        try{
        circleObjects = new ArrayList<CircleObject>();
        lineObjects = new ArrayList<LineObject>();
        boolean finish_presented=false;

       // lineObjects.add(new LineObject(100,100,100,300));
       // lineObjects.add(new LineObject(300,400,700,400));
       // lineObjects.add(new LineObject(600,100,600,300));

        CircleObject finish_obj=new CircleObject(400,450,CircleObject.TYPE_FINISH,0,30);


        if (field!="" ) {




            String[] field_objs = field.split("@");

            //Parsing field server string into 2 parts: lines (walls) and circles (player, oppontents, finish place)

            field=field_objs[0];

            lines_str=field_objs[1];

            String[] lines_array = lines_str.split(";");

            //Parsing lines string

            for (String s : lines_array) if (s!="") {
                String[] coors = s.split(",");
                if (coors.length==4)
                    lineObjects.add(new LineObject(Integer.valueOf(coors[0]),
                        Integer.valueOf(coors[1]),
                        Integer.valueOf(coors[2]),
                        Integer.valueOf(coors[3])));
            }

            bmp_finish = BitmapFactory.decodeResource(getResources(), R.drawable.finish);
            bmp_background = BitmapFactory.decodeResource(getResources(), R.drawable.game);

            bmp_player = BitmapFactory.decodeResource(getResources(), R.drawable.player);
            bmp_opponent = BitmapFactory.decodeResource(getResources(), R.drawable.opponent);


            bmp_background = Bitmap.createScaledBitmap(bmp_background, 800, 480, false);





            String[] points = field.split(";");
            for (String s : points)  if (s!="") {
                String[] point = s.split(",");
                Log.v("FIELD", s + "   ====== " + point[0]);

                if (point.length<3) continue;

                float x, y;
                int t;
                x=Float.valueOf(point[1]);
                y=Float.valueOf(point[2]);
                t=Integer.valueOf(point[0]);

                CircleObject o=new CircleObject(x + 35, y + 35, (t < 5 ? CircleObject.TYPE_OPPONENT :
                        (t < 9 ? CircleObject.TYPE_PLAYER : (CircleObject.TYPE_FINISH))), (t == 9 ? 0 : 5), 20 + (t == 9 ? 20 : 0), (t-1)%4+1);

                switch(o.circle_type) {
                    case  CircleObject.TYPE_OPPONENT:
                        o.radius = bmp_opponent.getWidth()/2-4;
                        break;
                    case  CircleObject.TYPE_PLAYER:
                        o.radius = bmp_player.getWidth()/2-4;
                        break;
                    case  CircleObject.TYPE_FINISH:
                       o=finish_obj;
                        break;
                }

                if (o.circle_type==CircleObject.TYPE_FINISH) {
                    circleObjects.add(0, o);
                    finish_presented=true;
                }else
                    circleObjects.add(o);  //

            }
        }else {


            circleObjects.add(new CircleObject(130, 300, CircleObject.TYPE_OPPONENT, 5, 20, CircleObject.DIR_WEST));  //
            circleObjects.add(new CircleObject(210, 230, CircleObject.TYPE_OPPONENT, 5, 20, CircleObject.DIR_NORTH));
            circleObjects.add(new CircleObject(380, 100, CircleObject.TYPE_OPPONENT, 5, 20, CircleObject.DIR_SOUTH));
            circleObjects.add(new CircleObject(430, 240, CircleObject.TYPE_OPPONENT, 5, 20, CircleObject.DIR_EAST));
            circleObjects.add(new CircleObject(550, 400, CircleObject.TYPE_OPPONENT, 5, 20, CircleObject.DIR_WEST));

            circleObjects.add(new CircleObject(700, 30, CircleObject.TYPE_PLAYER, 5, 20, CircleObject.DIR_WEST));
            circleObjects.add(new CircleObject(700, 400, CircleObject.TYPE_FINISH, 0, 40));

        }

        if(!finish_presented) {
            circleObjects.add(0, finish_obj);
        }

        }catch (Throwable e) {
        }


    }

    public GameSurface(Activity a, Context context ,  float _finish_x, float _finish_y, float _finish_radius  ) {

        this(a, context, "");

        try{

        finish_x=_finish_x;
        finish_y=_finish_y;
        finish_radius=_finish_radius;

        }catch (Throwable e) {
        }




        //Bitmap tmp = Bitmap.createScaledBitmap(bmp_finish, newx, newy, true);
        //bmp_finish = tmp;


    }


    public  void DrawGame(Canvas c){

        try{
        Iterator<CircleObject> iter = circleObjects.iterator();
        Paint paint = new Paint();




        c.drawBitmap(bmp_background, 0,0, paint);

            try{

        while(iter.hasNext()){
            CircleObject o=iter.next();



            switch(o.circle_type) {
                case CircleObject.TYPE_PLAYER:
                    paint.setColor(Color.BLUE);
                    //c.drawCircle(o.x, o.y, o.radius, paint);



                    c.drawBitmap(bmp_player, o.x - bmp_player.getWidth()/2, o.y- bmp_player.getHeight()/2, paint);

                    break;
                case CircleObject.TYPE_OPPONENT:
                    paint.setColor(Color.RED);
                    //c.drawCircle(o.x, o.y, o.radius, paint);

                    c.drawBitmap(bmp_opponent, o.x - bmp_opponent.getWidth() / 2, o.y - bmp_opponent.getHeight() / 2, paint);

                    break;
                case CircleObject.TYPE_FINISH:
                    paint.setColor(Color.YELLOW);
                    //c.drawBitmap(bmp_finish, o.x-bmp_finish.getWidth()/2,o.y-bmp_finish.getHeight()/2, paint);
                    //Do not draw finish because it hardcoded in design
                    break;


            }




        }
            }catch (Throwable e) {
            }

        Iterator<LineObject> iter2 = lineObjects.iterator();
        paint.setColor(  Color.CYAN);

            try{
        while(iter2.hasNext()) {
            LineObject o=iter2.next();


            c.drawCircle(o.x1+1, o.y1+1, 3, paint);
            c.drawCircle(o.x2+1, o.y2+1, 3, paint);
            if (o.direction==LineObject.DIR_VERT) {
                c.drawLine(o.x1 - 2, o.y1, o.x2 - 2,o.y2, paint);
                c.drawLine(o.x1 - 1, o.y1, o.x2 - 1,o.y2, paint);
                c.drawLine(o.x1 - 0, o.y1, o.x2 - 0,o.y2, paint);
                c.drawLine(o.x1 + 1, o.y1, o.x2 + 1,o.y2, paint);
                c.drawLine(o.x1 + 2, o.y1, o.x2 + 2,o.y2, paint);
                c.drawLine(o.x1 + 3, o.y1, o.x2 + 3, o.y2, paint);
            }
            else {
                c.drawLine(o.x1, o.y1 - 2, o.x2, o.y2 - 2, paint);
                c.drawLine(o.x1, o.y1 - 1, o.x2, o.y2 - 1, paint);
                c.drawLine(o.x1, o.y1 - 0, o.x2, o.y2 - 0, paint);
                c.drawLine(o.x1, o.y1 + 1, o.x2, o.y2 + 1, paint);
                c.drawLine(o.x1, o.y1 + 2, o.x2, o.y2 + 2, paint);
                c.drawLine(o.x1, o.y1 + 3, o.x2, o.y2 + 3, paint);
            }


        }
            }catch (Throwable e) {
            }

        }catch (Throwable e) {
        }



    }

    public int Step(){

        try{

        inited=true;

        Iterator<CircleObject> iter = circleObjects.iterator();
        while(iter.hasNext()){
            CircleObject o=iter.next();

            o.Move();

            //reflection from box wall
            if (o.x<o.radius) {
                o.x=o.radius;
                o.ChangeDir();
            }
            if (o.y<o.radius) {
                o.y=o.radius;
                o.ChangeDir();
            }
            if ( o.x>width-o.radius) {
                o.x=width-o.radius;
                o.ChangeDir();
            }
            if (o.y>height-o.radius) {
                o.y=height-o.radius;
                o.ChangeDir();
            }

            //reflection from cyan internal wall
            Iterator<LineObject> iter2 = lineObjects.iterator();
            while(iter2.hasNext()) {
                LineObject lin=iter2.next();
                //if sphere (player or opponent) contacts with any wall - change direction
                if (lin.IsContact(o))  o.ChangeDir();
            }


            if (player==null) if (o.circle_type==CircleObject.TYPE_PLAYER) player=o;
            if (finish==null) if (o.circle_type==CircleObject.TYPE_FINISH) finish=o;

        }

        if (player!=null && finish!=null) {
            iter = circleObjects.iterator();
            while (iter.hasNext()) {
                CircleObject o = iter.next();

                if (o.circle_type==CircleObject.TYPE_OPPONENT)
                    if (player.IsContact(o))
                            return GameSurface.GAME_OVER;



            }
            if (player.IsContact(finish)) return GameSurface.GAME_WIN;
            Log.v("POS", "player x"+Float.toString(player.x)+" y="+Float.toString(player.y));
            Log.v("POS", "finish x"+Float.toString(finish.x)+" y="+Float.toString(finish.y));
        }

        }catch (Throwable e) {
        }

        return 0;
    }

}
