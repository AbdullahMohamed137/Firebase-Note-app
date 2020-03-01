package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.icu.text.DateFormatSymbols;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DatabaseReference myRef = null;
    ArrayList<Note> notelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Notes");


        final FloatingActionButton A = findViewById(R.id.add_new_note);

        // creat variable من نوع AlertDialog.Builder
        final AlertDialog.Builder Alert = new AlertDialog.Builder(this);


        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // creat variable
                View view = getLayoutInflater().inflate(R.layout.add_note, null);
                Alert.setView(view);
                final AlertDialog build = Alert.create();
                build.show();

                final Button b = view.findViewById(R.id.add_note);
                final EditText E = view.findViewById(R.id.edit_note);
                final EditText T = view.findViewById(R.id.edit_title);


                //String mdformat = new SimpleDateFormat("EEEE hh : mm a").toString();
                //final String strData = mdformat.format(Calendar.t);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String note = E.getText().toString();
                        String title = T.getText().toString();


                        if (title.isEmpty() && note.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_LONG).show();
                        } else {
                            //final TextView time = findViewById(R.id.time_text_view);
                            //Calendar rightNow = Calendar.getInstance();
                            //time.setText(String.format("EEEE hh : mm a",rightNow));

                            String id = myRef.push().getKey();
                            Note mynote = new Note(id, title, note, CurrentDate());
                            myRef.child(id).setValue(mynote);
                            build.dismiss();
                        }

                    }
                });


            }
        });

        // open item ************************************************************
        final ListView list = findViewById(R.id.listview);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Note n = notelist.get(position);
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra("Date_key", n.getDate());
                intent.putExtra("id_key", n.getId());
                intent.putExtra("Title_key", n.getTitle());
                intent.putExtra("Note_key", n.getNote());
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        final ListView L = findViewById(R.id.listview);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // to  creat  one child
                // Note x = dataSnapshot.child("-M0h9vbkJkdq9-X-0f3h").getValue(new Note().getClass());
                //notelist.add(x);
                //Toast.makeText(getApplicationContext(),"notelist.get(0).gettitle",Toast.LENGTH_LONG).show();

                //ArrayList<String> x = new ArrayList();
                // Map<String,Note> x = new Map<String, Note>()

                Iterable<DataSnapshot> x = dataSnapshot.getChildren();
                notelist.clear();
                for (DataSnapshot i : x) {
                    Note y = i.getValue(Note.class);
                    notelist.add(0, y); // 0 cuz put last note in first row ************************
                }

                BaseAdapter baseAdapter = new BaseAdapter(getApplicationContext(), notelist);
                L.setAdapter(baseAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String CurrentDate() {
        Date calendar = Calendar.getInstance().getTime();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return s.format(calendar);
    }







    /*private String numberToText(int number){
        return String.valueOf(number);
    }*/

    /*private int textToNumber(String text){
        return Integer.valueOf(text);
    }*/
}
