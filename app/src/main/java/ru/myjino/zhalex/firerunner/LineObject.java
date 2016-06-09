package ru.myjino.zhalex.firerunner;

/**
 * Created by san on 27.05.2015.
 */
public class LineObject {
    public float x1,x2,y1,y2;
    public int direction;

    public static final int DIR_HORIZ = 1;
    public static final int DIR_VERT = 2;



    LineObject(int _direction, float _x1,float _y1,float coor2){
        float tmp;
        direction = _direction;
        x1=_x1;
        y1=_y1;
        if (_direction==DIR_HORIZ) {
            x2 = coor2;
            y2 = _y1;
        }else{
            y2 = coor2;
            x2 = _x1;
        }
        if (x1>x2) { tmp=x1; x1=x2; x2=tmp;}
        if (y1>y2) { tmp=y1; y1=y2; y2=tmp;}

    }

    public boolean IsContact(CircleObject o){

        try{
        float Xa=x1;
        float Xb=x2;
        float Ya=y1;
        float Yb=y2;

        float Xp=o.x;
        float Yp=o.y;

        float X_projection;
        float Y_projection;

        float radius=o.radius;

        if (   (Xp-Xa)*(Xp-Xa)+(Yp-Ya)*(Yp-Ya)<radius*radius) return true;
        if (   (Xp-Xb)*(Xp-Xb)+(Yp-Yb)*(Yp-Yb)<radius*radius) return true;

        if (direction==DIR_VERT) {
            X_projection=Xa;
            Y_projection=Yp;
            if (Math.abs(Xp-X_projection)>radius) return false;

            if (   Math.abs((Ya+Yb)/2 - Y_projection)<=Math.abs(Ya-Yb)/2   ) return true;



            return false;

        }else if (direction==DIR_HORIZ) {
            X_projection=Xp;
            Y_projection=Ya;

            if (Math.abs(Yp-Y_projection)>radius) return false;

            if (   Math.abs((Xa+Xb)/2 - X_projection)<=Math.abs(Xa-Xb)/2   ) return true;

            return false;

        }else {

            return false;
        }
        }catch (Throwable e) {
            return false;
        }
    }
}
