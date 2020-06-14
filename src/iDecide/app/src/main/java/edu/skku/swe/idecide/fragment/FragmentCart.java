package edu.skku.swe.idecide.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.skku.swe.idecide.entities.ItemAdapter;
import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.entities.Item;

public class FragmentCart extends Fragment {
    private RecyclerView recyclerView;
    private List<Item> list = new ArrayList<>();
    private String user_key;

    public FragmentCart(String user_key) { this.user_key = user_key; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_cart);


        // 원래는 파이어베이스 cart에서 받아와야함!!
        // 지금은 사진이 int로 되어있는데 firebase에서 받아올때는 bitmap으로 바꿀수도 있음
        if (list.isEmpty()) {
            for (int i = 0; i < 20; i++) {
                //list.add(new Item(R.drawable.ion, "SAMSUNG", "Ion", "", "90"));
            }
        }



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ItemAdapter(list));

        return rootView;
    }
}
