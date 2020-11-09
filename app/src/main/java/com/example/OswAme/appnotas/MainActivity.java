package com.example.OswAme.appnotas;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.OswAme.appnotas.Datos.ActivityDatos;
import com.example.OswAme.appnotas.Datos.ActivityNotifica;
import com.example.OswAme.appnotas.Datos.DaoNotas;
import com.example.OswAme.appnotas.Datos.Nota;
import com.example.OswAme.appnotas.Datos.Nota_Serial;
import com.example.OswAme.appnotas.Media.ActivityMedia;
import com.example.OswAme.appnotas.Media.ActivityMediaAudio;

import java.util.ArrayList;
import java.util.List;

/**
 * Oswaldo Jimenez
 */
public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener{

    private MediaRecorder grabacion;
    private String archivoSalida = null; //asignamos un nombre a la pista de audio que vamos a agregar
    private Button btn_recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargar();
        startService(new Intent(getBaseContext(),Servicio.class));

        btn_recorder = (Button)findViewById(R.id.btn_rec);

        //permisos para acceder a archivos multimedia guardados en la memoria
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }

//public void btn_audio(View v){
  //  public void Recorder(View view){
    //    if(grabacion == null){
        //    archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Grabacion.mp3";
      //      grabacion = new MediaRecorder();
          //  grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
           // grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            //grabacion.setOutputFile(archivoSalida);

            //try{
             //   grabacion.prepare();
               // grabacion.start();
            //}catch (IOException e){
            //}

            //btn_recorder.setBackgroundResource(R.drawable.rec);
            //Toast.makeText(getApplicationContext(), "Grabando...", Toast.LENGTH_SHORT).show();
        //}else if(grabacion!= null) {
          //  grabacion.stop();
            //grabacion.release();
            //grabacion = null;
            //btn_recorder.setBackgroundResource(R.drawable.stop_rec);
            //Toast.makeText(getApplicationContext(), "Grabación finalizada...", Toast.LENGTH_SHORT).show();
        //}
    //}
//}


    public void cargar(){
        // define TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_tab0));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_tab1));

        //tabLayout.addTab(tabLayout.newTab().setText(R.string.title_tab2));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // define ViewPager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        // list model
        Nota[] listModel0 = createDummyListModel_0("tab 0");
        Nota[] listModel1 = createDummyListModel_1("tab 1");

        //  ViewPager need a PagerAdapter
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), listModel0, listModel1);
        viewPager.setAdapter(adapter);

        // Listeners
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(getOnTabSelectedListener(viewPager));
    }

    public void reiniciarDatos(){

        // define TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.removeAllTabs();

        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_tab0));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_tab1));

        //tabLayout.addTab(tabLayout.newTab().setText(R.string.title_tab2));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // define ViewPager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        // list model
        Nota[] listModel0 = createDummyListModel_0("tab 0");
        Nota[] listModel1 = createDummyListModel_1("tab 1");

        //  ViewPager need a PagerAdapter
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), listModel0, listModel1);
        viewPager.setAdapter(adapter);

        // Listeners
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(getOnTabSelectedListener(viewPager));
    }

    /**
     * Listener for tab selected
     */
    @NonNull
    private TabLayout.OnTabSelectedListener getOnTabSelectedListener(final ViewPager viewPager) {
        return new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //Toast.makeText(MainActivity.this, "Tab selected " +  tab.getPosition(), Toast.LENGTH_SHORT).show();

                nuevo = (Button) findViewById(R.id.btn_new);

                if(tab.getPosition()==0){

                    tabFlag="0";
                    nuevo.setText(R.string.btnNewNota);

                }else if(tab.getPosition()==1){

                    tabFlag="1";
                    nuevo.setText(R.string.btnNewTarea);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // nothing now
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // nothing now
            }

        };

    }


    /**
     * Listener that comunicate fragment / recycler with this activity
     */
    @Override
    public void onListFragmentInteraction(final Nota model) {

        // the user clicked on this item over the list
        //Toast.makeText(MainActivity.this, Nota.class.getSimpleName() + ":" + model.getId_nota() + " - " +model.getTipo()+ " - " +model.getTitulo(), Toast.LENGTH_LONG).show();


        String aux[] = new String[]{" "};

        String opcs[] = new String[]{"Fotos", "Video"};

        if(model.getTipo()==0) {

            aux =  new String[]{"Editar","Eliminar","Foto","Audio"};

        }else if(model.getTipo()==1){

            aux =  new String[]{"Editar","Eliminar","Foto","Audio"};

        }

        final String opc[] =  aux;

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.alert_Title)
                .setIcon(R.mipmap.ic_launcher)
                .setItems(opc, new DialogInterface. OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){

                        //Toast.makeText(MainActivity.this,opc[which],Toast.LENGTH_SHORT).show();

                        if(opc[which].equals("Eliminar")){

                            AlertDialog dialog2 = new AlertDialog.Builder(MainActivity.this)
                                    .setTitle(R.string.del_alert_Title)
                                    .setIcon(android.R.drawable.ic_delete)
                                    .setMessage(R.string.del_alert_Message)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            try {

                                                DaoNotas dao = new DaoNotas(MainActivity.this);

                                                if(dao.delete(model.getId_nota()+"")>0){

                                                    //Toast.makeText(getBaseContext(),R.string.del_alert_resultGood,Toast.LENGTH_SHORT).show();
                                                    reiniciarDatos();

                                                }else{

                                                    //Toast.makeText(getBaseContext(),R.string.del_alert_resultBad,Toast.LENGTH_SHORT).show();

                                                }

                                            }catch (Exception err){

                                                //Toast.makeText(getBaseContext(),err.getMessage(),Toast.LENGTH_LONG).show();

                                            }

                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            //Toast.makeText(MainActivity.this, "Presiono CANCEL", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .create();

                            dialog2.show();

                        }else if(opc[which].equals("Foto")){

                            //Toast.makeText(MainActivity.this, "Presiono Multimedia", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplication(),ActivityMedia.class);
                            intent.putExtra("idregistro_integer", model.getId_nota());

                            startActivityForResult(intent,1020);

                        }else if(opc[which].equals("Audio")){

                            Intent intent = new Intent(getApplication(), ActivityMediaAudio.class);
                            intent.putExtra("idregistro_integer", model.getId_nota());

                            startActivityForResult(intent,1020);

                        }else if(opc[which].equals("Alertas")){

                            //Toast.makeText(MainActivity.this, "Presiono Alertas", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplication(),ActivityNotifica.class);
                            intent.putExtra("idtarea_integer", model.getId_nota());

                            startActivityForResult(intent,1010);

                        }else if(opc[which].equals("Editar")){

                            //Toast.makeText(MainActivity.this, "Presiono Editar", Toast.LENGTH_SHORT).show();

                            if(model.getTipo()==0){

                                Intent intent = new Intent(getApplication(),ActivityDatos.class);
                                intent.putExtra("tipo_integer", 2);

                                intent.putExtra("integer_ID",model.getId_nota());
                                intent.putExtra("string_Titulo",model.getTitulo());
                                intent.putExtra("string_Descrip",model.getDescripcion());
                                intent.putExtra("string_FechCreacion",model.getFecha_creacion());
                                intent.putExtra("string_FechaLimite",model.getFecha_limite());
                                intent.putExtra("string_HoraLimite",model.getHora_limite());
                                intent.putExtra("integer_checalo",model.getChecalo());


                                startActivityForResult(intent,1002);

                            }else if(model.getTipo()==1){

                                Intent intent = new Intent(getApplication(),ActivityDatos.class);
                                intent.putExtra("tipo_integer", 3);

                                intent.putExtra("integer_ID",model.getId_nota());
                                intent.putExtra("string_Titulo",model.getTitulo());
                                intent.putExtra("string_Descrip",model.getDescripcion());
                                intent.putExtra("string_FechCreacion",model.getFecha_creacion());
                                intent.putExtra("string_FechaLimite",model.getFecha_limite());
                                intent.putExtra("string_HoraLimite",model.getHora_limite());
                                intent.putExtra("integer_checalo",model.getChecalo());


                                startActivityForResult(intent,1003);

                            }

                        }
                        dialog.dismiss();
                    }

                })
                .create();
        dialog.show();

    }


    // model for NOTA purpose
    private Nota[] createDummyListModel_0(String msj) {

        List<Nota> l = new ArrayList<>();
        List<Nota_Serial> ls = new ArrayList<>();
        DaoNotas dao = new DaoNotas(MainActivity.this);

        ls=dao.buscarTodos(0);

        for(int i = 0;i<ls.size();i++){

            l.add(new Nota(
                    ls.get(i).getId_nota(),
                    ls.get(i).getTipo(),
                    ls.get(i).getTitulo(),
                    ls.get(i).getDescripcion(),
                    ls.get(i).getFecha_creacion(),
                    ls.get(i).getFecha_limite(),
                    ls.get(i).getHora_limite(),
                    ls.get(i).getChecalo()
            ));

        }
        return l.toArray(new Nota[l.size()]);
    }


    // model for TAREA purpose
    private Nota[] createDummyListModel_1(String msj) {

        List<Nota> l = new ArrayList<>();
        List<Nota_Serial> ls = new ArrayList<>();
        DaoNotas dao = new DaoNotas(MainActivity.this);

        ls=dao.buscarTodos(1);

        for(int i = 0;i<ls.size();i++){

            l.add(new Nota(
                    ls.get(i).getId_nota(),
                    ls.get(i).getTipo(),
                    ls.get(i).getTitulo(),
                    ls.get(i).getDescripcion(),
                    ls.get(i).getFecha_creacion(),
                    ls.get(i).getFecha_limite(),
                    ls.get(i).getHora_limite(),
                    ls.get(i).getChecalo()
            ));
        }

        return l.toArray(new Nota[l.size()]);
    }



    private String tabFlag = "0";
    Button nuevo;
    public void btnCheckList_click(View v){

        if(tabFlag.equals("0")){
            //Toast.makeText(MainActivity.this,R.string.toast_creaNota,Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplication(),ActivityDatos.class);
            intent.putExtra("tipo_integer", 0);

            startActivityForResult(intent,1000);
        }else if(tabFlag.equals("1")){
            //Toast.makeText(MainActivity.this,R.string.toast_creaTarea,Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplication(),ActivityDatos.class);
            intent.putExtra("tipo_integer", 1);

            startActivityForResult(intent,1001);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode==1000) {

            try {

                Nota_Serial objNota = (Nota_Serial) data.getSerializableExtra("miNota");
                DaoNotas dao = new DaoNotas(MainActivity.this);

                if(dao.insert(new Nota_Serial(0,objNota.getTipo(),objNota.getTitulo(),objNota.getDescripcion(),objNota.getFecha_creacion(),objNota.getFecha_limite(),objNota.getHora_limite(),objNota.getChecalo()))>0) {

                   // Toast.makeText(getBaseContext(), R.string.toast_notaCreada_, Toast.LENGTH_SHORT).show();
                    reiniciarDatos();

                }else{

                   // Toast.makeText(getBaseContext(), R.string.toast_notaCreadaProblem_, Toast.LENGTH_SHORT).show();

                }

            }catch (Exception err){

              //  Toast.makeText(getBaseContext(),err.getMessage(),Toast.LENGTH_LONG).show();

            }

        }else if(resultCode==RESULT_OK && requestCode==1001){

            //INSTERTA TAREA;
            try {

                Nota_Serial objNota = (Nota_Serial) data.getSerializableExtra("miTarea");
                DaoNotas dao = new DaoNotas(MainActivity.this);

                if(dao.insert(new Nota_Serial(0,objNota.getTipo(),objNota.getTitulo(),objNota.getDescripcion(),objNota.getFecha_creacion(),objNota.getFecha_limite(),objNota.getHora_limite(),objNota.getChecalo()))>0) {

                //    Toast.makeText(getBaseContext(), R.string.toast_tareaCreada, Toast.LENGTH_SHORT).show();
                    reiniciarDatos();

                }else{

                  //  Toast.makeText(getBaseContext(), R.string.toast_tareaCreadaProblem, Toast.LENGTH_SHORT).show();

                }

            }catch (Exception err){

              //  Toast.makeText(getBaseContext(),err.getMessage(),Toast.LENGTH_LONG).show();

            }

        }else if(resultCode==RESULT_OK && requestCode==1002) {

            try {

                Nota_Serial objNota = (Nota_Serial) data.getSerializableExtra("miNotaEdit");
                DaoNotas dao = new DaoNotas(MainActivity.this);

                if(dao.update(objNota) > 0) {

                  //  Toast.makeText(getBaseContext(), R.string.toast_notaEditada, Toast.LENGTH_SHORT).show();
                    reiniciarDatos();

                }else{

                  //  Toast.makeText(getBaseContext(), R.string.toast_notaEditadaProblem, Toast.LENGTH_SHORT).show();

                }

            }catch (Exception err){

              //  Toast.makeText(getBaseContext(),err.getMessage(),Toast.LENGTH_LONG).show();

            }

        }else if(resultCode==RESULT_OK && requestCode==1003) {

            try {

                Nota_Serial objNota = (Nota_Serial) data.getSerializableExtra("miTareaEdit");
                DaoNotas dao = new DaoNotas(MainActivity.this);

                if(dao.update(objNota) > 0) {

                 //   Toast.makeText(getBaseContext(), R.string.toast_tareaEditada, Toast.LENGTH_SHORT).show();
                    reiniciarDatos();

                }else{

                 //   Toast.makeText(getBaseContext(), R.string.toast_tareaEditadaProblem, Toast.LENGTH_SHORT).show();

                }

            }catch (Exception err){

            //    Toast.makeText(getBaseContext(),err.getMessage(),Toast.LENGTH_LONG).show();

            }

        }

    }

    public void Siguiente (View view){
        Intent siguiente = new Intent(this, grabadora.class);
        startActivity(siguiente);

    }

    /*public void video(View v) {
        startActivity(new
                Intent(this, CapturaVideo.class));
    }
    */

}
