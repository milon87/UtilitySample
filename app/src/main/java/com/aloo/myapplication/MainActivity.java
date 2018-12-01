package com.aloo.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.text.LocaleDisplayNames;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //image view from asset folder
        imageView = findViewById(R.id.imageView);
        try {
            imageView.setImageDrawable(loadDrawableFromAssets(this, "weatherf/weather.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //load json and save as a array
        try {
            JSONArray array = new JSONArray(loadJSONFromAsset());
            Gson gson = new Gson();
            WordItem items[] = gson.fromJson(array.toString(), WordItem[].class);

            for (WordItem item : items) {
                Log.d("Eng", item.getEng());
                Log.d("Dirija", item.getDiraji());

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //play a audio
        playMedia("weather");

    }

    private void playMedia(String fileName) {
        int resID = getResources().getIdentifier("clear", "raw", getPackageName());
        MediaPlayer mediaPlayer = MediaPlayer.create(this, resID);
        mediaPlayer.start();
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("weather.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public Drawable loadDrawableFromAssets(Context context, String path) throws IOException {
        Log.d("path", path);
        // get input stream
        InputStream ims = getAssets().open(path);
        // load image as Drawable
        Drawable d = Drawable.createFromStream(ims, null);
        // set image to ImageView
        return d;
    }


}
