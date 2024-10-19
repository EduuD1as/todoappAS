package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.TaskAdapter;
import com.example.todoapp.Task;
import com.example.todoapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Task> listaTarefas;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private EditText titleInput, descriptionInput;
    private Button addButton;

    private static final String PREFS_NAME = "task_prefs";
    private static final String TASK_LIST_KEY = "task_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaTarefas = loadTasks();
        taskAdapter = new TaskAdapter(listaTarefas, this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String description = descriptionInput.getText().toString();

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                Task task = new Task(title, description, false, currentDate);

                listaTarefas.add(task);
                taskAdapter.notifyDataSetChanged();
                saveTasks();

                Toast.makeText(this, "Tarefa adicionada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(listaTarefas);
        editor.putString(TASK_LIST_KEY, json);

        for (int i = 0; i < listaTarefas.size(); i++) {
            editor.putBoolean("Tarefa_" + i + "_Completada", listaTarefas.get(i).isCompleted());
        }

        editor.apply();
    }

    private ArrayList<Task> loadTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(TASK_LIST_KEY, null);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();

        ArrayList<Task> listaTarefas = gson.fromJson(json, type);

        for (int i = 0; i < listaTarefas.size(); i++) {
            boolean isCompleted = sharedPreferences.getBoolean("Tarefa_" + i + "_Completada", false);
            listaTarefas.get(i).setCompleted(isCompleted);
        }

        return listaTarefas;
    }
}
