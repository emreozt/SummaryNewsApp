package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.MainNews;
import com.example.myapplication.model.ModelClass;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
// alt+ insert ile oncreateview override ettim
    String api="a59557eb5b0a4953a0fb53a05bdc4480";

    String country="tr";
    private RecyclerView recyclerviewofhome;
    Adapter adapter;
    ArrayList<ModelClass> modelClassArrayList;


    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.homefragment,null);

        modelClassArrayList = new ArrayList<>();

        recyclerviewofhome = v.findViewById(R.id.recyclerviewofhome);

        recyclerviewofhome.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new Adapter(getContext(),modelClassArrayList);

        recyclerviewofhome.setAdapter(adapter);


        findNews();

        return v;

    }

    private void findNews() {

        ApiUtilities.getApiInterface().getNews(country,10,api).enqueue(new Callback<MainNews>() {
            @Override
            public void onResponse(Call<MainNews> call, Response<MainNews> response) {

                if (response.isSuccessful())
                {
                    modelClassArrayList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<MainNews> call, Throwable t) {

            }
        });
    }
}
