package dz.souhilazidane.booksrecommender;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {


    public static URL CreateLoginURL(String username, String password) {

        Uri uri =Uri.parse("http://"+UserActivity.ip+"/android_connect/loginUser.php")
                .buildUpon().appendQueryParameter("pseudo",username).appendQueryParameter("password",password)
                .build();

        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static URL CreateLoginURL(String url) {

        Uri uri =Uri.parse(url);

        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static URL CreateSignUpURL(String famillyname,String firstname,String password,String year, String specialty){
        Uri uri = Uri.parse("http://"+UserActivity.ip+"/android_connect/SignUp.php")
                .buildUpon().appendQueryParameter("famillyname",famillyname)
                .appendQueryParameter("firstname",firstname)
                .appendQueryParameter("password",password)
                .appendQueryParameter("year",year)
                .appendQueryParameter("specialty",specialty)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URL CreatechangePwURL(String username, String password) {
        Uri uri =Uri.parse("http://"+UserActivity.ip+"/android_connect/Change_password.php")
                .buildUpon().appendQueryParameter("pseudo",username).appendQueryParameter("password",password)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("Tag","creation URL Prblm");
        }
        return null;
    }

    public static String GetData(URL url) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();


        try {

            Response response = client.newCall(request).execute();
            return response.body().string();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
