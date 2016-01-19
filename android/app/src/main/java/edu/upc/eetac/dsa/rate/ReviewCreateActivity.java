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

public class ReviewCreateActivity extends AppCompatActivity {

    EditText etContent = null;
    Button btCreate = null;
    private CreateReviewTask mCreateReviewTask = null;
    private final static String TAG = ReviewCreateActivity.class.toString();


    class CreateReviewTask extends AsyncTask<Void, Void, Boolean> {
        private Form form;

        public CreateReviewTask(Form form) {
            this.form = form;

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean result = false;
            try {
                result = RateClient.getInstance().CreateReview(form);

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
        setContentView(R.layout.activity_review_create);
        etContent = (EditText) findViewById(R.id.etContent);
        btCreate = (Button) findViewById(R.id.btCreate);

        btCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (etContent.getText().length() != 0) {
                    String gameid = (String) getIntent().getExtras().get("gameid");
                    String content = etContent.getText().toString();
                    Form form = new Form();
                    form.param("gameid", gameid);
                    form.param("content", content);


                    // Execute AsyncTask
                    mCreateReviewTask = new CreateReviewTask(form);
                    mCreateReviewTask.execute((Void) null);
                } else {
                    Log.d(TAG, "Todavía no has escrito tu reseña");
                }
            }

        });
    }
}
