package com.gabrielgrimberg.trendingnow;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    private static  final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Starting AsyncTask");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("URL in here.");
        Log.d(TAG, "onCreate: done");

    }

    private class DownloadData extends AsyncTask<String, Void, String>
    {
        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: Parameter is " + s);
        }

        @Override
        protected String doInBackground(String... strings)
        {
            //Debug level.
            Log.d(TAG, "doInBackground: Starts with " + strings[0]);

            String rssFeed = downloadXML(strings[0]);

            if(rssFeed == null)
            {
                //Error handling.
                Log.e(TAG, "doInBackground: Error downloading.");
            }

            return rssFeed;
        }

        //Opening HTTP data. Method to read the data. Buffered Reader.
        private String downloadXML(String urlPath)
        {
            StringBuilder xmlResults = new StringBuilder();

            try
            {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was: " + response);

//              InputStream inputStream = connection.getInputStream();
//              InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//              BufferedReader reader = new BufferedReader(inputStreamReader);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream() ) );

                int charsRead;
                char[] inputBuffer = new char[500];

                //Loop while end of input string reached.
                while(true)
                {
                    charsRead = reader.read(inputBuffer);

                    //Singals end of the string data.
                    if(charsRead < 0)
                    {
                        break;
                    }

                    //Number of characters read from the string.
                    //Check if there is something to read.
                    if(charsRead > 0)
                    {
                        xmlResults.append(String.copyValueOf(inputBuffer, 0, charsRead) );
                    }
                }
                reader.close();
            }
            catch(MalformedURLException e)
            {
                Log.e(TAG, "downloadXML: Invalid URL. " + e.getMessage()); //Invalid link error.
            }
            catch(IOException e)
            {
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            }
        }

    }
}






