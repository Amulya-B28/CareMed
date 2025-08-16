package models;

public class Prescription {
    private int id;
    private int patientId;
    private String medicineName;
    private String dosage;
    private String scheduleTime;

    public Prescription() {}

    public Prescription(int id, int patientId, String medicineName, String dosage, String scheduleTime) {
        this.id = id;
        this.patientId = patientId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.scheduleTime = scheduleTime;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    @Override
    public String toString() {
        return medicineName + " - " + dosage + " @ " + scheduleTime;
    }
}
