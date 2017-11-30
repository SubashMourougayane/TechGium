package com.example.msuba.techgium.Onboarding;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.msuba.techgium.Adapter.UserCreds;
import com.example.msuba.techgium.R;
import com.firebase.client.Firebase;

public class SignUp extends AppCompatActivity {
    Firebase fb_db;
    EditText Email,Pass,Uname;
    Button Reg;
    String Mail,Password,uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Uname = (EditText)findViewById(R.id.Uname);
        Email = (EditText)findViewById(R.id.mail);
        Pass = (EditText)findViewById(R.id.pass);
        Reg = (Button)findViewById(R.id.regbut);
        Firebase.setAndroidContext(getApplicationContext());
        fb_db = new Firebase("https://techgiu-f18ca.firebaseio.com/");
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=0;
                uname = Uname.getText().toString();
                Mail = Email.getText().toString();
                Password = Pass.getText().toString();
                Log.d("USER CREDS",uname);
                Log.d("USER CREDS",Mail);
                Log.d("USER CREDS",Password);

                if (Password.length()<=6)
                {
                    i++;
                    Toast.makeText(getApplicationContext(),"Password must be minimum 6 characters",Toast.LENGTH_SHORT).show();
                }
                else if(Uname.length()<=6)
                {
                    i++;
                    Toast.makeText(getApplicationContext(),"User Name must be minimum 6 characters",Toast.LENGTH_SHORT).show();

                }
                if (i==0)
                {
                    UserCreds userCreds = new UserCreds();
                    userCreds.setUname(uname);
                    userCreds.setMail(Mail);
                    userCreds.setPass(Password);
                    fb_db.child("UserCreds").child(uname).setValue(userCreds);
                }
            }
        });
    }
}
