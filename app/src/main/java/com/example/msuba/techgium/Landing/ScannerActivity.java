package com.example.msuba.techgium.Landing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.msuba.techgium.R;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.net.FileNameMap;

/**
 * Created by msuba on 11/30/2017.
 */

public class ScannerActivity extends Activity {
    SurfaceView camPrev;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_cam);
        camPrev = (SurfaceView) findViewById(R.id.camview);
        DetectBarCode();
        DetectText();

    }
    private void DetectBarCode()
    {
        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();



        camPrev.getHolder().addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("MissingPermission")
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                        cameraSource.start(camPrev.getHolder());
                        return;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodeSparseArray = detections.getDetectedItems();
                Intent i = new Intent();
                i.putExtra("barcode",barcodeSparseArray.valueAt(0));
                setResult(CommonStatusCodes.SUCCESS,i);
                finish();
            }
        });
    }
    private void DetectText()
    {
         final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
         final CameraSource cameraSource = new CameraSource.Builder(getApplicationContext(),textRecognizer).build();




        camPrev.getHolder().addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("MissingPermission")
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    cameraSource.start(camPrev.getHolder());
                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections)
            {
                final SparseArray<TextBlock> textSparseArray = detections.getDetectedItems();
                for (int i=0;i<=textSparseArray.size();i++)
                {
                        TextBlock tb = textSparseArray.get(i);
                        if (!tb.getValue().equals(null))
                            System.out.println("$$$$$ TEXT RESULT ARE "+tb.getValue());
                }


            }
        });

    }

}
