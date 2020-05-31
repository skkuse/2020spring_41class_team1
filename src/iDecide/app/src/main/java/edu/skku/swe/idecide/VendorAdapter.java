package edu.skku.swe.idecide;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VendorAdapter extends RecyclerView.Adapter<VendorViewHolder>{
    List<Vendor> list;

    public VendorAdapter(List<Vendor> list){
        this.list = list;
    }

    @NonNull
    @Override
    public VendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor, parent, false);
        return new VendorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorViewHolder holder, final int position) {
        holder.imageView.setImageResource(list.get(position).siteImg);
        holder.textView1.setText(list.get(position).price);
        holder.textView2.setText(list.get(position).shipping);
        holder.imageView.setTag(list.get(position).siteLink);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}//Adapter



class VendorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView imageView;
    TextView textView1;
    TextView textView2;

    public VendorViewHolder(@NonNull View vendorView) {
        super(vendorView);

        imageView = vendorView.findViewById(R.id.img_vendor);
        textView1 = vendorView.findViewById(R.id.price_vendor);
        textView2 = vendorView.findViewById(R.id.shipping_vendor);

        vendorView.setOnClickListener(this);
    }

    public void onClick(View v) {
        String link = imageView.getTag().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        v.getContext().startActivity(intent);
        // 클릭하면 사이트 이동
    }
}//Holder