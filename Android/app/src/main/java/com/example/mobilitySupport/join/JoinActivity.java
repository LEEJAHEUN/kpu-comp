package com.example.mobilitySupport.join;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobilitySupport.R;
import com.example.mobilitySupport.login.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {

    Spinner spinner = null;                // chooseType 스피너
    ArrayAdapter<String> adapter = null;

    TextInputLayout id = null;
    TextInputLayout pw = null;
    TextInputLayout pwCheck = null;
    TextInputLayout email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        // spinner 초기화
        final String[] data = getResources().getStringArray(R.array.spinnerArray_type);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, data);
        spinner = (Spinner) findViewById(R.id.chooseType);
        spinner.setAdapter(adapter);

        id = (TextInputLayout)findViewById(R.id.TextInputLayout_join_id);
        email = (TextInputLayout)findViewById(R.id.TextInputLayout_email);

        // 비밀번호 표시 온 오프 설정
        pw = (TextInputLayout) findViewById(R.id.TextInputLayout_join_PW);
        pwCheck = (TextInputLayout) findViewById(R.id.TextInputLayer_PWCheck);
        pw.setPasswordVisibilityToggleEnabled(true);
        pwCheck.setPasswordVisibilityToggleEnabled(true);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String result = bundle.getString("RESULT");
            TextView test = (TextView) findViewById(R.id.chooseTypeText);
            test.setText(result);
        }
    };

    // 작성완료
    public void writeFin(View v) {
        textManage();
        /*
        final JoinJSONTask joinJSONTask = new JoinJSONTask();
        //joinJSONTask.execute("http://172.30.1.1:3000/signup");
        joinJSONTask.execute("http://127.0.0.1:3000/signup");
        //String check = joinJSONTask.doInBackground();

        new Thread() {
            public void run() {
                String check = joinJSONTask.doInBackground();

                Bundle bundle = new Bundle();
                bundle.putString("RESULT", check);
                Message message = handler.obtainMessage();
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }.start();


         */
        // test
        //TextView test = (TextView)findViewById(R.id.chooseTypeText);
        //test.setText(check);

        /*
        if(check.equals("ok")){
            // 회원가입 완료 창 띄우기

            // 로그인 화면으로 이동
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        String url = "http://172.30.1.1:8080/login.php";
        //String url = "http://umul.dothome.co.kr/Android/postTest.php";
        //String url = "http://mydomain:7070/";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //JSONObject object = new JSONObject(response);
                        Toast.makeText(JoinActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                            Toast.makeText(JoinActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Mysingleton.getmInstance(JoinActivity.this).addToRequestQueue(request);

         */
        //final EditText idText = (EditText) findViewById(R.id.register_ID);
        //final EditText pwText = (EditText) findViewById(R.id.register_PW);
        //final EditText mailText = (EditText) findViewById(R.id.register_email);
        //final Spinner spinner = (Spinner)findViewById(R.id.chooseType);

        String userID = id.getEditText().getText().toString();  //final ?
        String userPassword = pw.getEditText().getText().toString();
        String userMail = email.getEditText().getText().toString();
        String userType = spinner.getSelectedItem().toString();

        Response.Listener<String> responseListener = new Response.Listener<String> (){
            @Override
            public void onResponse(String response) {
                try{
                    Toast.makeText(JoinActivity.this, "", Toast.LENGTH_LONG).show();
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                        builder.setMessage("회원가입 완료")
                                .setNegativeButton("확인", null)
                                .create()
                                .show();

                        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                        JoinActivity.this.startActivity(intent);
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                        builder.setMessage("회원가입 실패 : 입력한 정보를 다시 확인해주십시오.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        JoinRequest JoinRequest = new JoinRequest(userID, userPassword, userMail, userType, responseListener);
        RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
        queue.add(JoinRequest);
    }


    // 입력 초기화
    public void reset(View v) {
        id.getEditText().setText("");
        pw.getEditText().setText("");
        email.getEditText().setText("");
        spinner.setAdapter(adapter);
        pwCheck.getEditText().setText("");
    }

    public void textManage(){
        String idText = id.getEditText().getText().toString();
        String pwText = pw.getEditText().getText().toString();
        String pwCheckText = pwCheck.getEditText().getText().toString();
        String emailText = email.getEditText().getText().toString();

        if(idText.isEmpty())
            id.setError("아이디를 입력해주십시오");
        else { id.setError(null); }

        if(pwText.isEmpty())
            pw.setError("비밀번호를 입력해주십시오");
        else { pw.setError(null); }

        if(pwCheckText.isEmpty())
            pwCheck.setError("비밀번호를 확인해주십시오");
       else if(!(pwCheckText.equals(pwText)))
            pwCheck.setError("비밀번호와 다릅니다");
        else { pwCheck.setError(null);}

        if(emailText.isEmpty())
            email.setError("이메일 주소를 입력해주십시오");
        else { email.setError(null);}

    }

    public static class Mysingleton{
        private RequestQueue mRequestQueue;
        private Context context;
        private static Mysingleton mInstance;

        public Mysingleton(Context context){
            this.context = context;
            mRequestQueue = getmRequestQueue();
        }
        public RequestQueue getmRequestQueue(){
            if(mRequestQueue == null){
                Cache cache = new DiskBasedCache(context.getCacheDir(), 1024*1024);
                Network network = new BasicNetwork(new HurlStack());
                mRequestQueue = new RequestQueue(cache, network);
                mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
            }
            return mRequestQueue;
        }
        public static synchronized Mysingleton getmInstance(Context context){
            if(mInstance == null)
                mInstance = new Mysingleton(context);
            return mInstance;
        }

        public <T> void addToRequestQueue(Request<T> request){
            mRequestQueue.add(request);
        }
    }
}


