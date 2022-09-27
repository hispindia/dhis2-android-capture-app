package org.dhis2.yesme.usescases.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.dhis2.yesme.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class VerifyOTP extends AppCompatActivity {
    int randonnumber;
    String phonenumber;
    Button verifyotp,getVerifyotp,getagain;
    EditText otp_edittext;
    String otp_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification_page);
        verifyotp=(Button)findViewById(R.id.verifyotp);
        getVerifyotp=(Button)findViewById(R.id.get_otp_no);
        getagain=(Button)findViewById(R.id.get_otp_no2);
        otp_edittext=(EditText)findViewById(R.id.otpedittext);
        final StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Intent intent=getIntent();
        phonenumber=intent.getStringExtra("phone");
        Log.d("phone----", phonenumber);

//        Toast.makeText(, "", Toast.LENGTH_SHORT).show();.makeText(VerifyOTP.this, ""+phonenumber, Toast.LENGTH_SHORT).show();
    }
    void initialsendotp(){
        try {
            // Construct data
            // Construct data
            String apiKey = "apikey=" + "MzAzNDc3NTQ0ODYxNmUzMzU1NzU2NjQzNzA3NTU2MzQ=";
            Random random = new Random();
            randonnumber=random.nextInt(99999);
            String message = "&message=" + "Please enter OTP "+randonnumber+" to login into HISP DHIS2";
            String sender = "&sender=" + "SFHISP";
            String numbers = "&numbers=" +phonenumber;
            Log.d("phone----1--", phonenumber);
            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
        }
    }
    public  void GetOTPNO(View view)
    {
        initialsendotp();
        Toast.makeText(VerifyOTP.this,"OTP SENT< Please Wait you may receive it in a moment",Toast.LENGTH_LONG).show();
        getVerifyotp.setVisibility(View.GONE);
        getagain.setVisibility(View.VISIBLE);
    }
    public void verifyOTP(View view) {
//        Toast.makeText(VerifyOTP.this,"Verify Button",Toast.LENGTH_LONG).show();
        otp_text = otp_edittext.getText().toString().trim();


        if(otp_text.equals(String.valueOf(randonnumber)))
        {
            Toast.makeText(VerifyOTP.this,"user login in successfully",Toast.LENGTH_LONG).show();
            Intent mainactivity = new Intent(VerifyOTP.this, LoginActivity.class);
            mainactivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainactivity.putExtra("otp-login", "true");
            mainactivity.putExtra("phone", phonenumber);
            startActivity(mainactivity);
            //@Sou crash fix for login
            finish();
        }
        else{
            Toast.makeText(VerifyOTP.this,"Invalid OTP, Please Try Again",Toast.LENGTH_LONG).show();
            finish();
        }
    }
}