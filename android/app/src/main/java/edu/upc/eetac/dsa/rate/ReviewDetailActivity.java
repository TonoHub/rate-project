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

import javax.ws.rs.core.Form;

import edu.upc.eetac.dsa.rate.client.RateClient;
import edu.upc.eetac.dsa.rate.client.RateClientException;
import edu.upc.eetac.dsa.rate.client.entity.Review;

public class ReviewDetailActivity extends AppCompatActivity {

    GetReviewTask mGetReviewTask = null;
    String uri = null;
    String id = null;
    String userid = null;
    String gameid = null;
    String content = null;
    Boolean likes = false;
    static final int reqnum = 4;
    static final int reqnum2 = 5;
    private final static String TAG = ReviewDetailActivity.class.toString();
    TextView textViewUserid = null;
    TextView textViewGameid = null;
    TextView textViewContent = null;
    Button btLike = null;
    Button btDislike = null;

    class GetReviewTask extends AsyncTask<Void, Void, String> {
        private String uri;

        public GetReviewTask(String uri) {
            this.uri = uri;

        }

        @Override
        protected String doInBackground(Void... params) {
            String jsonReview = null;
            try {
                jsonReview = RateClient.getInstance().getReview(uri);
            } catch (RateClientException e) {
                // TODO: Handle gracefully
                Log.d(TAG, e.getMessage());
            }
            return jsonReview;
        }

        @Override
        protected void onPostExecute(String jsonReview) {
            Log.d(TAG, jsonReview);
            Review rev = (new Gson()).fromJson(jsonReview, Review.class);
            id = rev.getId();
            userid = rev.getUserid();
            gameid = rev.getGameid();
            content = rev.getContent();

            textViewUserid.setText(userid);
            textViewGameid.setText(gameid);
            textViewContent.setText(content);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        uri = (String) getIntent().getExtras().get("uri");

        textViewUserid = (TextView) findViewById(R.id.textViewUserid);
        textViewGameid = (TextView) findViewById(R.id.textViewGameid);
        textViewContent = (TextView) findViewById(R.id.textViewContent);
        btLike = (Button) findViewById(R.id.btLike);
        btDislike = (Button) findViewById(R.id.btDislike);

        btLike = (Button) findViewById(R.id.btLike);

        btLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                likes = true;
                String slikes = String.valueOf(likes);
                Intent intent = new Intent(ReviewDetailActivity.this, LikeCreateActivity.class);
                intent.putExtra("revid", id);
                intent.putExtra("likes",slikes);
                startActivityForResult(intent, reqnum);

            }

        });

        btDislike = (Button) findViewById(R.id.btDislike);

        btDislike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                likes = false;
                String slikes = String.valueOf(likes);
                Intent intent = new Intent(ReviewDetailActivity.this, LikeCreateActivity.class);
                intent.putExtra("revid", id);
                intent.putExtra("likes", slikes);
                startActivityForResult(intent, reqnum2);

            }

        });



        // Execute AsyncTask
        mGetReviewTask = new GetReviewTask(uri);
        mGetReviewTask.execute((Void) null);


        }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == reqnum) {

            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Se ha guardado el like");
            }

            if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "no se ha guardado el like");
            }
        }

        if (requestCode == reqnum2) {

            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Se ha guardado el dislike");
            }

            if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "no se ha guardado el dislike");
            }
        }
    }
    }
