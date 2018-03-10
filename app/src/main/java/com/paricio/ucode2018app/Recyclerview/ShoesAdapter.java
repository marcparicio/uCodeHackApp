package com.paricio.ucode2018app.Recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paricio.ucode2018app.R;

import java.util.List;


public class ShoesAdapter extends RecyclerView.Adapter<ShoesViewHolder> {

    private List<String> shoes;
    private final ShoeItemListener listener;


    public ShoesAdapter(List<String> shoes, ShoeItemListener listener) {
        this.shoes = shoes;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @Override
    public ShoesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shoe_item, parent, false);
        return new ShoesViewHolder(view,listener);    }

    @Override
    public void onBindViewHolder(ShoesViewHolder holder, int position) {
        String shoe = shoes.get(position);
        holder.bind(shoe);
    }

    @Override
    public int getItemCount() {
        return shoes.size();
    }

    public void setShoesList(List<String> shoes) {
        this.shoes = shoes;
        notifyDataSetChanged();
    }
}
