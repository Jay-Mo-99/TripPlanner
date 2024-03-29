package com.example.a01_travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class tripPlan4 extends AppCompatActivity {
    private TextView tv_where, tv_name1, tv_name2, tv_name3, tv_name4,tv_warning,tv_startDate,tv_endDate;
    TextView tv_budget1, tv_budget2,tv_budget3,tv_budget4,tv_budget5,tv_totalBudget;
    String destination, name1,name2,name3,name4,startDate,endDate;
    //Variable for Button
    private Button btn_save, btn_clear, btn_cancel, btn_listView;
    //String for et_text

    //Variable for Spinner
    Spinner spinner,spinner2,spinner3,spinner4,spinner5;

    CheckBox cb_check1,cb_check2,cb_check3,cb_check4,cb_check5,cb_check6;
    String selectedItem1,selectedItem2,selectedItem3,selectedItem4,selectedItem5;

    private void saveTravelPlan() {
        String destination = tv_where.getText().toString();
        String startDate = tv_startDate.getText().toString();
        String endDate = tv_endDate.getText().toString();
        String budget = tv_totalBudget.getText().toString();


        // Creating a Database Helper Object
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_DESTINATION, destination);
        values.put(DatabaseHelper.COLUMN_START_DATE, startDate);
        values.put(DatabaseHelper.COLUMN_END_DATE, endDate);
        values.put(DatabaseHelper.COLUMN_BUDGET, Double.parseDouble(budget));

        long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Success to save", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Fail to save", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_plan4);

        //Retrieve from xml
        tv_where = findViewById(R.id.tv_where);
        tv_startDate = findViewById(R.id.tv_startDate);
        tv_endDate = findViewById(R.id.tv_endDate);

        tv_name1 = findViewById(R.id.tv_name1);
        tv_name2 = findViewById(R.id.tv_name2);
        tv_name3 = findViewById(R.id.tv_name3);
        tv_name4 = findViewById(R.id.tv_name4);
        tv_warning = findViewById(R.id.tv_warning);

        tv_budget1 = findViewById(R.id.et_budget);
        tv_budget2 = findViewById(R.id.et_budget2);
        tv_budget3 = findViewById(R.id.et_budget3);
        tv_budget4 = findViewById(R.id.et_budget4);
        tv_budget5 = findViewById(R.id.et_budget5);

        tv_totalBudget = findViewById(R.id.tv_totalBudget);

        btn_save = findViewById(R.id.btn_save);
        btn_clear = findViewById(R.id.btn_clear);



    //Spinner Part

        spinner = (Spinner) findViewById(R.id.spinner);
        setupSpinner(spinner);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        setupSpinner(spinner2);

        spinner3 = (Spinner) findViewById(R.id.spinner3);
        setupSpinner(spinner3);

        spinner4 = (Spinner) findViewById(R.id.spinner4);
        setupSpinner(spinner4);

        spinner5 = (Spinner) findViewById(R.id.spinner5);
        setupSpinner(spinner5);



        //Retrieve destination
        Intent thirdIntent = getIntent();

        // Retrieve and set data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            destination = extras.getString("destination");
            startDate = extras.getString("dateString1");
            endDate = extras.getString("dateString2");
            name1 = extras.getString("name1");
            name2 = extras.getString("name2");
            name3 = extras.getString("name3");
            name4 = extras.getString("name4");


            // Set the retrieved data to TextViews
            tv_where.setText(destination);
            tv_startDate.setText(startDate);
            tv_endDate.setText(endDate);
            if (name1 != null) tv_name1.setText(name1);
            if (name2 != null) tv_name2.setText(name2);
            if (name3 != null) tv_name3.setText(name3);
            if (name4 != null) tv_name4.setText(name4);
        }

        //Click the list Button
        btn_listView = findViewById(R.id.btn_listView);
        btn_listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(tripPlan4.this, listView5.class);


                startActivity(intent5);
            }
        });


        //Click cancel Button
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(tripPlan4.this, selectDate3.class);
                // Save the ex data to the intent
                if (!destination.isEmpty()) intent2.putExtra("destination", destination);
                if (!name1.isEmpty()) intent2.putExtra("name1", name1);
                if (!name2.isEmpty()) intent2.putExtra("name2", name2);
                if (!name3.isEmpty()) intent2.putExtra("name3", name3);
                if (!name4.isEmpty()) intent2.putExtra("name4", name4);
                startActivity(intent2);
            }
        });

        //If the user click the clean Button
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reset Total Budget
                tv_totalBudget.setText("");
                // Reset all spinners to the first item
                Spinner[] spinners = {spinner, spinner2, spinner3, spinner4, spinner5};
                for (Spinner s : spinners) {
                    s.setSelection(0);
                }

                // Clear all EditTexts
                EditText[] editTasks = {
                        findViewById(R.id.et_task),
                        findViewById(R.id.et_task2),
                        findViewById(R.id.et_task3),
                        findViewById(R.id.et_task4),
                        findViewById(R.id.et_task5)
                };
                for (EditText editText : editTasks) {
                    editText.setText("");
                }

                // Clear all EditTexts
                EditText[] editBudget = {
                        findViewById(R.id.et_budget),
                        findViewById(R.id.et_budget2),
                        findViewById(R.id.et_budget3),
                        findViewById(R.id.et_budget4),
                        findViewById(R.id.et_budget5)
                };
                for (EditText editText : editBudget) {
                    editText.setText("");
                }

                // Uncheck all CheckBoxes (assuming you have CheckBoxes)
                CheckBox[] checkBoxes = {
                        findViewById(R.id.cb_check1),
                        findViewById(R.id.cb_check2),
                        findViewById(R.id.cb_check3),
                        findViewById(R.id.cb_check4),
                        findViewById(R.id.cb_check5)

                };
                for (CheckBox checkBox : checkBoxes) {
                    checkBox.setChecked(false);
                }

            }
        });

        //If the user click the save Button
        btn_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                calculateTotalBudget();
                saveTravelPlan();
            }
        });

        //If the user click the download Button

    }


    // Function for setup Spinner
    private void setupSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // If the user does not select anything
            }
        });
    }

    //Function for calculate Budget
    private void calculateTotalBudget() {
        double total = 0;
        EditText[] budgetFields = {
                findViewById(R.id.et_budget),
                findViewById(R.id.et_budget2),
                findViewById(R.id.et_budget3),
                findViewById(R.id.et_budget4),
                findViewById(R.id.et_budget5),

        };

        for (EditText budgetField : budgetFields) {
            String budgetText = budgetField.getText().toString();
            if (!budgetText.isEmpty()) {
                try {
                    total += Double.parseDouble(budgetText);
                } catch (NumberFormatException e) {
                    // Handle exception if input is not a valid double
                    Toast.makeText(this, "Invalid number format in budget", Toast.LENGTH_SHORT).show();
                }
            }
        }

        tv_totalBudget.setText(String.valueOf(total));
    }
}