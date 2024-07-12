package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTextTitle, editTextContent, editTextDate, editTextType;
    Button buttonAdd, buttonUpdate, buttonDelete;
    ListView listView;
    CustomAdapter adapter;
    ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        editTextDate = findViewById(R.id.editTextDate);
        editTextType = findViewById(R.id.editTextType);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        listView = findViewById(R.id.listView);

        taskList = new ArrayList<>();
        adapter = new CustomAdapter(this, taskList);
        listView.setAdapter(adapter);

        viewData();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();
                String date = editTextDate.getText().toString();
                String type = editTextType.getText().toString();

                if (myDb.insertData(title, content, date, type)) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }
                viewData();
                clearForm();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = taskList.get(position);

                editTextTitle.setText(task.getTitle());
                editTextContent.setText(task.getContent());
                editTextDate.setText(task.getDate());
                editTextType.setText(task.getType());

                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = editTextTitle.getText().toString();
                        String content = editTextContent.getText().toString();
                        String date = editTextDate.getText().toString();
                        String type = editTextType.getText().toString();

                        if (myDb.updateData(task.getId(), title, content, date, type)) {
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                        }
                        viewData();
                        clearForm();
                    }
                });

                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(task.getId());
                        if (deletedRows > 0) {
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                        }
                        viewData();
                        clearForm();
                    }
                });
            }
        });
    }

    private void viewData() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
            return;
        }

        taskList.clear();
        while (res.moveToNext()) {
            Task task = new Task(
                    res.getString(0),
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4)
            );
            taskList.add(task);
        }
        adapter.notifyDataSetChanged();
    }

    private void clearForm() {
        editTextTitle.setText("");
        editTextContent.setText("");
        editTextDate.setText("");
        editTextType.setText("");
    }
}
