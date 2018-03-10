package com.paricio.ucode2018app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.paricio.ucode2018app.Recyclerview.ShoeItemListener;
import com.paricio.ucode2018app.Recyclerview.ShoesAdapter;

import java.util.ArrayList;


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
        //TODO temp data
        ArrayList<String> data = new ArrayList<>();
        data.add("ELEM1"); data.add("ELEM2");data.add("ELEM3");data.add("ELEM4");data.add("ELEM5");data.add("ELEM6");
        data.add("ELEM7");data.add("ELEM8");data.add("ELEM9");data.add("ELEM10");

        adapter = new ShoesAdapter(data, new ShoeItemListener() {
            @Override
            public void onItemClicked(int position) {
                //TODO sth?
            }
        });

        recyclerView.setAdapter(adapter);
        return view;
    }
}
