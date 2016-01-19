package edu.upc.eetac.dsa.rate;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import edu.upc.eetac.dsa.rate.client.RateClient;
import edu.upc.eetac.dsa.rate.client.RateClientException;
import edu.upc.eetac.dsa.rate.client.entity.Game;

public class GameDetailActivity extends AppCompatActivity {
GetGameTask mGetGameTask = null;
    String uri = null;
    String id = null;
    String name = null;
    String genre = null;
    Long creation = null;
    String screation = null;
    Button btReview = null;
    Button btScore = null;
    Button btRevList = null;
    static final int reqnum = 2;
    static final int reqnum2 = 3;
    private final static String TAG = GameDetailActivity.class.toString();
    TextView textViewName = null;
    TextView textViewGenre = null;
    TextView textViewCreation = null;

    class GetGameTask extends AsyncTask<Void, Void, String> {
        private String uri;

        public GetGameTask(String uri) {
            this.uri = uri;

        }

        @Override
        protected String doInBackground(Void... params) {
            String jsonGame = null;
            try {
                jsonGame = RateClient.getInstance().getGame(uri);
            } catch (RateClientException e) {
                // TODO: Handle gracefully
                Log.d(TAG, e.getMessage());
            }
            return jsonGame;
        }

        @Override
        protected void onPostExecute(String jsonGame) {
            Log.d(TAG, jsonGame);
            Game game = (new Gson()).fromJson(jsonGame, Game.class);
            id = game.getId();
            name = game.getName();
            genre = game.getGenre();
            creation = game.getCreationTimestamp();
            screation = String.valueOf(creation);
            textViewName.setText(name);
            textViewGenre.setText(genre);
            textViewCreation.setText(screation);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        uri = (String) getIntent().getExtras().get("uri");

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewGenre = (TextView) findViewById(R.id.textViewGenre);
        textViewCreation = (TextView) findViewById(R.id.textViewCreation);

        // Execute AsyncTask
        mGetGameTask = new GetGameTask(uri);
        mGetGameTask.execute((Void) null);

        btReview = (Button) findViewById(R.id.btReview);

        btReview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameDetailActivity.this, ReviewCreateActivity.class);
                intent.putExtra("gameid", id);
                startActivityForResult(intent, reqnum);

            }

        });

        btScore = (Button) findViewById(R.id.btScore);

        btScore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameDetailActivity.this, ScoreCreateActivity.class);
                intent.putExtra("gameid", id);
                startActivityForResult(intent, reqnum2);

            }

        });


        btRevList = (Button) findViewById(R.id.btRevList);

        btRevList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameDetailActivity.this, ReviewListActivity.class);
                startActivity(intent);

            }

        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == reqnum) {

            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Reseña guardada");
            }

            if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "no se ha creado la reseña");
            }
        }

        if (requestCode == reqnum2) {

            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Puntuación guardada");
            }

            if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "no se ha guardado la puntuación");
            }
        }
    }
}