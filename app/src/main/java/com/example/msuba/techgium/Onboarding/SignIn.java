package com.example.msuba.techgium.Onboarding;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.msuba.techgium.Adapter.UserCreds;
import com.example.msuba.techgium.Landing.Base;
import com.example.msuba.techgium.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText uname,pass;
    Button logbut,regbut;
    String Uname, Pass;
    Firebase fb_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Firebase.setAndroidContext(getApplicationContext());
        fb_db = new Firebase("https://techgiu-f18ca.firebaseio.com/UserCreds/");
        uname = (EditText)findViewById(R.id.Uname);
        pass = (EditText)findViewById(R.id.pass);
        logbut = (Button)findViewById(R.id.logbut);
        regbut = (Button)findViewById(R.id.regbut);

        regbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });
        logbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uname = uname.getText().toString();
                Pass = pass.getText().toString();
                new MyTask().execute();
            }
        });

    }
    public class MyTask extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
            fb_db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        System.out.println("KEYS ARE "+postSnapshot.getKey());
                        if(Uname.equals(postSnapshot.getKey()))
                        {
                            UserCreds userCreds = postSnapshot.getValue(UserCreds.class);
                            if(userCreds.getUname().equals(Uname)&&userCreds.getUname().equals(Pass))
                            {
                                Toast.makeText(getApplicationContext(),"Welcome "+Uname,Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(),Base.class));
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            return null;
        }
    }
}
