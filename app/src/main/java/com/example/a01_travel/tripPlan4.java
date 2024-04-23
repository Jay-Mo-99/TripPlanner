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
    private TextView tv_where, tv_name1, tv_name2, tv_name3, tv_name4,tv_startDate,tv_endDate;
    TextView tv_budget1, tv_budget2,tv_budget3,tv_budget4,tv_budget5,tv_totalBudget;
    String destination, name1,name2,name3,name4,startDate,endDate;
    private Button btn_save, btn_clear, btn_cancel, btn_listView;

    //Variable for Spinner
    Spinner spinner,spinner2,spinner3,spinner4,spinner5;


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

        tv_budget1 = findViewById(R.id.et_budget);
        tv_budget2 = findViewById(R.id.et_budget2);
        tv_budget3 = findViewById(R.id.et_budget3);
        tv_budget4 = findViewById(R.id.et_budget4);
        tv_budget5 = findViewById(R.id.et_budget5);

        tv_totalBudget = findViewById(R.id.tv_totalBudget);

        btn_save = findViewById(R.id.btn_save);
        btn_clear = findViewById(R.id.btn_clear);


        //Set the spinner, calling setupSpinner function
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


        //When the user click btn_cancel, Go back to the previous page(electDate3.class)
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

        //When the user click btn_clear, Initialize EditText, spinner, and checkbox.
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

        //When the user click btn_save, Call the calculateTotalBudget() and saveTravelPlan() functions.
        btn_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                calculateTotalBudget();
                saveTravelPlan();
            }
        });

    }


    /** @Function: setupSpinner()
     *  @Param: Spinner spinner
     *  @Detail: Configures a spinner with an array of items and handles selection events.*/
    private void setupSpinner(Spinner spinner) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Set up a callback for when an item is selected within the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item's string and possibly use it for some action
                String selectedItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // no action needed if nothing is selected
            }
        });
    }

    /** @Function: calculateTotalBudget()
     *  @Detail: Calculates the total budget by summing all the numbers the user entered in et_budget.
     *  If there is input other than a number, Toggle tells the user that it is in the wrong format.*/
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

    /** @Function: saveTravelPlan()
     *  @Detail: This function stores the destination, start date, end date, and total budget entered by the user in the database.
     * The budget information entered by the user is converted to Double format and stored in the database, and when successfully stored, the user is shown a success message.
     * If the save fails, a failure message is displayed. After all database operations, close the database to release the resource.*/
    private void saveTravelPlan() {
        String destination = tv_where.getText().toString();
        String startDate = tv_startDate.getText().toString();
        String endDate = tv_endDate.getText().toString();
        String budget = tv_totalBudget.getText().toString();


        // Create an instance of the database helper class to manage database creation and version management
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Prepare the values to insert into the database
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_DESTINATION, destination);
        values.put(DatabaseHelper.COLUMN_START_DATE, startDate);
        values.put(DatabaseHelper.COLUMN_END_DATE, endDate);
        // Convert the string budget to double and store
        values.put(DatabaseHelper.COLUMN_BUDGET, Double.parseDouble(budget));

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
        if (newRowId != -1) {
            // Display a toast message if the insert was successful
            Toast.makeText(this, "Success to save", Toast.LENGTH_SHORT).show();
        } else {
            // Display a toast message if the insert failed
            Toast.makeText(this, "Fail to save", Toast.LENGTH_SHORT).show();
        }

        db.close();// Close the database
    }
}