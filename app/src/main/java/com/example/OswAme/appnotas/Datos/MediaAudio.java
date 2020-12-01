package com.example.OswAme.appnotas.Datos;

import java.io.Serializable;

public class MediaAudio implements Serializable {
    private int id_Audio;
    private int id_TareaNota;
    private String dir_uri_Audio;
    private String descripAudio;

    public MediaAudio() {
    }

    public MediaAudio(int id_Audio, int id_TareaNota, String dir_uri_Audio, String descripAudio) {
        this.id_Audio = id_Audio;
        this.id_TareaNota = id_TareaNota;
        this.dir_uri_Audio = dir_uri_Audio;
        this.descripAudio = descripAudio;
    }

    public int getId_Audio() {
        return id_Audio;
    }

    public void setId_Audio(int id_Audio) {
        this.id_Audio = id_Audio;
    }

    public int getId_TareaNota() {
        return id_TareaNota;
    }

    public void setId_TareaNota(int id_TareaNota) {
        this.id_TareaNota = id_TareaNota;
    }

    public String getDir_uri_Audio() {
        return dir_uri_Audio;
    }

    public void setDir_uri_Audio(String dir_uri_Audio) {
        this.dir_uri_Audio = dir_uri_Audio;
    }

    public String getDescripAudio() {
        return descripAudio;
    }

    public void setDescripAudio(String descripAudio) {
        this.descripAudio = descripAudio;
    }
}
