package com.example.rohan.sharedpreferencesnonprimitivedata;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView txvDisplay;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvDisplay = findViewById(R.id.txvDisplay);
    }

    public void saveObjectType(View view) {


        Employee employeeStore = getEmployeeObject();

        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

//        serialisation
        Gson gson = new Gson();
        String jsonString = gson.toJson(employeeStore, Employee.class);

        Log.i(TAG, jsonString);

        editor.putString(Constants.JSON_STRING,jsonString);
        editor.apply();




    }

    public void loadObjectType(View view) {

        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);

       String jsonData = sp.getString(Constants.JSON_STRING,"N/A");

        Log.i(TAG, jsonData);

//       deserialization
        Gson gson = new Gson();
        Employee employee = gson.fromJson(jsonData,Employee.class);

        if (employee == null)
            return;

        String displayText =
                        employee.getName()
                +"\n" + employee.getProfession()
                +"\n" + employee.getProId()
                +"\n" + employee.getList().toString();

        txvDisplay.setText(displayText);


    }

    public void saveGenericType(View view) {


        Employee employeeStore = getEmployeeObject();

        Foo<Employee> foo = new Foo<>();
        foo.setObject(employeeStore);

        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

//        serialisation
        Gson gson = new Gson();
        Type type = new TypeToken<Foo<Employee>>(){}.getType();

        String jsonString = gson.toJson(foo, type);

        Log.i(TAG, jsonString);

        editor.putString(Constants.JSON_STRING_FOO,jsonString);
        editor.apply();


    }

    public void loadGenericType(View view) {


        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);

        String jsonData = sp.getString(Constants.JSON_STRING_FOO,"N/A");

        Log.i(TAG, jsonData);

//       deserialization
        Gson gson = new Gson();
        Type type = new TypeToken<Foo<Employee>>(){}.getType();

        Foo<Employee> employeeFoo = gson.fromJson(jsonData,type);

        Employee employee = employeeFoo.getObject();

        if (employee == null)
            return;

        String displayText =
                employee.getName()
                        +"\n" + employee.getProfession()
                        +"\n" + employee.getProId()
                        +"\n" + employee.getList().toString();

        txvDisplay.setText(displayText);



    }

    private Employee getEmployeeObject(){

        Employee employeeStore = new Employee();
        employeeStore.setName("rohan dhiman");
        employeeStore.setProfession("Android Developer");
        employeeStore.setProId(283);
        employeeStore.setList(Arrays.asList("Developer","admin"));
        return employeeStore;
    }

}
