package com.example.a01_travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class enterGroupMember2 extends AppCompatActivity {

    private TextView tv_where, tv_warning;
    private EditText et_name1, et_name2, et_name3, et_name4;
    private Button btn_who, btn_cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_group_member2);

        //Get tv_where from MainActivity
        tv_where = findViewById(R.id.tv_where);
        Intent secondIntent = getIntent();
        String destination = secondIntent.getStringExtra("destination");
        tv_where.setText(destination);

        //Get the id of the xml file and substitute it into a variable
        et_name1 = findViewById(R.id.et_name1);
        et_name2 = findViewById(R.id.et_name2);
        et_name3 = findViewById(R.id.et_name3);
        et_name4 = findViewById(R.id.et_name4);
        tv_warning = findViewById(R.id.tv_warning);
        btn_cancel = findViewById(R.id.btn_cancel);


        //When the user click the btn_cancel button, Go back to the MainActivity.class(first page)
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(enterGroupMember2.this, MainActivity.class);
                startActivity(intent2);
            }
        });



        btn_who = findViewById(R.id.btn_who);
        //When the user click the btn_who button, go to the next page(selectDate3.class)
        btn_who.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the names from EditText
                String name1 = et_name1.getText().toString().trim();
                String name2 = et_name2.getText().toString().trim();
                String name3 = et_name3.getText().toString().trim();
                String name4 = et_name4.getText().toString().trim();

                //When the user didn't enter names and click the button, tv_warning shows the warning message
                if(name1.isEmpty() && name2.isEmpty() && name3.isEmpty() && name4.isEmpty()) {

                    tv_warning.setText("Please Enter at least one name");
                }
                //When a user presses a button after typing a name out of order,
                // tv_warning prompts a user to enter a name in order.
                else if(name1.isEmpty() || (name2.isEmpty() && (!name3.isEmpty() || !name4.isEmpty()))
                        || (name3.isEmpty() && !name4.isEmpty())) {
                    tv_warning.setText("Please enter the names in order");
                }
                else {
                    // After the user enters the names in order, press the button to move to the next page (selectDate3.class).
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