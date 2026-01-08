package com.example.demo.entity;

public class CabDTO {
    private Long id;
    private String source;
    private String destination;
    private String type;
    private int kms;
    private double farePerKm;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getKms() { return kms; }
    public void setKms(int kms) { this.kms = kms; }

    public double getFarePerKm() { return farePerKm; }
    public void setFarePerKm(double farePerKm) { this.farePerKm = farePerKm; }
}
