package edu.skku.swe.idecide.entities;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.skku.swe.idecide.HistoryDetailActivity;
import edu.skku.swe.idecide.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder>{
    List<History> list;

    public HistoryAdapter(List<History> list){
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, final int position) {
        holder.textView1.setText(list.get(position).history);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}//Adapter



class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView textView1;

    public HistoryViewHolder(@NonNull View historyView) {
        super(historyView);

        textView1 = historyView.findViewById(R.id.info_history);

        historyView.setOnClickListener(this);
    }

    // 넘길때 history정보도 넘겨야함
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), HistoryDetailActivity.class);
        v.getContext().startActivity(intent);
    }
}//Holder