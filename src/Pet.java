import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Pet implements Serializable {
    private String id;
    private String name;
    private String species;
    private String breed;
    private int age;
    private String ownerName;
    private String contactInfo;
    private LocalDate registrationDate;
    private ArrayList<Appointment> appointments;
    private ArrayList<Appointment> appointmentHistory;

    public Pet(String id, String name, String species, String breed, int age, String ownerName, String contactInfo) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.ownerName = ownerName;
        this.contactInfo = contactInfo;
        registrationDate = LocalDate.now();
        appointments = new ArrayList<>();
        appointmentHistory = new ArrayList<>();
    }

    public void addAppointment(Appointment a){
        appointments.add(a);
        appointmentHistory.add(0, a);
    }

    public void completeAppointment(Appointment a){
        appointments.remove(a);
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public ArrayList<Appointment> getAppointmentHistory() {
        return appointmentHistory;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void setAppointmentHistory(ArrayList<Appointment> appointmentHistory) {
        this.appointmentHistory = appointmentHistory;
    }

    @Override
    public String toString(){
        return "Name: " + name + " -- Species: " + species + " -- Breed: " + breed + " -- Age: " + age;
    }
}
