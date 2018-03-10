package com.paricio.ucode2018app;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.paricio.ucode2018app.Recyclerview.ShoeItemListener;
import com.paricio.ucode2018app.Recyclerview.ShoesAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChooseModelFragment extends Fragment {

    private View view;
    private Button button;
    private ShoesAdapter adapter;

    public static ChooseModelFragment newInstance() {
        return new ChooseModelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_model_fragment,container,false);
        this.view = view;
        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ShoesAdapter(new ArrayList<>(), new ShoeItemListener() {
            @Override
            public void onItemClicked(int position) {
                showDialog(position);
            }
        });

        recyclerView.setAdapter(adapter);
        refreshImages();
        return view;
    }

    public void refreshImages () {
        List<Bitmap> data = new ArrayList<>();
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File myDir = cw.getDir("images" , Context.MODE_PRIVATE);
        if (myDir.exists()) {
            File[] files = myDir.listFiles();
            for (int i = 0; i < files.length; ++i) {
                File file = files[i];
                String filePath = file.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                data.add(bitmap);
            }
        }
        adapter.updateList(data);
    }

    private void showDialog(int position) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        adapter.deleteItemAtPosition(position);
                        deleteItemFromStorage(position);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Eliminar imagen?").setPositiveButton("Sí", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void deleteItemFromStorage(int position) {
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File myDir = cw.getDir("images" , Context.MODE_PRIVATE);
        if (myDir.exists()) {
            File[] files = myDir.listFiles();
            files[position].delete();
        }
        refreshImages();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshImages();
    }
}