package com.example.leona.zweiseitigerhypothesentest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText n = (EditText)findViewById(R.id.editText3);
                EditText p = (EditText)findViewById(R.id.editText2);
                EditText lv = (EditText)findViewById(R.id.editText4);
                EditText rv = (EditText)findViewById(R.id.editText5);
                EditText sigma = (EditText)findViewById(R.id.editText6);


                try {
                    int n1 = Integer.parseInt(n.getText().toString());
                    Double p1 = Double.parseDouble(p.getText().toString());
                    Double lv1 = Double.parseDouble(lv.getText().toString());
                    Double rv1 = Double.parseDouble(rv.getText().toString());
                    Double sigma1 = Double.parseDouble(sigma.getText().toString());

                    Calculate c = new Calculate();

                    Double erwartungswert = c.getErwartungswert(p1, n1);
                    Double sigm = c.getSigma(n1, p1);
                    int[] sigmabereich = c.getSigmaBereich(sigm, erwartungswert, sigma1);
                    int a = c.findA(lv1, p1, n1, sigmabereich);
                    int b = c.findB(rv1, p1, n1, sigmabereich);
                    Double irrtumswahrscheinlichkeit = c.getIrrtum(a, b, n1, p1);

                    TextView tv = (TextView) findViewById(R.id.textView);
                    tv.setText("Der Erwartungswert beträgt " + erwartungswert + ". Sigma liegt bei " + sigm + ". Daraus folgt ein Sigma-Intervall von [" + sigmabereich[0] + ";" + sigmabereich[1] + "]. " + "Das Intervall liegt bei [" + a + ";" + b + "], die Irrtumswahrscheinlichkeit ist " + irrtumswahrscheinlichkeit + "%.");
                }
                catch (Exception i){

                }
            }
        });
    }
}
