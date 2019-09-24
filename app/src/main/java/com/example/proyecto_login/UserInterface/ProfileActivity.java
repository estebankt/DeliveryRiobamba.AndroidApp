package com.example.proyecto_login.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyecto_login.Adapters.ProfileHelper;
import com.example.proyecto_login.Model_Classes.ModelCategories;
import com.example.proyecto_login.Model_Classes.ModelMenu;
import com.example.proyecto_login.Model_Classes.ModelUser;
import com.example.proyecto_login.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private EditText NameET;
    private EditText PasswordET;
    private EditText MailET;
    private EditText DeliveryAddET;
    private EditText InvoiceAddET;
    private EditText PhoneET;
    private List<ModelUser> mProfile;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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


    }
}
