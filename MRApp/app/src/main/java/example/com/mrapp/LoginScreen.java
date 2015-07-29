package example.com.mrapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginScreen extends ActionBarActivity {
    Button login,register;
    Toolbar toolbar;
    EditText enterpass,enteruser;
    TextView forgotpass;
    Database pat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);
        loginButtonListener();
        registrationButtonListener();
        forgotpasswordListener();

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        enteruser=(EditText)findViewById(R.id.ledittext);
        enterpass= (EditText)findViewById(R.id.ledittext1);

    }

    public void loginButtonListener(){

        login = (Button)findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = enteruser.getText().toString();
                String upass = enterpass.getText().toString();
                pat = new Database(LoginScreen.this);
                try {
                    pat.open();
                    boolean b = pat.validateUser(un, upass);
                    pat.close();

                    if(b==true) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else {
                        Dialog dialog = new Dialog(LoginScreen.this);
                        dialog.setTitle("HELLO");
                        TextView textView = new TextView(LoginScreen.this);
                        textView.setText("please put correct password \n or signup if u have no account");
                        dialog.setContentView(textView);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    String error = e.getMessage();
                    Dialog d = new Dialog(LoginScreen.this);
                    d.setTitle("ERROR");
                    TextView t = new TextView(LoginScreen.this);
                    t.setText(error);
                    t.setTextSize(25);
                    d.setContentView(t);
                    d.setCanceledOnTouchOutside(true);
                    d.show();
                }
            }
        });
    }
    public void registrationButtonListener(){
        register = (Button)findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegistrationScreen.class);
                startActivity(intent);
            }
        });
    }
    public void forgotpasswordListener(){
        forgotpass=(TextView)findViewById(R.id.forgot_pass);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LoginScreen.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.forget_search);
                dialog.show();

                final EditText security = (EditText)dialog.findViewById(R.id.security_hint_edt);
                final EditText usern = (EditText)dialog.findViewById(R.id.user_hint_edt);

                final TextView getpass = (TextView)dialog.findViewById(R.id.textView3);

                Button ok = (Button)dialog.findViewById(R.id.getpassword_btn);

                Button cancel= (Button)dialog.findViewById(R.id.cancel_button);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String User = security.getText().toString();
                        String Userr = usern.getText().toString();
                        pat = new Database(LoginScreen.this);
                        if (User.equals("")||Userr.equals("")) {
                            Toast.makeText(getApplicationContext(), "Please Fill in all the fields \n provided", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                pat.open();
                                String storedPass = pat.getHint(Userr,User);
                                pat.close();

                                if (storedPass == null) {
                                    Toast.makeText(getApplicationContext(), "Please check your security hint \n or username", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("GET PASSWORD", storedPass);
                                    getpass.setText(storedPass);
                                }
                            }catch (Exception x){
                                x.printStackTrace();
                            }

                            }
                        }
                    }

                    );

                    cancel.setOnClickListener(new View.OnClickListener()

                    {
                        @Override
                        public void onClick (View v){
                        dialog.dismiss();
                    }
                    }

                    );
                    dialog.show();
                }
            });
    }

}
