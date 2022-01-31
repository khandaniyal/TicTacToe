package com.example.tictactoe;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadNewGame extends AsyncTask<Void, Void, String> {
    Socket socket;
    private MainActivity instance;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ThreadNewGame(Socket socket, MainActivity instance) throws IOException{
        this.socket = socket;
        this.instance = instance;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());

    }

    @Override
    protected String doInBackground(Void... voids) {

        try{
            dos.writeByte(0x01);
            byte none = dis.readByte();
            int whoBegins = dis.readInt();

            if(whoBegins == 1){
                byte noneM = dis.readByte();
                int coord1 = dis.readInt();
                int coord2 = dis.readInt();
                Log.i("Coordinate 1 and 2", "" + coord1 + " " + coord2);
            }
            return "The Match has begun, the server goes first";
        }catch (IOException e) {e.printStackTrace();}

        return "";
    }
}
