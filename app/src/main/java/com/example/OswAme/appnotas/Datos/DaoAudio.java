package com.example.OswAme.appnotas.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DaoAudio {
    private Context _contexto;
    private SQLiteDatabase _midb;

    public DaoAudio(Context contexto){

        this._contexto = contexto;
        this._midb = new NotasBD(contexto).getWritableDatabase();

    }


    public long insert(MediaAudio c){

        ContentValues cv = new ContentValues();

        cv.put(NotasBD.COLUMNS_AUDIOS[1],c.getId_TareaNota());
        cv.put(NotasBD.COLUMNS_AUDIOS[2],c.getDir_uri_Audio());
        cv.put(NotasBD.COLUMNS_AUDIOS[3],c.getDescripAudio());

        return _midb.insert(NotasBD.TABLE_AUDIOS_NAME,null,cv) ;

    }

    //ACTUALIZAR MEDIA;
    public long update(MediaAudio c){

        ContentValues cv = new ContentValues();

        cv.put(NotasBD.COLUMNS_AUDIOS[1],c.getId_TareaNota());
        cv.put(NotasBD.COLUMNS_AUDIOS[2],c.getDir_uri_Audio());
        cv.put(NotasBD.COLUMNS_AUDIOS[3],c.getDescripAudio());

        return _midb.update(NotasBD.TABLE_AUDIOS_NAME, cv, "_id=?", new String[] { String.valueOf( c.getId_Audio())});

    }

    //ELIMINAR MEDIA MEDIANTE ID;
    public int delete(String id){

        return  _midb.delete("AUDIOS","_id='"+id+"'",null);

    }

    //ELIMINAR MEDIA ID TAREA;
    public int deleteTODAS(String id){

        return  _midb.delete("AUDIOS","id_Tarea='"+id+"'",null);

    }

    //CREACION DE LISTA;
    public List<MediaAudio> buscarTodos() {

        List<MediaAudio> notesArrayList = new ArrayList<MediaAudio>();
        String selectQuery = "SELECT * FROM audios";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                MediaAudio notas = new MediaAudio();
                notas.setId_Audio(c.getInt(c.getColumnIndex("_id")));
                notas.setId_TareaNota(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setDir_uri_Audio(c.getString(c.getColumnIndex("dirUri")));
                notas.setDescripAudio(c.getString(c.getColumnIndex("descripcion")));

                notesArrayList.add(notas);

            } while (c.moveToNext());

        }

        return notesArrayList;

    }

    //BUSQUEDA POR ID TAREA;
    public List<MediaAudio> buscarTodosDeTarea(int iden) {

        List<MediaAudio> notesArrayList = new ArrayList<MediaAudio>();
        String selectQuery = "SELECT * FROM audios WHERE id_Tarea = '"+iden+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                MediaAudio notas = new MediaAudio();
                notas.setId_Audio(c.getInt(c.getColumnIndex("_id")));
                notas.setId_TareaNota(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setDir_uri_Audio(c.getString(c.getColumnIndex("dirUri")));
                notas.setDescripAudio(c.getString(c.getColumnIndex("descripcion")));

                notesArrayList.add(notas);

            } while (c.moveToNext());

        }

        return notesArrayList;

    }

    //BUSQUEDA POR ID;
    public MediaAudio buscarUno(int iden) {

        MediaAudio notesUno = new MediaAudio();
        String selectQuery = "SELECT * FROM audios WHERE _id = '"+iden+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                MediaAudio notas = new MediaAudio();
                notas.setId_Audio(c.getInt(c.getColumnIndex("_id")));
                notas.setId_TareaNota(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setDir_uri_Audio(c.getString(c.getColumnIndex("dirUri")));
                notas.setDescripAudio(c.getString(c.getColumnIndex("descripcion")));

                notesUno = notas;

            } while (c.moveToNext());

        }

        return notesUno;

    }
}
