package com.chenkh.media.bean;

import org.springframework.stereotype.Component;

@Component
public class PlayactorBean {
    private String id;
    private String name;
    private String img;
    private String type;
    private String point;
    private String face_rate;
    private String figure_rate;
    private String vagina_rate;
    private String breast_rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getFace_rate() {
        return face_rate;
    }

    public void setFace_rate(String face_rate) {
        this.face_rate = face_rate;
    }

    public String getFigure_rate() {
        return figure_rate;
    }

    public void setFigure_rate(String figure_rate) {
        this.figure_rate = figure_rate;
    }

    public String getVagina_rate() {
        return vagina_rate;
    }

    public void setVagina_rate(String vagina_rate) {
        this.vagina_rate = vagina_rate;
    }

    public String getBreast_rate() {
        return breast_rate;
    }

    public void setBreast_rate(String breast_rate) {
        this.breast_rate = breast_rate;
    }
}
