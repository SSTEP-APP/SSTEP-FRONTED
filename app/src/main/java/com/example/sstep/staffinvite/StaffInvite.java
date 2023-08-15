package com.example.sstep.staffinvite;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.alarm.Alarm1_RecyclerViewAdpater;
import com.example.sstep.alarm.Alarm1_recyclerViewWordItemData;
import com.example.sstep.home.Home_Ceo;
import com.example.sstep.store.store_api.StoreApiService;
import com.example.sstep.user.staff_api.StaffRequestDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StaffInvite extends AppCompatActivity implements View.OnClickListener {

    Button completeBtn, yesBtn, noBtn;
    ImageButton backib;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 100;

    // 생성된 인증번호를 저장할 리스트 선언
    private List<String> generatedCodes = new ArrayList<>();

    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffinvite1);

        completeBtn = findViewById(R.id.staffInvite1_completeBtn); completeBtn.setOnClickListener(this);
        backib = findViewById(R.id.staffInvite1_backib); backib.setOnClickListener(this);
        yesBtn=findViewById(R.id.staffInvite1_yesBtn); yesBtn.setOnClickListener(this);
        noBtn=findViewById(R.id.staffInvite1_noBtn); noBtn.setOnClickListener(this);

        baseDialog_okCenter = new BaseDialog_OkCenter(StaffInvite.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(StaffInvite.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결



        // 맨 아래 '직원초대' 클릭 시
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StaffInvite2.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.staffInvite1_backib: // 뒤로가기
                intent = new Intent(getApplicationContext(), Home_Ceo.class);
                startActivity(intent);
                finish();
                break;
            case R.id.staffInvite1_completeBtn: // 직원 초대
                intent = new Intent(getApplicationContext(),StaffInvite2.class);
                startActivity(intent);
                finish();
                break;
            case R.id.staffInvite1_yesBtn: // 승인
                break;
            case R.id.staffInvite1_noBtn: // 거절
                try {

                    //네트워크 요청 구현
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    StoreApiService apiService = retrofit.create(StoreApiService.class);

                    // 사업장등록에 필요한 데이터를 StoreRequestDto 객체로 생성
                    StaffRequestDto staffRequestDto = new StaffRequestDto(
                            "testi2",
                            154362,
                            null,
                            null,
                            0,
                            0,
                            false,
                            false,
                            false
                    );

                    //적은 id를 기반으로 db에 검색
                    Call<Void> call = apiService.inviteStaffToStore(staffRequestDto);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                            } else {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // 실패 처리
                            String errorMessage = t != null ? t.getMessage() : "Unknown error";
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            t.printStackTrace();

                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.staffInvite1_resendBtn: // 재전송
                break;
            case R.id.staffInvite1_deleteBtn: // 삭제
                break;
            default:
                break;
        }
    }

    // 휴대폰 인증 처리 메서드
//    private void sendSMS() {
//        // 휴대폰 인증 처리 코드 작성
//        // 휴대폰 인증번호 발송
//
//        /*
//        db에서 이름과 전화번호 받아오기
//         */
////        String name = nameEt.getText().toString().trim();
////        String phoneNumber = numberEt.getText().toString().trim().replace("-","");
//        if (!phoneNumber.isEmpty()) {
//            // 권한 체크
//            if (ContextCompat.checkSelfPermission(StaffInvite.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
//                // 인증번호 생성 및 발송
//                String verificationCode = generateUniqueVerificationCode();
//                String message = name + " 님이 초대되었습니다. \n 사업장 코드는 " + verificationCode + " 입니다.";
//                sendVerificationCode(phoneNumber, message);
//                showCompleteDl(message); // 다이얼로그 표시
//            } else {
//                // 권한 요청
//                ActivityCompat.requestPermissions(StaffInvite.this, new String[]{android.Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
//            }
//        } else {
//            Toast.makeText(StaffInvite.this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    // 인증번호 생성 메서드 (중복 방지)
//    private String generateUniqueVerificationCode() {
//        String verificationCode = generateVerificationCode();
//        // Check if the code already exists in the list
//        while (generatedCodes.contains(verificationCode)) {
//            verificationCode = generateVerificationCode();
//        }
//        // Add the newly generated code to the list
//        generatedCodes.add(verificationCode);
//        return verificationCode;
//    }
//
//    // 인증번호 생성 메서드
//    private String generateVerificationCode() {
//        Random random = new Random();
//        int verificationCode = random.nextInt(900000) + 100000;
//        return String.valueOf(verificationCode);
//    }
//
//    // 인증번호 발송 메서드
//    private void sendVerificationCode(String phoneNumber, String verificationCode) {
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNumber, null, verificationCode, null, null);
//        } catch (Exception e) {
//            Toast.makeText(StaffInvite.this, "인증번호 발송에 실패했습니다." + verificationCode, Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }
}