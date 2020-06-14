package edu.skku.swe.idecide.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.skku.swe.idecide.entities.ItemAdapter;
import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.entities.Item;
import edu.skku.swe.idecide.entities.User;
import edu.skku.swe.idecide.entities.Vendor;

public class FragmentCart extends Fragment {
    private RecyclerView recyclerView;
    private List<Item> list = new ArrayList<>();
    private String user_key;
    private ProgressBar progressBar;

    public FragmentCart(String user_key) { this.user_key = user_key; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_cart);
        progressBar = rootView.findViewById(R.id.progressBar_c);


        // 원래는 파이어베이스 cart에서 받아와야함!!
        // 지금은 사진이 int로 되어있는데 firebase에서 받아올때는 bitmap으로 바꿀수도 있음

        showDialog();
        list = new ArrayList<>();

        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("User").document(user_key).collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot snapshots = task.getResult();
                if (snapshots != null) {
                    for (DocumentSnapshot documentSnapshot : snapshots.getDocuments()) {
                        final Item item = new Item(documentSnapshot.getData());


                        firestore.collection("User").document(user_key).collection("Cart")
                                .document(documentSnapshot.getId()).collection("Vendor").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                QuerySnapshot snapshots1 = task.getResult();
                                List<Vendor> vendors = new ArrayList<>();
                                for (DocumentSnapshot documentSnapshot1 : snapshots1.getDocuments()) {
                                    Vendor vendor = new Vendor(documentSnapshot1.getData());
                                    Log.v("error", vendor.toString());
                                    vendors.add(vendor);
                                }
                                item.setVendors(vendors);
                                list.add(item);

                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(new ItemAdapter(list));
                                hideDialog();
                            }
                        });
                    }
                }
                else hideDialog();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideDialog();
            }
        });



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ItemAdapter(list));

        return rootView;
    }

    private void showDialog(){
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideDialog(){
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}
