package edu.skku.swe.idecide.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.SearchingActivity;
import edu.skku.swe.idecide.getPreferences.p1_budget;


public class FragmentSearch extends Fragment {
    private String user_key;
    private Button start_search;

    public FragmentSearch(String user_key) { this.user_key = user_key; }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Nullable
    @Override
    public  View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_search, container, false);
        start_search = rootView.findViewById(R.id.button_search);

        start_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), p1_budget.class);
                intent.putExtra("user_key", user_key);
                startActivity(intent);
            }
        });


        return rootView;

    }
}