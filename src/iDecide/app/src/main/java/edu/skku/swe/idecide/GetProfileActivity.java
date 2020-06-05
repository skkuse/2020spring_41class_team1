package edu.skku.swe.idecide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

import edu.skku.swe.idecide.entities.User;

public class GetProfileActivity extends AppCompatActivity {
    Button startButton;
    TextInputEditText nicknameEditText, genderEditText, ageEditText;
    TextInputLayout l_nickname, l_gender, l_age;

    String getNickname = null;
    int getGender = -1, getAge = -1;

    // temp
    final CharSequence[] genderList = {"남자", "여자", "기타"};
    final CharSequence[] changeProfile = {"새 프로필 사진", "프로필 사진 삭제"};
    int getIndex = -1;

    private FirebaseFirestore mDb;
    private String user_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_profile);

        nicknameEditText = findViewById(R.id.nickname_get_profile);
        genderEditText = findViewById(R.id.gender_get_profile);
        ageEditText = findViewById(R.id.age_get_profile);
        l_nickname = findViewById(R.id.l_nickname_get_profile);
        l_gender = findViewById(R.id.l_gender_get_profile);
        l_age = findViewById(R.id.l_age_get_profile);
        startButton = findViewById(R.id.startBT_get_profile);


        l_nickname.setCounterEnabled(true);
        l_nickname.setCounterMaxLength(20);
        genderEditText.setInputType(0);
        ageEditText.setInputType(0);


        Intent intent = getIntent();
        user_key = intent.getStringExtra("user_key");


        // nickname
        nicknameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0 || s == null) {
                    l_nickname.setError("닉네임을 입력해 주세요");
                } else if (s.toString().length() > 20){
                    l_nickname.setError("닉네임을 20자 이내로 입력해 주세요");
                }
                else {
                    l_nickname.setError(null);
                }
            }
        });
        // gender
        genderEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);


                AlertDialog.Builder builder = new AlertDialog.Builder(GetProfileActivity.this);
                builder.setTitle("성별을 선택해주세요")
                        .setSingleChoiceItems(genderList, getGender, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int index){ getIndex = index; }
                        });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getGender = getIndex;
                        genderEditText.setText(genderList[getGender].toString());
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
        // age
        ageEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);


                final NumberPicker numberPicker = new NumberPicker(GetProfileActivity.this);
                numberPicker.setMinValue(5);
                numberPicker.setMaxValue(120);
                if (getAge < 0) numberPicker.setValue(20);
                else numberPicker.setValue(getAge);
                numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                AlertDialog.Builder builder = new AlertDialog.Builder(GetProfileActivity.this);

                builder.setTitle("나이를 선택해주세요");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getAge = numberPicker.getValue();
                        ageEditText.setText(Integer.toString(getAge));
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });

                builder.setView(numberPicker);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNickname = nicknameEditText.getText().toString();
                if (getNickname.length() == 0 || getGender < 0 || getAge < 0) {
                    Toast.makeText(GetProfileActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        Log.v("erroruser", user_key);

                        if (user_key != null)
                        {
                            /**
                             * 여기에 이메일, 닉네임, 성별, 나이 firestore에 저장하는 코드 작성해야 함
                             */
                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            Map<String, Object> postValue = null;

                            User user = new User(getNickname, getGender, getAge);
                            postValue = user.toMap();

                            db.collection("User").document(user_key).update(postValue);

                            redirectLoginScreen();
                        }
                        else {
                            Toast.makeText(GetProfileActivity.this, "회원가입에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        //Intent intent = new Intent();
                        //startActivity(intent);
                        //finish();
                    } catch (Exception e) {
                        Log.v("error3", e.getMessage());
                        Toast.makeText(GetProfileActivity.this, "서비스 이용이 원활하지 않습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }



    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen(){
        Intent intent = new Intent(GetProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
