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

    @Override
    public String toString(){
        return "Name: " + name + " -- Species: " + species + " -- Breed: " + breed + " -- Age: " + age;
    }
}
