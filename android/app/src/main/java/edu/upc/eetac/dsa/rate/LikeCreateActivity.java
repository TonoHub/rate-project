package edu.upc.eetac.dsa.rate;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.ws.rs.core.Form;

import edu.upc.eetac.dsa.rate.client.RateClient;
import edu.upc.eetac.dsa.rate.client.RateClientException;

public class LikeCreateActivity extends AppCompatActivity {

    private final static String TAG = ReviewCreateActivity.class.toString();
    private CreateLikeTask mCreateLikeTask = null;

    class CreateLikeTask extends AsyncTask<Void, Void, Boolean> {
        private Form form;

        public CreateLikeTask(Form form) {
            this.form = form;

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean result = false;
            try {
                result = RateClient.getInstance().CreateLike(form);

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
        setContentView(R.layout.activity_like_create);
        String revid = (String) getIntent().getExtras().get("revid");
        String likes = (String) getIntent().getExtras().get("likes");
        Form form = new Form();
        form.param("revid", revid);
        form.param("likes", likes);
        mCreateLikeTask = new CreateLikeTask(form);
        mCreateLikeTask.execute((Void) null);
    }
}
