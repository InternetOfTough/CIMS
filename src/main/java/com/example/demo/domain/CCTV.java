package com.example.demo.domain;

import static com.example.demo.Constants.constants.OUTPUT_M3U8;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("CCTVDTO")
public class CCTV {
    // PK가 테이블하고 타입 안맞음( 테이블 string != int)
    private int id;  // 라즈베리파이 id이자 primary key
    private String name;    // 사용자설정 cctv2, cctv3, ...
    private String videoUrl;

    private String storagePath;
    private double xCoordinate;
    private double yCoordinate;
    private int networkStat;
    private boolean cameraValid;
    private int emergency;
    // 인터넷 세기

    public CCTV(int id, String name, double xCoordinate, double yCoordinate, int networkStat) {
        this.id = id;
        this.name = name;
        this.videoUrl = "http://localhost:8080/cctv/" + id + OUTPUT_M3U8;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.networkStat = networkStat;
        this.storagePath = "/videos/" + name + ".mp4";
    }

    public CCTV() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getNetworkStat() {
        return networkStat;
    }

    public void setNetworkStat(int networkStat) {
        this.networkStat = networkStat;
    }

    public boolean isCameraValid() {
        return cameraValid;
    }

    public void setCameraValid(boolean cameraValid) {
        this.cameraValid = cameraValid;
    }

    public int getEmergency() {
        return emergency;
    }

    public void setEmergency(int emergency) {
        this.emergency = emergency;
    }
}
