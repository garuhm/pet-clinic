import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class PetCareScheduler {
    private static ArrayList<String> getIDS() {
        ArrayList<String> ids = new ArrayList<>();

        File idFile = new File("ids.txt");
        if(!idFile.exists()){
            try(FileWriter file = new FileWriter("ids.txt"); BufferedWriter writer = new BufferedWriter(file)){
                writer.write("");
                return ids;
            } catch (IOException e){
                System.out.println("Error reading file! :(");
                return null;
            }
        }

        try(FileReader file = new FileReader("ids.txt"); BufferedReader reader = new BufferedReader(file)){
            String line = "";
            while((line = reader.readLine()) != null){
                ids.add(line);
            }
        } catch (IOException e){
            System.out.println("Error reading file! :(");
            return null;
        }

        return ids;
    }

    private static void writeIDS(ArrayList<String> ids){
        for(int i = 0; i < ids.size(); i++){
            try(FileWriter file = new FileWriter("ids.txt"); BufferedWriter writer = new BufferedWriter(file)){
                writer.write(ids.get(i));
                writer.newLine();
            } catch (IOException e){
                System.out.println("Error writing file! :(");
            }
        }
    }

    // use folder for individual pet data
    // go over each file and deserialize and add to list during runtime
    // when serializing, go over each object and write file for each

    private static ArrayList<Pet> readData(){
        File petsFile = new File("pets.ser");
        ArrayList<Pet> pets = null;

        if(!petsFile.exists()){
            try(FileWriter file = new FileWriter("pets.ser"); BufferedWriter writer = new BufferedWriter(file)){
                writer.write("");
                return new ArrayList<Pet>();
            } catch (IOException e){
                System.out.println("Error reading file! :(");
                return null;
            }
        }

        try(FileInputStream file = new FileInputStream("pets.ser"); ObjectInputStream reader = new ObjectInputStream(file)){
            pets = (ArrayList<Pet>) reader.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Error reading file! :(");
        }
        return pets;
    }

    private static void writeData(ArrayList<Pet> pets){
        try(FileOutputStream file = new FileOutputStream("pets.ser"); ObjectOutputStream writer = new ObjectOutputStream(file)){
            writer.writeObject(pets);
        } catch (IOException e){
            System.out.println("Error reading file! :(");
        }
    }

    private static void register(Scanner sc, ArrayList<Pet> pets, ArrayList<String> ids){
        String id = "";
        while(true){
            System.out.println("ID for pet?");
            id = sc.nextLine();
            if(ids.contains(id)){
                System.out.println("This ID is already taken! Please enter another ID");
            }
            else {
                break;
            }
        }
        System.out.println("Pet name?");
        String name = sc.nextLine();
        System.out.println("Species?");
        String species = sc.nextLine();
        System.out.println("Breed?");
        String breed = sc.nextLine();
        int age = -1;
        while(true){
            System.out.println("Age?");
            age = sc.nextInt();
            if(age < 0){
                System.out.println("Please enter a valid age!");
            } else{
                break;
            }
        }
        sc.nextLine();
        System.out.println("Owner name?");
        String ownerName = sc.nextLine();
        System.out.println("Contact info?");
        String contact = sc.nextLine();

        Pet p = new Pet(id, name, species, breed, age, ownerName, contact);
        ids.add(id);
        pets.add(p);

        System.out.println("Pet registered!\n");
    }

    private static void schedule(Scanner sc, ArrayList<Pet> pets, ArrayList<String> ids){
        String id = "";
        while(true){
            System.out.println("Pet ID?");
            id = sc.nextLine();

            if(!ids.contains(id)){
                System.out.println("No pet with that ID was found! Please try again");
            } else {
                break;
            }
        }

        Pet p = null;
        for(int i = 0; i < pets.size(); i++){
            if(pets.get(i).getId().equals(id)){
                p = pets.get(i);
                break;
            }
        }

        System.out.println("Appointment type?");
        String type = sc.nextLine();
        LocalDateTime time = null;
        while(true){
            try{
                System.out.println("Date and time? please enter in format MM/dd/yy HH:mm, with hours ranging from 00 - 23");
                String inputTime = sc.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
                time = LocalDateTime.parse(inputTime, formatter);
                break;
            } catch (DateTimeParseException e){
                System.out.println("Please enter the date and time in the specified format");
            }
        }
        System.out.println("Any notes?"); // if there are no notes, user will likely enter something like "none"
        String notes = sc.nextLine();

        Appointment appointment = new Appointment(type, time, notes);
        p.addAppointment(appointment);

        System.out.println("Appointment made!\n");
    }

    private static void completeAppointment(Scanner sc, ArrayList<Pet> pets, ArrayList<String> ids){
        String id = "";
        while(true){
            System.out.println("Pet ID?");
            id = sc.nextLine();

            if(!ids.contains(id)){
                System.out.println("No pet with that ID was found! Please try again");
            } else {
                break;
            }
        }

        Pet p = null;
        for(int i = 0; i < pets.size(); i++){
            if(pets.get(i).getId().equals(id)){
                p = pets.get(i);
                break;
            }
        }

        System.out.println("Which appointment would you like to complete?:");
        int i = 1;
        for(Appointment a : p.getAppointments()){
            System.out.println(i + ". " + a.toString());
            i++;
        }

        int option = -1;
        while(true){
            option = sc.nextInt();
            if(option > p.getAppointments().size() || option <= 0){
                System.out.println("Please enter a valid option!");
            }
            else{
                p.completeAppointment(p.getAppointments().get(option - 1));
                System.out.println("Appointment completed!\n");
                break;
            }
        }
    }

    private static void displayAllPets(ArrayList<Pet> pets){
        System.out.println("\nAll pets:");
        for(Pet p : pets){
            System.out.println(p.toString());
        }
    }

    private static void displayOnePetRecord(Scanner sc, ArrayList<Pet> pets, ArrayList<String> ids){
        String id = "";
        while(true){
            System.out.println("Pet ID?");
            id = sc.nextLine();

            if(!ids.contains(id)){
                System.out.println("No pet with that ID was found! Please try again");
            } else {
                break;
            }
        }

        Pet p = null;
        for(int i = 0; i < pets.size(); i++){
            if(pets.get(i).getId().equals(id)){
                p = pets.get(i);
                break;
            }
        }

        System.out.println("\nPet's appointment history: ");
        for(Appointment a : p.getAppointmentHistory()){
            System.out.println(a.toString());
        }

        System.out.println("\n");
    }

    private static void displayOnePetUpcomingAppointments(Scanner sc, ArrayList<Pet> pets, ArrayList<String> ids){
        String id = "";
        while(true){
            System.out.println("Pet ID?");
            id = sc.nextLine();

            if(!ids.contains(id)){
                System.out.println("No pet with that ID was found! Please try again");
            } else {
                break;
            }
        }

        Pet p = null;
        for(int i = 0; i < pets.size(); i++){
            if(pets.get(i).getId().equals(id)){
                p = pets.get(i);
                break;
            }
        }

        System.out.println("\nAll upcoming pet's appointments: ");
        for(Appointment a : p.getAppointments()){
            System.out.println(a.toString());
        }

        System.out.println("\n");
    }

    private static void displayAllUpcomingAppointments(ArrayList<Pet> pets){
        System.out.println("All upcoming appointments clinic-wise:");
        for(Pet p : pets){
            if(p.getAppointments().size() > 0){
                System.out.println("Upcoming appointments for " + p.getName() + ": ");
                for(Appointment a : p.getAppointments()){
                    System.out.println(a.toString());
                }
                System.out.println("-------------------");
            }
        }
    }

    private static void displayAllOverdueAppointments(ArrayList<Pet> pets){
        LocalDateTime now = LocalDateTime.now();

        System.out.println("All overdue appointments: ");
        for(Pet p: pets){
            for(Appointment a : p.getAppointments()){
                LocalDateTime apptTime = a.getTime();
                long period = apptTime.until(now, ChronoUnit.DAYS);
                if(a.getTime().isBefore(now) && period >= 182){
                    System.out.println("Overdue appointment for " + p.getName() + ": " + a.toString());
                }
            }
        }
        System.out.println("\n");
    }

    private static void displayAllPetsOverdue(ArrayList<Pet> pets){
        LocalDateTime now = LocalDateTime.now();

        ArrayList<Pet> overdue = new ArrayList<>();

        System.out.println("All pets with overdue appointments: ");
        for(Pet p: pets){
            for(Appointment a : p.getAppointments()){
                LocalDateTime apptTime = a.getTime();
                long period = apptTime.until(now, ChronoUnit.DAYS);
                if(a.getTime().isBefore(now) && period >= 182){
                    overdue.add(p);
                    System.out.println(p.toString());
                    break;
                }
            }
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {
        ArrayList<Pet> pets = readData();
        ArrayList<String> ids = getIDS();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the pet care scheduler!");

        while(true){
            System.out.println("1. Register a pet");
            System.out.println("2. Schedule an appointment");
            System.out.println("3. Complete an appointment");
            System.out.println("4. Display all pets");
            System.out.println("5. Display individual pet record");
            System.out.println("6. Display upcoming appointments for a pet");
            System.out.println("7. Display upcoming appointments for all pets");
            System.out.println("8. Display all overdue appointments");
            System.out.println("9. Display pets with (an) overdue appointment/s");
            System.out.println("10. Exit");

            String response = sc.nextLine();

            switch(response){
                case "1":
                    register(sc, pets, ids);
                    break;
                case "2":
                    schedule(sc, pets, ids);
                    break;
                case "3":
                    completeAppointment(sc, pets, ids);
                    break;
                case "4":
                    displayAllPets(pets);
                    break;
                case "5":
                    displayOnePetRecord(sc, pets, ids);
                    break;
                case "6":
                    displayOnePetUpcomingAppointments(sc, pets, ids);
                    break;
                case "7":
                    displayAllUpcomingAppointments(pets);
                    break;
                case "8":
                    displayAllOverdueAppointments(pets);
                    break;
                case "9":
                    displayAllPetsOverdue(pets);
                    break;
                case "10":
                    System.out.println("Thank you for coming!");
                    writeIDS(ids);
                    writeData(pets);
                    break;
                default:
                    break;
            }

            if(response.equals("10")){
                break;
            }
        }
    }
}
