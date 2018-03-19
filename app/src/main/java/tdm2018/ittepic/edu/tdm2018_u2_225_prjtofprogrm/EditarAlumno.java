package tdm2018.ittepic.edu.tdm2018_u2_225_prjtofprogrm;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarAlumno extends AppCompatActivity {
    public static int idusuario,varafi;
    EditText nombre,control,telefono,correo,carrera;
    String nombreAct, conAct, telefonoAct, correAct, carreraAct;
    BaseDatos dbms;
    Integer var;
    Button actualizar, eliminar, cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_alumno);
        var = MainActivity.variable;
        dbms = new BaseDatos(this, "BASE2", null,1);

        nombre=findViewById(R.id.etalumno);
        control=findViewById(R.id.etcontrol);
        telefono=findViewById(R.id.ettelefono);
        correo=findViewById(R.id.edtcorreo);
        carrera=findViewById(R.id.carrera);
        actualizar= findViewById(R.id.btActualizar);
        eliminar= findViewById(R.id.btEliminar);
        cancelar= findViewById(R.id.cancelar);


        buscarDatosUsuario();

        nombreAct = nombre.getText().toString();
        conAct = control.getText().toString();
        telefonoAct = telefono.getText().toString();
        correAct= correo.getText().toString();
        carreraAct= carrera.getText().toString();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
            }
        });


        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarDatos();
            }
        });


    }


    private void buscarDatosUsuario(){
        try{
            SQLiteDatabase tabla = dbms.getReadableDatabase();
            String SQL = "SELECT * FROM ALUMNO ORDER BY NOMBRE ASC";
            Cursor resultado = tabla.rawQuery(SQL,null);
            if(resultado.moveToFirst()==true){
                for(int i=0;i<= var;i++){

                    idusuario = resultado.getInt(0);
                    nombre.setText(resultado.getString(1));
                    control.setText(resultado.getString(2));
                    telefono.setText(resultado.getString(3));
                    correo.setText(resultado.getString(4));
                    carrera.setText(resultado.getString(5));

                    resultado.moveToNext();
                }
            } else
                tabla. close();
        }catch (SQLiteException e){
            mensajes("Error en busqueda",e.getMessage());
        }
    }

    public void actualizarDatos(){
        try{
            dbms.updateDataUser(

                    nombreAct.toString(),

                    nombre.getText().toString().trim(),
                    control.getText().toString().trim(),
                    telefono.getText().toString().trim(),
                    correo.getText().toString().trim(),
                    carrera.getText().toString().trim()

            );
            Toast.makeText(this, "¡Se actualizarón los datos correctamente!", Toast.LENGTH_LONG).show();
            //tabla.close();
            nombre.setText("");control.setText("");telefono.setText("");correo.setText(""); carrera.setText("");
            Intent ventanaEstudiante = new Intent(EditarAlumno.this,DatosAlumno.class);
            startActivity(ventanaEstudiante);

        }catch (SQLException e){

            mensajes("Sucedio un error",e.getMessage());
        }
    }


    public void eliminarDatos(){
        try{
            SQLiteDatabase tabla = dbms.getWritableDatabase();
            String SQL = "DELETE FROM ALUMNO WHERE NOMBRE= '"+nombre.getText().toString()+"'";
            tabla.execSQL(SQL);
            tabla.close();
            Toast.makeText(this, "¡Alumno eliminado!", Toast.LENGTH_LONG).show();
            Intent ventanaEstudiante = new Intent(EditarAlumno.this,DatosAlumno.class);
            startActivity(ventanaEstudiante);

        }catch (SQLiteException e) {
            mensajes("ERROR",e.getMessage());
        }
    }

    private void mensajes(String titulo, String mensaje){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(titulo).setMessage(mensaje);
        alerta.show();
    }

}
