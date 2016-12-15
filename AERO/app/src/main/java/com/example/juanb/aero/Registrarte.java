package com.example.juanb.aero;

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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Registrarte extends AppCompatActivity {

    EditText nombre, apellido, email, pass, residencia, edad;
    Button insert, show;
    RequestQueue requestQueue;
    //String insertUrl = "http://192.168.1.100/prog5/Registration.php";
    String insertUrl = "http://172.40.2.208/prog5/Registration.php";
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarte);

        nombre = (EditText) findViewById(R.id.editText3);
        apellido = (EditText) findViewById(R.id.editText4);
        email = (EditText) findViewById(R.id.editText5);
        pass = (EditText) findViewById(R.id.editText6);
        residencia = (EditText) findViewById(R.id.editText7);
        edad = (EditText) findViewById(R.id.editText8);
        insert = (Button) findViewById(R.id.buttonA);
        result = (TextView) findViewById(R.id.textView9);


        requestQueue = Volley.newRequestQueue(getApplicationContext());

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result.setText("Exitoso" + email, TextView.BufferType.EDITABLE);
                        System.out.println(response.toString());
                        Toast.makeText(getApplicationContext(), "Exitoso" , Toast.LENGTH_SHORT).show();
                        Intent siguiente = new Intent(Registrarte.this, Entrar.class);
                        startActivity(siguiente);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                        result.setText("Error2  " + email + " ", TextView.BufferType.EDITABLE);
                        Toast.makeText(getApplicationContext(), " Ya existe  " , Toast.LENGTH_SHORT).show();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters  = new HashMap<String, String>();
                        parameters.put("email",email.getText().toString());
                        parameters.put("nombre",nombre.getText().toString());
                        parameters.put("apellido",apellido.getText().toString());
                        parameters.put("pass",pass.getText().toString());
                        parameters.put("residencia",residencia.getText().toString());
                        parameters.put("edad",edad.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);
            }

        });

    }
}
