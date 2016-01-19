package edu.upc.eetac.dsa.rate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.upc.eetac.dsa.rate.client.entity.ReviewCollection;

/**
 * Created by tono on 05/01/2016.
 */
public class ReviewCollectionAdapter extends BaseAdapter {
    private ReviewCollection reviewCollection;
    private LayoutInflater layoutInflater;

    public ReviewCollectionAdapter(Context context, ReviewCollection reviewCollection){
        layoutInflater = LayoutInflater.from(context);
        this.reviewCollection = reviewCollection;
    }

    class ViewHolder{
        TextView textViewCreator;
        TextView textViewSubject;

        ViewHolder(View row){
            this.textViewCreator = (TextView) row
                    .findViewById(R.id.textViewUserid);
            this.textViewSubject = (TextView) row
                    .findViewById(R.id.textViewGameid);
        }
    }

    @Override
    public int getCount() {
        return reviewCollection.getReviews().size();
    }

    @Override
    public Object getItem(int position) {
        return reviewCollection.getReviews().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_data_rev, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String userid = reviewCollection.getReviews().get(position).getUserid();
        String gameid = reviewCollection.getReviews().get(position).getGameid();


        viewHolder.textViewCreator.setText(userid);
        viewHolder.textViewSubject.setText(gameid);
        return convertView;
    }

}

