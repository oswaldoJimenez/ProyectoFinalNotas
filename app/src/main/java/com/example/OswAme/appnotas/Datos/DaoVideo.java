package com.example.OswAme.appnotas.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DaoVideo {

    private Context _contexto;
    private SQLiteDatabase _midb;


    public DaoVideo(Context contexto){

        this._contexto = contexto;
        this._midb = new NotasBD(contexto).getWritableDatabase();

    }


    public long insert(Media c){

        ContentValues cv = new ContentValues();

        cv.put(NotasBD.COLUMNS_MEDIA[1],c.getId_TareaNota());
        cv.put(NotasBD.COLUMNS_MEDIA[2],c.getDir_uri());
        cv.put(NotasBD.COLUMNS_MEDIA[3],c.getDescripMedia());

        return _midb.insert(NotasBD.TABLE_MEDIA_NAME,null,cv) ;

    }


    public long update(Media c){

        ContentValues cv = new ContentValues();

        cv.put(NotasBD.COLUMNS_MEDIA[1],c.getId_TareaNota());
        cv.put(NotasBD.COLUMNS_MEDIA[2],c.getDir_uri());
        cv.put(NotasBD.COLUMNS_MEDIA[3],c.getDescripMedia());

        return _midb.update(NotasBD.TABLE_MEDIA_NAME, cv, "_id=?", new String[] { String.valueOf( c.getId_media())});

    }


    public int delete(String id){

        return  _midb.delete("media","_id='"+id+"'",null);

    }


    public int deleteTODAS(String id){

        return  _midb.delete("media","id_Tarea='"+id+"'",null);

    }


    public List<Media> buscarTodos() {

        List<Media> notesArrayList = new ArrayList<Media>();
        String selectQuery = "SELECT * FROM media";
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


    public List<Media> buscarTodosDeTarea1(int iden) {

        List<Media> notesArrayList = new ArrayList<Media>();
        String selectQuery = "SELECT * FROM media WHERE id_Tarea = '"+iden+"'";
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


    public Media buscarUno(int iden) {

        Media notesUno = new Media();
        String selectQuery = "SELECT * FROM media WHERE _id = '"+iden+"'";
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
