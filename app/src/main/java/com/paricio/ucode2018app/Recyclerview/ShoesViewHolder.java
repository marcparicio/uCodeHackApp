package com.paricio.ucode2018app.Recyclerview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.paricio.ucode2018app.R;

import java.lang.ref.WeakReference;


public class ShoesViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    private final WeakReference<ShoeItemListener> listener;
    private ImageView image;

    public ShoesViewHolder(View itemView, ShoeItemListener listener) {
        super(itemView);
        this.listener = new WeakReference<>(listener);
        image = itemView.findViewById(R.id.image_item);
        itemView.setOnLongClickListener(this);
    }

    public void bind(Bitmap shoe) {
        image.setImageBitmap(shoe);
    }

    @Override
    public boolean onLongClick(View view) {
        listener.get().onItemClicked(getAdapterPosition());
        return true;
    }
}
