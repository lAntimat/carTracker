package com.tracker.lantimat.cartracker.mapActivity.models;

/**
 * Created by GabdrakhmanovII on 02.11.2017.
 */

public class User {

    int id;
    int carId;
    String driverLicenseId;
    String name;
    String subName;
    String imgUrl;

    public User() {
    }

    public User(int id, int carId, String driverLicenseId, String name, String subName, String imgUrl) {
        this.id = id;
        this.carId = carId;
        this.driverLicenseId = driverLicenseId;
        this.name = name;
        this.subName = subName;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getDriverLicenseId() {
        return driverLicenseId;
    }

    public void setDriverLicenseId(String driverLicenseId) {
        this.driverLicenseId = driverLicenseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
