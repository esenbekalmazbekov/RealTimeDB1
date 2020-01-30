package com.example.realtimedatabase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText editTextName;
    Button buttonAdd,btnChange,btnDelete,btnRetrieve;
    Spinner spinnerGenres;

    RecyclerView recyclerView;
    DatabaseReference databaseArtists;

    List<Artist> artistList;

    FirebaseRecyclerOptions<Artist> options;
    FirebaseRecyclerAdapter<Artist,MyRecycleViewHolder> adapter;

    Artist selectedArtist;
    String selectedKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        buttonAdd = findViewById(R.id.buttonAddArtists);
        btnChange = findViewById(R.id.buttonChangeArtists);
        btnDelete = findViewById(R.id.buttonDeleteArtists);
        spinnerGenres = findViewById(R.id.spinnerGenres);
        recyclerView = findViewById(R.id.listViewArtists);
        btnRetrieve = findViewById(R.id.buttonRetrieveToken);

        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(adapter != null)
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        artistList = new ArrayList<>();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtist();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseArtists
                        .child(selectedKey)
                        .setValue(new Artist(
                                selectedKey,
                                editTextName.getText().toString(),
                                spinnerGenres.getSelectedItem().toString())
                        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Updated !",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,"" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseArtists
                        .child(selectedKey)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Deleted !",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"" + e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                        channelName, NotificationManager.IMPORTANCE_LOW));
            }
        }

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get token
                // [START retrieve_current_token]
                FirebaseMessaging.getInstance().subscribeToTopic("weather")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg = getString(R.string.msg_subscribed);
                                if (!task.isSuccessful()) {
                                    msg = getString(R.string.msg_subscribe_failed);
                                }
                                Log.d(TAG, msg);
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                // [END retrieve_current_token]
            }
        });

    }


    private void showComment() {
        options = new FirebaseRecyclerOptions.Builder<Artist>()
                .setQuery(databaseArtists,Artist.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Artist, MyRecycleViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecycleViewHolder holder, int position, @NonNull final Artist model) {
                holder.txt_title.setText(model.getArtistName());
                holder.txt_comment.setText(model.getArtistGenre());

                holder.setiItemClickListener(new IItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        selectedArtist = Objects.requireNonNull(model);
                        selectedKey = getSnapshots().getSnapshot(position).getKey();
                        Log.d("Key Item","" + selectedKey);

                        // Bind data
                        editTextName.setText(model.getArtistName());
                        for (int i = 0;i < 4;i++)
                            if(spinnerGenres.getItemAtPosition(i).equals(model.getArtistName()))
                                spinnerGenres.setSelected(true);
                    }
                });

            }

            @NonNull
            @Override
            public MyRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_list,parent,false);
                return new MyRecycleViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void addArtist() {
        String name = editTextName.getText().toString();
        String genre = spinnerGenres.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){
            String id = databaseArtists.push().getKey();

            Artist artist = new Artist(id, name, genre);

            if (id != null) {
                databaseArtists.child(id).setValue(artist);
            }

            Toast.makeText(this,"Artists added",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"You should enter name!",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        showComment();
    }

    @Override
    protected void onStop() {
        if(adapter != null)
            adapter.stopListening();
        super.onStop();
    }
}
