package com.example.unreal_kz.randomizer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Randomizer extends AppCompatActivity implements View.OnClickListener {

    private Button btnBegin;
    private ParseQuery<ParseObject> parseObjectParseQuery;
    private int totalPlayerNumber;
    private String usersCurId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomizer);

        ParseQuery<ParseObject> object = ParseQuery.getQuery("StayAliveUsers");
        try {
            totalPlayerNumber = object.count();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        btnBegin = (Button) findViewById(R.id.btnBegin);
        btnBegin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBegin:
                parseObjectParseQuery = ParseQuery.getQuery("StayAliveUsers");
                parseObjectParseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        int p_counter = 0;
                        if (e == null) {
                            for (ParseObject parseObject : list) {
                                p_counter = parseObject.getNumber("counter").intValue();
                                if (p_counter == 1) {
                                    try {
                                        parseObject.put("hunter_id", parseObjectParseQuery.
                                                whereEqualTo("counter", totalPlayerNumber).
                                                getFirst().getObjectId());
                                        parseObject.saveInBackground();
                                        btnBegin.setVisibility(View.GONE);
                                    } catch (ParseException exp) {
                                        Toast.makeText(Randomizer.this, exp.getMessage(), Toast.LENGTH_SHORT).show();
                                        exp.printStackTrace();
                                    }
                                } else {
                                    try {
                                        parseObject.put("hunter_id", parseObjectParseQuery.
                                                whereEqualTo("counter", p_counter - 1).getFirst().getObjectId());
                                        parseObject.saveInBackground();
                                    } catch (ParseException exp) {
                                        Toast.makeText(Randomizer.this, exp.getMessage() + "Something wromg1", Toast.LENGTH_SHORT).show();
                                        exp.printStackTrace();
                                    }
                                }
                            }
                            Toast.makeText(Randomizer.this, "GOOD", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Randomizer.this, e.getMessage() + "Something wromg2", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }
}