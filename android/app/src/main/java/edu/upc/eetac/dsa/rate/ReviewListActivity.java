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

import edu.upc.eetac.dsa.rate.R;
import edu.upc.eetac.dsa.rate.client.RateClient;
import edu.upc.eetac.dsa.rate.client.RateClientException;
import edu.upc.eetac.dsa.rate.client.entity.Review;
import edu.upc.eetac.dsa.rate.client.entity.ReviewCollection;

public class ReviewListActivity extends AppCompatActivity {

    private final static String TAG = ReviewListActivity.class.toString();
    private GetReviewTask mGetReviewTask = null;
    private ReviewCollection review = new ReviewCollection();
    private ReviewCollectionAdapter adapter = null;

    class GetReviewTask extends AsyncTask<Void, Void, String> {
        private String uri;

        public GetReviewTask(String uri) {
            this.uri = uri;

        }

        @Override
        protected String doInBackground(Void... params) {
            String jsonReviewCollection = null;
            try {
                jsonReviewCollection = RateClient.getInstance().getReviews(uri);
            } catch (RateClientException e) {
                // TODO: Handle gracefully
                Log.d(TAG, e.getMessage());
            }
            return jsonReviewCollection;
        }

        @Override
        protected void onPostExecute(String jsonReviewCollection) {
            Log.d(TAG,  jsonReviewCollection);
            ReviewCollection revCollection = (new Gson()).fromJson( jsonReviewCollection, ReviewCollection.class);
            for(Review rev : revCollection.getReviews()){
                review.getReviews().add(review.getReviews().size(), rev);
            }
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_review_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Execute AsyncTask
        mGetReviewTask = new GetReviewTask(null);
        mGetReviewTask.execute((Void) null);

        // set list adapter
        ListView list = (ListView)findViewById(R.id.list);
        adapter = new ReviewCollectionAdapter(this, review);
        list.setAdapter(adapter);

        // set list OnItemClick listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReviewListActivity.this, ReviewDetailActivity.class);
                String uri = RateClient.getLink(review.getReviews().get(position).getLinks(), "self").getUri().toString();
                intent.putExtra("uri", uri);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Selecciona cualquiera de estos analisis para verlos en detalle", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }


}
