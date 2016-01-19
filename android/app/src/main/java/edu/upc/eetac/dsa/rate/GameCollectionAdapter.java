package edu.upc.eetac.dsa.rate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.upc.eetac.dsa.rate.client.entity.GameCollection;

/**
 * Created by tono on 19/12/2015.
 */
public class GameCollectionAdapter extends BaseAdapter {
    private GameCollection gameCollection;
    private LayoutInflater layoutInflater;

    public GameCollectionAdapter(Context context, GameCollection gameCollection){
        layoutInflater = LayoutInflater.from(context);
        this.gameCollection = gameCollection;
    }

    class ViewHolder{
        TextView textViewName;
        TextView textViewId;
        TextView textViewDate;

        ViewHolder(View row){
            this.textViewName = (TextView) row
                    .findViewById(R.id.textViewName);
            this.textViewId = (TextView) row
                    .findViewById(R.id.textViewId);
            this.textViewDate = (TextView) row
                    .findViewById(R.id.textViewDate);
        }
    }

    @Override
    public int getCount() {
        return gameCollection.getGames().size();
    }

    @Override
    public Object getItem(int position) {
        return gameCollection.getGames().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_data, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String name = gameCollection.getGames().get(position).getName();
        String id = gameCollection.getGames().get(position).getId();
        Long date = gameCollection.getGames().get(position).getCreationTimestamp();
        String sdate = String.valueOf(date);

        viewHolder.textViewName.setText(name);
        viewHolder.textViewId.setText(id);
        viewHolder.textViewDate.setText(sdate);
        return convertView;
    }

}
