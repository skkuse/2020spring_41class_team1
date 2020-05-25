package edu.skku.swe.idecide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentHistory extends Fragment {
    private RecyclerView recyclerView;
    private List<History> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_history);


        // 원래는 파이어베이스 history에서 받아와야함!!
        for (int i = 0; i < 20; i++)
        {
            list.add(new History("HISTORY"));
        }


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new HistoryAdapter(list));

        return rootView;
    }
}
