package com.example.kant.artme.Activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.kant.artme.R;

import com.example.kant.artme.ArtmeAPI.Event;
import com.example.kant.artme.MyGeneralImageLoader;

import java.util.List;

/**
 * Created by Shaft on 16/02/2015.
 */
public class ManageEventAdapter extends RecyclerView.Adapter<ManageEventAdapter.MyViewHolder>{
    private List<Event> events;
    private LayoutInflater inflater;
    private ClickListener clickListener;

    public ManageEventAdapter(Context context, List<Event> projects) {
        inflater = LayoutInflater.from(context);
        this.events = projects;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.event_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(events.get(i).title);
       // Log.d("title_pic ===> ",  events.get(i).picture_url);
        if (events.get(i).picture_url != null)
            new MyGeneralImageLoader(myViewHolder.title_pic).execute("http://192.168.0.10/artme-api" + "/" + events.get(i).picture_url);
        else
            myViewHolder.title_pic.setImageResource(R.drawable.df);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        public void manageItemClicked(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView title_pic;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.event_title);
            title_pic = ((ImageView) itemView.findViewById(R.id.title_pic));
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.manageItemClicked(getPosition());
            }
        }
    }
}

