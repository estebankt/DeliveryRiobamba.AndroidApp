package com.example.proyecto_login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import de.hdodenhof.circleimageview.CircleImageView;

public class LogInActivity extends OptionMenuActivity implements View.OnClickListener{

    private String TAG = LogInActivity.class.getSimpleName();
    private EditText UN;
    private EditText PASS;
    private Button loginButt;
    private Button RegisterButt;
    private LoginButton loginButton;
    private CircleImageView circleimageview;
    private TextView txtName,txtEmail;


    private CallbackManager callbackManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Importamos los EditText creados en el xml activity_main
        //Buscamos los editText y los botones por el ID y les metemos dentro de variables ya generadas
        UN = (EditText) findViewById(R.id.UserName);
        PASS = (EditText) findViewById(R.id.Password);
        loginButt = (Button) findViewById(R.id.loginbt);
        RegisterButt = (Button) findViewById(R.id.registerbt);

        loginButt.setOnClickListener(this);
        RegisterButt.setOnClickListener(this);
        //ejecutamos el metodo CreateMenu de la clase OptionMenuActivity, para crear el menu principal
        //Est tiene que hacerse para todas las clases que tengan el menu
        CreateMenu();


        //agregado por pato-------------------------------------------------------------------------
        loginButton = findViewById(R.id.login_button);
        //txtName = findViewById(R.id.profile_name);
        //txtEmail = findViewById(R.id.profile_email);
        //circleimageview = findViewById(R.id.profile_image);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LogInActivity.this,"sucess",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LogInActivity.this,"canel",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                String error1 = error.toString();
               Toast.makeText(LogInActivity.this, error1,Toast.LENGTH_LONG).show();
            }
        });

    }


    //----------------------------------------------------------------------------------------------
    //código agregado por Pato
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken==null){
                txtName.setText("");
                txtEmail.setText("");
                circleimageview.setImageResource(0);
                Toast.makeText(LogInActivity.this,"El usuario se desconectó",Toast.LENGTH_LONG).show();
            }else{
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken newAccessToken){

        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");

                    String image_url = "https://graph.facebook.com/"+id+ "/picture?type=normal";
                    Toast.makeText(LogInActivity.this,"El usuario ingreso",Toast.LENGTH_LONG).show();
                    //txtEmail.setText(email);
                    //txtName.setText(first_name +" " +last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    //------------------------------------------------------------------------------------------------------




    @Override
    //Definimos el metodo onClick para tomar acciones deacuerdo al boton que se presiono
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.loginbt:
                new Login().execute();
                break;
            case R.id.registerbt:
                //En caso de que presionemos el boton de registrar nos va a enviar a otra Activity
                Intent Registeractivity = new Intent(LogInActivity.this, RegisterActivity.class);
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
        ProgressDialog progressDialog = new ProgressDialog(LogInActivity.this);


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
                Intent menuactivity = new Intent(LogInActivity.this, MenuActivity.class);
                startActivity(menuactivity);
            }else if(checker.equals("no_accepted")) {
                //Si el Loggin fallo aparecera un mensaje diciendo que el loggin fue incorrecto
                Toast.makeText(LogInActivity.this,"Usuario y Contrase\u00F1a Invalidos",Toast.LENGTH_SHORT).show();
            }else if(checker.equals("invalid_pass")){
                //Si el Loggin fallo por contrasena invalida
                Toast.makeText(LogInActivity.this,"Contrase\u00F1a Invalida",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(LogInActivity.this,checker,Toast.LENGTH_SHORT).show();
            }
        }
    }



}