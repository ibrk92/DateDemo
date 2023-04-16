package com.example.datedemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.datedemo.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Dog> DogList = new ArrayList<>();
    //Note that buildFeatures viewBinding must be set to true for binding to work
    ActivityMainBinding binding;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Method to read from file
        ReadDogData();

        Log.d("DATEDEMO", DogList.size() + " dogs read from file");


        DogAdapter adapter = new DogAdapter(DogList); // eger tiklaninca bir sey olmayacaksa bunu yazman yeterli

        //eger tiklaninca recycler view de bir yazi ciksin istiyosan asagidaki dog adapteri yazacaksin
        /*
        DogAdapter adapter = new DogAdapter(DogList, new DogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                binding.txtViewAdoptionSumary.setText("Thanks for taking " + DogList.get(i).getDogName() + "to their forever home");
            }
        });
        */
        binding.recyclerViewDogItems.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewDogItems.setAdapter(adapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ReadDogData() {

        DogList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.doginfo)));

        try {
            String csvLine;
            //eger baslik varsa bunu ayri olarak yapmalisin;
            //process header separately
            /*
            if ((csvLine = reader.readLine()) != null){
                // process header csvLine
            }*/

            while((csvLine = reader.readLine()) != null){
                    String[] dogFields = csvLine.split(","); // it will be separated based on the comma;
                    int id = Integer.parseInt(dogFields[0]);
                    String picName = dogFields[1];
                    String breedName = dogFields[2];
                    String dogName =  dogFields[3];
                    String dogDobStr = dogFields[4];
                    //get pic drawable -e.g., R.drawable.americanbulldog;

                int dogDrawable = getResources().getIdentifier(picName, "drawable", getPackageName());

                //d - date of month (8,21 etc) // yani tek basamaklilarda yanina 0 koymuyor
                //dd - two digit date (08,21 etc..)
                //MM - two digit month (01,02,12,etc.)
                //MMM - three digit letter code.
                //yy - two digit year (20,21,23)
                //yyyy - four digit year (2020,2021,2023)

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
                LocalDate dob = LocalDate.parse(dogDobStr, formatter);

                //create Dog object

                Dog eachDog = new Dog(id,breedName, dogName,dogDrawable,dob);
                DogList.add(eachDog);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}