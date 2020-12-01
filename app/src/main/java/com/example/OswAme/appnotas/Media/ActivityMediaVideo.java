package com.example.OswAme.appnotas.Media;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.OswAme.appnotas.Datos.DaoMedia;
import com.example.OswAme.appnotas.Datos.DaoVideo;
import com.example.OswAme.appnotas.Datos.Media;
import com.example.OswAme.appnotas.Datos.MediaVideo;
import com.example.OswAme.appnotas.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivityMediaVideo extends AppCompatActivity {

    int tomaID = 0;

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_video);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityMediaVideo.this, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
        Bundle datos = this.getIntent().getExtras();
        int recupera_idRegistro = datos.getInt("idregistro_integer");
        tomaID = recupera_idRegistro;

        recycler = (RecyclerView) findViewById(R.id.lista_Fotos);

        cargar();
    }

    public void cargar(){

        List<MediaVideo> items = new ArrayList<>();

        DaoVideo dao = new DaoVideo(ActivityMediaVideo.this);
        items = dao.buscarTodosDeTarea1(tomaID);

        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new VideoAdapter(items);
        recycler.setAdapter(adapter);

    }

    private String mDirAbsoluto = null;
    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final String ALBUM = "GuardaVideo";
    private static final String EXTENSION_MP4 = ".mp4";
    final int MY_PERMISSIONS_REQUEST_READ_ESTORAGE=124;

    public void btnMediaVideo_click(View v){
        Toast.makeText(ActivityMediaVideo.this,"Grabar video",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File file = null;
        try {
            // Crea el Nombre del video
            String fechaHora = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String nombre = ALBUM + "_" + fechaHora;
            // Crea el Archivo del video
            file = nombrarArchivo(ActivityMediaVideo.this, ALBUM, nombre, EXTENSION_MP4);
            // Obtiene el Nombre y el Directorio Absoluto y los Muestra
            // Guarda el Directorio Absoluto en una Variable Global
            mDirAbsoluto = file.getAbsolutePath();
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        } catch (IOException e) {
            e.printStackTrace();
            file = null;
            mDirAbsoluto = null;
        }
        try {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Tienes permiso :3", Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
            } else {
                Toast.makeText(getApplicationContext(), "No tienes permiso :c", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    } else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_READ_ESTORAGE);
                    }

                }
            }
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex+"", Toast.LENGTH_SHORT).show();
        }
    }

    private File nombrarArchivo(Context context, String album, String nombre, String extension) throws IOException {

        return new File(obtenerDirectorioPublico(context, album), nombre + extension);

    }

    private File obtenerDirectorioPublico(Context context, String album) {

        File file = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), album);

            if (file != null) {

                if (!file.mkdirs()) {

                    if (!file.exists()) {

                        Toast.makeText(context, "Error al crear el directorio.", Toast.LENGTH_SHORT).show();
                        return null;

                    }

                }

            }

        } else {

            Toast.makeText(context, "Tarjeta SD no disponible.", Toast.LENGTH_SHORT).show();
            file = new File(context.getFilesDir(), album);

        }

        return file;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQUEST_VIDEO_CAPTURE:

                if (resultCode == RESULT_OK) {

                    //Bitmap bitmap = escalarBitmap(mDirAbsoluto, SCALE_FACTOR_IMAGE_VIEW);
                    //imageView.setImageBitmap(bitmap);

                    try {

                        MediaVideo objNota = new MediaVideo(0, tomaID, String.valueOf(mDirAbsoluto), "VIDEO");
                        DaoVideo dao = new DaoVideo(ActivityMediaVideo.this);

                        if(dao.insert(new Media(0,objNota.getId_TareaNota(),objNota.getDir_uriVideo(),objNota.getDescripMediaVideo()))>0) {

                            Toast.makeText(getBaseContext(), "Video guardado", Toast.LENGTH_SHORT).show();
                            cargar();

                        }else{

                            Toast.makeText(getBaseContext(), "El video no pudo ser guardado", Toast.LENGTH_SHORT).show();

                        }

                    }catch (Exception err){

                        Toast.makeText(getBaseContext(),err.getMessage(),Toast.LENGTH_LONG).show();

                    }

                }

                break;

            default:

                break;

        }

    }
}
