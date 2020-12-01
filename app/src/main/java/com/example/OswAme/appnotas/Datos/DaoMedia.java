package com.example.OswAme.appnotas.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DaoMedia {
    private Context _contexto;
    private SQLiteDatabase _midb;

    //CREACION DE LA DB;
    public DaoMedia(Context contexto){

        this._contexto = contexto;
        this._midb = new NotasBD(contexto).getWritableDatabase();

    }

    //INSERTAR MEDIA;
    public long insert(Media c){

        ContentValues cv = new ContentValues();

        cv.put(NotasBD.COLUMNS_FOTOS[1],c.getId_TareaNota());
        cv.put(NotasBD.COLUMNS_FOTOS[2],c.getDir_uri());
        cv.put(NotasBD.COLUMNS_FOTOS[3],c.getDescripMedia());

        return _midb.insert(NotasBD.TABLE_FOTOS_NAME,null,cv) ;

    }

    //ACTUALIZAR MEDIA;
    public long update(Media c){

        ContentValues cv = new ContentValues();

        cv.put(NotasBD.COLUMNS_FOTOS[1],c.getId_TareaNota());
        cv.put(NotasBD.COLUMNS_FOTOS[2],c.getDir_uri());
        cv.put(NotasBD.COLUMNS_FOTOS[3],c.getDescripMedia());

        return _midb.update(NotasBD.TABLE_FOTOS_NAME, cv, "_id=?", new String[] { String.valueOf( c.getId_media())});

    }

    //ELIMINAR MEDIA MEDIANTE ID;
    public int delete(String id){

        return  _midb.delete("media","_id='"+id+"'",null);

    }

    //ELIMINAR MEDIA ID TAREA;
    public int deleteTODAS(String id){

        return  _midb.delete("media","id_Tarea='"+id+"'",null);

    }

    //CREACION DE LISTA;
    public List<Media> buscarTodos() {

        List<Media> notesArrayList = new ArrayList<Media>();
        String selectQuery = "SELECT * FROM fotos";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                Media notas = new Media();
                notas.setId_media(c.getInt(c.getColumnIndex("_id")));
                notas.setId_TareaNota(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setDir_uri(c.getString(c.getColumnIndex("dirUri")));
                notas.setDescripMedia(c.getString(c.getColumnIndex("descripcion")));

                notesArrayList.add(notas);

            } while (c.moveToNext());

        }

        return notesArrayList;

    }

    //BUSQUEDA POR ID TAREA;
    public List<Media> buscarTodosDeTarea(int iden) {

        List<Media> notesArrayList = new ArrayList<Media>();
        String selectQuery = "SELECT * FROM fotos WHERE id_Tarea = '"+iden+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                Media notas = new Media();
                notas.setId_media(c.getInt(c.getColumnIndex("_id")));
                notas.setId_TareaNota(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setDir_uri(c.getString(c.getColumnIndex("dirUri")));
                notas.setDescripMedia(c.getString(c.getColumnIndex("descripcion")));

                notesArrayList.add(notas);

            } while (c.moveToNext());

        }

        return notesArrayList;

    }

    //BUSQUEDA POR ID;
    public Media buscarUno(int iden) {

        Media notesUno = new Media();
        String selectQuery = "SELECT * FROM fotos WHERE _id = '"+iden+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                Media notas = new Media();
                notas.setId_media(c.getInt(c.getColumnIndex("_id")));
                notas.setId_TareaNota(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setDir_uri(c.getString(c.getColumnIndex("dirUri")));
                notas.setDescripMedia(c.getString(c.getColumnIndex("descripcion")));

                notesUno = notas;

            } while (c.moveToNext());

        }

        return notesUno;

    }
}
