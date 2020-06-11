package edu.skku.swe.idecide.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import java.util.Arrays;

import edu.skku.swe.idecide.entities.HomeStackAdapter;
import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.entities.Item;
import edu.skku.swe.idecide.entities.User;

public class FragmentHome extends Fragment implements CardStackView.ItemExpendListener {
    public static Integer[] TEST_DATAS = new Integer[]{
    R.color.color_1,
        R.color.color_2,
        R.color.color_3,
        R.color.color_4,
        R.color.color_5,
    };
    private ArrayList<Item> list= new ArrayList<>();
    private CardStackView mStackView;
    private FrameLayout mActionButtonContainer;
    private HomeStackAdapter mHomeStackAdapter;
    private RecyclerView mHomeRecycler;
    private String user_key;
    private TextView nicknameTV;
    private String nickname;

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
        ViewGroup rootView= (ViewGroup)inflater.inflate(R.layout.fragment_home_stack_view,container, false);
        mStackView = (CardStackView) rootView.findViewById(R.id.stackview_main);
       // mActionButtonContainer = (FrameLayout) rootView.findViewById(R.id.button_container_home);
        mStackView.setItemExpendListener(this);
        nicknameTV = rootView.findViewById(R.id.nickname_home);

        // get and set user nickname
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("User").document(user_key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                User user = new User(document.getData());
                nickname = user.getNickname();
                if (nickname.length() == 0) { nickname = "회원"; }
                nicknameTV.setText(nickname + "님,");
            }
        });


        //DB로부터 받아와서 list에 넣기
        for (int i = 0; i < 20; i++)
        {
            list.add(new Item(R.drawable.ion,"LG","Gram2020", "", "75"));
        }
        //adapter로 넘겨주기.
        mHomeStackAdapter = new HomeStackAdapter(getActivity(),list);

        mStackView.setAdapter(mHomeStackAdapter);


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mHomeStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_all_down:
                mStackView.setAnimatorAdapter(new AllMoveDownAnimatorAdapter(mStackView));
                break;
            case R.id.menu_up_down:
                mStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(mStackView));
                break;
            case R.id.menu_up_down_stack:
                mStackView.setAnimatorAdapter(new UpDownStackAnimatorAdapter(mStackView));
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