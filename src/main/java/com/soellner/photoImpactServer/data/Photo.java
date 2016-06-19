package com.soellner.photoImpactServer.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Arrays;

/**
 * Created by asoel on 09.06.2016.
 */
@Entity
public class Photo {
    private String _id;
    private String _date;
    private byte[] _image;
    private String _originalDateTime;
    private Double _gpsLatidude;
    private Double _gpsLongitude;


    public Photo() {
    }

    @Id
    @GeneratedValue
    public String getId() {
        return _id;
    }


    public String getOriginalDateTime() {
        return _originalDateTime;
    }

    public void setOriginalDateTime(String originalDateTime) {
        _originalDateTime = originalDateTime;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String date) {
        _date = date;
    }

    @Lob
    public byte[] getImage() {
        return _image;
    }

    public void setImage(byte[] image) {
        _image = image;
    }

    public Double getGpsLatidude() {
        return _gpsLatidude;
    }

    public void setGpsLatidude(Double gpsLatidude) {
        _gpsLatidude = gpsLatidude;
    }

    public Double getGpsLongitude() {
        return _gpsLongitude;
    }

    public void setGpsLongitude(Double gpsLongitude) {
        _gpsLongitude = gpsLongitude;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "_id='" + _id + '\'' +
                ", _date='" + _date + '\'' +
                ", _originalDateTime='" + _originalDateTime + '\'' +
                ", _gpsLatidude=" + _gpsLatidude +
                ", _gpsLongitude=" + _gpsLongitude +
                '}';
    }
}
