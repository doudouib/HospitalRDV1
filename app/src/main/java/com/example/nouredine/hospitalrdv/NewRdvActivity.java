package com.example.nouredine.hospitalrdv;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewRdvActivity extends AppCompatActivity {

    public static final int DATE_DIALOG_ID = 1;
    public static final int TIME_DIALOG_ID = 2;
    int d = 1, m = 7, y = 2017, hh, mm;

    EditText etDate, etHoure, etPatient, etDoctor;
    Button btnSave;
    TextView tvCansel;

    DBHandler data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rdv);

        etDate = (EditText) findViewById(R.id.editText3);
        etHoure = (EditText) findViewById(R.id.editText2);
        etPatient = (EditText) findViewById(R.id.editText);
        etDoctor = (EditText) findViewById(R.id.editText1);
        btnSave = (Button) findViewById(R.id.button2);
        tvCansel = (TextView) findViewById(R.id.textView6);

        data = new DBHandler(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etDate.getText().equals("") || etHoure.getText().equals("") || etPatient.getText().equals("") || etDoctor.getText().equals("")){
                    Toast.makeText(NewRdvActivity.this, "Veillez remplir les champs correctement!!!", Toast.LENGTH_SHORT).show();
                }else{
                    data.addRdv(etDate.getText().toString(),etHoure.getText().toString(),etPatient.getText().toString(),etDoctor.getText().toString());
                    etDate.setText("");
                    etHoure.setText("");
                    etPatient.setText("");
                    etDoctor.setText("");
                    Toast.makeText(NewRdvActivity.this, "Rendez-vous sauvegardé avec succès", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvCansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rdvIntent = new Intent(NewRdvActivity.this, MainMenuActivity.class);
                startActivity(rdvIntent);
                finish();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        etHoure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
    }

    private DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            y = year;
            m = month;
            d = dayOfMonth;
            etDate.setText("Le " + d + "/" + m + "/" + y);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSet = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hh = hourOfDay;
            mm = minute;
            etHoure.setText(hh + "H" + mm);
        }
    };



    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, dateSet, y, m, d);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timeSet, hh, mm, true);
        }
        return null;
    }
}
