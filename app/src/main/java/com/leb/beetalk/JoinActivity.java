package com.leb.beetalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {
    Button btn_join;
    EditText edt_id, edt_pw, edt_name;
    RequestQueue requestQueue; // 데이터 전송할 통로
    StringRequest stringRequest;  // 전송하고싶은 데이터


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edt_id = findViewById(R.id.edt_id);
        edt_pw = findViewById(R.id.edt_pw);
        edt_name = findViewById(R.id.edt_name);
        btn_join = findViewById(R.id.btn_join);

        // 서버에 데이터를 전송해봅시다!
        // 이전까지는 httpConnection이라고 하는 클래스를 상속받아서
        // Connection을 구현해야 했습니다...
        // 그런데 Volley라고 하는 API를 만들면서 아아아아아아주우우우우 간단해졌어요!
        // 한번 사용해봅시다!
        // 1. Volley API를 임포트
        // JSON이란? 웹상에서 데이터를 주고받을 때 편리하게 주고받을 수 있도록 만든 데이터 구조
        // 520page!!!
        // Like ArrayList 같이 데이터를 담는 형태입니다.

        // 2. requestQueue 생성!
        requestQueue = Volley.newRequestQueue(JoinActivity.this);

        // 3. 보내고 싶은 URL 설정
        String url = "http://220.93.169.94:8088/LoginServer/JoinServlet";

        // 4. stringRequest 세팅하기!!

        // 첫번째 - GET/POST 결정
        // 두번째 - url 설정
        // 세번째 - 데이터 응답 리스너
        // 네번째 - 데이터 응답 에러 리스터
        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("1")){
                            Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "실패...", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("volleyerror", error.toString());
                    }
                }){
            // stringRequest 클래스내의 값을 전송하는 메소드 오버라이딩
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> joinData = new HashMap<String, String>();
                joinData.put("id", edt_id.getText().toString());
                joinData.put("pw", edt_pw.getText().toString());
                joinData.put("name", edt_name.getText().toString());

                return joinData;
            }
        };

        // 5. stringRequest에 태그달기
        stringRequest.setTag("MAIN");

        // 6. requestQueue에 stringRequest 전송하기
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue.add(stringRequest);
            }
        });
    }
}






