/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package myapplication.uk.ac.shef.oak.myapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import myapplication.uk.ac.shef.oak.myapplication.model.VisitData;

public class VisitAdapter extends RecyclerView.Adapter<VisitAdapter.View_Holder> {
    static private Context context;
    private static List<VisitData> items;

    public VisitAdapter(List<VisitData> items) {
        this.items = items;
    }

    public VisitAdapter(Context cont, List<VisitData> items) {
        super();
        this.items = items;
        context = cont;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_visit,
                parent, false);
        View_Holder holder = new View_Holder(v);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the
        // current row on the RecyclerView
        if (holder!=null && items.get(position)!=null) {
            String title = items.get(position).getTitle();
            holder.textView.setText(title);
            Log.i("Album Adapter", "Image isn't null");
        } else {

        }

            // CHANGE THIS TO OPEN ROUTE ACTIVITY

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, ShowImageActivity.class);
//                    intent.putExtra("position", position);
//                    context.startActivity(intent);
//                }
//            });
    }



    // convenience method for getting data at click position
    VisitData getItem(int id) {
        return items.get(id);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

//    public void setPhotos(List<Image> images) {
//        items = images;
//    }

    public class View_Holder extends RecyclerView.ViewHolder  {
        TextView textView;

        View_Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.visit_item);

        }

    }

    public void setVisits(List<VisitData> visits){
        items = visits;
    }

    public List<VisitData> getVisits() { return items; }

//    public static List<VisitElement> getItems() {
//        return items;
//    }
//
//    public static void setItems(List<VisitElement> items) {
//        VisitAdapter.items = items;
//    }
}