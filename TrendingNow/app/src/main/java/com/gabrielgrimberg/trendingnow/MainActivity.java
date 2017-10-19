package com.gabrielgrimberg.trendingnow;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    private ListView listApps;
    private String feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
    private int feedLimit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listApps = (ListView) findViewById(R.id.xmlListView);

        downloadUrl("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.feeds_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        String feedUrl;

        switch(id)
        {
            case R.id.menuFree:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml";
                break;

            case R.id.MenuPaid:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=10/xml";
                break;

            case R.id.menuSongs:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml";
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        downloadUrl(feedUrl);
        return true;
    }

    private void downloadUrl(String feedUrl)
    {
        Log.d(TAG, "downloadUrl: Starting Asynctask");
        DownloadData downloadData = new DownloadData();

        //Link to the data of the RSS.
        downloadData.execute(feedUrl);

        Log.d(TAG, "downloadUrl: done");
    }

    private class DownloadData extends AsyncTask<String, Void, String>
    {
        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
//            Log.d(TAG, "onPostExecute: parameter is " + s);
            AppParser parseApplications = new AppParser();
            parseApplications.parse(s);

            //Sending through to the array adapter.
//            ArrayAdapter<FeedContainer> arrayAdapter = new ArrayAdapter<FeedContainer>(
//                    MainActivity.this,  //Pass in instance of main activity.
//                    R.layout.list_item,  //Resource containing the text view that the array adapter will put the data into.
//                    parseApplications.getApps() ); //List of objects to dispaly.
//
//            listApps.setAdapter(arrayAdapter);

            FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this, R.layout.list_record, parseApplications.getApps());

            listApps.setAdapter(feedAdapter);

        }

        @Override
        protected String doInBackground(String... strings)
        {
            //Debug level.
            Log.d(TAG, "doInBackground: starts with " + strings[0]);

            String rssFeed = downloadXML(strings[0]);

            if(rssFeed == null)
            {
                //Error handling.
                Log.e(TAG, "doInBackground: Error downloading");
            }

            return rssFeed;
        }

        //Opening HTTP data. Method to read the data. Buffered Reader.
        private String downloadXML(String urlPath)
        {
            StringBuilder xmlResult = new StringBuilder();

            try
            {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was " + response);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char[] inputBuffer = new char[500];

                //Loop while end of input string reached.
                while(true)
                {
                    charsRead = reader.read(inputBuffer);

                    //Signals end of string data.
                    if(charsRead < 0)
                    {
                        break;
                    }

                    //Number of characters read from the string.
                    //Check if there is something to read.
                    if(charsRead > 0)
                    {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }

                //To auto close the input stream reader and the input stream.
                reader.close();

                return xmlResult.toString();

            }
            catch(MalformedURLException e)
            {
                //Invalid link error.
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            }
            catch(IOException e)
            {
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            }
            catch(SecurityException e)
            {
                //Connection to internet.
                Log.e(TAG, "downloadXML: Security Exception.  Needs permisson? " + e.getMessage());
            }

            return null;
        }
    }
}