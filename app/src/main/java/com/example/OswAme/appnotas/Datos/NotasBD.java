package com.example.OswAme.appnotas.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotasBD extends SQLiteOpenHelper{

    //CONSTRUCCION DE LA BD;
    private static final String DB_NAME = "db_notes";
    private static final int DB_VERSION = 2;



    public static final String[]COLUMNS_REGISTROS = {"_id","tipo","titulo","descripcion","fecha_creacion","fecha_limite","hora_limite","checalo"};
    public static final String TABLE_REGISTROS_NAME="registros";

    private  final String TABLE_REGISTROS = "create table registros ("+
            COLUMNS_REGISTROS[0]+" integer primary key autoincrement, "+
            COLUMNS_REGISTROS[1]+" integer not null," +
            COLUMNS_REGISTROS[2]+" varchar(100) not null,"+
            COLUMNS_REGISTROS[3]+" text not null,"+
            COLUMNS_REGISTROS[4]+" varchar(12) not null,"+
            COLUMNS_REGISTROS[5]+" varchar(12) null,"+
            COLUMNS_REGISTROS[6]+" varchar(12) null,"+
            COLUMNS_REGISTROS[7]+" integer null);";



    public static final String[]COLUMNS_NOTIFICACIONES = {"_id","id_Tarea","titulo","descripcion","fechaAlerta","horaAlerta"};
    public static final String TABLE_NOTIFICACIONES_NAME="notificaciones";

    private  final String TABLE_NOTIFICACIONES = "create table NOTIFICACIONES ("+
            COLUMNS_NOTIFICACIONES[0]+" integer primary key autoincrement, "+
            COLUMNS_NOTIFICACIONES[1]+" integer not null," +
            COLUMNS_NOTIFICACIONES[2]+" varchar(100) not null,"+
            COLUMNS_NOTIFICACIONES[3]+" text not null,"+
            COLUMNS_NOTIFICACIONES[4]+" varchar(12) null,"+
            COLUMNS_NOTIFICACIONES[5]+" varchar(12) null);";



    public static final String[]COLUMNS_FOTOS = {"_id","id_Tarea","dirUri","descripcion"};
    public static final String TABLE_FOTOS_NAME="FOTOS";

    private  final String TABLE_FOTOS = "create table FOTOS ("+
            COLUMNS_FOTOS[0]+" integer primary key autoincrement, "+
            COLUMNS_FOTOS[1]+" integer not null," +
            COLUMNS_FOTOS[2]+" varchar not null,"+
            COLUMNS_FOTOS[3]+" varchar(100) null);";


    public static final String[]COLUMNS_VIDEOS = {"_id","id_Tarea","dirUri","descripcion"};
    public static final String TABLE_VIDEOS_NAME="VIDEOS";

    private  final String TABLE_VIDEOS = "create table VIDEOS ("+
            COLUMNS_VIDEOS[0]+" integer primary key autoincrement, "+
            COLUMNS_VIDEOS[1]+" integer not null," +
            COLUMNS_VIDEOS[2]+" varchar not null,"+
            COLUMNS_VIDEOS[3]+" varchar(100) null);";


    public NotasBD(Context contexto) {

        super(contexto, DB_NAME, null, DB_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL(TABLE_REGISTROS);
            sqLiteDatabase.execSQL(TABLE_NOTIFICACIONES);
            sqLiteDatabase.execSQL(TABLE_FOTOS);
            sqLiteDatabase.execSQL(TABLE_VIDEOS);

        }catch (Exception err){}

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS registros");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS notificaciones");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS fotos");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS videos");

            onCreate(sqLiteDatabase);

        }catch (Exception err){}
    }
}
