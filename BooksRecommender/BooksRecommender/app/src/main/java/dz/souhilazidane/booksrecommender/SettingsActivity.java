package dz.souhilazidane.booksrecommender;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class SettingsActivity extends AppCompatActivity implements TaskInterface {

    LinearLayout layout;
    Switch notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        layout = (LinearLayout)findViewById(R.id.layout);
        notification = (Switch)findViewById(R.id.notification);

        URL url = HttpUtils.CreateLoginURL("http://"+UserActivity.ip+"/android_connect/get_notification.php?id_user="+UserActivity.id);
        new ConnectionTask(SettingsActivity.this).execute(url);


        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //update database notification=1
                    URL url = HttpUtils.CreateLoginURL("http://"+UserActivity.ip+"/android_connect/set_notification.php?id_user="+UserActivity.id+"&notification=1");
                    new ConnectionTask(SettingsActivity.this).execute(url);

                } else {
                    //update database notification=0
                    URL url = HttpUtils.CreateLoginURL("http://"+UserActivity.ip+"/android_connect/set_notification.php?id_user="+UserActivity.id+"&notification=0");
                    new ConnectionTask(SettingsActivity.this).execute(url);
                }
            }
        });

    }


    @Override
    public void OnFinished(String response) {

        try {
            JSONObject mJSONFile =new JSONObject(response);
            String notify=mJSONFile.getString("notification");

            if(notify.equals("1")) {
                notification.setChecked(true);
            } else {
                notification.setChecked(false);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //up navigation doing the same thing as back navigation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onModify(View view) {
        layout.setVisibility(View.VISIBLE);
    }

    public void onDone(View view) {
        Toast.makeText(this,"password modified successfully!",Toast.LENGTH_SHORT).show();
    }

}
