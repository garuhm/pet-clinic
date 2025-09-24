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

    @Override
    public String toString(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

        return "Time: " + time.format(format) + " -- Type: " + type + " -- " + " -- Notes: " + notes;
    }
}
