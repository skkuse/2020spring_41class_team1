package edu.skku.swe.idecide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class FragmentProfile extends Fragment {
    private static final int REQUEST_CODE = 0;
    Button editButton, historyOptionButton, logoutButton;
    TextView nickname, age_gender, email;
    CircleImageView img;
    int notSaveHistory = 0;
    int getIndex;

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
        age_gender.setText("성별 · 00세");
        email.setText("iamryan@email.com");
        img.setImageResource(R.drawable.ryan);


        // EDIT PROFILE
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditProfileActivity.class);
                //v.getContext().startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE);
            }

        });
        // SAVE HISTORY OPTION
        historyOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] saveOrNot = {"저장", "저장 안함"};

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("검색기록 저장 옵션을 선택해주세요")
                        .setSingleChoiceItems(saveOrNot, notSaveHistory, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int index){ getIndex = index; }
                        });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notSaveHistory = getIndex;
                        if (notSaveHistory == 1)
                        {
                            // History를 저장하지 않을 경우 firebase에서 user history 삭제
                        }
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        // LOGOUT
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(" 종료하시겠습니까?");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                                //System.runFinalization();
                                //System.exit(0);
                            }
                        });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        // 원래는 캐시나 디비에 저장해서 가져와야함! 확인용 코드,,
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap tempImg = (Bitmap)intent.getParcelableExtra("img");
                String tempNickname = intent.getStringExtra("nickname");
                int tempAge = intent.getIntExtra("age", -1);
                int tempGender = intent.getIntExtra("gender", -1);

                if (tempImg == null) { img.setImageResource(R.drawable.default5); }
                else { img.setImageBitmap(tempImg); }
                nickname.setText(tempNickname);

                String tempAgeGender;
                if (tempAge < 0) { tempAgeGender = "연령 미상 · "; }
                else {
                    tempAgeGender = Integer.toString(tempAge) + "세";
                    tempAgeGender += " · "; }

                final CharSequence[] genderList = {"남자", "여자", "기타"};
                if (tempGender < 0) { tempAgeGender += "알 수 없음"; }
                else { tempAgeGender += genderList[tempGender]; }
                age_gender.setText(tempAgeGender);
            }
        }

    }

}
