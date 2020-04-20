package dz.souhilazidane.booksrecommender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class PasswordActivity extends AppCompatActivity implements TaskInterface {

    EditText mUserName,mNewPassW,mNewPassConfirmW;
    ImageView mConfirm;
    static ProgressBar progress;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_password);
        mUserName=findViewById(R.id.UsernameID);
        mNewPassW=findViewById(R.id.newPassID);
        mNewPassConfirmW=findViewById(R.id.newPassConfirmID);
        mConfirm=findViewById(R.id.confirmPWbtnID);
        progress=findViewById(R.id.ProgressBar);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUserName.getText().toString().length() != 0) {

                    if (mNewPassW.getText().toString().length() != 0) {
                        if (mNewPassConfirmW.getText().toString().length() != 0) {
                            if (mNewPassW.getText().toString().equals(mNewPassConfirmW.getText().toString())) {
                                //get the new password
                                username=mUserName.getText().toString();
                                password=mNewPassW.getText().toString();
                                URL url=HttpUtils.CreatechangePwURL(username,password);
                                ConnectionTask connection = new ConnectionTask(PasswordActivity.this);
                                connection.execute(url);

                            } else {
                                Toast.makeText(PasswordActivity.this, "Wrong confirmation", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PasswordActivity.this, "Please confirm your new password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PasswordActivity.this, "Please enter your new password", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(PasswordActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void OnFinished(String response) {
        try {
            JSONObject mJSONFile = new JSONObject(response);
            int msg = mJSONFile.getInt("message");
            String message="";
            switch (msg){
                case(1): message="Your password was successfully changed"; break;
                case(0): message="User not found"; break;
                case(-1): message="Something wrong"; break;
            }

            Toast.makeText(PasswordActivity.this, message, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Tag","JSON Prblm");
        }

        onBackPressed();
        //Intent intent = new Intent(PasswordActivity.this,LoginActivity.class);
        //startActivity(intent);
    }
}

