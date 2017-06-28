package com.morivash.newapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_userName, et_password, et_email;
    Button btRegister;
    ProgressDialog progressDialog;
    TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedprefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }

        et_userName = (EditText) findViewById(R.id.ed_userName);
        et_password = (EditText) findViewById(R.id.ed_pass);
        et_email = (EditText) findViewById(R.id.ed_email);
        textViewLogin = (TextView) findViewById(R.id.tv_login);

        btRegister = (Button) findViewById(R.id.btn_register);

        progressDialog = new ProgressDialog(this);

        btRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

    }

    private void registerUser() {
        final String userName = et_userName.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        final String email = et_email.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTEr_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);


                    Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, "some thing wrong with json response", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", userName);
                params.put("password", password);
                params.put("email", email);
                return params;
            }
        };
        RequestManager.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == btRegister) {
            registerUser();
        }
        if (v == textViewLogin) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
