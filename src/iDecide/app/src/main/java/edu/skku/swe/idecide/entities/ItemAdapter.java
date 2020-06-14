package edu.skku.swe.idecide.entities;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import edu.skku.swe.idecide.ItemDetailActivity;
import edu.skku.swe.idecide.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> implements Serializable{
    List<Item> list;
    String temp;

    public ItemAdapter(List<Item> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        String name = list.get(position).name;
        if (name.equals("갤럭시북 이온")) holder.imageView.setImageResource(R.drawable.laptop_ion);
        else if (name.equals("갤럭시북 플렉스 알파")) holder.imageView.setImageResource(R.drawable.laptop_flex_alpha);
        else if (name.equals("갤럭시북 플렉스")) holder.imageView.setImageResource(R.drawable.laptop_flex);
        else if (name.equals("갤럭시북 S")) holder.imageView.setImageResource(R.drawable.laptop_s);
        else if (name.equals("그램")) holder.imageView.setImageResource(R.drawable.laptop_gram);
        else if (name.equals("비보북")) holder.imageView.setImageResource(R.drawable.laptop_vivo);
        else if (name.equals("GF 시리즈")) holder.imageView.setImageResource(R.drawable.laptop_gf);
        else if (name.equals("인스피론")) holder.imageView.setImageResource(R.drawable.laptop_inspiron);
        else if (name.equals("요가북")) holder.imageView.setImageResource(R.drawable.laptop_yogabook);
        else if (name.equals("블레이드")) holder.imageView.setImageResource(R.drawable.laptop_blade);
        else if (name.equals("스위프트 3")) holder.imageView.setImageResource(R.drawable.laptop_swift3);
        else if (name.equals("아이디어패드")) holder.imageView.setImageResource(R.drawable.laptop_ideapad);


        holder.textView1.setText(list.get(position).manufacture);
        holder.textView2.setText(list.get(position).name);
        holder.textView3.setText(list.get(position).num);
        holder.textView4.setText(list.get(position).score);


        holder.linearlayout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItemDetailActivity.class);
                intent.putExtra("user_key", list.get(position).user_key);
                intent.putExtra("code", list.get(position).code);
                intent.putExtra("manufacture", list.get(position).manufacture);
                intent.putExtra("name", list.get(position).name);
                intent.putExtra("num", list.get(position).num);

                intent.putExtra("hardware", list.get(position).hardware);
                intent.putExtra("review", list.get(position).review);
                intent.putExtra("score", list.get(position).score);

                intent.putExtra("vendors", (Serializable) list.get(position).vendors);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView textView1, textView2, textView3, textView4;
    LinearLayout linearlayout_item;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.img_item);
        textView1 = itemView.findViewById(R.id.manufacture_item);
        textView2 = itemView.findViewById(R.id.name_item);
        textView3 = itemView.findViewById(R.id.num_item);
        textView4 = itemView.findViewById(R.id.score_item);
        linearlayout_item = itemView.findViewById(R.id.linearlayout_item);
    }
}