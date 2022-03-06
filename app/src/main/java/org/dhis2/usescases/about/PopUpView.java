package org.dhis2.usescases.about;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.dhis2.R;
import org.dhis2.usescases.login.LoginActivity;
import org.dhis2.usescases.main.MainActivity;
import org.hisp.dhis.android.core.D2Manager;
import org.hisp.dhis.android.core.maintenance.D2Error;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityAttributeValue;

public class PopUpView extends AppCompatActivity {
    RadioGroup rad;
    RadioButton radb1,radb2;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);
        rad=(RadioGroup) findViewById(R.id.radioGroup1);
        radb1=(RadioButton) findViewById(R.id.radio0);
        radb2=(RadioButton) findViewById(R.id.radio1);
    tv=(TextView)findViewById(R.id.textView1);

        if (D2Manager.getD2().settingModule().userSettings().blockingGet().keyDbLocale().equals("en"))
        {
            tv.setText(R.string.about_pop);
        }
        else
        {
            tv.setText(R.string.about_pop_my);
            radb1.setText(R.string.yes_my);
            radb2.setText(R.string.no_my);
        }
        rad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id=rad.getCheckedRadioButtonId();
                View radioButton = rad.findViewById(id);

                if(radioButton.getId()==R.id.radio0)
                {
//                    Log.d("Consent value-",D2Manager.getD2().trackedEntityModule().trackedEntityAttributeValues().byTrackedEntityAttribute().eq("hRv7cihTHxT").blockingGet().get(0).value());
//                    D2Manager.getD2().trackedEntityModule().trackedEntityAttributeValues().value("hRv7cihTHxT", D2Manager.getD2().trackedEntityModule().trackedEntityInstances().blockingGet().get(0).uid()).set("true");
                    try {
                        D2Manager.getD2().trackedEntityModule().trackedEntityAttributeValues().value("hRv7cihTHxT", D2Manager.getD2().trackedEntityModule().trackedEntityInstances().blockingGet().get(0).uid()).blockingSet("true");
                    } catch (D2Error d2Error) {
                        d2Error.printStackTrace();
                    }
//                    D2Manager.getD2().trackedEntityModule().trackedEntityAttributeValues().value("hRv7cihTHxT", "jRV1D7Vy8TQ").se
                    D2Manager.getD2().trackedEntityModule().trackedEntityInstances().upload();
//                    D2Manager.getD2().trackedEntityModule().trackedEntityAttributeValues().byTrackedEntityAttribute().eq("hRv7cihTHxT").blockingGet().get(0).value()

                    Intent myIntent = new Intent(PopUpView.this, MainActivity.class);
                    PopUpView.this.startActivity(myIntent);
                    Log.d("radio check--","button1");
//                    if(radb1.getText().toString().equals("YES")){
//                    }

                }
                else if(radioButton.getId()==R.id.radio1)
                {
                    Log.d("radio check--","button2");
//                    D2Manager.getD2().trackedEntityModule().trackedEntityAttributeValues().value("hRv7cihTHxT", D2Manager.getD2().trackedEntityModule().trackedEntityInstances().blockingGet().get(0).uid()).set("false");
                    try {
                        D2Manager.getD2().trackedEntityModule().trackedEntityAttributeValues().value("hRv7cihTHxT", D2Manager.getD2().trackedEntityModule().trackedEntityInstances().blockingGet().get(0).uid()).blockingSet("false");
                    } catch (D2Error d2Error) {
                        d2Error.printStackTrace();
                    }
                    D2Manager.getD2().trackedEntityModule().trackedEntityInstances().upload();
                    Intent myIntent = new Intent(PopUpView.this, MainActivity.class);
                    PopUpView.this.startActivity(myIntent);
//                    if(radb1.getText().toString().equals("NO")){
//                        D2Manager.getD2().trackedEntityModule().trackedEntityAttributeValues().value("hRv7cihTHxT", D2Manager.getD2().trackedEntityModule().trackedEntityInstances().blockingGet().get(0).uid()).set("true");
//                    }

                }
            }
        });
//        simpleButton1 = (Button) findViewById(R.id.simpleButton1);//get id of button 1
//        simpleButton2 = (Button) findViewById(R.id.simpleButton2);//get id of button 2
//
//        simpleButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Simple Button 1", Toast.LENGTH_LONG).show();//display the text of button1
//            }
//        });
//        simpleButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Simple Button 2", Toast.LENGTH_LONG).show();//display the text of button2
//            }
//        });
    }
}
