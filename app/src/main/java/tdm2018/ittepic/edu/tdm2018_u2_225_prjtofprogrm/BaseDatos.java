package tdm2018.ittepic.edu.tdm2018_u2_225_prjtofprogrm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Jair on 07/03/2018.
 */

public class BaseDatos extends SQLiteOpenHelper {


    public BaseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE ALUMNO(IDALUMNO INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE VARCHAR(200) , NO_CTL INTEGER, TELEFONO INTEGER, CORREO VARCHAR(200), CARRERA VARCHAR(100))");
        db.execSQL("CREATE TABLE ACTIVIDAD(IDACTIVIDAD INTEGER PRIMARY KEY AUTOINCREMENT,IDALUMNO INTEGER , ACTIVIDAD1 VARCHAR(200), FECHA_INI VARCHAR(100),FECHA_FIN VARCHAR(100),CREDITOS INTEGER, FOREIGN KEY (IDALUMNO) REFERENCES ALUMNO(IDALUMNO) )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertDataUser(String nombre, String no_ctl, String telefono , String correo, String carrera){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO ALUMNO (nombre, no_ctl,telefono, correo, carrera) VALUES (? , ? , ? , ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, nombre);
        statement.bindString(2, no_ctl);
        statement.bindString(3, telefono);
        statement.bindString(4, correo);
        statement.bindString(5, carrera);
        statement.executeInsert();

    }
    public void insertDataActividad(String id,String nombreactividad, String fechain, String fechafin , String creditos){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO ACTIVIDAD (IDALUMNO, ACTIVIDAD1, FECHA_INI, FECHA_FIN, CREDITOS) VALUES (?, ? , ? , ? , ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, id);
        statement.bindString(2, nombreactividad);
        statement.bindString(3, fechain);
        statement.bindString(4, fechafin);
        statement.bindString(5, creditos);

        statement.executeInsert();

    }
    public void updateDataUser(String actual, String nombre, String no_ctl, String telefono , String correo, String carrera){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE ALUMNO SET NOMBRE = ?, NO_CTL = ? ,TELEFONO = ? , CORREO = ?, CARRERA = ? WHERE  NOMBRE ='"+actual+"'";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, nombre);
        statement.bindString(2, no_ctl);
        statement.bindString(3, telefono);
        statement.bindString(4, correo);
        statement.bindString(5, carrera);
        statement.executeInsert();
    }

    public void updateActividad(String idactividad, String idalumno, String nombre, String fechai, String fechaf, String credito){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE ACTIVIDAD SET ACTIVIDAD1 = ?, FECHA_INI = ?, FECHA_FIN = ?, CREDITOS =  ? WHERE IDACTIVIDAD =? AND IDALUMNO = ?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, nombre);
        statement.bindString(2, fechai);
        statement.bindString(3, fechaf);
        statement.bindString(4, credito);
        statement.bindString(5, idactividad);
        statement.bindString(6, idalumno);



        statement.executeInsert();
    }
}
