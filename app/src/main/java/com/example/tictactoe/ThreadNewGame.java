package com.example.tictactoe;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadNewGame extends AsyncTask<Void, Void, String> {
    Socket socket;
    private MainActivity instance;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ThreadNewGame(Socket socket, MainActivity instance){
        this.socket = socket;
        this.instance = instance;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try{
            dos.writeByte(0x01);
            byte none = dis.readByte();
            int whoBegins = 1;

            if(whoBegins == 1){
                byte noneM = dis.readByte();
                int coord1 = dis.readInt();
                int coord2 = dis.readInt();
            }
            return "The Match has begun, the server goes first";
        }catch (IOException e) {e.printStackTrace();}

        return "";
    }
}
