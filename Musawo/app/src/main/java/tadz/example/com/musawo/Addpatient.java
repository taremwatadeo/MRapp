package tadz.example.com.musawo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;


public class Addpatient extends ActionBarActivity {

    EditText patname,patage,patdisease,patprescription,patdrug,patreturndate,patremarks;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatient);
        patname = (EditText)findViewById(R.id.pedittext);
        patage = (EditText)findViewById(R.id.pedittext1);
        patdisease=(EditText)findViewById(R.id.pedittext2);
        patprescription =(EditText)findViewById(R.id.pedittext3);
        patdrug =(EditText)findViewById(R.id.pedittext4);
        patreturndate=(EditText)findViewById(R.id.pedittext5);
        patremarks = (EditText)findViewById(R.id.pedittext6);
        save = (Button)findViewById(R.id.save_button);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.save_button:
                        try {
                            String pname = patname.getText().toString();
                            String page = patage.getText().toString();
                            String pdisease = patdisease.getText().toString();
                            String pprescription = patprescription.getText().toString();
                            String pdrug = patdrug.getText().toString();
                            String preturndate = patreturndate.getText().toString();
                            String premarks = patremarks.getText().toString();
                            Database pp = new Database (Addpatient.this);


                            pp.open();
                            Long insertedRow = pp.addingPatient(pname, page, pdisease, pprescription, pdrug, preturndate, premarks);
                            if (insertedRow != -1) {
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "failed to insert", Toast.LENGTH_LONG).show();
                            }
                            pp.close();
                        }catch (SQLException x){
                            x.printStackTrace();
                        }
                }
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
       // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
        //    return true;
       // }

        return super.onOptionsItemSelected(item);
    }
}
