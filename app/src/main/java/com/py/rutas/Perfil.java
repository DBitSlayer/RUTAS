package com.py.rutas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import  com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;

public class Perfil extends AppCompatActivity {
    //private FirebaseUser user;
    //private DatabaseReference reference;

   // private String userID;

   private Button logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        logout=(Button)findViewById(R.id.signOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Perfil.this,MainActivity.class));
            }
        });
     //  user = FirebaseAuth.getInstance().getCurrentUser();
       // reference = FirebaseDatabase.getInstance().getReference("Users");
        //userID = user.getUid();

       // final TextView emailTextView = (TextView) findViewById(R.id.correo);

       // reference.child(userID).addValueEventListener(new ValueEventListener() {
          //  @Override
          //  public void onDataChange(DataSnapshot dataSnapshot) {
            //    Usuario userProfile = dataSnapshot.getValue(Usuario.class);
             //   if(userProfile != null){
               //     String email = userProfile.correo;

                 //   emailTextView.setText(email);
             //   }

          //  }

        //    @Override
          //  public void onCancelled(DatabaseError databaseError) {
             //   Toast.makeText(Perfil.this, "Ocurrio un error", Toast.LENGTH_LONG).show();

          //  }
     //  });


    }
}