package edu.skku.swe.idecide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {
    private NumberPicker.OnValueChangeListener valueChangeListener;
    private static final int REQUEST_CODE = 0;
    private File newImageFile;

    Button editImg;
    CircleImageView imageView;
    TextInputEditText nickname, gender, age;
    TextInputLayout l_nickname, l_gender, l_age;

    // initialize
    // firebase에서 받아와야함
    Bitmap getImage = null;
    String getNickname = null;
    int getGender = -1, getAge = -1;

    // temp
    int getIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar_edit_profile);
        tb.setTitle("프로필 수정");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.img_edit_profile);
        editImg = findViewById(R.id.editButton_edit_profile);
        nickname = findViewById(R.id.nickname_edit_profile);
        gender = findViewById(R.id.gender_edit_profile);
        age = findViewById(R.id.age_edit_profile);
        l_nickname = findViewById(R.id.l_nickname_edit_profile);
        l_gender = findViewById(R.id.l_gender_edit_profile);
        l_age = findViewById(R.id.l_age_edit_profile);
        l_nickname.setCounterEnabled(true);
        l_nickname.setCounterMaxLength(20);

        // SET
        // 원래는 서버에서 받아와서 set 해줘야 함
        if (getImage == null) {
            imageView.setImageResource(R.drawable.default5);
        }


        // GET
        // image
        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사진 변경, 삭제 고르는 메뉴 추가해야함
                tedPermission(); // 권한확인부분 수정 필요
                Intent gallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallary, REQUEST_CODE);
            }
        });
        // nickname
        nickname.addTextChangedListener(new TextWatcher() {
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
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] genderList = {"남자", "여자", "기타"};
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("성별을 선택해주세요")
                        .setSingleChoiceItems(genderList, getGender, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int index){ getIndex = index; }
                        });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getGender = getIndex;
                        gender.setText(genderList[getGender].toString());
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
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NumberPicker numberPicker = new NumberPicker(EditProfileActivity.this);
                numberPicker.setMinValue(5);
                numberPicker.setMaxValue(120);
                numberPicker.setValue(20);
                numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);

                builder.setTitle("나이를 선택해주세요");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getAge = numberPicker.getValue();
                        age.setText(Integer.toString(getAge));
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



    }

    // TOOL BAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:{
                finish();
                return true;
            }
            case R.id.toolbar_confirm:{
                getNickname = nickname.getText().toString();
                if (getNickname.length() == 0 || getGender < 0 || getAge < 0) {
                    Toast.makeText(this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    // change image to bytearray
                    Intent intent = new Intent();

                    intent.putExtra("img", getImage);
                    intent.putExtra("nickname", getNickname);
                    intent.putExtra("gender", getGender);
                    intent.putExtra("age", getAge);
                    setResult(RESULT_OK, intent);
                    finish();
                    Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.confirm_toolbar, menu);
        return true;
    }


    // GET IMAGE FROM USER STORAGE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri photoUri = data.getData();
                Cursor cursor = null;

                try {
                    String[] proj = {MediaStore.Images.Media.DATA};

                    assert photoUri != null;
                    cursor = getContentResolver().query(photoUri, proj, null, null, null);

                    assert cursor != null;
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    cursor.moveToFirst();
                    newImageFile = new File(cursor.getString(column_index));

                    getImage = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                    getImage = resizeBitmap(getImage, 300, 300);
                    imageView.setImageBitmap(getImage);


                } catch (Exception e) { }
                finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }

            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void tedPermission() {

        /*
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() { } // 권한 요청 성공

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) { } // 권한 요청 실패
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setDeniedMessage("사진 및 파일을 저장하기 위하여 접근 권한이 필요합니다.")
                .check();

         */
    }


    // GET AGE
    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }
    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }


    // RESIZE & CROP IMAGE
    public Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap.getWidth() != width || bitmap.getHeight() != height){
            float ratio = 1.0f;

            if (width > height) {
                ratio = (float)width / (float)bitmap.getWidth();
            } else {
                ratio = (float)height / (float)bitmap.getHeight();
            }

            bitmap = Bitmap.createScaledBitmap(bitmap,
                    (int)(((float)bitmap.getWidth()) * ratio), // Width
                    (int)(((float)bitmap.getHeight()) * ratio), // Height
                    true);
        }

        return bitmap;
    }
}