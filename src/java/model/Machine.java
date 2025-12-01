package model;

public class Machine {
    private String name;
    private String description;
    private String department;
    private double latitude;
    private double longitude;
    private String imagePath; // Path to uploaded image
    private String registrationNumber;

    // Constructor
    public Machine(String name, String description, String department, double latitude, double longitude, String imagePath, String registrationNumber) {
        this.name = name;
        this.description = description;
        this.department = department;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagePath = imagePath;
        this.registrationNumber = registrationNumber;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getDepartment() { return department; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getImagePath() { return imagePath; }
    public String getRegistrationNumber() { return registrationNumber; }
}