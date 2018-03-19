package tdm2018.ittepic.edu.tdm2018_u2_225_prjtofprogrm;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DatosAlumno extends AppCompatActivity {
    public static int variable, varafi;
    public static int idusuario;
    TextView nombre, no_ctrl, cel, mail, carrera;
    String nombreact;
    String[] arreglo;
    TextView credito;
    BaseDatos dbms;
    ListView lvActividad;
    Integer var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_alumno);
        var = MainActivity.variable;
        dbms = new BaseDatos(this, "BASE2", null, 1);

        nombre = (TextView) findViewById(R.id.etactividad);
        credito = (TextView) findViewById(R.id.textView9);
        no_ctrl = (TextView) findViewById(R.id.no_ctrl);
        cel = (TextView) findViewById(R.id.telef);
        mail = (TextView) findViewById(R.id.mail);
        carrera = (TextView) findViewById(R.id.carrera);
        lvActividad =(ListView) findViewById(R.id.actividad);



        buscarConcepto();
        cargarActividad();
        nombreact = nombre.getText().toString();




        lvActividad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                variable=position;
                Intent ventanaPrincipal = new Intent(DatosAlumno.this,EditarActividad.class);
                startActivity(ventanaPrincipal);
            }
        });
    }


    public void cargarActividad(){
        try {
            SQLiteDatabase tabla = dbms.getReadableDatabase();
            String SQL = "SELECT * FROM ACTIVIDAD WHERE IDALUMNO ="+idusuario+"";
            Cursor resultado = tabla.rawQuery(SQL,null);
            if(resultado.moveToFirst()==true){
                int datos = resultado.getCount();
                arreglo = new String[datos];
                for(int i=0;i<datos;i++){
                    arreglo[i]="";
                    arreglo[i]= "\nActividad: "+resultado.getString(2)+"  \nFecha inicio: "+ resultado.getString(3)+"  \nFecha Fin: "+ resultado.getString(4)+"  \nCreditos: "+ resultado.getString(5);
                    resultado.moveToNext();
                }
            }
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arreglo);
            lvActividad.setAdapter(adaptador);

        }catch (Exception e){

        }
    }
    private void buscarConcepto(){
        try{
            SQLiteDatabase tabla = dbms.getReadableDatabase();
            String SQL = "SELECT * FROM ALUMNO ORDER BY NOMBRE ASC";
            Cursor resultado = tabla.rawQuery(SQL,null);
            if(resultado.moveToFirst()==true){
                for(int i=0;i<= var;i++){
                    idusuario = resultado.getInt(0);
                    nombre.setText(resultado.getString(1));
                    no_ctrl.setText(resultado.getString(2));
                    cel.setText(resultado.getString(3));
                    mail.setText(resultado.getString(4));
                    carrera.setText(resultado.getString(5));
                    resultado.moveToNext();
                }
            } else
                tabla. close();
        }catch (SQLiteException e){
            mensajes("Error en busqueda",e.getMessage());
        }
    }


    private void mensajes(String titulo, String mensaje){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(titulo).setMessage(mensaje);
        alerta.show();
    }


    public boolean onCreateOptionsMenu(Menu m){
        MenuInflater constructor = getMenuInflater();
        constructor.inflate(R.menu.menu_alumno,m);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem mi) {
        switch (mi.getItemId()){
            case R.id.editAlumno:
                Intent Ventana = new Intent(DatosAlumno.this,EditarAlumno.class);
                startActivity(Ventana);
                break;

            case R.id.AgregarActividad:
                Intent Ventana2 = new Intent(DatosAlumno.this,AgregarActividad.class);
                Ventana2.putExtra("id",idusuario);
                startActivity(Ventana2);
                break;

            case R.id.regresar:
                finish();
                Intent Ventana3 = new Intent(DatosAlumno.this,MainActivity.class);
                startActivity(Ventana3);
                break;
        }
        return true;

    }
}
