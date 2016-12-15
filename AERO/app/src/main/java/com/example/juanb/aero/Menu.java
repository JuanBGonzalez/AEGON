package com.example.juanb.aero;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Menu extends AppCompatActivity {

    RequestQueue requestQueue;
    Button siguiente, siguienteperfil, siguientebiblioteca;
    TextView correo;
    //String showUrl = "http://192.168.1.126/prog5/User_Control.php";
    String showUrl = "http://172.40.2.208/prog5/MostrarUsuarioActual.php";
    String identificador, ID,nombre,apellido,residencia,edad,pass;
    StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        siguienteperfil = (Button) findViewById(R.id.btperfil);
        siguientebiblioteca = (Button) findViewById(R.id.btBiblioteca);
        siguiente = (Button) findViewById(R.id.btVuelo);

        correo = (TextView) findViewById(R.id.txCorreo);
        identificador = ((Myglobalvar) this.getApplication()).getSomeVariable();
        correo.setText(identificador, TextView.BufferType.EDITABLE);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        MostrarMenuID();


        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguiente = new Intent(Menu.this, VerMillasYDetalles.class);
                startActivity(siguiente);
            }

        });

        siguienteperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguienteperfil = new Intent(Menu.this, Perfil.class);
                startActivity(siguienteperfil);
            }
        });

        siguientebiblioteca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguientebiblioteca = new Intent(Menu.this, BibliotecaYEntretenimiento.class);
                startActivity(siguientebiblioteca);
            }
        });
    }






    public void setearVarGlobal(){
        ((Myglobalvar) this.getApplication()).setID(ID);
        ((Myglobalvar) this.getApplication()).setNombre(nombre);
        ((Myglobalvar) this.getApplication()).setApellido(apellido);
        ((Myglobalvar) this.getApplication()).setPass(pass);
        ((Myglobalvar) this.getApplication()).setResidencia(residencia);
        ((Myglobalvar) this.getApplication()).setEdad(edad);
    }

    public void MostrarMenuID() {
        request = new StringRequest(Request.Method.POST, showUrl , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray usuarios = jsonObject.getJSONArray("usuarios");
                    if (jsonObject.names().get(0).equals("usuarios")) {
                        JSONObject usuario = usuarios.getJSONObject(0);

                        ID = (usuario.getString("ID"));
                        nombre = (usuario.getString("nombre"));
                        apellido = (usuario.getString("apellido"));
                        pass = (usuario.getString("pass"));
                        residencia =(usuario.getString("residencia"));
                        edad = (usuario.getString("edad"));
                        setearVarGlobal();
                        Toast.makeText(getApplicationContext(), "ID: " + usuario.getString("ID") , Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error1: " + jsonObject.getJSONArray("usuarios"), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error2: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error3: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("email", identificador);


                return hashMap;
            }
        };

        requestQueue.add(request);
    }
}
