package edu.skku.swe.idecide.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.skku.swe.idecide.EditProfileActivity;
import edu.skku.swe.idecide.LoginActivity;
import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.entities.User;

import static android.app.Activity.RESULT_OK;

public class FragmentProfile extends Fragment {
    private static final int REQUEST_CODE = 0;
    Button editButton, historyOptionButton, logoutButton;
    TextView nicknameTextView, age_genderTextView, emailTextView;
    CircleImageView imgImageView;
    int notSaveHistory = 0;
    int getIndex;
    ProgressBar mProgressBar;

    private User user;
    final CharSequence[] genderList = {"남자", "여자", "기타"};
    Bitmap img = null;
    String nickname = "", email = "";
    int gender = -1, age = -1;

    private String user_key;

    public FragmentProfile(String user_key) { this.user_key = user_key; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 초기화는 DB에서 받아와야함,,
        super.onCreate(savedInstanceState);
        /*
        // initialize
        if (email.length() == 0) { getDataFromFireBase(); }
        setProfile();

         */
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // initialize
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        editButton = (Button)view.findViewById(R.id.editButton_profile);
        historyOptionButton = (Button)view.findViewById(R.id.historyOptionButton_profile);
        logoutButton = (Button)view.findViewById(R.id.logoutButton_profile);
        nicknameTextView = (TextView)view.findViewById(R.id.nickname_profile);
        age_genderTextView = (TextView)view.findViewById(R.id.age_gender_profile);
        emailTextView = (TextView)view.findViewById(R.id.email_profile);
        imgImageView = (CircleImageView)view.findViewById(R.id.img_profile);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progressBar_p);

        /** initialize **/
        if (email.length() == 0) { getNsetProfile();}
        else { setProfile(); }


        // EDIT PROFILE BUTTON
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(v.getContext(), EditProfileActivity.class);

                    intent.putExtra("user_key", user_key);
                    intent.putExtra("nickname", nickname);
                    intent.putExtra("gender", gender);
                    intent.putExtra("age", age);
                    //intent.putExtra("img", img);


                    //v.getContext().startActivity(intent);
                    startActivityForResult(intent, REQUEST_CODE);
                } catch (Exception e) {
                    Log.v("error", e.getMessage());
                }

            }

        });
        // SAVE HISTORY OPTION BUTTON
        historyOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] saveOrNot = {"저장", "저장 안함"};

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("검색기록 공개 여부 선택해주세요")
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
                builder.setTitle("로그아웃하시겠습니까?");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                getActivity().finish();
                                Intent intent = new Intent(getContext(), LoginActivity.class);

                                startActivity(intent);
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

                getNsetProfile();
            }
        }

    }

    private void getNsetProfile()
    {
        showDialog();
        /**
         * get data from firestore(except profile image)
         */
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("User").document(user_key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                user = new User(document.getData());
                email = user.getEmail();
                gender = user.getGender();
                age = user.getAge();
                nickname = user.getNickname();

                /**
                 * get profile image from firestore
                 */
                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference().child("User").child(user_key).child("profile.jpg");
                final long ONE_MEGABYTE = 1024 * 1024;
                mStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        img = bitmap;
                        setProfile();
                        hideDialog();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) { img = null; setProfile(); hideDialog();}});
            }
        });
    }


    // profile을 설정하는 overloading method
    private void setProfile()
    {
        showDialog();
        // set image
        if (img == null) { imgImageView.setImageResource(R.drawable.default5); }
        else { imgImageView.setImageBitmap(img); }

        // set nickname
        if (nickname.length() == 0) { nicknameTextView.setText("닉네임을 설정해 주세요"); }
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
        if (email.length() == 0) { emailTextView.setText("이메일을 설정해 주세요"); }
        else { emailTextView.setText(email); }
        hideDialog();
    }

    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

}
