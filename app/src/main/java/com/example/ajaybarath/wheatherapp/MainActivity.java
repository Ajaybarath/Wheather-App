package com.example.ajaybarath.wheatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button find = (Button)findViewById(R.id.button);
        final EditText search = (EditText) findViewById(R.id.city);
        final TextView result = (TextView) findViewById(R.id.result);

        final String apiKey = "0c04195e9ee49de1fa8aaf631bc5c835";

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city = "" + search.getText();
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +"&appid=" + apiKey;
                Log.i("city", "" + city);

                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONArray weather = response.getJSONArray("weather");
                                    JSONObject temp = weather.getJSONObject(0);
                                    String temp1 = temp.getString("main");
                                    result.setText(temp1);
                                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();


                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), "No results found", Toast.LENGTH_SHORT).show();
                                    result.setText("No results found ..");
                                    e.printStackTrace();
                                }

                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Error", ""+error);
                            }
                        }

                );
                requestQueue.add(jsonObjectRequest);

            }
        });



    }
}
