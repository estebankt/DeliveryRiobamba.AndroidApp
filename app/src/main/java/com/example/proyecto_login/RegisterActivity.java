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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText NameET;
    private EditText PasswordET;
    private EditText MailET;
    private EditText DeliveryAddET;
    private EditText InvoiceAddET;
    private EditText PhoneET;
    private String TAG = MainActivity.class.getSimpleName();
    private Button RegisterButt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        NameET= (EditText)findViewById(R.id.Name);
        PasswordET= (EditText)findViewById(R.id.Password);
        MailET=(EditText)findViewById(R.id.Mail);
        DeliveryAddET=(EditText)findViewById(R.id.DelieryAdd);
        InvoiceAddET=(EditText)findViewById(R.id.InvoiceAddr);
        PhoneET=(EditText)findViewById(R.id.Phone);
        RegisterButt = (Button) findViewById(R.id.ConfRgbt);
        RegisterButt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ConfRgbt:
                new Register().execute();
                break;
        }
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
            //chumeo eres un puto 
            checker = ch.check(JSON_STRING,Mail,Password);


            if (checker.equals("accept") || checker.equals("invalid_pass")){
                resu="AlreadyExist";
            }else {
                //Llamamos al metodo que va a insertar un usuario por JSON
                InsertUsers in = new InsertUsers();
                resu = in.InsertUsr(url,Name, Password, Mail, DelivAddr, InvvAddr, Phone);

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
