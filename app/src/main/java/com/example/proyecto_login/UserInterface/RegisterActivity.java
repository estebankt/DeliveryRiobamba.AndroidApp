package com.example.proyecto_login.UserInterface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_login.CommonClasses.CheckUsers;
import com.example.proyecto_login.CommonClasses.InsertUsers;
import com.example.proyecto_login.Interfaces.RecyclerInterface;
import com.example.proyecto_login.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText NameET;
    private EditText PasswordET;
    private EditText MailET;
    private EditText DeliveryAddET;
    private EditText InvoiceAddET;
    private EditText PhoneET;
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
                fetchJSON();
                break;
        }
    }

    private void fetchJSON(){

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Autenticando...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        RecyclerInterface api = RecyclerInterface.retrofit.create(RecyclerInterface.class);
        Call<String> call = api.getString("https://api.myjson.com/bins/w53kx");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                progressDialog.dismiss();
                String jsonresponse = response.body().toString();
                Register(jsonresponse);


            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Algo salio mal, intentelo mas tarde", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void Register (String JSON_STRING) {
        //transformamos los EditText en string para poder comparar
        String Name = NameET.getText().toString();
        String Password = PasswordET.getText().toString();
        String Mail = MailET.getText().toString();
        String DelivAddr = DeliveryAddET.getText().toString();
        String InvvAddr = InvoiceAddET.getText().toString();
        String Phone = PhoneET.getText().toString();
        String resu;
        String checker;


            String url = "https://api.myjson.com/bins/w53kx";
            // Llamamos al metodo check de la clase CheckUser para revisar si el usuario existe
            CheckUsers ch = new CheckUsers();
            // El metodo check me devuelve un string con los posibles resultados:accept(Login Valido),no_accepted(Login invalido)
            // invalid_pass(password invalido), error (Error por JSON)
            checker = ch.check(JSON_STRING,Mail,Password);

            if (checker.equals("accept") || checker.equals("invalid_pass")){
                resu="AlreadyExist";
            }else {
                //Llamamos al metodo que va a insertar un usuario por JSON
                InsertUsers in = new InsertUsers();
                resu = in.InsertUsr(url,Name, Password, Mail, DelivAddr, InvvAddr, Phone);
            }
            if(resu.equals("AlreadyExist")){
                //En caso de que el loggin sea sucesss,llamamos al Activity "MenuActivity"
                Toast.makeText(RegisterActivity.this,"El Usuario ya existe. Seleccione otro Usuario",Toast.LENGTH_SHORT).show();

            }else  {

                Intent menuactivity = new Intent(RegisterActivity.this, MenuActivity.class);
                startActivity(menuactivity);
            }
    }
}
