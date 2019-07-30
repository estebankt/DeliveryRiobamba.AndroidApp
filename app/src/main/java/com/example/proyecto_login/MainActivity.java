package com.example.proyecto_login;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = MainActivity.class.getSimpleName();
    private EditText UN;
    private EditText PASS;
    private Button loginButt;
    private Button RegisterButt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Importamos los EditText creados en el xml activity_main
        //Buscamos los editText y los botones por el ID y les metemos dentro de variables ya generadas
        UN = (EditText) findViewById(R.id.UserName);
        PASS = (EditText) findViewById(R.id.Password);
        loginButt = (Button) findViewById(R.id.loginbt);
        RegisterButt = (Button) findViewById(R.id.registerbt);

        loginButt.setOnClickListener(this);
        RegisterButt.setOnClickListener(this);


    }
    @Override
    //Definimos el metodo onClick para tomar acciones deacuerdo al boton que se presiono
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.loginbt:
                new Login().execute();
                break;
            case R.id.registerbt:
                //En caso de que presionemos el boton de registrar nos va a enviar a otra Activity
                Intent Registeractivity = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(Registeractivity);
                break;
        }
    }

    // Creamos la clase Login y heredamos de la clase AsyncTask para realizar operaciones en background
    private class Login  extends AsyncTask<Void, Void, Void> {
        //transformamos los EditText en string para poder comparar
        String userN = UN.getText().toString();
        String Passw = PASS.getText().toString();
        String checker;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);


        @Override
        //Llamamos este metodo antes de la ejecucion
        protected void onPreExecute() {
            super.onPreExecute();
            //Muestro un Progress Dialog en background

            progressDialog.setMessage("Autenticando...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();


        }
        //Llamamos este metodo para que se ejecute en background
        @Override
        protected Void doInBackground(Void... arg0) {


            HttpHandler sh = new HttpHandler();
            // Hacemos el request al URL
            String url = "https://api.myjson.com/bins/w53kx";
            String JSON_STRING = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + JSON_STRING);
            // Llamamos al metodo check de la clase CheckUser para revisar si el usuario existe
            CheckUsers ch = new CheckUsers();
            // El metodo check me devuelve un string con los posibles resultados:accept(Login Valido),no_accepted(Login invalido)
            // invalid_pass(password invalido), error (Error por JSON)
            checker = ch.check(JSON_STRING,userN,Passw);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(checker.equals("accept")){
                //En caso de que el loggin sea sucesss,llamamos al Activity "MenuActivity"
                Intent menuactivity = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(menuactivity);
            }else if(checker.equals("no_accepted")) {
                //Si el Loggin fallo aparecera un mensaje diciendo que el loggin fue incorrecto
                Toast.makeText(MainActivity.this,"Usuario y Contrase\u00F1a Invalidos",Toast.LENGTH_SHORT).show();
            }else if(checker.equals("invalid_pass")){
                //Si el Loggin fallo por contrasena invalida
                Toast.makeText(MainActivity.this,"Contrase\u00F1a Invalida",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this,checker,Toast.LENGTH_SHORT).show();
            }
        }
    }



}