package com.example.backloginsurmountable;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.graphics.Typeface.createFromAsset;

public class BacklogActivity extends AppCompatActivity {
    @Bind(R.id.listView_NESGameList) ListView mListView_NESGameList;
    @Bind(R.id.textView_Completed) TextView mTextView_Completed;
    @Bind(R.id.textView_Remaining) TextView mTextView_Remaining;
    @Bind(R.id.textView_PercentCompleted) TextView mTextView_PercentCompleted;
    @Bind(R.id.textView_CompletedHeader) TextView mTextView_CompletedHeader;
    @Bind(R.id.textView_RemainingHeader) TextView mTextView_RemainingHeader;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    ArrayList<String> mNESGameList = new ArrayList<String>();
    int mNumberOfGames;
    int mRemaining;
    int mCompleted;
    String mPercentCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);
        ButterKnife.bind(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("gamelists/nes_game_list.txt")));
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                mNESGameList.add(mLine);
            }

        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        Typeface erbosDraco = createFromAsset(getAssets(), "fonts/erbosdraco_nova_open_nbp.ttf");
        mTextView_Completed.setTypeface(erbosDraco);
        mTextView_Remaining.setTypeface(erbosDraco);
        mTextView_PercentCompleted.setTypeface(erbosDraco);
        mTextView_CompletedHeader.setTypeface(erbosDraco);
        mTextView_RemainingHeader.setTypeface(erbosDraco);


        ArrayAdapter NESGameListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mNESGameList);
        mListView_NESGameList.setAdapter(NESGameListAdapter);

        mListView_NESGameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView tileView;
                tileView = (TextView) v.findViewById(android.R.id.text1);

                if(!(String.valueOf(tileView.getCurrentTextColor()).equals("-1703936"))){
                    mRemaining--;
                    mCompleted++;
                    tileView.setTextColor(0xffe60000);
                    tileView.setPaintFlags(tileView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    mRemaining++;
                    mCompleted--;
                    tileView.setTextColor(0xff000000);
                    tileView.setPaintFlags( tileView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
                mTextView_Completed.setText(String.valueOf(mCompleted));
                mTextView_Remaining.setText(String.valueOf(mRemaining));

                mPercentCompleted = String.valueOf(String.format( "%.2f", ((double) mCompleted / (double) mRemaining) * 100))+ "%";
                mTextView_PercentCompleted.setText(String.valueOf(mPercentCompleted));
            }
        });

        mNumberOfGames = mNESGameList.size();
        mRemaining = mNumberOfGames;
        mCompleted = 0;
        mPercentCompleted = "0%";

        mTextView_Completed.setText(String.valueOf(mCompleted));
        mTextView_Remaining.setText(String.valueOf(mRemaining));
        mTextView_PercentCompleted.setText(String.valueOf(mPercentCompleted));
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Backlog Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.backloginsurmountable/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Backlog Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.backloginsurmountable/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
