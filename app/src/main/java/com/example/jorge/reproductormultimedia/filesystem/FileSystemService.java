package com.example.jorge.reproductormultimedia.filesystem;

import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class FileSystemService {

    private static final String CARPETA_MUSICA = "/Música";

    private static FileSystemService filesystemService;

    private FileSystemService(){}

    public static FileSystemService getInstance() {
        if (null == filesystemService) {
            filesystemService = new FileSystemService();
        }

        return filesystemService;
    }

    public ArrayList<File> escaneaSD() {
        Log.e(this.getClass().getSimpleName(), "Escaneando SD en busqueda de canciones..." + Environment.getExternalStorageDirectory().getAbsolutePath() + CARPETA_MUSICA);
        File ruta = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + CARPETA_MUSICA);
        ArrayList<File> ficherosMusica = new ArrayList<File>();

        File[] listaCancionesEncontradas = ruta.listFiles();
        if (null != listaCancionesEncontradas && listaCancionesEncontradas.length > 0) {
            Log.e(this.getClass().getSimpleName(), "Encontradas: " + listaCancionesEncontradas.length + " canciones");
            for (int i = 0; i < listaCancionesEncontradas.length; i++) {
                ficherosMusica.add(listaCancionesEncontradas[i]);
            }
        }
        return ficherosMusica;
    }

    public String obtenerDuracion(File fichero) {
        String duracionRet = "";

        MediaMetadataRetriever metadataRetriever =  new MediaMetadataRetriever();
        metadataRetriever.setDataSource(fichero.getAbsolutePath());

        String duracion = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long dur = Long.parseLong(duracion);
        String segundos = String.valueOf((dur % 60000) / 100);
        String minutos = String.valueOf(dur / 60000);

        duracionRet = minutos + ":" + segundos;

        if (segundos.length() == 1) {
            duracionRet = "0" + minutos + ":0" + segundos;
        } else {
            duracionRet = "0" + minutos + ":" + segundos;
        }

        metadataRetriever.release();

        return duracionRet;
    }
}
