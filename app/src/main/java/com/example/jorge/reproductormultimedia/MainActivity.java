package com.example.jorge.reproductormultimedia;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jorge.reproductormultimedia.filesystem.FileSystemService;
import com.example.jorge.reproductormultimedia.widget.cancion.Cancion;
import com.example.jorge.reproductormultimedia.widget.cancion.CancionAdapter;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FileSystemService fileSystemService;
    private ListView lv;
    private MediaPlayer mp;
    private int flagCancion;
    private ArrayList<Cancion> canciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initServices();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        ArrayList<File> ficheros = fileSystemService.escaneaSD();
        canciones = construirCanciones(ficheros);

        // Cargamos el listView
        lv = (ListView) findViewById(R.id.lvCanciones);
        CancionAdapter adapter = new CancionAdapter(this, canciones);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Log.e("Posicion item view: ", position + "");
                Log.e(this.getClass().getSimpleName(), "" + lv.getAdapter().getCount());
                for (int x=0; x < lv.getAdapter().getCount(); x++) {
                    if (null != lv.getChildAt(x)) {
                        lv.getChildAt(x).setBackgroundColor(getResources().getColor(android.R.color.white));
                        
                    }
                }

                if (null != lv.getChildAt(position)) {
                    lv.getChildAt(position).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                }
                flagCancion = position;
            }
        });
    }

    private ArrayList<Cancion> construirCanciones(ArrayList<File> ficheros) {

        ArrayList<Cancion> listaDeCanciones = new ArrayList<Cancion>();

        for (File fichero: ficheros) {
            String nombreCancion = fichero.getName();
            String duracionCancion = fileSystemService.obtenerDuracion(fichero);
            String ruta = fichero.getAbsolutePath();
            Log.e("Nombre Cancion", nombreCancion);
            Log.e("Duracion", duracionCancion);

            Cancion c = new Cancion(nombreCancion, duracionCancion, ruta);
            listaDeCanciones.add(c);
        }

        return listaDeCanciones;
    }

    private void initServices() {
        this.fileSystemService = FileSystemService.getInstance();
        mp = new MediaPlayer();
        flagCancion = 0;
    }

    public void play(View view) {
        try {
            mp=new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(canciones.get(flagCancion).getRuta());
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            Log.e("*****",errors.toString());
            Log.e("*****",e.getMessage());
        }
    }
    public void stop(View view) {
        mp.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp!=null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}
