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

public class EditarActividad extends AppCompatActivity {
    EditText actividad, etFecha_Ini, etFecha_Fin, credito;
    Integer idmedi, idusuario,posicion;
    Button actualizar, eliminar, cancelar;
    String nombreAct;
    BaseDatos dbms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_actividad);
        dbms = new BaseDatos(this, "BASE2", null,1);

        actividad = findViewById(R.id.edtActividad);
        etFecha_Ini = findViewById(R.id.edtFechaI);
        etFecha_Fin = findViewById(R.id.edtFechaF);
        credito = findViewById(R.id.edtCredito);
        actualizar= findViewById(R.id.btActualizar);
        eliminar= findViewById(R.id.btEliminar);
        cancelar=findViewById(R.id.cancelar);

        nombreAct = actividad.getText().toString();

        posicion=DatosAlumno.variable;
        idusuario=DatosAlumno.idusuario;

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarActividad();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaPrincipal = new Intent(EditarActividad.this,DatosAlumno.class);
                startActivity(ventanaPrincipal);
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarActividad();
            }
        });
        buscarActividad();
    }

    public void buscarActividad(){
        SQLiteDatabase tabla = dbms.getReadableDatabase();
        String SQL = "SELECT * FROM ACTIVIDAD WHERE IDALUMNO = '"+idusuario+"'";
        Cursor resultado = tabla.rawQuery(SQL,null);
        if(resultado.moveToFirst()==true){
            for(int i=0;i<= posicion;i++){
                idmedi=resultado.getInt(0);

                actividad.setText(resultado.getString(2));
                etFecha_Ini.setText(resultado.getString(3));
                etFecha_Fin.setText(resultado.getString(4));
                credito.setText(resultado.getString(5));

                resultado.moveToNext();
            }
        }

    }
    public void actualizarActividad(){
        try{
            dbms.updateActividad(


                    idmedi.toString(),
                    idusuario.toString(),
                    actividad.getText().toString().trim(),
                    etFecha_Ini.getText().toString().trim(),
                    etFecha_Fin.getText().toString().trim(),
                    credito.getText().toString().trim()


            );
            Toast.makeText(this, "¡Se actualizo la actividad!", Toast.LENGTH_LONG).show();
            actividad.setText("");etFecha_Ini.setText("");etFecha_Fin.setText("");credito.setText("");
            Intent ventanaEstudiante = new Intent(EditarActividad.this,DatosAlumno.class);
            startActivity(ventanaEstudiante);

        }catch (SQLException e){

            mensajes("Sucedio un error",e.getMessage());
        }

    }

    public void eliminarActividad(){
        SQLiteDatabase tabla = dbms.getWritableDatabase();
        String SQL = "DELETE FROM ACTIVIDAD WHERE IDACTIVIDAD= '"+idmedi+"'";
        tabla.execSQL(SQL);
        tabla.close();
        Toast.makeText(this, "¡Actividad eliminada!", Toast.LENGTH_LONG).show();
        Intent ventanaEstudiante = new Intent(EditarActividad.this,DatosAlumno.class);
        startActivity(ventanaEstudiante);
    }

    private void mensajes(String titulo, String mensaje){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(titulo).setMessage(mensaje);
        alerta.show();
    }


}
