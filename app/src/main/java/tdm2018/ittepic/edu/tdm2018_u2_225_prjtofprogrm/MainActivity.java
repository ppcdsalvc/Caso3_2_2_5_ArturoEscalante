package tdm2018.ittepic.edu.tdm2018_u2_225_prjtofprogrm;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class MainActivity extends AppCompatActivity {
    public static int variable;
    BaseDatos dbms;
    String[] arreglo;
    ListView lista;
    String actividad[], contenido[];
    int ids[],idEstudiante,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idEstudiante = DatosAlumno.idusuario;
        dbms = new BaseDatos(this, "BASE2", null,1);
        lista = (ListView)findViewById(R.id.datos);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                variable = position;
                Intent ventanaEstudiante = new Intent(MainActivity.this,DatosAlumno.class);
                startActivity(ventanaEstudiante);
            }
        });
        cargarConceptos();

    }

    public void cargarConceptos(){

        BaseDatos baseHelper = new BaseDatos(this,"BASE2",null,1);
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        if (db!=null){
            Cursor c = db.rawQuery("select ALUMNO.IDALUMNO, ALUMNO.NOMBRE, ALUMNO.NO_CTL from ALUMNO " +
                    "ORDER BY ALUMNO.NOMBRE ASC;", null);

            int cantidad = c.getCount();
            int i=0;
            String linea2="";

            arreglo =  new String[cantidad];
            if (c.moveToFirst()){
                do {
                    String linea =c.getString(1);
                    Cursor c2 = db.rawQuery("select sum(ACTIVIDAD.CREDITOS) from ACTIVIDAD where ACTIVIDAD.IDALUMNO='"+c.getString(0)+"';", null);
                    if (c2.moveToFirst()){
                        do{
                            if (c2.getString(0) == null){
                                linea2="0";
                            }else {
                                linea2 =c2.getString(0);
                            }
                        }while (c2.moveToNext());
                    }
                    arreglo[i] = "\n"+linea+"  \n \nCreditos Totales: " + linea2+"\n";
                    i++;

                }while (c.moveToNext());

            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arreglo);
        ListView lista = (ListView)findViewById(R.id.datos);
        lista.setAdapter(adapter);
    }


    private void mensajes(String titulo, String mensaje){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(titulo).setMessage(mensaje);
        alerta.show();
    }

    public boolean onCreateOptionsMenu(Menu m){
        MenuInflater constructor = getMenuInflater();
        constructor.inflate(R.menu.menu,m);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem mi) {
        switch (mi.getItemId()){
            case R.id.agregarAlumno:
                Intent Ventana = new Intent(MainActivity.this,AgregarAlumno.class);

                startActivity(Ventana);
                break;



            case R.id.salir:
                finish();
                break;
        }
        return true;
    }

}
