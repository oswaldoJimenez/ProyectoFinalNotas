package com.example.OswAme.appnotas.Datos;

import java.io.Serializable;

public class MediaVideo  implements Serializable {
    private int id_mediaVideo;
    private int id_TareaNota;
    private String dir_uriVideo;
    private String descripMediaVideo;

    public MediaVideo() {
    }

    public MediaVideo(int id_mediaVideo, int id_TareaNota, String dir_uriVideo, String descripMediaVideo) {
        this.id_mediaVideo = id_mediaVideo;
        this.id_TareaNota = id_TareaNota;
        this.dir_uriVideo = dir_uriVideo;
        this.descripMediaVideo = descripMediaVideo;
    }

    public int getId_mediaVideo() {
        return id_mediaVideo;
    }

    public void setId_mediaVideo(int id_mediaVideo) {
        this.id_mediaVideo = id_mediaVideo;
    }

    public int getId_TareaNota() {
        return id_TareaNota;
    }

    public void setId_TareaNota(int id_TareaNota) {
        this.id_TareaNota = id_TareaNota;
    }

    public String getDir_uriVideo() {
        return dir_uriVideo;
    }

    public void setDir_uriVideo(String dir_uriVideo) {
        this.dir_uriVideo = dir_uriVideo;
    }

    public String getDescripMediaVideo() {
        return descripMediaVideo;
    }

    public void setDescripMediaVideo(String descripMediaVideo) {
        this.descripMediaVideo = descripMediaVideo;
    }

    @Override
    public String toString() {
        return "MediaVideo{" +
                "id_mediaVideo=" + id_mediaVideo +
                ", id_TareaNota=" + id_TareaNota +
                ", dir_uriVideo='" + dir_uriVideo + '\'' +
                ", descripMediaVideo='" + descripMediaVideo + '\'' +
                '}';
    }
}
