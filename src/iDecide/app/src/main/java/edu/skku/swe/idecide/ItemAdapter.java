package edu.skku.swe.idecide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{
    List<Item> list;

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

        holder.imageView.setImageResource(list.get(position).image);
        holder.textView1.setText(list.get(position).manufacture);
        holder.textView2.setText(list.get(position).name);
        holder.textView3.setText(list.get(position).num);
        holder.textView4.setText(list.get(position).score);

        holder.textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), list.get(position).manufacture, Toast.LENGTH_SHORT).show();
            }
        });

        holder.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), list.get(position).name, Toast.LENGTH_SHORT).show();
            }
        });

        holder.textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), list.get(position).num, Toast.LENGTH_SHORT).show();
            }
        });

        holder.textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), list.get(position).score, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView1, textView2, textView3, textView4;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.img_item);
        textView1 = itemView.findViewById(R.id.manufacture_item);
        textView2 = itemView.findViewById(R.id.name_item);
        textView3 = itemView.findViewById(R.id.num_item);
        textView4 = itemView.findViewById(R.id.score_item);
    }
}