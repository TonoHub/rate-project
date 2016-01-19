package edu.upc.eetac.dsa.rate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import edu.upc.eetac.dsa.rate.client.RateClient;
import edu.upc.eetac.dsa.rate.client.RateClientException;
import edu.upc.eetac.dsa.rate.client.entity.Game;
import edu.upc.eetac.dsa.rate.client.entity.GameCollection;

public class GameListActivity extends AppCompatActivity {

    private final static String TAG = GameListActivity.class.toString();
    private GetGamesTask mGetGamesTask = null;
    private GameCollection games = new GameCollection();
    private GameCollectionAdapter adapter = null;

    class GetGamesTask extends AsyncTask<Void, Void, String> {
        private String uri;

        public GetGamesTask(String uri) {
            this.uri = uri;

        }

        @Override
        protected String doInBackground(Void... params) {
            String jsonGameCollection = null;
            try {
                jsonGameCollection = RateClient.getInstance().getGames(uri);
            } catch (RateClientException e) {
                // TODO: Handle gracefully
                Log.d(TAG, e.getMessage());
            }
            return jsonGameCollection;
        }

        @Override
        protected void onPostExecute(String jsonGameCollection) {
            Log.d(TAG, jsonGameCollection);
            GameCollection gameCollection = (new Gson()).fromJson(jsonGameCollection, GameCollection.class);
            for(Game game : gameCollection.getGames()){
                games.getGames().add(games.getGames().size(), game);
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Execute AsyncTask
        mGetGamesTask = new GetGamesTask(null);
        mGetGamesTask.execute((Void) null);

        // set list adapter
        ListView list = (ListView)findViewById(R.id.list);
        GameCollectionAdapter adapter = new GameCollectionAdapter(this, games);
        list.setAdapter(adapter);

        // set list OnItemClick listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GameListActivity.this, GameDetailActivity.class);
                String uri = RateClient.getLink(games.getGames().get(position).getLinks(), "self").getUri().toString();
                intent.putExtra("uri", uri);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Selecciona cualquiera de estos juegos para verlos en detalle", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

}
