package edu.skku.swe.idecide.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.skku.swe.idecide.entities.HistoryAdapter;
import edu.skku.swe.idecide.entities.HomeCard;
import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.entities.HomeStackAdapter;
import edu.skku.swe.idecide.entities.Item;
import edu.skku.swe.idecide.entities.User;

public class FragmentHome extends Fragment implements CardStackView.ItemExpendListener {
    private ArrayList<HomeCard> homeCards = new ArrayList<>();
    private RecyclerView recyclerView;
    private String user_key;
    private TextView nicknameTV, nickname2TV;
    private String nickname = "";

    private HomeStackAdapter homeStackAdapter;
    private CardStackView cardStackView;

    public FragmentHome(String user_key) { this.user_key = user_key; }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public  View onCreateView( @NonNull LayoutInflater inflater,
                              @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView= (ViewGroup)inflater.inflate(R.layout.fragment_home,container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_item_homecard);
        nicknameTV = rootView.findViewById(R.id.nickname_home);
        nickname2TV = rootView.findViewById(R.id.nickname2_home);

        if (nickname.length() == 0) {
            // get and set user nickname
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            firestore.collection("User").document(user_key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    User user = new User(document.getData());
                    nickname = user.getNickname();
                    if (nickname.length() == 0) {
                        nickname = "회원";
                    }
                    nicknameTV.setText(nickname + "님,");
                    nickname2TV.setText(nickname);
                }
            });
        }
        else {
            nicknameTV.setText(nickname + "님,");
            nickname2TV.setText(nickname);
        }

        cardStackView = rootView.findViewById(R.id.card_homecard);
        cardStackView.setItemExpendListener(this);


        if (homeCards.isEmpty()) {

            List<Item> list1 = new ArrayList<>();
            list1.add(new Item(R.drawable.ion, "SAMSUNG", "Ion"));
            list1.add(new Item(R.drawable.ion, "SAMSUNG", "Ion"));


            homeCards.add(new HomeCard(R.drawable.card1, "# 사무용 노트북", list1));
            homeCards.add(new HomeCard(R.drawable.card2, "# 게이밍 노트북", list1));
            homeCards.add(new HomeCard(R.drawable.card5, "# 가벼운 노트북", list1));
            homeCards.add(new HomeCard(R.drawable.card3, "# 디자이너를 위한 노트북", list1));
            homeCards.add(new HomeCard(R.drawable.card4, "# 동영상/음악 감상용 노트북", list1));
        }

        homeStackAdapter = new HomeStackAdapter(getActivity(),homeCards);
        cardStackView.setAdapter(homeStackAdapter);
        homeStackAdapter.updateData(homeCards);



        return rootView;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_all_down:
                cardStackView.setAnimatorAdapter(new AllMoveDownAnimatorAdapter(cardStackView));
                break;
            case R.id.menu_up_down:
                cardStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(cardStackView));
                break;
            case R.id.menu_up_down_stack:
                cardStackView.setAnimatorAdapter(new UpDownStackAnimatorAdapter(cardStackView));
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemExpend(boolean expend) { }
}



/*
License on Card Stack View
Copyright 2016 Loopeer

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/