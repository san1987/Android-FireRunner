package ru.myjino.zhalex.firerunner;

/**
 * Created by san on 24.05.2015.
 */
public class CircleObject {


    public static final int DIR_NORTH = 1;
    public static final int DIR_EAST = 2;
    public static final int DIR_SOUTH = 3;
    public static final int DIR_WEST = 4;

    public static final int TYPE_OPPONENT = 1;
    public static final int TYPE_PLAYER = 2;
    public static final int TYPE_FINISH = 3;


    public float x, y, last_x, last_y, speed=1, radius=10;
    public int circle_type=TYPE_OPPONENT;
    public int direction = 0;

    CircleObject(float _x, float _y){
        x=_x;
        y=_y;
    }

    CircleObject(float _x, float _y, int _circle_type){
        this(_x, _y);
        circle_type=_circle_type;
    }

    CircleObject(float _x, float _y,  int _circle_type, float _speed, float _radius){
        this(_x, _y, _circle_type);
        speed=_speed;
        radius=_radius;
    }

    CircleObject(float _x, float _y,  int _circle_type, float _speed, float _radius, int _direction){
        this(_x, _y, _circle_type,_speed, _radius);
        direction=_direction;
    }

    public void ChangeDir(){
        switch(direction) {


            case CircleObject.DIR_NORTH:

                direction = DIR_SOUTH;
                break;
            case CircleObject.DIR_EAST:
                direction = DIR_WEST;

                break;
            case CircleObject.DIR_SOUTH:

                direction = DIR_NORTH;
                break;
            case CircleObject.DIR_WEST:
                direction = DIR_EAST;

                break;
        }
        Previos();
    }
    public void Previos(){
        x=last_x;
        y=last_y;
    }

    public void Move (){
        last_x=x;
        last_y=y;
        try{

        switch(direction){


            case CircleObject.DIR_NORTH:

                y-=speed;
                break;
            case CircleObject.DIR_EAST:
                x+=speed;

                break;
            case CircleObject.DIR_SOUTH:

                y+=speed;
                break;
            case CircleObject.DIR_WEST:
                x-=speed;

                break;
        }}catch (Throwable e) {
        }
    }

    public boolean IsContact( float _x,  float _y,  float _radius){
        try{
        return ((_x-x)*(_x-x)+(_y-y)*(_y-y))<=(_radius+radius)*(_radius+radius);
        }catch (Throwable e) {
            return false;
        }
    }

    public boolean IsContact( CircleObject o ){
        return IsContact(o.x, o.y, o.radius);
    }


}
