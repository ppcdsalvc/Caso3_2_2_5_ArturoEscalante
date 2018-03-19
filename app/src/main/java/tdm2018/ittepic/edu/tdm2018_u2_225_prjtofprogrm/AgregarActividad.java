package tdm2018.ittepic.edu.tdm2018_u2_225_prjtofprogrm;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarActividad extends AppCompatActivity {
    EditText nombreactividad,fechain,fechafin,creditos;
    Button guardar, cancelar;
    BaseDatos dbms;
    String idusuario2;
    public static String idusuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_actividad);
        dbms = new BaseDatos(this, "BASE2", null,1);
        nombreactividad=findViewById(R.id.tituloactividad);
        fechafin=findViewById(R.id.fechafin);
        fechain=findViewById(R.id.fechainicio);
        creditos=findViewById(R.id.creditos);
        guardar=findViewById(R.id.agregar);
        cancelar=findViewById(R.id.cancelar);
        Intent i = getIntent();

        idusuario2=i.getExtras().getInt("id")+"";

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDatos();
                Intent ventanaPrincipal = new Intent(AgregarActividad.this,MainActivity.class);
                startActivity(ventanaPrincipal);
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ventanaPrincipal = new Intent(AgregarActividad.this,MainActivity.class);
                startActivity(ventanaPrincipal);
            }
        });
    }
    public void insertarDatos(){
        try{
            if(nombreactividad !=null){
                dbms.insertDataActividad(
                        idusuario2,
                        nombreactividad.getText().toString().trim(),
                        fechain.getText().toString().trim(),
                        fechafin.getText().toString().trim(),
                        creditos.getText().toString().trim()

                );
                Toast.makeText(this, "¡Se agregó una actividad!", Toast.LENGTH_LONG).show();


            }else{mensajes("¡Atención!","Algun campo esta vacio");}
        }catch (SQLException e){
            mensajes("Sucedió un error",e.getMessage());
        }
    }




    private void mensajes(String titulo, String mensaje){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(titulo).setMessage(mensaje);
        alerta.show();
    }

}
