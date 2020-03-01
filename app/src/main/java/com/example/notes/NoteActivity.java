package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {
    DatabaseReference myRef = null;
    ArrayList<Note> notelist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        final TextView title = findViewById(R.id.Title_note_show);
        final TextView note = findViewById(R.id.note_show);
        final TextView time = findViewById(R.id.new_time_text_view);


        Bundle b = getIntent().getExtras();
        final String gom3a = b.getString("id_key");
        final String date = b.getString("Date_key");
        final String Title = b.getString("Title_key");
        final String Note = b.getString("Note_key");
        title.setText(Title);
        note.setText(Note);
        time.setText(date);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Notes");

        final AlertDialog.Builder Alert = new AlertDialog.Builder(this);
        ImageButton edit = findViewById(R.id.edit);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.update_note, null);

                final EditText editeTitle = view.findViewById(R.id.update_title);
                final EditText editNote = view.findViewById(R.id.updat__note);
                editeTitle.setText(Title);
                editNote.setText(Note);

                Alert.setView(view);
                final AlertDialog build = Alert.create();
                build.show();

                //final EditText ediT = findViewById(R.id.edit_title);
                //final EditText ediN = findViewById(R.id.edit_note);
                final Button update = view.findViewById(R.id.btn_update_note);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String Editetitle = editeTitle.getText().toString();
                        final String Editenote = editNote.getText().toString();

                        final String gom33a2 = CurrentDate();

                        if (Editetitle.isEmpty() && Editenote.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_LONG).show();
                        } else {
                            myRef.child(gom3a).child("title").setValue(Editetitle);
                            myRef.child(gom3a).child("note").setValue(Editenote);
                            myRef.child(gom3a).child("date").setValue(gom33a2);
                            build.dismiss();
                        }

                    }
                });

            }
        });
        final ImageButton delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(gom3a).removeValue();
                finish();
            }
        });

    }


    public String CurrentDate() {
        Date calendar = Calendar.getInstance().getTime();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return s.format(calendar);
    }
}
