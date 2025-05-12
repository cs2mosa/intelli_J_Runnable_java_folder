package Service_Interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Class_model.*;

/**
 * PrescriptionRepository is an interface that defines the contract for managing prescriptions.
 * It provides methods to add, delete, and retrieve prescriptions based on specific criteria.
 */
abstract interface PrescriptionRepository {

    /**
     * Adds a new prescription to the repository.
     * @param prescription The Prescription object to be added.
     * @param userId The ID of the user associated with the prescription.
     * @return prescription id on success. -1 else.
     * @throws IllegalArgumentException if the prescription is null or if the user ID is invalid.
     */
    int Add(int userId, Prescription prescription) throws IllegalArgumentException;    

    /**
     * Deletes a prescription from the repository based on its unique ID.
     * @param ID The unique identifier of the prescription to be deleted.
     * @return status code of 0 on success , -1 else.
     * @throws IllegalArgumentException if the prescription ID is invalid or if the user ID is invalid.
     */
    int Delete(int userId, int ID) throws IllegalArgumentException;

    /**
     * Finds and retrieves a list of prescriptions associated with a specific patient's name.
     * @param patientName The name of the patient whose prescriptions are to be retrieved.
     * @return A list of prescriptions matching the given patient name. or null if not found.
     * @throws IllegalArgumentException if the patient name is null or empty.
     */
    List<Prescription> findByPatientID(int patientId) throws IllegalArgumentException;

    /**
     * Retrieves all prescriptions from the repository.
     * @return A list of all prescriptions. or null if not found.
     * @throws IllegalArgumentException if no prescriptions are found.
     */
    List<Prescription> findAll() throws IllegalArgumentException;

    /**
     * Retrieves a prescription by its unique ID.
     * @param preID The unique identifier of the prescription to be retrieved.
     * @return The Prescription object corresponding to the given ID, or null if not found.
     * @throws IllegalArgumentException if the prescription ID is invalid or if no prescriptions are found.
     */
    Prescription getPreById(int preID) throws IllegalArgumentException;
}
class Prescription_Repository implements PrescriptionRepository {
    
    //Map to store payments:-> Integer : UserId.
    private static Map<Integer, List<Prescription>> PRESCRIPTIONS;
    // Singleton instance of Prescription_Repository
    private static Prescription_Repository instance = null;

    private Prescription_Repository(){
       // Private constructor to prevent instantiation from outside.
       PRESCRIPTIONS = new HashMap<>();
    }

    public static Prescription_Repository GetInstance(){
        if (instance == null) {
            instance = new Prescription_Repository();
            return instance;
        }
        else{
            return instance;
        }
    }
    //works fine
    @Override
    public int Add(int userId, Prescription prescription)  throws IllegalArgumentException{
        // Implementation for adding a prescription
        List<Prescription> temp = PRESCRIPTIONS.get(userId);
        if(Patient_Repository.getInstance().GetPatient(userId) == null) throw new IllegalArgumentException("Patient not found, you should add patient first or check the id");
        if (prescription == null) throw new IllegalArgumentException("prescription cannot be null, check usage");
        if(temp == null ||temp.isEmpty()){
            temp = new ArrayList<>();
            temp.add(prescription);
            PRESCRIPTIONS.put(userId,temp);
            return prescription.getId();
        }
        temp.add(prescription);
        return prescription.getId();
    }
    //works fine
    @Override
    public int Delete(int userId, int ID) throws IllegalArgumentException {
        // Implementation for deleting a prescription
        try {
            List<Prescription> prescriptions = PRESCRIPTIONS.get(userId);
            if(Patient_Repository.getInstance().GetPatient(userId) == null) throw new IllegalArgumentException("Patient not found, you should add patient first or check the id");
            if (prescriptions == null || prescriptions.isEmpty()) throw new IllegalArgumentException("No prescriptions found for the given user ID.");
            for(Prescription prescription : prescriptions){
                if(prescription.getId() == ID){
                    prescriptions.remove(prescription);
                    return 0;
                }
            }
            return -1;
        } catch (Exception e) {
            // handle exception
            System.out.println("Error in deleting prescription: " + e.getMessage());
            return -1; // Error occurred during deletion
        }
    }
    //works fine
    @Override
    public List<Prescription> findByPatientID(int patientId) {
        // Implementation for finding prescriptions by patient ID
        return PRESCRIPTIONS.get(patientId);
    }
    //works fine
    @Override
    public List<Prescription> findAll()  throws IllegalArgumentException{
        // Implementation for finding all prescriptions
        try {
            if (PRESCRIPTIONS.isEmpty()) throw new IllegalArgumentException("No prescriptions found.");
            return (List<Prescription>)PRESCRIPTIONS.values().stream().flatMap(List::stream).toList();
        } catch (Exception e) {
            // handle exception
            System.out.println("Error in finding all prescriptions: " + e.getMessage());
            return null; // Error occurred during retrieval
        }
    }
    //works fine
    @Override
    public Prescription getPreById(int preID) throws IllegalArgumentException{
        try {
            if (PRESCRIPTIONS.isEmpty()) throw new IllegalArgumentException("No prescriptions are found.");
            if (PRESCRIPTIONS.values() == null) throw new IllegalArgumentException("Prescription database is empty.");
            return PRESCRIPTIONS.values()
                .stream()
                .flatMap(List::stream)
                .filter(prescription -> prescription.getId() == preID)
                .findFirst()
                .orElse(null);
        } catch (Exception e) {
            //handle exception
            System.out.println("Error in finding prescription by ID: " + e.getMessage());
            return null; // Error occurred during retrieval
        }
    }
}