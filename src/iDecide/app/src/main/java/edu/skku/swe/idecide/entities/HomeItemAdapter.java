package edu.skku.swe.idecide.entities;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.skku.swe.idecide.ItemDetailActivity;
import edu.skku.swe.idecide.R;

public class HomeItemAdapter extends RecyclerView.Adapter <HomeViewHolder> {
    HomeCardItem homeCardItem;
    ArrayList<HomeCardItem> homeCardItems;
    Context context;


    //constructor
    public HomeItemAdapter(FragmentActivity activity, ArrayList<HomeCardItem> list) {
        this.context = activity;
        this.homeCardItems = list;
    }
    public HomeItemAdapter(ArrayList<HomeCardItem> list) {
        this.homeCardItems = list;
    }

    //Viewholder
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home
                , parent, false);
        HomeViewHolder holder =  new  HomeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        HomeCardItem item = homeCardItems.get(position);
    }

    @Override
    public int getItemCount() {
        return this.homeCardItems.size();
    }
}
    class HomeViewHolder extends RecyclerView.ViewHolder {
        //home_item_card_view에서 찾기.
        public ImageView card_image; //카드에 삽입될 배경 이미지
        public TextView card_preference; //카드위에 올라갈 설명 (ex)"이동이 많은 사용자들에게 추천하는 노트북!"
        public HomeCardItem homeCard;
        public CardView cardView;

        //constructor
        public HomeViewHolder(View itemView) {
            super(itemView);
//            card_image = (ImageView) itemView.findViewById(R.id.home_image);
//            card_preference = (TextView) itemView.findViewById(R.id.home_preference);
            cardView = (CardView) itemView.findViewById(R.id.home_card);
            Log.d("HomeItemViewHolder","findviewbyId cardimage and preference");
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Log.d("card", "CLicked");
                    Intent intent = new Intent(v.getContext(), ItemDetailActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
}//class