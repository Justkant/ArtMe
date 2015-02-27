package com.example.kant.artme.Activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kant.artme.ArtmeAPI.Event;
import com.example.kant.artme.ArtmeAPI.Group;
import com.example.kant.artme.MyGeneralImageLoader;
import com.example.kant.artme.R;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Shaft on 27/02/2015.
 */
public class ManageGroupAdapter extends RecyclerView.Adapter<ManageGroupAdapter.MyViewHolder>{
    private List<Group> groups;
    private LayoutInflater inflater;
    private ClickListener clickListener;

    public ManageGroupAdapter(Context context, List<Group> groups) {
        inflater = LayoutInflater.from(context);
        this.groups = groups;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.event_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(groups.get(i).title);
        //SAVE BTP
        if (groups.get(i).picture_url != null) {
            try {
                groups.get(i).picture_btm =  new MyGeneralImageLoader(myViewHolder.title_pic).execute("http://192.168.0.10/artme-api" + "/" + groups.get(i).picture_url).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else
            myViewHolder.title_pic.setImageResource(R.drawable.df);
        //ERROR BITMAP ENVOIE ACTIVITY
        groups.get(i).picture_btm = null;
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        public void manageItemClicked(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView desc;
        ImageView title_pic;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.event_title);
            title_pic = ((ImageView) itemView.findViewById(R.id.title_pic));
            desc = ((TextView) itemView.findViewById(R.id.description));
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.manageItemClicked(getPosition());
            }
        }
    }
}

