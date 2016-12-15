package com.example.juanb.aero;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class Perfil extends AppCompatActivity  {

    RequestQueue requestQueue;
    //String showUrl = "http://192.168.1.100/prog5/Image_Loader.php";
    String showUrl = "http://172.40.2.208/prog5/Image_Loader.php";
    EditText idtx, nombretx, apellidotx, emailtx, passwordtx, residenciatx, edadtx;
    String identificador, ID;
    StringRequest request;

    private Button button;
    private String encoded_string, image_name;
    private Bitmap bitmap;
    private File file;
    private Uri file_uri;

    public static final String UPLOAD_URL = "http://192.168.1.126/prog5/Imagen_Perfil.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";

    private int PICK_IMAGE_REQUEST = 1;

    private Button buttonView;
    private Bitmap bitmap2;
    private Uri filePath;
    private Button buttonGetImage;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        idtx = (EditText) findViewById(R.id.editText10);
        nombretx = (EditText) findViewById(R.id.editText11);
        apellidotx = (EditText) findViewById(R.id.editText12);
        emailtx = (EditText) findViewById(R.id.editText13);
        passwordtx = (EditText) findViewById(R.id.editText16);
        residenciatx = (EditText) findViewById(R.id.editText14);
        edadtx = (EditText) findViewById(R.id.editText15);
        identificador = ((Myglobalvar) this.getApplication()).getSomeVariable();
        ID = ((Myglobalvar) this.getApplication()).getID();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        idtx.setText(((Myglobalvar) this.getApplication()).getID());
        nombretx.setText(((Myglobalvar) this.getApplication()).getNombre());
        apellidotx.setText(((Myglobalvar) this.getApplication()).getApellido());
        emailtx.setText(((Myglobalvar) this.getApplication()).getSomeVariable());
        passwordtx.setText(((Myglobalvar) this.getApplication()).getPass());
        residenciatx.setText(((Myglobalvar) this.getApplication()).getResidencia());
        edadtx.setText(((Myglobalvar) this.getApplication()).getEdad());



        buttonView = (Button) findViewById(R.id.btGetImage);
        imageView = (ImageView) findViewById(R.id.imageViewShow);

        buttonView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getImage();
        }
    });

        button = (Button) findViewById(R.id.btstart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    getFileUri();
                    i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
                    startActivityForResult(i, 10);
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "ErrorOnclick: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }



    private void getImage() {
        class GetImage extends AsyncTask<String,Void,Bitmap>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Perfil.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                imageView.setImageBitmap(b);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String id = params[0];
                String add = "http://192.168.1.126/prog5/Imagen_Perfil.php?id="+id;
                URL url = null;
                Bitmap image = null;
                id = ID;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }
        GetImage gi = new GetImage();
        gi.execute(ID);

    }






    private void getFileUri() {
        try {
            String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            image_name =  ((Myglobalvar) this.getApplication()).getSomeVariable()+ "_perfil.jpg";
            file = new File(storageDir + File.separator + image_name);

            file_uri = Uri.fromFile(file);
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "ErrorGETfile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 10 && resultCode == RESULT_OK) {
                new Encode_image().execute();
            }else{
             Toast.makeText(getApplicationContext(), "Error2: " , Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "ErrorOnActivity: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private class Encode_image extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            bitmap = BitmapFactory.decodeFile(file_uri.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            bitmap.recycle();

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
                makeRequest();
        }
    }


    private void makeRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        try {
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.126/prog5/Image_Loader.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("encoded_string",encoded_string);
                map.put("image_name",image_name);
                map.put("ID", ID );

                return map;
            }
        };
        requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "ErrorRequest: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}