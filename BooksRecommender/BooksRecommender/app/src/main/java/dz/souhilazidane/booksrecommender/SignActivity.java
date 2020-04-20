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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class SignActivity extends AppCompatActivity implements TaskInterface{
    ImageView mSignUp;
    EditText mFirstName,mFamilyName, mPassword, mConfirmPassword;
    RadioGroup mYear,mSpecialty;
    RadioButton L1,L2,L3,M1,M2,GL,GI,RT;
    LinearLayout SpecialtyLL;
    String FirstName,FamilyName,Password, Year,Specialty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign);
        setViews();
    }


    public void setViews(){
        mSignUp= findViewById(R.id.SignUpIdSU);
        mFamilyName=findViewById(R.id.FMEditIdSU);
        mFirstName=findViewById(R.id.FREditIdSU);
        mPassword=findViewById(R.id.PasswordEditIdSU);
        mConfirmPassword=findViewById(R.id.PasswordConfirmEditIdSU);
        SpecialtyLL = findViewById(R.id.LinearSpecialtyId);
        mYear=findViewById(R.id.YearRadioGroupe);
        mSpecialty=findViewById(R.id.SpecialtyRadioGroupe);
        L1=findViewById(R.id.L1RadioBtn);
        L2=findViewById(R.id.L2RadioBtn);
        L3=findViewById(R.id.L3RadioBtn);
        M1=findViewById(R.id.M1RadioBtn);
        M2=findViewById(R.id.M2RadioBtn);
        GL=findViewById(R.id.GlRadioBtn);
        GI=findViewById(R.id.GIRadioBtn);
        RT=findViewById(R.id.RTRadioBtn);


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFamilyName.getText().toString().length()!=0){ //test if the user taped the family name
                    if(mFirstName.getText().toString().length()!=0){ //test if the user taped the first name
                        if (mPassword.getText().toString().length()!=0){//test if the user taped the password
                            if(mConfirmPassword.getText().toString().length()!=0){ //test if the user confirmed the password
                                if(mPassword.getText().toString().equals( mConfirmPassword.getText().toString())){ //testing the confirmation
                                    FamilyName=mFamilyName.getText().toString().trim();
                                    FirstName=mFirstName.getText().toString().trim();
                                    Password=mPassword.getText().toString().trim();
                                    if(mYear.getCheckedRadioButtonId() != -1) {
                                        int selectedYearId = mYear.getCheckedRadioButtonId();
                                        RadioButton radioButtonY = (RadioButton) findViewById(selectedYearId);
                                        String RadioTextY = radioButtonY.getText().toString();
                                        switch (RadioTextY) {
                                            case "L1":
                                                Year = "L1";
                                                Specialty="MI";
                                                break;
                                            case "L2":
                                                Year = "L2";
                                                Specialty="I";
                                                break;
                                            case "L3":
                                                Year = "L3";
                                                Specialty="I";
                                                break;
                                            case "M1":
                                                Year = "M1";
                                                break;
                                            case "M2":
                                                Year = "M2";
                                                break;
                                        }
                                        if (selectedYearId == M1.getId() || selectedYearId == M2.getId()) {
                                            if(mSpecialty.getCheckedRadioButtonId() != -1) {
                                                int selectedSpecId = mSpecialty.getCheckedRadioButtonId();
                                                RadioButton radioButtonS = (RadioButton) findViewById(selectedSpecId);
                                                String RadioTextS = radioButtonS.getText().toString();
                                                switch (RadioTextS) {
                                                    case "GL":
                                                        Specialty = "GL";
                                                        break;
                                                    case "GI":
                                                        Specialty = "GI";
                                                        break;
                                                    case "RT":
                                                        Specialty = "RT";
                                                        break;
                                                }
                                            }else {
                                                Toast.makeText(SignActivity.this,"Please choose your specialty",Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        //getting all student data

                                        URL url=HttpUtils.CreateSignUpURL(FamilyName,FirstName,Password,Year,Specialty); //create SignUp url
                                        ConnectionTask connection= new ConnectionTask(SignActivity.this);
                                        connection.execute(url);



                                    }else {
                                        Toast.makeText(SignActivity.this,"Please choose the year",Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(SignActivity.this,"Wrong confirmation",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(SignActivity.this,"Please confirm your password",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SignActivity.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(SignActivity.this,"Please enter your first name",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SignActivity.this,"Please enter your family name",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.M1RadioBtn:
                SpecialtyLL.setVisibility(View.VISIBLE);
                break;
            case R.id.M2RadioBtn:
                SpecialtyLL.setVisibility(View.VISIBLE);
                break;
            default:
                SpecialtyLL.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnFinished(String response) {
        try {
            JSONObject mJSONFile =new JSONObject(response);
            int msg = mJSONFile.getInt("message");
            String message;

            switch (msg){
                case (0) :
                    message="Existing Username, choose another one ";
                    break;
                case (1) :
                    message="welcome "+FirstName+"!";
                    Intent intent = new Intent(SignActivity.this,UserActivity.class);
                    intent.putExtra(UserActivity.EXTRA_STUDENT_USERNAME,FirstName+"_"+FamilyName);
                    intent.putExtra(UserActivity.EXTRA_STUDENT_1NAME,FirstName);
                    intent.putExtra(UserActivity.EXTRA_STUDENT_2NAME,FamilyName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    break;
                default:
                    message="error";

            }

            Toast.makeText(SignActivity.this,message,Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Tag", "Json  prblm");
        }

    }


}
