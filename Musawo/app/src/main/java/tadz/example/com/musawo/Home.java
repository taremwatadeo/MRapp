package tadz.example.com.musawo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Home extends ActionBarActivity {
    Button but, but1;
    String kk,returnname;
    AlertDialog.Builder alert;
    Database pat;

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addpatientButtonListener();
        updatepatientButtonListener();
        deletepatientButtonListener();
        text = (TextView) findViewById(R.id.viewtext);
        pat = new Database(this);


        try {
            pat.open();
            kk = pat.getPatient();
            pat.close();

        }catch(Exception x){
           kk = x.getMessage().toString();
        }
        text.setText(kk);

        Button but2 = (Button)findViewById(R.id.pdelete_button);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setTitle("Enter row id");

                final EditText input = new EditText(Home.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String m_text = input.getText().toString();
                                long row = Long.parseLong(m_text);
                                Database dat = new Database(Home.this);
                                try {
                                    dat.open();
                                    returnname = dat.getPatientName(row);
                                    dat.close();
                                } catch (Exception e) {
                                    Dialog tad = new Dialog(Home.this);
                                    tad.setTitle("HELLO");
                                    TextView textView = new TextView(Home.this);
                                    textView.setText(e.getMessage());
                                    textView.setTextSize(25);
                                    tad.setContentView(textView);
                                    tad.show();

                                }
                                alert = new AlertDialog.Builder(Home.this);
                                alert.setMessage(R.string.message + returnname);
                                alert.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        long rrow = Long.parseLong(m_text);
                                        Database dat = new Database(Home.this);
                                        try {
                                            dat.open();
                                            dat.deleteEntry(rrow);
                                            dat.close();
                                            Intent intent = new Intent(getApplicationContext(), Home.class);
                                            startActivity(intent);

                                        } catch (Exception a) {

                                            Dialog tad = new Dialog(Home.this);
                                            tad.setTitle("HELLO");
                                            TextView textView = new TextView(Home.this);
                                            textView.setText(a.getMessage());
                                            textView.setTextSize(25);
                                            tad.setContentView(textView);
                                            tad.show();
                                        }


                                    }
                                });


                                alert.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener()

                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                                startActivity(intent);
                                            }
                                        }

                                );


                            }
                        }

                );
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()

                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }

                );
                builder.show();

            }

        });

    }


    public void addpatientButtonListener(){
        but = (Button)findViewById(R.id.add_patient);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), Addpatient.class);
                startActivity(a);
            }
        });
    }

    public void updatepatientButtonListener(){
        but1=(Button)findViewById(R.id.update_patient);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public void deletepatientButtonListener(){



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings && id == R.id.action_help&& id==R.id.action_logout) {

            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
