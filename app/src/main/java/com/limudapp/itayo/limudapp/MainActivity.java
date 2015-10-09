package com.limudapp.itayo.limudapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaActionSound;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Boolean bIsDownloadSuccess = false;
    private String strCurrentDaily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        strCurrentDaily = Calendar.getInstance().getTime().toString();
        File flCurrentLimud = new File(strCurrentDaily);

        // Check if the current daily Limud is not downloaded
        if (!flCurrentLimud.exists()) {
            DownloadLimud dlTask = new DownloadLimud();
            dlTask.execute();
        } else {
            bIsDownloadSuccess = true;
        }

        Bitmap bmpImageBitmap =
                BitmapFactory.decodeFile(MainActivity.this.getFilesDir().getPath() +
                                         "/" +
                                         strCurrentDaily);
        TextView txvAlert = (TextView) findViewById(R.id.txvAlert);

        // If the download success
        if (bIsDownloadSuccess) {
            // Set up the Limud image
            ImageView imgLimudImage = (ImageView) findViewById(R.id.imgLimud);
            imgLimudImage.setImageBitmap(bmpImageBitmap);
            txvAlert.setVisibility(View.INVISIBLE);
        } else {
            txvAlert.setVisibility(View.VISIBLE);
        }
    }

    // This private class is an AsyncTask class, in order to download the image of the daily Limud
    private class DownloadLimud extends AsyncTask<Void, Void, Boolean> {

        private ProgressDialog dialog;

        // Triggered before the real action - doInBackground - happen
        protected void onPreExecute() {
            super.onPreExecute();

            // Shows the loading dialog
            this.dialog = new ProgressDialog(MainActivity.this);
            this.dialog.setMessage("טוען את הלימוד...");
            this.dialog.setCancelable(false);
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.show();
        }

        // The real action- download the Limud image
        @Override
        protected Boolean doInBackground(Void... params) {
            return downloadDailyLimud();
        }

        // The function download and save the current daily Limud
        // Return true- if the download and the save success, else- false
        private Boolean downloadDailyLimud () {
            // Download the current daily Limud
            /***** HERE YOU DOWNLOAD THE IMAGE FROM THE SERVER *****/

            // Save the image to internal storage
            /* To save the picture,
               **** see---> http://developer.android.com/training/basics/data-storage/files.html
            */

            // Set the global strCurrentDaily String to the file name

            // Return true- if the download and the save success, else- false
            return false;
        }

        // Triggered after the real action done
        protected void onPostExecute(Boolean bSuccess) {
            super.onPostExecute(bSuccess);

            // Cancles the loading dialog
            this.dialog.cancel();

            // If the image download from the server failed
            if (!bSuccess) {
                // Shows alert to describe that there was a problem to download the daily Limud
                Toast tstFailedAlert =
                        Toast.makeText(MainActivity.this,
                                       "הלימוד היומי לא הורד בשל תקלת אינטרנט!",
                                       Toast.LENGTH_LONG);
                tstFailedAlert.show();
            } else {
                // Change the global success boolean
                bIsDownloadSuccess = true;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
