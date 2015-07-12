package tadz.example.com.musawo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Register extends ActionBarActivity {
    public EditText firstname,lastname,email,category,password,confirmpass;
    //public String data,data1,data2,data3,data4,data5;
   // public String file = "Registration";
    Button submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        submitButtonListener();
        firstname = (EditText)findViewById(R.id.edit_Text);
        lastname = (EditText) findViewById(R.id.edit_Text1);
        email = (EditText) findViewById(R.id.edit_Text2);
        category = (EditText)findViewById(R.id.edit_Text3);
        password = (EditText)findViewById(R.id.edit_Text4);
        confirmpass = (EditText)findViewById(R.id.edit_Text5);
        //Reg = new Database(this);



    }
    public void submitButtonListener(){
        submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* data = firstname.getText().toString();
                data1 = lastname.getText().toString();
                data2 = email.getText().toString();
                data3 = category.getText().toString();
                data4 = password.getText().toString();
                data5 = confirmpass.getText().toString();*/

                /*try{
                    FileOutputStream fout = openFileOutput(file,MODE_WORLD_WRITEABLE);
                    fout.write(data.getBytes());
                    fout.write(data1.getBytes());
                    fout.write(data2.getBytes());
                    fout.write(data3.getBytes());
                    fout.write(data4.getBytes());
                    fout.write(data5.getBytes());
                    fout.close();*/

                switch (v.getId()){
                    case R.id.submit_button:
                        boolean hey = false;
                        try {
                            String fname = firstname.getText().toString();
                            String lname = lastname.getText().toString();
                            String emaill = email.getText().toString();
                            String categoryy = category.getText().toString();
                            String passwordd = password.getText().toString();
                            String confirm_pass = confirmpass.getText().toString();
                            Database Reg = new Database(Register.this);

                            Log.v("Error.........", ""+(passwordd.equals(confirm_pass)));


                            if (passwordd.equals(confirm_pass)){
                                Reg.open();
                                long insertedRow = Reg.registration(fname, lname, emaill, categoryy, passwordd, confirm_pass);

                                if(insertedRow != -1){
                                    Intent z = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(z);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Failed to insert data into the database", Toast.LENGTH_LONG).show();
                                }

                            }else{
                                Toast.makeText(getApplicationContext(), "Please check your password", Toast.LENGTH_LONG).show();
                            }


                            Reg.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                            break;

                }





                  /*  if (Reg.registration(firstname.getText().toString(), lastname.getText().toString(),
                            email.getText().toString(), category.getText().toString(), password.getText().toString(),
                            confirmpass.getText().toString())) {
                        if (password.getText().toString().equals(confirmpass.getText().toString()) && password != null) {

                            Toast.makeText(getApplicationContext(), "Registration successfull", Toast.LENGTH_LONG).show();

                            Intent z = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(z);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "password does not match", Toast.LENGTH_LONG).show();
                    }

                /*}catch (Exception e){

                    e.printStackTrace();
                }*/
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings && id == R.id.action_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
