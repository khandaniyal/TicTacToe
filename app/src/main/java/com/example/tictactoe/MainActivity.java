package com.example.tictactoe;

import static com.example.tictactoe.model.DefaultConstants.CONNECTION_KO;
import static com.example.tictactoe.model.DefaultConstants.CONNECTION_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    ThreadConnection conn;

    Button connect, start;
    EditText txtIP, txtPort;
    TextView txtResult;
    MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = findViewById(R.id.btnConnect);
        start = findViewById(R.id.btnStart);
        txtResult = findViewById(R.id.txtResult);
        txtIP = findViewById(R.id.txtIP);
        txtPort = findViewById(R.id.txtPort);

        start.setEnabled(false);
        instance = this;

        connect.setOnClickListener(e->{
            String ip = txtIP.getText().toString();
            int port = Integer.valueOf(txtPort.getText().toString());

            if(!ip.equals("") && port!= 0){
                conn = new ThreadConnection(ip, port, instance);
                conn.execute();

            }else{
                Toast.makeText(getApplicationContext(), "Ip o port", Toast.LENGTH_LONG).show();
            }
        });

        start.setOnClickListener(e->{

            Socket socket = conn.getSocket();
            ThreadNewGame newGame = null;
            try {
                newGame = new ThreadNewGame(socket, instance);
                newGame.execute();

            } catch (IOException exception) {
                exception.printStackTrace();
            }

            //go to game activity
            startActivity(new Intent(this, Game.class));
        });
    }

    public void updateUI(byte header){
        switch (header){
            case CONNECTION_OK:
                txtResult.setText("CONNETED OK");
                start.setEnabled(true);
                break;
            case CONNECTION_KO:
                txtResult.setText("CONNECTED KO");
                break;
        }
    }
}