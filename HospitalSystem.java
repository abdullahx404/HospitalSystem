
import java.util.HashMap;
import java.util.Map;

// BAD PRACTICE: HashMap is not thread-safe.
// Multiple threads accessing and modifying this map can cause problems.
class HospitalSystem {

    // Shared patient records accessed by multiple threads
    static Map<Integer, String> patients = new HashMap<>();

    // BAD PRACTICE: No synchronization is used.
    // Multiple threads can execute this method simultaneously.
    public static void addPatient(int id, String name) {
        patients.put(id, name);
        System.out.println("Patient added: " + name);
    }

    // BAD PRACTICE: Reading shared data without synchronization.
    public static void getPatient(int id) {
        String patient = patients.get(id);

        if (patient != null) {
            System.out.println("Patient found: " + patient);
        } else {
            System.out.println("Patient not found: " + id);
        }
    }

    // BAD PRACTICE: Updating shared records without synchronization.
    public static void updatePatient(int id, String name) {
        if (patients.containsKey(id)) {
            patients.put(id, name);
            System.out.println("Patient updated: " + name);
        } else {
            System.out.println("Patient does not exist.");
        }
    }

    // BAD PRACTICE: Directly accessing shared HashMap while
    // other threads might be modifying it.
    public static void displayPatients() {
        System.out.println("\n--- Patient Records ---");

        for (Integer id : patients.keySet()) {
            System.out.println(id + " : " + patients.get(id));
        }
    }

    public static void main(String[] args) {

        // Creating multiple threads to perform patient operations.

        Thread t1 = new Thread(() -> {
            addPatient(1, "Ali");
            addPatient(2, "Ahmed");
        });

        Thread t2 = new Thread(() -> {
            addPatient(3, "Usman");
            updatePatient(1, "Ali Updated");
        });

        Thread t3 = new Thread(() -> {
            getPatient(1);
            getPatient(3);
        });

        Thread t4 = new Thread(() -> {
            displayPatients();
        });

        // Starting all threads concurrently.
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
