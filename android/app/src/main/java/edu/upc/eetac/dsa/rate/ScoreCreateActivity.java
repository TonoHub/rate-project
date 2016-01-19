package edu.upc.eetac.dsa.rate;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import javax.ws.rs.core.Form;

import edu.upc.eetac.dsa.rate.client.RateClient;
import edu.upc.eetac.dsa.rate.client.RateClientException;

public class ScoreCreateActivity extends AppCompatActivity {

    TextView etScore = null;
    Button btScore = null;
    SetScoreTask mSetScoreTask = null;
    public final static String[] items = {"0","1","2","3","4","5","6","7","8","9","10"};
    private final static String TAG = ScoreCreateActivity.class.toString();


    class SetScoreTask extends AsyncTask<Void, Void, Boolean> {
        private Form form;

        public SetScoreTask(Form form) {
            this.form = form;

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean result = false;
            try {
                result = RateClient.getInstance().CreateScore(form);

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

        setContentView(R.layout.activity_score_create);

        etScore =  (TextView) findViewById(R.id.tvScore);
        btScore =  (Button) findViewById(R.id.btCreate);

        // set list adapter
        ListView list = (ListView)findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        list.setAdapter(adapter);

        // set list OnItemClick listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 etScore.setText(items[position]);
            }
        });

        btScore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (etScore.getText().length() != 0) {
                    String gameid = (String) getIntent().getExtras().get("gameid");
                    String score = etScore.getText().toString();
                    Form form = new Form();
                    form.param("gameid", gameid);
                    form.param("score", score);


                    // Execute AsyncTask
                    mSetScoreTask = new SetScoreTask(form);
                    mSetScoreTask.execute((Void) null);
                } else {
                    Log.d(TAG, "Todavía no has escrito tu reseña");
                }
            }

        });



    }
}
