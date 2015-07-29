package example.com.mrapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


public class RegistrationScreen extends ActionBarActivity {
    Button reg,cancel;
    Toolbar toolbar;
    EditText username,category,password,confirmpass,securityhint;
    CheckBox checkBox;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationscreen);
        registerButtonListener();
        cancelButtonListener();


        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        username = (EditText)findViewById(R.id.username_edt);
        category = (EditText)findViewById(R.id.derpartment_edt);
        password = (EditText)findViewById(R.id.password_edt);
        confirmpass = (EditText)findViewById(R.id.repassword_edt);
        securityhint = (EditText)findViewById(R.id.securityhint_edt);
        checkBox = (CheckBox)findViewById(R.id.checkBox1);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else
                {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }

        });


}
    public void registerButtonListener(){

        reg = (Button)findViewById(R.id.register_btn);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.register_btn:

                        String user_name = username.getText().toString();
                        String categorry = category.getText().toString();
                        String passwordd = password.getText().toString();
                        String confirm_pass = confirmpass.getText().toString();
                        String security_hint = securityhint.getText().toString();
                        database = new Database(RegistrationScreen.this);

                        if (user_name.equals("")||categorry.equals("")||passwordd.equals("")||confirm_pass.equals("")
                                ||security_hint.equals("")){
                            Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (!passwordd.equals(confirm_pass)){
                            Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                        }
                        else {
                            try {
                                database.open();
                                long insertedRow = database.registration(user_name, categorry, passwordd, confirm_pass, security_hint);

                                if (insertedRow != -1) {
                                    Toast.makeText(getApplicationContext(), "Registration successfull", Toast.LENGTH_LONG).show();

                                    Intent z = new Intent(getApplicationContext(), LoginScreen.class);
                                    startActivity(z);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed to insert data into the database", Toast.LENGTH_LONG).show();
                                }
                                database.close();
                            }catch (Exception e){
                                String z = e.getMessage();
                                Log.v("Error.......","" + z);
                            }
                        }

                }
            }
        });

    }
    public void cancelButtonListener(){
        cancel = (Button)findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(z);
            }
        });
    }

}
