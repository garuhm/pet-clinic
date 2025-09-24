import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment implements Serializable {
    private String type;
    private LocalDateTime time;
    private String notes;

    public Appointment(String type, LocalDateTime time, String notes) {
        this.type = type;
        this.time = time;
        this.notes = notes;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

        return "Time: " + time.format(format) + " -- Type: " + type + " -- " + " -- Notes: " + notes;
    }
}
