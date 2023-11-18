package com.example.paint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Random;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.content.ContentValues;
import android.content.ContentResolver;
import java.io.OutputStream;
import java.io.IOException;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    MyCanvas myCanvas;
    Button btn_save;
    Button btn_clear;
    Button btn_colors;
    FrameLayout frameLayout;
    Button red;
    Button black;
    Button green;
    Button yellow;
    Button blue;
    Button white;
    LinearLayout ll;
    LinearLayout ll2;
    LinearLayout.LayoutParams lp;
    boolean added_buttons;
    boolean added_brushes;
    Button btn_brushes;
    Button brush1;
    Button brush2;
    Button brush3;
    Button brush4;
    Button brush5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCanvas = new MyCanvas(this, null);
        frameLayout = (FrameLayout)findViewById(R.id.mainframe);
        frameLayout.addView(myCanvas);

        btn_save = (Button) findViewById(R.id.button_save);
        btn_save.setOnClickListener(this);

        btn_clear = (Button) findViewById(R.id.button_clear);
        btn_clear.setOnClickListener(this);

        btn_colors = (Button) findViewById(R.id.button_colors);
        btn_colors.setOnClickListener(this);

        red = new Button(this);
        red.setBackgroundColor(Color.RED);
        red.setOnClickListener(this);

        black = new Button(this);
        black.setBackgroundColor(Color.BLACK);
        black.setOnClickListener(this);

        green = new Button(this);
        green.setBackgroundColor(Color.GREEN);
        green.setOnClickListener(this);

        yellow = new Button(this);
        yellow.setBackgroundColor(Color.YELLOW);
        yellow.setOnClickListener(this);

        blue = new Button(this);
        blue.setBackgroundColor(Color.BLUE);
        blue.setOnClickListener(this);

        white = new Button(this);
        white.setBackgroundColor(Color.WHITE);
        white.setText("WHITE");
        white.setOnClickListener(this);

        btn_brushes = (Button) findViewById(R.id.button_brushes);
        btn_brushes.setOnClickListener(this);

        brush1  = new Button(this);
        brush1.setText("Level 1");
        brush1.setOnClickListener(this);

        brush2  = new Button(this);
        brush2.setText("Level 2");
        brush2.setOnClickListener(this);

        brush3  = new Button(this);
        brush3.setText("Level 3");
        brush3.setOnClickListener(this);

        brush4  = new Button(this);
        brush4.setText("Level 4");
        brush4.setOnClickListener(this);

        brush5  = new Button(this);
        brush5.setText("Level 5");
        brush5.setOnClickListener(this);

        ll = (LinearLayout)findViewById(R.id.colors_layout);
        ll2 = (LinearLayout)findViewById(R.id.brushes_layout);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        added_buttons = false;
        added_brushes = false;
    }

    @Override
    public void onClick(View v) {
        if (v == btn_clear)
        {
            frameLayout.removeAllViews();
            myCanvas = new MyCanvas(this, null);
            FrameLayout frameLayout = (FrameLayout)findViewById(R.id.mainframe);
            frameLayout.addView(myCanvas);
        }
        else if (v == btn_colors)
        {
            if (!added_buttons) {
                ll.addView(red, lp);
                ll.addView(black, lp);
                ll.addView(green, lp);
                ll.addView(yellow, lp);
                ll.addView(blue, lp);
                ll.addView(white, lp);
            }
            else
            {
                ll.removeView(red);
                ll.removeView(black);
                ll.removeView(green);
                ll.removeView(yellow);
                ll.removeView(blue);
                ll.removeView(white);
            }
            added_buttons = !added_buttons;
        }

        else if(v == red || v == black || v == green | v == yellow || v == blue || v == white)
        {
            String color;
            if (v == red)
                color = "red";
            else if (v == black)
                color = "black";
            else if (v == green)
                color = "green";
            else if (v == yellow)
                color = "yellow";
            else if (v == blue)
                color = "blue";
            else
                color = "white";

            myCanvas.changeColor(color);

            ll.removeView(red);
            ll.removeView(black);
            ll.removeView(green);
            ll.removeView(yellow);
            ll.removeView(blue);
            ll.removeView(white);
            added_buttons = false;
        }

        else if (v == btn_brushes)
        {
            if (!added_brushes) {
                ll2.addView(brush1, lp);
                ll2.addView(brush2, lp);
                ll2.addView(brush3, lp);
                ll2.addView(brush4, lp);
                ll2.addView(brush5, lp);
            }
            else
            {
                ll2.removeView(brush1);
                ll2.removeView(brush2);
                ll2.removeView(brush3);
                ll2.removeView(brush4);
                ll2.removeView(brush5);

            }
            added_brushes = !added_brushes;
        }
        else if(v == brush1 || v == brush2 || v == brush3 | v == brush4 || v == brush5)
        {
            int level;
            if (v == brush1)
                level = 1;
            else if (v == brush2)
                level = 2;
            else if (v == brush3)
                level = 3;
            else if (v == brush4)
                level = 4;
            else
                level = 5;


            myCanvas.changeBrush(level);

            ll2.removeView(brush1);
            ll2.removeView(brush2);
            ll2.removeView(brush3);
            ll2.removeView(brush4);
            ll2.removeView(brush5);
            added_brushes = false;
        }


        else if(v == btn_save)
        {
            takeScreenshot();
        }

    }

    public void takeScreenshot(){
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        // image naming and path  to include sd card  appending name you choose for file
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        ContentResolver resolver = getContentResolver();
        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        try {
            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            if (imageUri != null) {
                OutputStream outputStream = resolver.openOutputStream(imageUri);
                // Write your image to the output stream
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
            }

            //openScreenshot(imageFile);
        }
        catch (IOException e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }

    }

   /* public void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    } */
    public void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }
}
