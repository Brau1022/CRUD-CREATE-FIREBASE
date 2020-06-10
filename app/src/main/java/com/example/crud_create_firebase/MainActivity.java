package com.example.crud_create_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.crud_create_firebase.Model.Persona;

public class MainActivity extends AppCompatActivity {

    private List<Persona> listPerson = new ArrayList<Persona>();
    ArrayAdapter<Persona> ArrayAdapterpersona;

    EditText nomP,appP,correoP,passwordP;
    ListView listV_personas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Persona personaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomP = findViewById(R.id.nombrePersona);
        appP = findViewById(R.id.apellidoPersona);
        correoP = findViewById(R.id.correoPersona);
        passwordP = findViewById(R.id.passwordPersona);

        listV_personas = findViewById(R.id.lv1);

        initializarFirebase(); //always on top to reach all data bellow.
        listarDatos();

        listV_personas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSeleccionada = (Persona) parent.getItemAtPosition(position);
                nomP.setText(personaSeleccionada.getNombre());
                appP.setText(personaSeleccionada.getApellido());
                correoP.setText(personaSeleccionada.getCorreo());
                passwordP.setText(personaSeleccionada.getPassword());
            }
        });

    }

    private void listarDatos() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPerson.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Persona p = objSnapshot.getValue(Persona.class);
                    listPerson.add(p);

                    ArrayAdapterpersona = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1,listPerson);
                    listV_personas.setAdapter(ArrayAdapterpersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //to put menu icons into the main menu.
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //switch

        String nombre = nomP.getText().toString();
        String apellido = appP.getText().toString();
        String password = passwordP.getText().toString();
        String correo = correoP.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add:{
                if (nombre.equals("")||correo.equals("")||apellido.equals("")||password.equals("")){
                    validation();

                }
                else {

                    Persona p = new Persona();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellido(apellido);
                    p.setCorreo(correo);
                    p.setPassword(password);
                    databaseReference.child("Persona").child(p.getUid()).setValue(p);

                    Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
                    cleantxt();

                }
                break;
            }
            case R.id.icon_save:{
                if (nombre.equals("")||correo.equals("")||apellido.equals("")||password.equals("")){
                    validation();

                }
                else {

                    Persona p = new Persona();
                    p.setUid(personaSeleccionada.getUid());
                    p.setNombre(nomP.getText().toString().trim());
                    p.setApellido(appP.getText().toString().trim());
                    p.setCorreo(correoP.getText().toString().trim());
                    p.setPassword(passwordP.getText().toString().trim());
                    databaseReference.child("Persona").child(p.getUid()).setValue(p);

                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                    cleantxt();
                }
                break;
            }
            case R.id.icon_delete:{
                if (nombre.equals("")||correo.equals("")||apellido.equals("")||password.equals("")){
                    validation();

                }
                else {
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                    cleantxt();
                }
                break;
            }
        }
        return true;
    }

    private void cleantxt() {
        nomP.setText("");
        appP.setText("");
        correoP.setText("");
        passwordP.setText("");
    }

    private void validation() {
        String nombre = nomP.getText().toString();
        String apellido = appP.getText().toString();
        String password = passwordP.getText().toString();
        String correo = correoP.getText().toString();

        if(nombre.equals("")){
            nomP.setError("Required");
        }
        else if (apellido.equals("")){
            appP.setError("Required");
        }
        else if (correo.equals("")){
            correoP.setError("Required");
        }
        else if (password.equals("")){
            passwordP.setError("Required");
        }
    }
}