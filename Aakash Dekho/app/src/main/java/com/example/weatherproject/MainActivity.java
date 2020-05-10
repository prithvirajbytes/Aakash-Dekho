package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView textResult;
    private Button btnFind;
    private EditText textCity;
    RequestQueue requestQueue;
//    https://api.openweathermap.org/data/2.5/weather?q=kolkata&APPID=15620bd0ace7c9323e49293ce3f6f9ac
    String baseURL="https://api.openweathermap.org/data/2.5/weather?q=";
    String API="&APPID=15620bd0ace7c9323e49293ce3f6f9ac";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult=findViewById(R.id.textResult);
        textCity=findViewById(R.id.textCity);
        btnFind=findViewById(R.id.btnFind);

        requestQueue=Singleton.getInstance(this).getRequestQueue();

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textCity.getText().toString().equals("")) {
                    textCity.setError("Enter city name");
                    return;
                }
                textResult.setText("WEATHER : ");
                String myURL=baseURL+textCity.getText().toString()+API;
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, myURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray weather=response.getJSONArray("weather");
                            for(int i=0;i<weather.length();i++){
                                JSONObject main=weather.getJSONObject(i);
                                String n=main.getString("main");
                                textResult.append(n);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });


    }


}
