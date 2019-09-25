package com.example.proyecto_login.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyecto_login.Adapters.ProfileHelper;
import com.example.proyecto_login.Model_Classes.ModelCategories;
import com.example.proyecto_login.Model_Classes.ModelMenu;
import com.example.proyecto_login.Model_Classes.ModelUser;
import com.example.proyecto_login.R;
import com.example.proyecto_login.ToolBarMenu.OptionMenuActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends OptionMenuActivity implements View.OnClickListener {
    private EditText NameET;
    private EditText PasswordET;
    private EditText MailET;
    private EditText DeliveryAddET;
    private EditText InvoiceAddET;
    private EditText PhoneET;
    private Button RegisterButt;
    private Boolean button=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //ejecutamos el metodo CreateMenu de la clase OptionMenuActivity, para crear el menu principal
        //Est tiene que hacerse para todas las clases que tengan el menu
        CreateMenu(1);

        SharedPreferences sp;
        sp = getSharedPreferences("log",MODE_PRIVATE);

        NameET= (EditText)findViewById(R.id.Name);
        PasswordET= (EditText)findViewById(R.id.Password);
        MailET=(EditText)findViewById(R.id.Mail);
        DeliveryAddET=(EditText)findViewById(R.id.DelieryAdd);
        InvoiceAddET=(EditText)findViewById(R.id.InvoiceAddr);
        PhoneET=(EditText)findViewById(R.id.Phone);

        NameET.setText(sp.getString("name",""));
        PasswordET.setText(sp.getString("password",""));
        MailET.setText(sp.getString("email",""));
        DeliveryAddET.setText(sp.getString("deladdress",""));
        InvoiceAddET.setText(sp.getString("inadress",""));
        PhoneET.setText(sp.getString("phone",""));
        RegisterButt = (Button) findViewById(R.id.ConfRgbt);
        RegisterButt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ConfRgbt:

                if (button){
                    button=false;
                    NameET.setFocusable(false);
                    NameET.setFocusableInTouchMode(false);
                    NameET.setCursorVisible(false);
                    NameET.setClickable(false);

                    PasswordET.setFocusable(false);
                    PasswordET.setFocusableInTouchMode(false);
                    PasswordET.setCursorVisible(false);
                    PasswordET.setClickable(false);

                    MailET.setFocusable(false);
                    MailET.setFocusableInTouchMode(false);
                    MailET.setCursorVisible(false);
                    MailET.setClickable(false);

                    DeliveryAddET.setFocusable(false);
                    DeliveryAddET.setFocusableInTouchMode(false);
                    DeliveryAddET.setCursorVisible(false);
                    DeliveryAddET.setClickable(false);

                    InvoiceAddET.setFocusable(false);
                    InvoiceAddET.setFocusableInTouchMode(false);
                    InvoiceAddET.setCursorVisible(false);
                    InvoiceAddET.setClickable(false);

                    PhoneET.setFocusable(false);
                    PhoneET.setFocusableInTouchMode(false);
                    PhoneET.setCursorVisible(false);
                    PhoneET.setClickable(false);

                    RegisterButt.setText("Editar");

                }else {
                    NameET.setClickable(true);
                    NameET.setCursorVisible(true);
                    NameET.setFocusable(true);
                    NameET.setFocusableInTouchMode(true);
                    NameET.requestFocus();

                    PasswordET.setFocusable(true);
                    PasswordET.setFocusableInTouchMode(true);
                    PasswordET.setCursorVisible(true);
                    PasswordET.setClickable(true);

                    MailET.setFocusable(true);
                    MailET.setFocusableInTouchMode(true);
                    MailET.setCursorVisible(true);
                    MailET.setClickable(true);

                    DeliveryAddET.setFocusable(true);
                    DeliveryAddET.setFocusableInTouchMode(true);
                    DeliveryAddET.setCursorVisible(true);
                    DeliveryAddET.setClickable(true);

                    InvoiceAddET.setFocusable(true);
                    InvoiceAddET.setFocusableInTouchMode(true);
                    InvoiceAddET.setCursorVisible(true);
                    InvoiceAddET.setClickable(true);

                    PhoneET.setFocusable(true);
                    PhoneET.setFocusableInTouchMode(true);
                    PhoneET.setCursorVisible(true);
                    PhoneET.setClickable(true);

                    RegisterButt.setText("Guardar");
                    button=true;

                }

                break;
        }
    }



}
