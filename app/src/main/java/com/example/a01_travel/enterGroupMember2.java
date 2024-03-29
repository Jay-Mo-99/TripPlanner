package com.example.a01_travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class enterGroupMember2 extends AppCompatActivity {

    private TextView tv_where;
    private EditText et_name1;
    private EditText et_name2;
    private EditText et_name3;
    private EditText et_name4;

    private Button btn_who, btn_cancel;
    private TextView tv_warning;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_group_member2);

        //Get tv_where from MainActivity
        tv_where = findViewById(R.id.tv_where);
        Intent secondIntent = getIntent();
        String destination = secondIntent.getStringExtra("destination");
        tv_where.setText(destination);

        //Get the name variable from edit text
        et_name1 = findViewById(R.id.et_name1);
        et_name2 = findViewById(R.id.et_name2);
        et_name3 = findViewById(R.id.et_name3);
        et_name4 = findViewById(R.id.et_name4);

        //Get the warning from id
        tv_warning = findViewById(R.id.tv_warning);

        //Cancle button click
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(enterGroupMember2.this, MainActivity.class);
                startActivity(intent2);
            }
        });
        //Submit button click
        btn_who = findViewById(R.id.btn_who);
        btn_who.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the names from EditText
                String name1 = et_name1.getText().toString().trim();
                String name2 = et_name2.getText().toString().trim();
                String name3 = et_name3.getText().toString().trim();
                String name4 = et_name4.getText().toString().trim();


                if(name1.isEmpty() && name2.isEmpty() && name3.isEmpty() && name4.isEmpty()) {

                    tv_warning.setText("Please Enter at least one name");
                }
                else if(name1.isEmpty() || (name2.isEmpty() && (!name3.isEmpty() || !name4.isEmpty()))
                        || (name3.isEmpty() && !name4.isEmpty())) {
                    tv_warning.setText("Please enter the names in order");
                }
                else {
                    // Proceed to the next activity
                    Intent intent2 = new Intent(enterGroupMember2.this, selectDate3.class);
                    // Add only entered names to the intent
                    if (!name1.isEmpty()) intent2.putExtra("name1", name1);
                    if (!name2.isEmpty()) intent2.putExtra("name2", name2);
                    if (!name3.isEmpty()) intent2.putExtra("name3", name3);
                    if (!name4.isEmpty()) intent2.putExtra("name4", name4);
                    intent2.putExtra("destination",destination);
                    startActivity(intent2);
                }
            }
        });
    }
}