package edu.skku.swe.idecide.entities;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.skku.swe.idecide.R;

public class VendorAdapter extends RecyclerView.Adapter<VendorViewHolder>{
    List<Vendor> list;

    public VendorAdapter(List<Vendor> list){
        this.list = list;
    }

    @NonNull
    @Override
    public VendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor, parent,
                false);
        return new VendorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorViewHolder holder, final int position) {
        String vendor_name = list.get(position).name;
        if (vendor_name.equals("11번가")) { holder.imageView.setImageResource(R.drawable.vendor_11st); }
        else if (vendor_name.equals("쿠팡")) { holder.imageView.setImageResource(R.drawable.vendor_coupang); }
        else if (vendor_name.equals("G마켓")) { holder.imageView.setImageResource(R.drawable.vendor_gmarket); }
        else if (vendor_name.equals("인터파크")) { holder.imageView.setImageResource(R.drawable.vendor_interpark); }
        else if (vendor_name.equals("티몬")) { holder.imageView.setImageResource(R.drawable.vendor_tmon); }
        else if (vendor_name.equals("옥션")) { holder.imageView.setImageResource(R.drawable.vendor_auction); }
        else if (vendor_name.equals("위메프")) { holder.imageView.setImageResource(R.drawable.vendor_wemakeprice); }
        else if (vendor_name.equals("G9")) { holder.imageView.setImageResource(R.drawable.vendor_g9); }


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