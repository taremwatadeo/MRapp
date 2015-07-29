package example.com.mrapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;



public class SplashScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

       Thread timer = new Thread(){

        public void run(){
                 try{
                     sleep(3000);
                 }catch (InterruptedException e){
                     e.printStackTrace();
                 }finally {
                     Intent intent = new Intent(SplashScreen.this,LoginScreen.class);
                     startActivity(intent);
                 }
            }

        };
        timer.start();
    }

}
