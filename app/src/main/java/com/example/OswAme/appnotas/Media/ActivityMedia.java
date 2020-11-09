package com.example.OswAme.appnotas.Media;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.OswAme.appnotas.Datos.DaoMedia;
import com.example.OswAme.appnotas.Datos.Media;
import com.example.OswAme.appnotas.R;
import com.example.OswAme.appnotas.grabadora;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivityMedia extends AppCompatActivity {

    Button btn_aud;
    int tomaID = 0;

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private MediaRecorder grabacion;
    private String archivoSalida = null;
    private Button btn_recorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        btn_aud = findViewById(R.id.btn_audio);

        btn_aud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btn_recorder = findViewById(R.id.btn_rec);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityMedia.this, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
        Bundle datos = this.getIntent().getExtras();
        int recupera_idRegistro = datos.getInt("idregistro_integer");
        tomaID = recupera_idRegistro;

        //Toast.makeText(ActivityMedia.this, ""+tomaID, Toast.LENGTH_SHORT).show();

        recycler = (RecyclerView) findViewById(R.id.lista_Fotos);

        cargar();

    }

    public void Recorder(View view){

        if(grabacion == null){
            archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Grabacion.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            grabacion.setOutputFile(archivoSalida);

            try{
                grabacion.prepare();
                grabacion.start();
            }catch (IOException e){
            }

            btn_recorder.setBackgroundResource(R.drawable.rec);
            Toast.makeText(getApplicationContext(), "Grabando...", Toast.LENGTH_SHORT).show();
        }else if(grabacion!= null) {
            grabacion.stop();
            grabacion.release();
            grabacion = null;
            btn_recorder.setBackgroundResource(R.drawable.stop_rec);
            Toast.makeText(getApplicationContext(), "Grabación finalizada...", Toast.LENGTH_SHORT).show();
        }
    }



    public void cargar(){

        List<Media> items = new ArrayList<>();

        DaoMedia dao = new DaoMedia(ActivityMedia.this);
        items = dao.buscarTodosDeTarea(tomaID);

        //items.add(new Media(R.drawable.nota, 10, "IMAGEN", "LLLL/LLLLLL"));

        // Obtener el Recycler
        //recycler = (RecyclerView) findViewById(R.id.lista_Fotos);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new MediaAdapter(items);
        recycler.setAdapter(adapter);


    }



    private String mDirAbsoluto = null;
    private static final int REQUEST_CODE_CAMARA = 1;
    private static final int SCALE_FACTOR_IMAGE_VIEW = 4;
    private static final String ALBUM = "GuardaNotas";
    private static final String EXTENSION_JPEG = ".jpg";
    final int MY_PERMISSIONS_REQUEST_READ_ESTORAGE=124;



    public void btnMedia_click(View v){
        Toast.makeText(ActivityMedia.this,"Tomar foto",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = null;
        try {
            // Crea el Nombre de la Fotografía
            String fechaHora = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String nombre = ALBUM + "_" + fechaHora;
            // Crea el Archivo de la Fotografía
            file = nombrarArchivo(ActivityMedia.this, ALBUM, nombre, EXTENSION_JPEG);
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
                startActivityForResult(intent, REQUEST_CODE_CAMARA);
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

            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), album);

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

            case REQUEST_CODE_CAMARA:

                if (resultCode == RESULT_OK) {

                    //Bitmap bitmap = escalarBitmap(mDirAbsoluto, SCALE_FACTOR_IMAGE_VIEW);
                    //imageView.setImageBitmap(bitmap);

                    try {

                        Media objNota = new Media(0, tomaID, String.valueOf(mDirAbsoluto), "FOTO");
                        DaoMedia dao = new DaoMedia(ActivityMedia.this);

                        if(dao.insert(new Media(0,objNota.getId_TareaNota(),objNota.getDir_uri(),objNota.getDescripMedia()))>0) {

                            Toast.makeText(getBaseContext(), "Foto guardada", Toast.LENGTH_SHORT).show();
                            cargar();

                        }else{

                            Toast.makeText(getBaseContext(), "La foto no pudo ser guardada", Toast.LENGTH_SHORT).show();

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
    public void reproducir(View view) {

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(archivoSalida);
            mediaPlayer.prepare();
        } catch (IOException e){
        }

        mediaPlayer.start();
        Toast.makeText(getApplicationContext(), "Reproduciendo audio", Toast.LENGTH_SHORT).show();
    }

    //Botón Audio
    public void Siguiente (View view){
        Intent siguiente = new Intent(this, grabadora.class);
        startActivity(siguiente);
    }
    }
