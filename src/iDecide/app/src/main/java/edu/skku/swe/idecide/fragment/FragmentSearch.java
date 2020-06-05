//package edu.skku.swe.idecide;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//
//public class FragmentHome extends Fragment {
//    private RecyclerView homeRecycler;
//    private HomeItemAdapter homeAdapter;
//    private ArrayList<HomeCardItem> list = new ArrayList<>();
//    private RecyclerView.LayoutManager layoutManager;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(  @NonNull LayoutInflater inflater,
//                              @Nullable ViewGroup container,
//                              @Nullable Bundle savedInstanceState) {
//        ViewGroup rootView= (ViewGroup)inflater.inflate(R.layout.fragment_home,container, false);
//
//        homeRecycler = (RecyclerView) rootView.findViewById(R.id.home_item_recyclerView);
//        list = HomeCardItem.createItemsList(5);
//        homeRecycler.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(getActivity());
//        homeRecycler.setLayoutManager(layoutManager);
//
//        homeAdapter = new HomeItemAdapter(getActivity(),list);
//        homeRecycler.setAdapter(new HomeItemAdapter(list));
//        return rootView;
//        };
//
//
//
//    }

package edu.skku.swe.idecide.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;
import java.util.Arrays;

import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.SearchResultActivity;
import edu.skku.swe.idecide.entities.SearchStackAdapter;

public class FragmentSearch extends Fragment implements CardStackView.ItemExpendListener{
    public static Integer[] TEST_DATAS = new Integer[]{
//            R.color.color_1,
//            R.color.color_2,
//            R.color.color_3,
//            R.color.color_4,
//            R.color.color_5,

//        R.color.color_6,
//        R.color.color_7,
//        R.color.color_8,
//        R.color.color_9,
//        R.color.color_10,
//        R.color.color_11,
        R.color.color_12,
        R.color.color_13,
        R.color.color_14,
        R.color.color_15,
        R.color.color_16,
//        R.color.color_17,
//        R.color.color_18,
//        R.color.color_19,
//        R.color.color_20,
//        R.color.color_21,
//        R.color.color_22,
//        R.color.color_23,
//        R.color.color_24,
//        R.color.color_25,
//        R.color.color_26
    };
    private CardStackView mStackView;
//    private FrameLayout mActionButtonContainer;
    private SearchStackAdapter mSearchStackAdapter;
    private Button returnB;
    private String user_key;

    public FragmentSearch(String user_key) { this.user_key = user_key; }



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
        ViewGroup rootView= (ViewGroup)inflater.inflate(R.layout.fragment_search_stack_view,
                container, false);
        mStackView = (CardStackView) rootView.findViewById(R.id.stackview_search);
//        mActionButtonContainer = (FrameLayout) rootView.findViewById(R.id.button_container_search);
        mStackView.setItemExpendListener(this);
        mSearchStackAdapter = new SearchStackAdapter(getActivity());
        mStackView.setAdapter(mSearchStackAdapter);
        returnB = rootView.findViewById(R.id.returnB);
        returnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button","returnB clicked");
                Intent intent = new Intent(v.getContext(), SearchResultActivity.class);
                v.getContext().startActivity(intent);
            }
        });


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mSearchStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );
        return rootView;
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_actions, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
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

    public void onPreClick(View view) {
        mStackView.pre();
    }

    public void onNextClick(View view) {
        mStackView.next();
    }

    @Override
    public void onItemExpend(boolean expend) {
//        mActionButtonContainer.setVisibility(expend ? View.VISIBLE : View.GONE);
    }

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