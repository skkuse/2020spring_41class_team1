package edu.skku.swe.idecide.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.skku.swe.idecide.EditProfileActivity;
import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.entities.Profile;

import static android.app.Activity.RESULT_OK;

public class FragmentProfile extends Fragment {
    private static final int REQUEST_CODE = 0;
    Button editButton, historyOptionButton, logoutButton;
    TextView nicknameTextView, age_genderTextView, emailTextView;
    CircleImageView imgImageView;
    int notSaveHistory = 0;
    int getIndex;

    final CharSequence[] genderList = {"남자", "여자", "기타"};
    Bitmap img;
    String nickname = null, email = "IneedtoGetEmail@firebase";
    int gender = -1, age = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 초기화는 DB에서 받아와야함,,
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        editButton = (Button)view.findViewById(R.id.editButton_profile);
        historyOptionButton = (Button)view.findViewById(R.id.historyOptionButton_profile);
        logoutButton = (Button)view.findViewById(R.id.logoutButton_profile);
        nicknameTextView = (TextView)view.findViewById(R.id.nickname_profile);
        age_genderTextView = (TextView)view.findViewById(R.id.age_gender_profile);
        emailTextView = (TextView)view.findViewById(R.id.email_profile);
        imgImageView = (CircleImageView)view.findViewById(R.id.img_profile);

        setProfile(img, nickname, age, gender, email);

        // EDIT PROFILE BUTTON
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditProfileActivity.class);

                intent.putExtra("img", img);
                intent.putExtra("nickname", nickname);
                intent.putExtra("gender", gender);
                intent.putExtra("age", age);

                //v.getContext().startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE);
            }

        });
        // SAVE HISTORY OPTION BUTTON
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
        // LOGOUT BUTTON
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

                setProfile(tempImg, tempNickname, tempAge, tempGender, email);

                // DB에 저장해야함!!!
            }
        }

    }


    // profile을 설정하는 overloading method
    private void setProfile(Profile profile)
    {
        img = profile.getImage();
        nickname = profile.getNickname();
        email = profile.getEmail();
        gender = profile.getGender();
        age = profile.getAge();

        // set image
        if (img == null) { imgImageView.setImageResource(R.drawable.default5); }
        else { imgImageView.setImageBitmap(img); }

        // set nickname
        nicknameTextView.setText(nickname);

        // set age & gender
        String tempAgeGender;
        if (age < 0) { tempAgeGender = "연령 미상 · "; }
        else {
            tempAgeGender = Integer.toString(age) + "세";
            tempAgeGender += " · "; }
        if (gender < 0) { tempAgeGender += "알 수 없음"; }
        else { tempAgeGender += genderList[gender]; }
        age_genderTextView.setText(tempAgeGender);

        // set email
        emailTextView.setText(email);
    }
    private void setProfile(Bitmap t_img, String t_nickname, int t_age, int t_gender, String t_email)
    {
        img = t_img;
        nickname = t_nickname;
        email = t_email;
        gender = t_gender;
        age = t_age;

        // set image
        if (img == null) { imgImageView.setImageResource(R.drawable.default5); }
        else { imgImageView.setImageBitmap(img); }

        // set nickname
        if (nickname == null) { nicknameTextView.setText("닉네임을 설정해 주세요"); }
        else { nicknameTextView.setText(nickname); }

        // set age & gender
        String tempAgeGender;
        if (age < 0) { tempAgeGender = "연령 미상 · "; }
        else {
            tempAgeGender = Integer.toString(age) + "세";
            tempAgeGender += " · "; }
        if (gender < 0) { tempAgeGender += "알 수 없음"; }
        else { tempAgeGender += genderList[gender]; }
        age_genderTextView.setText(tempAgeGender);

        // set email
        if (email == null) { emailTextView.setText("이메일을 설정해 주세요"); }
        else { emailTextView.setText(email); }
    }

}
