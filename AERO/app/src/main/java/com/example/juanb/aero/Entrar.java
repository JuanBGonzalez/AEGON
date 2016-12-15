package com.example.juanb.aero;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Entrar extends AppCompatActivity {

    Button siguiente, siguienteRegistrar;
    EditText email,password;
    TextView estado;
    RequestQueue requestQueue;
    String URL = "http://172.40.2.208/prog5/User_Control.php";
    StringRequest request;
    String correo;

    public void empezar(){
        ((Myglobalvar) this.getApplication()).setSomeVariable(correo);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        siguiente = (Button) findViewById(R.id.btentrar);
        siguienteRegistrar = (Button) findViewById(R.id.btregis);
        email = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        estado = (TextView) findViewById(R.id.txestado);
        requestQueue = Volley.newRequestQueue(this);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), "SUCCESS: " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                estado.setText(jsonObject.getString("success") + " Exitoso ", TextView.BufferType.EDITABLE);
                                correo = jsonObject.getString("success");
                                empezar();
                                email.setText("");
                                password.setText("");
                                estado.setText("  ", TextView.BufferType.EDITABLE);
                                startActivity(new Intent(getApplicationContext(), Menu.class));

                            } else {
                                Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("Error"), Toast.LENGTH_SHORT).show();
                                estado.setText("Error en tus datos", TextView.BufferType.EDITABLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            estado.setText("Json exception" + e.getMessage(), TextView.BufferType.EDITABLE);
                        }
                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        estado.setText("Error listener", TextView.BufferType.EDITABLE);

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("email", email.getText().toString());
                        hashMap.put("pass", password.getText().toString());

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }

        });



        siguienteRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  siguienteRegis = new Intent(Entrar.this, Registrarte.class);
                startActivity(siguienteRegis);
            }
        });
    }
}
