package com.projects.cristianzapata.ejerciciospref3;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView puntaje;
    private int valorAleatorio;
    int valorSeleccionado, puntajeActual;
    NumberPicker numPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se obtiene valor aleatorio
        valorAleatorio = randomValue();
        numPicker = (NumberPicker)findViewById(R.id.numPicker);
        puntaje = (TextView) findViewById(R.id.puntos);

        //Se mira si hay preferencias
        SharedPreferences pref = getSharedPreferences("puntaje", Context.MODE_PRIVATE);
        puntajeActual=pref.getInt("Valor",0);
//        puntaje.setInputType(puntajeActual);
        puntaje.setText(Integer.toString(puntajeActual));

        numPicker.setMinValue(1);
        numPicker.setMaxValue(50);
        numPicker.setWrapSelectorWheel(true);

        numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                valorSeleccionado = newVal;
            }
        });
    }

    public void verificar(View v){

        if(valorSeleccionado > valorAleatorio){
            Toast.makeText(this,"El número a adivinar es menor",Toast.LENGTH_SHORT).show();
        }
        else if(valorSeleccionado < valorAleatorio){
            Toast.makeText(this,"El número a adivinar es mayor",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Ha adivinado el número",Toast.LENGTH_SHORT).show();

            //Se aumenta el puntaje
            puntajeActual++;
            SharedPreferences pref = getSharedPreferences("puntaje", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("Valor",puntajeActual);
            editor.commit();


            //Se pone otro valor aleatorio
            valorAleatorio = randomValue();
        }
        //Se pone el valor
        puntaje = (TextView) findViewById(R.id.puntos);
        puntaje.setText(Integer.toString(puntajeActual));
    }

    public int randomValue(){
        Random random = new Random();
        int randomV =1+ random.nextInt(49);
        return randomV;
    }
}
