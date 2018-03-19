package tdm2018.ittepic.edu.tdm2018_u2_225_prjtofprogrm;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarAlumno extends AppCompatActivity {
    public static int idusuario, variable;
    EditText nombre, control, telefono, correo, carrera;
    BaseDatos dbms;
    Button guardar, cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alumno);
        dbms = new BaseDatos(this, "BASE2", null,1);

        nombre=(EditText)findViewById(R.id.etalumno);
        control=(EditText)findViewById(R.id.etcontrol);
        telefono=(EditText)findViewById(R.id.ettelefono);
        correo=(EditText)findViewById(R.id.edtcorreo);
        carrera=(EditText)findViewById(R.id.carrera);
        guardar=(Button) findViewById(R.id.agregar);
        cancelar=(Button)findViewById(R.id.cancelar);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDatos();
                Intent ventanaPrincipal = new Intent(AgregarAlumno.this,MainActivity.class);
                startActivity(ventanaPrincipal);
            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ventanaPrincipal = new Intent(AgregarAlumno.this,MainActivity.class);
                startActivity(ventanaPrincipal);
            }
        });


    }
    public void insertarDatos(){
        try{
            if(nombre !=null){
                dbms.insertDataUser(
                        nombre.getText().toString().trim(),
                        control.getText().toString().trim(),
                        telefono.getText().toString().trim(),
                        correo.getText().toString().trim(),
                        carrera.getText().toString().trim()

                );
                Toast.makeText(this, "¡Se agregó un alumno!", Toast.LENGTH_LONG).show();
                asignarIdUsuario();

            }else{mensajes("¡Atención!","Algun campo esta vacio");}
        }catch (SQLException e){
            mensajes("Sucedió un error",e.getMessage());
        }
    }

    public void asignarIdUsuario(){
        try {

            SQLiteDatabase tabla = dbms.getReadableDatabase();
            String SQL = "SELECT * FROM ALUMNO WHERE NOMBRE = '"+nombre.getText().toString()+"' ORDER BY NOMBRE ASC";
            Cursor resultado = tabla.rawQuery(SQL,null);
            if(resultado.moveToFirst()==true){
                idusuario = resultado.getInt(0);
                Toast.makeText(this, resultado.getString(0), Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            mensajes("Error",e.getMessage());
        }
    }


    private void mensajes(String titulo, String mensaje){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(titulo).setMessage(mensaje);
        alerta.show();
    }

}
