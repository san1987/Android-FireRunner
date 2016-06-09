package ru.myjino.zhalex.firerunner;

/**
 * Created by san on 27.05.2015.
 */



import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.*;

import org.apache.http.Header;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.FileWriter;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;



/**
 * Created by san on 18.05.2015.
 */
public class TermikRest {


    public static String BASE_URL = "";// "http://zhalex.myjino.ru:/termik/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static String _uuid="";




    public static void lf(  String s){

        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String dateTime = (sdf.format(cal.getTime()));


            String file_dir = Environment.getExternalStorageDirectory().getAbsolutePath();

            String log_num_file = (file_dir + File.separator + "log_num.txt");
            String fname;
            String log_num_txt = "0";
            try {


                FileInputStream fStream = new FileInputStream(log_num_file);
                InputStreamReader iReader = new InputStreamReader(fStream, "windows-1251");
                BufferedReader bReader = new BufferedReader(iReader);

                log_num_txt = bReader.readLine();
            } catch (Throwable t) {
                Log.v("EXCPPP",
                        "Throwable: " + t.toString());

            }


            fname = (file_dir + File.separator + "log" + log_num_txt + ".txt");

            Boolean not_append = true;

            File f = new File(fname);
            if (f.length() > 50 * 1024 * 1024) {
                int i = Integer.parseInt(log_num_txt) + 1;
                if (i > 5) i = 0;
                log_num_txt = String.valueOf(i);
                fname = (file_dir + File.separator + "log" + log_num_txt + ".txt");
                not_append = false;
                try {
                    FileOutputStream fis = new FileOutputStream(new File(log_num_file));
                    fis.write(log_num_txt.getBytes());
                    fis.close();
                } catch (Throwable t) {
                    Log.v("FFERR1", t.getMessage());
                }
            }


            try {
                FileOutputStream fis = new FileOutputStream(new File(fname), not_append);
                fis.write((dateTime + " : " + s + "\n").getBytes());
                fis.close();
            } catch (Throwable t) {
                Log.v("FFERR1", t.getMessage());
            }


        }catch(Throwable e){

        }


    }




    public static String file_uuid(Context ctx, String fileName){
        try {


            FileInputStream fStream = new FileInputStream(fileName);
            InputStreamReader iReader = new InputStreamReader(fStream, "windows-1251");
            BufferedReader bReader = new BufferedReader(iReader);

            String fileLine = bReader.readLine();

            return fileLine;



        } catch (Throwable t) {
            Log.v("EXCPPP",
                    "Throwable: " + t.toString());

        }
        return "";

    }


    public static String uuid(Context ctx) {
        try{

        if(!TextUtils.isEmpty(_uuid)) return _uuid;

        String s= Environment.getExternalStorageDirectory().getAbsolutePath();

        _uuid=file_uuid(ctx, s+ File.separator + "uid.txt");

        }catch(Throwable e){

        }

        return _uuid;

        ////////////////////////////////////////
        /*

        String identifier = "";
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if(tm!=null)
            identifier=tm.getDeviceId();
        identifier+= Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);

        _uuid=identifier;

        return  _uuid;
        */

    }

    public static void get(Context ctx, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        try{
        String absoluteUrl=getAbsoluteUrl(ctx, url);
        if (responseHandler==null) responseHandler=new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            }

            @Override
            public void onFailure (int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }


        };
        Log.v("INET", absoluteUrl);
        TermikRest.lf("  get before " + absoluteUrl);
        client.get(absoluteUrl, params, responseHandler);
        TermikRest.lf("  get after " + absoluteUrl);
        }catch(Throwable e){

        }
    }

    public static void post(Context ctx, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        try{
            String absoluteUrl=getAbsoluteUrl(ctx, url);
            TermikRest.lf("  post before " + absoluteUrl);
            client.post(absoluteUrl, params, responseHandler);
            TermikRest.lf("  post a " + absoluteUrl);
        }catch(Throwable e){

        }
    }

    private static String getAbsoluteUrl(Context ctx,String relativeUrl) {
        try{
            uuid(ctx);
            Log.v("IINET", "uuid="+_uuid);
        }catch(Throwable e){

        }
        return BASE_URL+":3000" +"/"+_uuid + relativeUrl;
    }

}
