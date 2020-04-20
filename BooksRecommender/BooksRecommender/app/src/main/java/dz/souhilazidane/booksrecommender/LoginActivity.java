package dz.souhilazidane.booksrecommender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class LoginActivity extends AppCompatActivity implements TaskInterface {

    EditText Username,Password;
    static ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        progress = (ProgressBar)findViewById(R.id.progress);

    }

    @Override
    public void OnFinished(String response) {

        try {
            JSONObject mJSONFile =new JSONObject(response);
            int msg = mJSONFile.getInt("message");
            String id_user="",username="",first_name="",family_name="",year="",field="";



            String message;

            switch (msg){
                case (0) :
                    message="You're not registred!";
                    break;
                case (1) :
                    id_user = mJSONFile.getString("id_user");
                    username = mJSONFile.getString("pseudo");
                    first_name = mJSONFile.getString("first_name");
                    family_name = mJSONFile.getString("family_name");
                    year = mJSONFile.getString("year");
                    field = mJSONFile.getString("field");

                    message="Welcome "+first_name+"!";
                    Intent intent = new Intent(this,UserActivity.class);
                    intent.putExtra(UserActivity.EXTRA_USER_ID,id_user);
                    intent.putExtra(UserActivity.EXTRA_STUDENT_USERNAME,username);
                    intent.putExtra(UserActivity.EXTRA_STUDENT_1NAME,first_name);
                    intent.putExtra(UserActivity.EXTRA_STUDENT_2NAME,family_name);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    break;
                case (2) :
                    message="Wrong password!";
                    break;
                default:
                    message="Internet missing";

            }

            Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onLogin(View view) {
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);

        if (Username.getText().toString().length() != 0) {
            if(Password.getText().toString().length() != 0) {

                String username = Username.getText().toString();
                String password = Password.getText().toString();


                URL url = HttpUtils.CreateLoginURL(username,password);
                new ConnectionTask(LoginActivity.this).execute(url);

            } else {
                Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSigup(View view) {
        Intent intent = new Intent(LoginActivity.this, SignActivity.class);
        startActivity(intent);
    }

    public void onForget(View view) {
        Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
        startActivity(intent);
    }

}

