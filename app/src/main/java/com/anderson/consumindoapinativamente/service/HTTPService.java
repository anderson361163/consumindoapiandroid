package com.anderson.consumindoapinativamente.service;

import android.os.AsyncTask;

import com.anderson.consumindoapinativamente.model.CEP;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HTTPService extends AsyncTask<Void, Void, CEP> {

    //RECEBE O CEP VIA ATRIBUTO
    private final String cep;


    public HTTPService(String cep){
        this.cep = cep;
    }

    //RESPONSAVEL PELO PROCESSO DE CONEXAO COM NOSSO WEB SERVICE
    @Override
    protected CEP doInBackground(Void... voids){

        StringBuilder resposta = new StringBuilder();

        //DEFINE A URL DO NOSSO WEB SERVICE
        //COMO ESSA URL PODE GERAR UMA EXCESSÃO COM FACILIDADE É NECESSÁRIO UMA TRY CATCH
        try {
            //ARMAZENA DENTRO DE UMA VARIAVEL LOCAL
            URL url = new URL("http://ws.matheuscastiglioni.com.br/ws/cep/find/" + this.cep + "/json/");

            //CRIA UMA CONEXAO HTTP PARA DEFINIR O RETORNO DO NOSSO WEB SERVICE, O TIPO DE MÉTODO QUE VAI SER UTILIZADO PARA FAZER O CONSUMO
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //AQUI DEFINE COMO CONFIGURAÇÃO O MÉTODO QUE VAI SER UTILIZADO PARA PEGAR AS INFORMAÇÕES, NO CASO O GET
            connection.setRequestMethod("GET");
            //DEFINE O TIPO DE RETORNO QUE O WEB SERVICE DEVE RETORNAR
            connection.setRequestProperty("Accept", "application/json");
            //DEFINE QUAL VAI SER O TEMPO MÁXIMO PARA O WEBSERVICE ESTABELECER UMA CONEXAO, CASO NÃO FAÇA ELE GERA UM EXCEPTION DE TIMEOUT
            connection.setConnectTimeout(5000);
            //AQUI ELE GERA A CONEXAO
            connection.connect();

            //PEGA A REPOSTA DO WEB SERVICE E TRANSFORMAR NUMA Stream, PODENDO SER LIDO PELA CLASSE SCANNER
            Scanner scanner = new Scanner(url.openStream());
            //ENQUANTO HAVER LINHAS DENTRO DO SCANNER ELE VAI ADICIONAR LINHAS DENTRO DA VARIAVEL RESPOSTA
            while(scanner.hasNext()){
                //ELE PEGA A LINHA ATUAL E INSERE NA STRING REPOSTA
                resposta.append(scanner.next());
            }

        }catch (MalformedURLException e){
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //PARA RETORNAR UM OBJETO JAVA PRECISAMOS TRAZER UM JAVA
        //DENTRO DO BUILD.GRADLE TEMOS QUE INSERIR NA ULTIMA LINHA DE IMPORTAR A SEGUINTE LINHA
        //  implementation 'com.google.code.gson:gson:2.8.5'
        //NA LINHA ABAIXO, ESTAMOS PEGANDO O OBJETO QUE ESTÁ EM JSON E CONVERTENDO PARA UMA CLASSE JAVA E RETORNANDO
        return new Gson().fromJson(resposta.toString(), CEP.class);
    }

}
