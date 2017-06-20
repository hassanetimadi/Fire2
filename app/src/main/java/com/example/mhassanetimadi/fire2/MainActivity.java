package com.example.mhassanetimadi.fire2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.print.PrintAttributes;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String prefix ="E-";
    int num = 0;
    private Button add;
    private EditText value;
    private TextView mname;
    private Button retrieve;
    private DatabaseReference mdatabaseadd;
    private DatabaseReference mdatabaseretrieve;
    private DatabaseReference mdatabaseaddNotification;


    public final int id = 5445;
    Notification.Builder mynotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mynotification = new Notification.Builder(this);
        mynotification.setAutoCancel(true);

        add = (Button)findViewById(R.id.Firebase);
        value = (EditText)findViewById(R.id.edt_value);
        retrieve = (Button)findViewById(R.id.retrieve);
        mname = (TextView)findViewById(R.id.tv_name);
        mdatabaseadd = FirebaseDatabase.getInstance().getReference().child("users");
        mdatabaseretrieve = FirebaseDatabase.getInstance().getReference().child("users");
        mdatabaseaddNotification = FirebaseDatabase.getInstance().getReference().child("Notification");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num += 1;
                String text = value.getText().toString();
                String id = prefix + num;
                DatabaseReference users = mdatabaseadd.child(id);
                users.child("name").setValue(text);
                users.child("id").setValue(id);



                DatabaseReference notification = mdatabaseaddNotification.child("100");
                notification.child("id").setValue("101");
                notification.child("to").setValue("ahmad");


                  }
                });

        mdatabaseaddNotification.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                mynotification.setSmallIcon(R.mipmap.deer);
                mynotification.setTicker("this is my ticker");
                mynotification.setWhen(System.currentTimeMillis());
                mynotification.setContentTitle("new task assigned");
                mynotification.setContentText("see the assigned task.");

                Intent i = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent pi =PendingIntent.getActivities(MainActivity.this,0, new Intent[] {i},PendingIntent.FLAG_UPDATE_CURRENT);


                mynotification.setContentIntent(pi);
                NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                nm.notify(id,mynotification.build());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       // retrieving data

      /*  retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ret1= mdatabaseretrieve.child("E-1");
                DatabaseReference ret2 = ret1.child("Name");

                 ret2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.getValue().toString();
                        mname.setText(name);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

        });

        */

    }
}
