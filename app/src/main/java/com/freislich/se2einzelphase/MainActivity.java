package com.freislich.se2einzelphase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Client client;
    EditText editText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client=new Client(myObserver);
        editText= (EditText) findViewById(R.id.editText);
        textView=findViewById(R.id.textView3);
    }
    private Observer myObserver = new Observer<String>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull String s) {
            textView.setText(s);
        }

        @Override
        public void onError(@NonNull Throwable e) {

        }

        @Override
        public void onComplete() {
            System.out.println("I'm done!");
        }
    };
    public void send(View view){
        Observable<String> myobs = client.sendMessage(editText.getText().toString());
        myobs.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(myObserver);
    }
    public void calc(View view){
        textView.setText(task1(editText.getText().toString()));
    }
    public String task1(String matr){
        char[] arr= matr.toCharArray();
        matr="";
        for(int i=0;i<arr.length;i++){
            if(i%2==1){
                if(arr[i]!='0'){
                    arr[i]=(char)(arr[i]+48);
                }else{
                    arr[i]='j';
                }
            }
            matr+=arr[i];
        }
        return matr;
    }
}
