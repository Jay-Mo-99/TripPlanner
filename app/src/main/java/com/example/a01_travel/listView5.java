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
    private Button btn_capture;

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

        btn_capture = findViewById(R.id.btn_capture);
        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takeScreenshot();
            }
        });
    }

    public void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // path to save the screenshot
            String mPath = getExternalCacheDir() + "/" + now + ".jpg";

            // capture the view
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            // write the screenshot to the file
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            // scan the file so it can appear in the gallery
            MediaScannerConnection.scanFile(this, new String[]{imageFile.toString()}, null, null);

            Toast.makeText(this, "Screenshot saved", Toast.LENGTH_SHORT).show();
        } catch (Throwable e) {
            // Exception handling
            Toast.makeText(this, "Screenshot failed: " + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }






    private void populateListView() {
        Cursor cursor = dbHelper.getAllTravelPlans();

        // Match the view ID of a column and list view item in the DB
        String[] fromColumns = {
                DatabaseHelper.COLUMN_DESTINATION,
                DatabaseHelper.COLUMN_START_DATE,
                DatabaseHelper.COLUMN_END_DATE,
                DatabaseHelper.COLUMN_BUDGET
        };
        int[] toViews = {
                R.id.tv_destination,
                R.id.tv_start_date,
                R.id.tv_end_date,
                R.id.tv_budget
        };



        // // Create a SimpleCursorAdapter.
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.activity_list_item,
                cursor,
                fromColumns,
                toViews,
                0
        );

        listView.setAdapter(adapter);
    }


    //Close the Cursor and Database when the App shuts down
    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.getCursor().close();
        dbHelper.close();
    }
}
