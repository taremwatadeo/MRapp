package example.com.mrapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;


public class AddPatientFragment extends android.support.v4.app.Fragment {
    EditText patname,patage,patdisease,patprescription,patdrug,patreturndate,patremarks;
    Button save;


    public AddPatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_add_patient, container, false);

        patname = (EditText)rootView.findViewById(R.id.patientname_edt);
        patage = (EditText)rootView.findViewById(R.id.age_edt);
        patdisease=(EditText)rootView.findViewById(R.id.disease_edt);
        patprescription =(EditText)rootView.findViewById(R.id.prescription_edt);
        patdrug =(EditText)rootView.findViewById(R.id.drug_edt);
        patreturndate=(EditText)rootView.findViewById(R.id.returndate_edt);
        patremarks = (EditText)rootView.findViewById(R.id.remarks_edt);
        save = (Button)rootView.findViewById(R.id.save_btn);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.save_btn:
                        try {
                            String pname = patname.getText().toString();
                            String page = patage.getText().toString();
                            String pdisease = patdisease.getText().toString();
                            String pprescription = patprescription.getText().toString();
                            String pdrug = patdrug.getText().toString();
                            String preturndate = patreturndate.getText().toString();
                            String premarks = patremarks.getText().toString();
                            Database pp = new Database (getActivity());


                            pp.open();
                            Long insertedRow = pp.addingPatient(pname, page, pdisease, pprescription, pdrug, preturndate, premarks);
                            if (insertedRow != -1) {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getActivity(),"Successfully added",Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getActivity(), "failed to insert", Toast.LENGTH_LONG).show();
                            }
                            pp.close();
                        }catch (SQLException x){
                            x.printStackTrace();
                        }
                }
            }
        });

        return rootView;
    }


}
