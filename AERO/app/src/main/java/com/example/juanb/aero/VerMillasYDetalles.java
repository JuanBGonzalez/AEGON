package com.example.juanb.aero;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class VerMillasYDetalles extends AppCompatActivity {


    RequestQueue requestQueue;
    Button siguiente, siguienteperfil, siguientebiblioteca;
    TextView correo;
    //String showUrl = "http://192.168.1.100/prog5/MostrarUsuarioViaje.php";
    String showUrl = "http://172.40.2.208/prog5/MostrarUsuarioViaje.php";
    String  ID;
    StringRequest request;
    TextView idvuelo, vuelohacia, vuelodesde , tiempoestimado, clima, aeropuertoSalida,aeropuertoEntrada, millasrecorridas, fecha,estado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_millas_ydetalles);
        ID = ((Myglobalvar) this.getApplication()).getID();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        idvuelo = (TextView) findViewById(R.id.idvuelotx);
        vuelohacia =  (TextView) findViewById(R.id.vuelohaciatx);
        vuelodesde = (TextView) findViewById(R.id.vuelodesdetx);
        tiempoestimado= (TextView) findViewById(R.id.tiempoestimadotx);
        clima = (TextView) findViewById(R.id.climatx);
        aeropuertoEntrada = (TextView) findViewById(R.id.aeropuertoEntradatx);
        aeropuertoSalida = (TextView) findViewById(R.id.aeropuertoSalidatx);
        millasrecorridas = (TextView) findViewById(R.id.millasrecorridastx);
        fecha = (TextView) findViewById(R.id.fechatx);
        estado = (TextView) findViewById(R.id.estadotx);
        Mostrar();
    }


    public void Mostrar() {
            request = new StringRequest(Request.Method.POST, showUrl , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray usuarios = jsonObject.getJSONArray("vuelo");
                        if (jsonObject.names().get(0).equals("vuelo")) {
                            JSONObject usuario = usuarios.getJSONObject(0);

                            idvuelo.setText(usuario.getString("ID_Vuelo"), TextView.BufferType.EDITABLE);
                            vuelohacia.setText(usuario.getString("Vuelo_Hacia") , TextView.BufferType.EDITABLE);
                            vuelodesde.setText(usuario.getString("Vuelo_Desde"), TextView.BufferType.EDITABLE);
                            tiempoestimado.setText(usuario.getString("Tiempo_Estimado"), TextView.BufferType.EDITABLE);
                            clima.setText(usuario.getString("Clima"), TextView.BufferType.EDITABLE);
                            aeropuertoEntrada.setText(usuario.getString("Aeropuerto_de_Salida"), TextView.BufferType.EDITABLE);
                            aeropuertoSalida.setText(usuario.getString("Aeropuerto_de_Llegada"), TextView.BufferType.EDITABLE);
                            millasrecorridas.setText(usuario.getString("Millas_de_Vuelo"), TextView.BufferType.EDITABLE);
                            fecha.setText(usuario.getString("Fecha"), TextView.BufferType.EDITABLE);
                            estado.setText(usuario.getString("Estado"), TextView.BufferType.EDITABLE);


                        } else {
                            Toast.makeText(getApplicationContext(), "Error1: " + jsonObject.getJSONArray("vuelo"), Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error2 hola: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    hashMap.put("ID", ID );


                    return hashMap;
                }
            };

            requestQueue.add(request);
        }
}
