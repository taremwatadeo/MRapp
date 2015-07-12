package tadz.example.com.musawo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    Button button,button1,button2;
    EditText username,password;
    Database st;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signupButtonListener();
        loginButtonListener();
        cancelButtonListener();
        username = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText1);
        st = new Database(this);
    }


    public void signupButtonListener(){

        button = (Button)findViewById(R.id.signup_button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

    }

    public void loginButtonListener() {

        button1 = (Button) findViewById(R.id.login_button);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String un = username.getText().toString();
                String upass = password.getText().toString();

                try {
                    st.open();
                    boolean b = st.validateUser(un, upass);
                    st.close();
                    if(b==true) {
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    }else {
                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setTitle("HELLO");
                        TextView textView = new TextView(MainActivity.this);
                        textView.setText("please put correct password or signup if u have no account");
                        dialog.setContentView(textView);
                        dialog.show();

                    }

                } catch (Exception e) {

                    String error = e.getMessage();
                    Dialog d = new Dialog(MainActivity.this);
                    d.setTitle("ERROR");
                    TextView t = new TextView(MainActivity.this);
                    t.setText(error);
                    t.setTextSize(25);
                    d.setContentView(t);
                    d.show();
                }
            }
        });
    }

    public void cancelButtonListener(){
        button2 = (Button)findViewById(R.id.cancle_button);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    }

