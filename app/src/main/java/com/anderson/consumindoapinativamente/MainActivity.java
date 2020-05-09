package com.anderson.consumindoapinativamente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anderson.consumindoapinativamente.service.HTTPService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etCep = findViewById(R.id.etMain_cep);

        Button botao =  findViewById(R.id.btnMain_buscarCep);
        botao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(etCep.getText().toString().length() > 0
                   && !etCep.getText().toString().equals("")
                   && etCep.getText().toString().length() == 8){
                        //Agora importamos a classe HTTPService criada PARA DENTRO DO CÓDIGO QUE PRECISA CONSUMI-LA
                        //ENVIANDO O CÓDIGO DO CEP DENTRO DO PARAMETRO DA CLASSE
                        HTTPService service = new HTTPService(etCep.getText().toString());
                }
            }
        });

    }


}
