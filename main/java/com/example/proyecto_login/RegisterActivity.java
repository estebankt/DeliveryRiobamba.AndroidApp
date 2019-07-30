package com.example.proyecto_login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private EditText NameET;
    private EditText PasswordET;
    private EditText MailET;
    private EditText DeliveryAddET;
    private EditText InvoiceAddET;
    private EditText PhoneET;
    private String TAG = MainActivity.class.getSimpleName();
    private Button RegisterButt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        NameET= (EditText)findViewById(R.id.Name);
        PasswordET= (EditText)findViewById(R.id.Password);
        MailET=(EditText)findViewById(R.id.Mail);
        DeliveryAddET=(EditText)findViewById(R.id.DelieryAdd);
        InvoiceAddET=(EditText)findViewById(R.id.InvoiceAddr);
        PhoneET=(EditText)findViewById(R.id.Phone);
    }
    public void Click(View view) {
        new Register().execute();
    }
    private class Register  extends AsyncTask<Void, Void, Void> {
        //transformamos los EditText en string para poder comparar
        String Name = NameET.getText().toString();
        String Password = PasswordET.getText().toString();
        String Mail = MailET.getText().toString();
        String DelivAddr = DeliveryAddET.getText().toString();
        String InvvAddr = InvoiceAddET.getText().toString();
        String Phone = PhoneET.getText().toString();
        String resu;
        ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        String checker;
        // Hacemos el request al URL


        @Override
        //Llamamos este metodo antes de la ejecucion
        protected void onPreExecute() {
            super.onPreExecute();
            //Muestro un Progress Dialog en background
            progressDialog.setMessage("Verificando...");
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
            checker = ch.check(JSON_STRING,Mail,Password);


            if (checker.equals("accept") || checker.equals("invalid_pass")){
                resu="AlreadyExist";
            }else {

                try {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.accumulate("name", Name);
                    jsonObj.accumulate("password", Password);
                    jsonObj.accumulate("email", Mail);
                    jsonObj.accumulate("deladdress", DelivAddr);
                    jsonObj.accumulate("inaddress", InvvAddr);
                    jsonObj.accumulate("phone", Phone);
                    // 4. make POST request to the given URL
                    // 5. return response message
                    HttpPost shp = new HttpPost();
                    resu = shp.HttpPost(url, jsonObj);

                } catch (final JSONException | IOException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(resu.equals("AlreadyExist")){
                //En caso de que el loggin sea sucesss,llamamos al Activity "MenuActivity"
                Toast.makeText(RegisterActivity.this,"El Usuario ya existe. Seleccione otro Usuario",Toast.LENGTH_SHORT).show();

            }else  {

                Intent menuactivity = new Intent(RegisterActivity.this, MenuActivity.class);
                startActivity(menuactivity);
            }
        }
    }
}
