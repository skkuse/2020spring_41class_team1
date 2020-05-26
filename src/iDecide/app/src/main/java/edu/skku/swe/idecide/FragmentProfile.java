package edu.skku.swe.idecide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentProfile extends Fragment {
    Button editButton, historyOptionButton, logoutButton;
    TextView nickname, age_gender, email;
    CircleImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        editButton = (Button)view.findViewById(R.id.editButton_profile);
        historyOptionButton = (Button)view.findViewById(R.id.historyOptionButton_profile);
        logoutButton = (Button)view.findViewById(R.id.logoutButton_profile);
        nickname = (TextView)view.findViewById(R.id.nickname_profile);
        age_gender = (TextView)view.findViewById(R.id.age_gender_profile);
        email = (TextView)view.findViewById(R.id.email_profile);
        img = (CircleImageView)view.findViewById(R.id.img_profile);


        // 원래는 DB에서 받아와야함!!
        nickname.setText("RYAN");
        age_gender.setText("00세 · 성별");
        email.setText("iamryan@email.com");
        img.setImageResource(R.drawable.ryan);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditProfileActivity.class);
                v.getContext().startActivity(intent);
            }
        });


        return view;
    }
}
