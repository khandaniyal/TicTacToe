package com.example.tictactoe;

import static com.example.tictactoe.model.DefaultConstants.*;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ThreadConnection extends AsyncTask<Void, Void, Boolean> {

    String ip;
    int port;
    Socket socket;

    MainActivity instance;

    public ThreadConnection(String ip, int port, MainActivity instance){
        this.ip = ip;
        this.port = port;
        this.instance = instance;
    }

    /**
     * If the smartphone does not recive a response from the server this dialog ill block the screen until then
     * */
    ProgressDialog progressDialog;

    /**
     * shows an emerging window
     * */
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = new ProgressDialog(instance);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Connecting to server");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            //Connection to server
            InetAddress serverAddr = InetAddress.getByName(ip);
            Log.i("I/TCP Client", "Connecting...");
            socket = new Socket(serverAddr, port);
            Log.i("I/TCP Client", "Connected to server");

            return true;
        }catch (UnknownHostException ex) {
            Log.e("E/TCP Client", "" + ex.getMessage());
            return false;
        } catch (IOException ex) {
            Log.e("E/TCP Client", "" + ex.getMessage());
            return false;
        }
    }


    @Override
    protected void onPostExecute(Boolean response){
        progressDialog.dismiss();

        if(response == true){
            instance.updateUI(CONNECTION_OK);
        }else{
            instance.updateUI(CONNECTION_OK);
        }
    }
}