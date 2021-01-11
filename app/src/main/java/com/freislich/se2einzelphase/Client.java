package com.freislich.se2einzelphase;
import java.net.*;
import java.io.*;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Observer observer;
    private String addr;
    private int port;
// test
    Client(Observer observer){
        this.observer=observer;
        this.addr="se2-isys.aau.at";
        this.port=53212;
    }

    public Observable<String> sendMessage(String message){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                try {
                    clientSocket = new Socket(addr, port);
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out.println(message);
                    String resp=in.readLine();
                    in.close();
                    out.close();
                    clientSocket.close();
                    observer.onNext(resp);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}
