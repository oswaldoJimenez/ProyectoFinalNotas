package com.example.OswAme.appnotas.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;
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

        cv.put(NotasBD.COLUMNS_VIDEOS[1],c.getId_TareaNota());
        cv.put(NotasBD.COLUMNS_VIDEOS[2],c.getDir_uri());
        cv.put(NotasBD.COLUMNS_VIDEOS[3],c.getDescripMedia());

        return _midb.insert(NotasBD.TABLE_VIDEOS_NAME,null,cv) ;

    }


    public long update(Media c){

        ContentValues cv = new ContentValues();

        cv.put(NotasBD.COLUMNS_VIDEOS[1],c.getId_TareaNota());
        cv.put(NotasBD.COLUMNS_VIDEOS[2],c.getDir_uri());
        cv.put(NotasBD.COLUMNS_VIDEOS[3],c.getDescripMedia());

        return _midb.update(NotasBD.TABLE_VIDEOS_NAME, cv, "_id=?", new String[] { String.valueOf( c.getId_media())});

    }


    public int delete(String id){

        return  _midb.delete("media","_id='"+id+"'",null);

    }


    public int deleteTODAS(String id){

        return  _midb.delete("media","id_Tarea='"+id+"'",null);

    }


    public List<MediaVideo> buscarTodos() {

        List<MediaVideo> notesArrayList = new ArrayList<MediaVideo>();
        String selectQuery = "SELECT * FROM media";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                MediaVideo notas = new MediaVideo();
                notas.setId_mediaVideo(c.getInt(c.getColumnIndex("_id")));
                notas.setId_TareaNota(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setDir_uriVideo(c.getString(c.getColumnIndex("dirUri")));
                notas.setDescripMediaVideo(c.getString(c.getColumnIndex("descripcion")));

                notesArrayList.add(notas);

            } while (c.moveToNext());

        }

        return notesArrayList;

    }


    public List<MediaVideo> buscarTodosDeTarea1(int iden) {

        List<MediaVideo> notesArrayList = new ArrayList<MediaVideo>();
        String selectQuery = "SELECT * FROM videos WHERE id_Tarea = '"+iden+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                MediaVideo notas = new MediaVideo();
                notas.setId_mediaVideo(c.getInt(c.getColumnIndex("_id")));
                notas.setId_TareaNota(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setDir_uriVideo(c.getString(c.getColumnIndex("dirUri")));
                notas.setDescripMediaVideo(c.getString(c.getColumnIndex("descripcion")));

                notesArrayList.add(notas);

            } while (c.moveToNext());

        }

        return notesArrayList;

    }


    public MediaVideo buscarUno(int iden) {

        MediaVideo notesUno = new MediaVideo();
        String selectQuery = "SELECT * FROM videos WHERE _id = '"+iden+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                MediaVideo notas = new MediaVideo();
                notas.setId_mediaVideo(c.getInt(c.getColumnIndex("_id")));
                notas.setId_TareaNota(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setDir_uriVideo(c.getString(c.getColumnIndex("dirUri")));
                notas.setDescripMediaVideo(c.getString(c.getColumnIndex("descripcion")));

                notesUno = notas;

            } while (c.moveToNext());

        }

        return notesUno;

    }
}
