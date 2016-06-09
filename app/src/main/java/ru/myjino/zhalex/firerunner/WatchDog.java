package ru.myjino.zhalex.firerunner;

/**
 * Created by san on 02.08.2015.
 */



import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by san on 02.08.2015.
 */
public class WatchDog extends TimerTask {

    private static String _url="";
    private static String _uid="";

    public static Timer mTimer = new Timer();
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static JsonHttpResponseHandler responseHandler=new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        }
    };


    private static void get_settings() {
        if(TextUtils.isEmpty(_url))
            try {
                String s= Environment.getExternalStorageDirectory().getAbsolutePath();

                String     fileName =  s + File.separator + "watch_dog.txt";



                FileInputStream fStream = new FileInputStream(fileName);
                InputStreamReader iReader = new InputStreamReader(fStream, "windows-1251");
                BufferedReader bReader = new BufferedReader(iReader);

                _url = bReader.readLine();




            } catch (Throwable t) {
                Log.v("EXCPPP",
                        "Exception: " + t.toString());

            }

        if(TextUtils.isEmpty(_uid))
            try {
                String s= Environment.getExternalStorageDirectory().getAbsolutePath();

                String     fileName =  s + File.separator + "uid.txt";



                FileInputStream fStream = new FileInputStream(fileName);
                InputStreamReader iReader = new InputStreamReader(fStream, "windows-1251");
                BufferedReader bReader = new BufferedReader(iReader);

                _uid = bReader.readLine();




            } catch (Throwable t) {
                Log.v("EXCPPP",
                        "Exception: " + t.toString());

            }


    }




    @Override
    public void run() {
        try {


            get_settings();
            String s=_url+"?carrier_id="+_uid+"&device_id[0]=0&status_id[0]="+(WatchSocket.state>0?1:0);
            client.get(s, null, responseHandler);
            Log.v("VVINET", s);
        }
        catch(Throwable e){
            Log.v("VVINET", e.toString());
        }
    }


}
