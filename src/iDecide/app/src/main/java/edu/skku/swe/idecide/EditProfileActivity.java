package edu.skku.swe.idecide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    private boolean allSetFlag = false;
    private File newImageFile;

    Button editImg;
    CircleImageView imageView;
    TextInputEditText nickname, gender, birthday;
    TextInputLayout l_nickname, l_gender, l_birthday;

    // initialize
    Bitmap getImage = null;
    String getNickname = null;
    int getGender = 0, getBirthday = 0;

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
        birthday = findViewById(R.id.birthday_edit_profile);
        l_nickname = findViewById(R.id.l_nickname_edit_profile);
        l_gender = findViewById(R.id.l_gender_edit_profile);
        l_birthday = findViewById(R.id.l_birthday_edit_profile);
        l_nickname.setCounterEnabled(true);
        l_nickname.setCounterMaxLength(20);

        // SET
        // 원래는 서버에서 받아와서 set 해줘야 함


        // GET
        // image
        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사진 변경, 삭제 고르는 메뉴 추가해야함
                tedPermission(); // 권한확인부분 수정 필요
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);
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
                    allSetFlag = false;
                } else if (s.toString().length() > 20){
                    l_nickname.setError("닉네임을 20자 이내로 입력해 주세요");
                    allSetFlag = false;
                }
                else {
                    l_nickname.setError(null);
                    getNickname = s.toString();
                    allSetFlag = true;
                }
            }
        });

        // gender
        // birthday



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
                if (allSetFlag == false) {
                    Toast.makeText(this, "모든 정보를 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                else {
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
                } catch (Exception e) { }
                finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }

                setImage();
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
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
    private void setImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        getImage = BitmapFactory.decodeFile(newImageFile.getAbsolutePath(), options);

        imageView.setImageBitmap(getImage);
    }
}