package com.paricio.ucode2018app.Recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.paricio.ucode2018app.R;

import java.lang.ref.WeakReference;


public class ShoesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final WeakReference<ShoeItemListener> listener;
    private TextView image;

    public ShoesViewHolder(View itemView, ShoeItemListener listener) {
        super(itemView);
        this.listener = new WeakReference<>(listener);
        image = itemView.findViewById(R.id.image_item);
        itemView.setOnClickListener(this);
    }

    public void bind(String shoe) {
        image.setText(shoe);
    }

    @Override
    public void onClick(View view) {
        listener.get().onItemClicked(getAdapterPosition());
    }
}
