package com.shubham.theshoppy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText nameEText;
    AutoCompleteTextView provinceView;
    Spinner spinner;
    RadioButton btnDesktop,btnLaptop;
    CheckBox ssd,printer;
    RadioButton none,add1, add2;
    RadioGroup deskLap;
    Button orderButton;

    // Using this for autocomplete the textview with provinces name
    ArrayList<String> provinces = new ArrayList<>();
    ArrayAdapter<String> provinceAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bind ui elements and set related actions
        setup();
    }

    //To store which brand is selected by storing the index into it
    int brandName =0;

    //Here User Interface is starts setting up
    private void setup() {
        province();


        //Here the spinner will be filled with the brand choice name
        spinner = findViewById(R.id.spinner);
        ArrayList<String> sizes = new ArrayList<String>();
        sizes.add("Dell");
        sizes.add("Lenovo");
        sizes.add("HP");
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sizes);
        spinner.setAdapter(sizeAdapter);


        // Now here the the selected brand name will be stored
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brandName =position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                brandName =0;
            }
        });

        //Here the elements are getting binded
        nameEText = findViewById(R.id.nameEText);
        btnDesktop = findViewById(R.id.desktop);
        btnLaptop = findViewById(R.id.laptop);
        ssd = findViewById(R.id.ssd);
        printer = findViewById(R.id.printer);

        none = findViewById(R.id.none);
        add1 = findViewById(R.id.add1);
        add2= findViewById(R.id.add2);
        deskLap = findViewById(R.id.deskLap);


        //When order button is clicked this function will execute
        setupOrder();
    }
    private void setupOrder() {
        orderButton = findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here the message string will be displayed
                String toastMessage ="";

                //this will check the whether the name field is filled or not
                if(nameEText.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "The name is empty...", Toast.LENGTH_SHORT).show();
                    return;
                }

                // This will check wheter the province name is entered or not
                if(provinceView.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "The province field is empty...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!provinces.contains(provinceView.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter correct province name...", Toast.LENGTH_SHORT).show();
                    return;
                }

                // here the customer order details with the cost will be displayed
                toastMessage+="Customer: "+ nameEText.getText() + "\n";
                toastMessage+= " Province: "+ provinceView.getText()+"\n";
                toastMessage+= " Brand: "+ spinner.getSelectedItem().toString() + "\n" + "Computer: " + (btnDesktop.isChecked() ? " Desktop": " Laptop") + ", " ;
                toastMessage+= (ssd.isChecked() || printer.isChecked()? (" \n Additionals: " + (ssd.isChecked()?"SSD":"") + (printer.isChecked()?(ssd.isChecked()? " and":"")+" Printer,":" ")) : "") + "";

                // here the messgae will be displayed based on the choice made by the customer
                if(btnDesktop.isChecked()){
                    toastMessage+= " \n Added Peripherals: " + (none.isChecked()?"No ":(add1.isChecked()?"Webcam":"External Hard Drive")) + "," ;
                }else{
                    toastMessage+= "\n Added Peripherals:  " + (none.isChecked()?"Cooling Mat":(add1.isChecked()?"USB C-HUB":"Laptop Stand")) + "," ;
                }

                //setting the cost formate and the calculate the cost
                float cost = calcualteCost();
                DecimalFormat df = new DecimalFormat("0.00");
                toastMessage+= "\n Cost: $"+ df.format(cost);
                //show message
                Toast.makeText(MainActivity.this,  toastMessage, Toast.LENGTH_LONG).show();
            }
        });

        //this will change the user interface base on the customer selection
        deskLap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.desktop:
                        none.setText("None");
                        add1.setText("Webcam");
                        add2.setText("External Hard Drive");

                        break;
                    case R.id.laptop:
                        none.setText("Cooling Mat");
                        add1.setText("USB C-HUB");
                        add2.setText("Laptop Stand");

                        break;
                }
            }
        });
    }

    // here the cost for the order will be calculated as per the choices made by the customer
    private float calcualteCost() {
        float cost = 0;
        if(brandName == 0) cost+= 1249;
        else if(brandName ==1) cost+= 1549;
        else cost+= 1150;

        if(ssd.isChecked()) cost+= 60;
        if(printer.isChecked()) cost+= 100;


        if(btnDesktop.isChecked()){
            if(none.isChecked()) cost+= 0;
            if(add1.isChecked()) cost+= 95;
            if(add2.isChecked()) cost+= 64;
        }else{
            if(none.isChecked()) cost+= 33;
            if(add2.isChecked()) cost+= 60;
            if(add2.isChecked()) cost+= 139;
        }

        cost+= cost*(0.13);
        return cost;
    }

    private void province() {
        provinceView = findViewById(R.id.provinceView);
        provinces.add("Alberta");
        provinces.add("British Columbia");
        provinces.add("New Brunswick");
        provinces.add("Newfoundlanad and Labrador");
        provinces.add("Northwest Territories");
        provinces.add("Nova Scotia");
        provinces.add("Ontario");
        provinces.add("Nunavut");
        provinces.add("Prince Edwards Island");
        provinces.add("Quebec");
        provinces.add("Saskatchewan");
        provinces.add("Nova Scotia");
        provinces.add("Yukon");
        provinceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,provinces);
        provinceView.setAdapter(provinceAdapter);

    }
}