package com.example.msuba.techgium.Landing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msuba.techgium.R;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class Base extends AppCompatActivity
{
    Button detectbut;
    TextView restext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        detectbut = (Button)findViewById(R.id.detectbut);
        restext = (TextView)findViewById(R.id.restext);
        detectbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ScannerActivity.class);
                startActivityForResult(i,0);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0)
        {
            if (resultCode== CommonStatusCodes.SUCCESS)
            {
                if(data!=null){
                    Barcode barcode = data.getParcelableExtra("barcode");
                    System.out.println("$$$$$$$$ RESULT ARE "+barcode.displayValue);
                    restext.setText(barcode.displayValue);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
