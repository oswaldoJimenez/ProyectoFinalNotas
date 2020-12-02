package com.example.OswAme.appnotas.Media;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.OswAme.appnotas.Datos.MediaAudio;
import com.example.OswAme.appnotas.R;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MediaViewHolder>{

    private List<MediaAudio> items;

    private View.OnLongClickListener onLongClickListener;

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener){
        this.onLongClickListener = onLongClickListener;
    }


    public static class MediaViewHolder extends RecyclerView.ViewHolder {

        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView ruta;
        public Button btnreproducir;
        public Button btnpausa;



        public MediaViewHolder(View v) {
            super(v);
            //imagen = (ImageView) v.findViewById(R.id.foto);
            nombre = (TextView) v.findViewById(R.id.txt_titulo);
            ruta = (TextView) v.findViewById(R.id.txt_ruta);
            btnreproducir = (Button) v.findViewById(R.id.btn_play);
        }
    }


    public AudioAdapter(List<MediaAudio> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.audio_card, viewGroup, false);
        v.setOnLongClickListener(onLongClickListener);
        return new MediaViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MediaViewHolder viewHolder, int i) {

        //Bitmap bitmap = escalarBitmap(String.valueOf(items.get(i).getDir_uri()), SCALE_FACTOR_IMAGE_VIEW);

        //viewHolder.imagen.setImageBitmap(bitmap);

        viewHolder.nombre.setText(items.get(i).getDescripAudio());
        viewHolder.ruta.setText("Ruta: "+String.valueOf(items.get(i).getDir_uri_Audio()));
        MediaPlayer mediaPlayer = new MediaPlayer();
        viewHolder.btnreproducir.setOnClickListener((v)->{
            click(mediaPlayer,i);
        });

    }

    int bandera = 0;

    private void click(MediaPlayer mediaPlayer,int i) {
        if(bandera==0){
            try{
                mediaPlayer.setDataSource((items.get(i).getDir_uri_Audio()));
                mediaPlayer.prepare();
                mediaPlayer.start();
                bandera=bandera+1;
            }catch (IOException e){
                e.printStackTrace();
            }

        }else{
            mediaPlayer.pause();
            bandera= bandera - 1;
        }
    }



    private static final int SCALE_FACTOR_IMAGE_VIEW = 4;
    /**
     * Escala un Bitmap
     *
     * @param uri
     * @param factor
     * @return Bitmap
     */
    public Bitmap escalarBitmap(String uri, Integer factor) {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = factor;
        bmOptions.inPurgeable = true;

        return rotarBitmap(uri, BitmapFactory.decodeFile(uri, bmOptions));
    }

    /**
     * Hace la Rotación de un Bitmap
     *
     * @param Url
     * @param bitmap
     * @return Bitmap
     */
    private Bitmap rotarBitmap(String Url, Bitmap bitmap) {

        try {

            ExifInterface exifInterface = new ExifInterface(Url);
            int orientacion = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();

            if (orientacion == 6) {

                matrix.postRotate(90);

            } else if (orientacion == 3) {

                matrix.postRotate(180);

            } else if (orientacion == 8) {

                matrix.postRotate(270);

            }

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true); // rotating bitmap

        } catch (Exception e) {

        }
        return bitmap;
    }
}
