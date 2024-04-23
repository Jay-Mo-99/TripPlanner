package com.example.a01_travel;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class listView5 extends AppCompatActivity {
    String destination, name1,name2,name3,name4,startDate,endDate;
    private DatabaseHelper dbHelper;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private Button btn_capture, btn_goFirstPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view5);

        // Retrieve and set data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            destination = extras.getString("destination");
            startDate = extras.getString("startDate");
            endDate = extras.getString("endDate");
            name1 = extras.getString("name1");
            name2 = extras.getString("name2");
            name3 = extras.getString("name3");
            name4 = extras.getString("name4");
        }

        listView = findViewById(R.id.lv_listView);
        dbHelper = new DatabaseHelper(this);

        populateListView();

        btn_goFirstPage = findViewById(R.id.btn_goFirstPage);
        //If user click the go First Page button, Go back to the first page for planning new travel.
        btn_goFirstPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listView5.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Delete all currently activity

            }
        });

        btn_capture = findViewById(R.id.btn_capture);

        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takeScreenshot();
            }
        });
    }

    /** @Function: takeScreenshot()
     *  @Detail: Captures the current screen of the app and saves it as a JPEG file in the external cache directory.
     *  * It notifies the user whether the screenshot was saved successfully or if the operation failed. */
    public void takeScreenshot() {
        // Get the current date and time formatted as a string
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // Define the path where the screenshot will be saved
            String mPath = getExternalCacheDir() + "/" + now + ".jpg";

            // Get the root view of the window and capture it as a bitmap
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            // Write the captured bitmap to a file in the defined path
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            // Ensure the screenshot is visible in gallery apps by scanning the file
            MediaScannerConnection.scanFile(this, new String[]{imageFile.toString()}, null, null);
            // Notify the user of success
            Toast.makeText(this, "Screenshot saved", Toast.LENGTH_SHORT).show();
        } catch (Throwable e) {
            // Exception handling
            // Notify the user of failed
            Toast.makeText(this, "Screenshot failed: " + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }



    /** @Function: populateListView()
     *  @Detail: Fetches travel plan data from the database and populates a ListView with this data using a SimpleCursorAdapter.
     *  * Maps database columns to TextViews in a list item layout. */
    private void populateListView() {
        // Fetch all travel plan records from the database
        Cursor cursor = dbHelper.getAllTravelPlans();

        // Define column names from the database to map to the TextViews in the ListView item layout
        String[] fromColumns = {
                DatabaseHelper.COLUMN_DESTINATION,
                DatabaseHelper.COLUMN_START_DATE,
                DatabaseHelper.COLUMN_END_DATE,
                DatabaseHelper.COLUMN_BUDGET
        };
        // Define IDs of TextViews in the ListView item layout that will be bound to the columns defined above
        int[] toViews = {
                R.id.tv_destination,
                R.id.tv_start_date,
                R.id.tv_end_date,
                R.id.tv_budget
        };

        // Create a SimpleCursorAdapter to manage the data and map it to the UI components in the ListView
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.activity_list_item,
                cursor,
                fromColumns,
                toViews,
                0
        );
        // Set the adapter to the ListView to display the items
        listView.setAdapter(adapter);
    }


    /** @Function: onDestroy()
     *  @Detail: Cleans up resources when the activity is destroyed to prevent memory leaks. Closes the cursor and database connections.*/
    @Override
    protected void onDestroy() {
        super.onDestroy();// Call the superclass method first to handle necessary operations
        adapter.getCursor().close(); // Close the cursor to free up database resources and avoid memory leaks
        dbHelper.close(); // Close the database helper to ensure all database connections are closed properly
    }
}
