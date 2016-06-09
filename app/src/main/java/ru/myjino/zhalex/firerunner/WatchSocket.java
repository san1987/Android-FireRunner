package ru.myjino.zhalex.firerunner;


/**
 * Created by san on 06.06.2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by san on 04.06.2015.
 */
public class WatchSocket extends AsyncTask<Activity, Integer, Integer>
{
    Activity mCtx;
    Socket mySock;

    public static int state=0;


    protected void onProgressUpdate(Integer... progress)
    { }

    protected void onPostExecute(Integer result)
    {

    }







    protected Integer doInBackground(Activity... param)
    {

        int SERVERPORT=8070;



        mCtx = param[0];

        TermikRest.lf( "Do in back ws start");

        Socket socket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        ServerSocket serverSocket = null;
        try
        {
            TermikRest.lf( "Before server socket create");
            serverSocket = new ServerSocket(SERVERPORT);
            Log.v("INET", "Listening  " + SERVERPORT);
            Log.v("INET", "Server IP:" + serverSocket.getInetAddress());


            int command=0;
            String fname="";
            String server_ip="";
            String repeat="";
            boolean stopped=false;
            TermikRest.lf( "Before socket listen loop");
            while(!stopped)
            {
                try
                {
                    TermikRest.lf( "Before waiting for connect");
                    socket = serverSocket.accept();
                    TermikRest.lf( "connect accepted");
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    TermikRest.lf( "in buffer created");


                    server_ip= ""+socket.getInetAddress();
                    Log.v("INET", "ip: " + socket.getInetAddress());
                    String req=null;
                    String response=new String();
                    TermikRest.lf( "connection from "+server_ip+", ready to read");
                    while (true) {
                        TermikRest.lf( "Before line reading");
                        String str = in.readLine();
                        TermikRest.lf( "After line reading \n read line: "+str+"\n\n");
                        if (req==null) req=str;

                        if (str==null  || str.equals("\n") || str.equals("\r\n")  || str.equals("")) break;
                        Log.v("INET", "message Received: " + str);
                    }


                    command=0;
                    if (req!=null) {
                        if (req.indexOf("GET /stop ")!=-1) {
                            response="{\"result\": 1}";
                            command=3;

                        }else if (req.indexOf("GET /whoiam")!=-1) {
                            response="{\"result\": \"firefly\"}";

                        }else if (req.indexOf("GET /0/0/0")!=-1) {
                            response="{\"success\": 1}";
                            command=1;
                        }else if(req.indexOf("GET /0/1")!=-1) {

                            /*
                            Pattern p= Pattern.compile("^.+field=([^\\s&]+)(&|\\s).+$");
                            Matcher m=p.matcher(req);
                            try {
                                if (m.matches())
                                    fname = m.group(1);
                            }catch (Exception e){

                            }

                            */

                            fname="2,540,180;3,240,60;3,120,360;6,660,0;3,660,300;9,720,360;@2,70,0,140;1,70,140,140;1,215,140,430;2,430,140,200;1,430,70,585;2,585,70,140;1,585,140,730;2,730,0,70;2,215,200,345;1,290,270,800;2,800,470,480";


                            response="{\"success\": 1}";
                            command=2;
                        }else if(req.indexOf("GET /255/0/0")!=-1) {
                            //response="{\"success\": 1, \"arduino\": \""+TermikRest.uuid(mCtx)+"\", \"devices\": [{\"id\": 0, \"state\":  "+state+"}]}";
							
							response="{\"success\": 1, \"carrier_id\": \""+TermikRest.uuid(mCtx)+"\", \"onboard_devices\": [{\"id\": 0, \"state\": "+(state>0?1:0)+"}]} ";
                        }
                    }

                    TermikRest.lf( "Command found "+command);
                    TermikRest.lf( "Before socket output prepare");

                    OutputStream out = socket.getOutputStream();
                    String output_str = "HTTP/1.1 200 OK\n" +
                            // "Content-Type: application/json\n" +
                            "Content-Length: "+response.length()+"\n\n"+response;
                    byte buf[] = output_str.getBytes();
                    TermikRest.lf( "Before  socket output ");
                    out.write(buf);
                    TermikRest.lf( "After  socket output ");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    TermikRest.lf( " error1501 : " +e );
                }
                finally
                {
                    if( socket!= null)
                    {
                        try
                        {
                            TermikRest.lf( " before accepted socket close ");
                            socket.close();
                            TermikRest.lf( " after accepted socket close");
                            Intent intent;

                            switch(command) {
                                case 2:
                                    state=1;
                                    intent= new Intent(mCtx, Brifing.class);

                                    intent.putExtra("field", fname);
                                    intent.putExtra("SERVER", server_ip);
                                    TermikRest.lf( " before start activity cmd 2 ");
                                    mCtx.startActivity(intent);
                                    TermikRest.lf( " after start activity cmd 2 ");

                                    break;
                                case 1:
                                    state=0;
                                    intent = new Intent(mCtx, Start.class);

                                    intent.putExtra("SERVER", server_ip);
                                    TermikRest.lf( " before start activity cmd 1 ");
                                    mCtx.startActivity(intent);
                                    TermikRest.lf( " after  start activity cmd 2 ");

                                    break;
                                case 3:
                                    stopped=true;
                                    break;

                            }



                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                            TermikRest.lf( " error1502 : " + e);
                        }
                    }
                    if( dataInputStream!= null)
                    {
                        try
                        {
                            TermikRest.lf( " before dataInputStream closing ");
                            dataInputStream.close();
                            TermikRest.lf( " dataInputStream closed ");
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                            TermikRest.lf( " error1503 : " + e);
                        }
                    }
                    if( dataOutputStream!= null)
                    {
                        try
                        {
                            TermikRest.lf( " before dataOutputStream closing ");
                            dataOutputStream.close();
                            TermikRest.lf( " after dataOutputStream closing ");

                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                            TermikRest.lf( " error1504 : " + e);
                        }
                    }
                }
            }
            serverSocket.close();
            TermikRest.lf( " Server socket closed  ");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            TermikRest.lf( " error1505 : " + e);
        }

        TermikRest.lf( " return 0 socket listening ");

        return 0;


    }
}

