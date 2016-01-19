package edu.upc.eetac.dsa.rate;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.ws.rs.core.Form;

import edu.upc.eetac.dsa.rate.client.RateClient;
import edu.upc.eetac.dsa.rate.client.RateClientException;

public class GameCreateActivity extends AppCompatActivity {

    EditText etName = null;
    EditText etGenre = null;
    EditText etYear = null;
    Button btCreate = null;
    private CreateGameTask mCreateGameTask = null;
    private final static String TAG = GameCreateActivity.class.toString();


    class CreateGameTask extends AsyncTask<Void, Void, Boolean> {
        private Form form;

        public CreateGameTask(Form form) {
            this.form = form;

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean result = false;
            try {
                result = RateClient.getInstance().CreateGame(form);

            } catch (RateClientException e) {
                // TODO: Handle gracefully
                Log.d(TAG, e.getMessage());
            }

            return result;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if (result == true) {
                Intent i = getIntent();
                setResult(RESULT_OK, i);
                finish();
            }

            if (result == false) {
                Intent i = getIntent();
                setResult(RESULT_CANCELED, i);
                finish();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_create);
        etName = (EditText) findViewById(R.id.Name);
        etGenre = (EditText) findViewById(R.id.Genre);
        etYear = (EditText) findViewById(R.id.Year);
        btCreate = (Button) findViewById(R.id.Create);

        btCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (etName.getText().length() != 0 && etGenre.getText().length() != 0 && etYear.getText().length() != 0) {
                    String name = etName.getText().toString();
                    String genre = etGenre.getText().toString();
                    String year = etYear.getText().toString();
                    Form form = new Form();
                    form.param("name", name);
                    form.param("genre", genre);
                    form.param("year", year);

                    // Execute AsyncTask
                    mCreateGameTask = new CreateGameTask(form);
                    mCreateGameTask.execute((Void) null);
                } else {
                    Log.d(TAG, "Debes escribir en los tres campos para crear el juego");
                }
            }

        });
    }
}

