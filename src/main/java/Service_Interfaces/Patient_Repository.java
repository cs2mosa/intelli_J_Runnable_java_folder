package Service_Interfaces;

import java.util.HashSet;
import java.util.Set;

import Class_model.Patient;

/**
 * The PatientRepository interface defines the contract for managing patient records.
 * It provides methods to add, remove, update, retrieve, Get, and list patients.
 */
abstract interface PatientRepository {

    /**
     * Adds a new patient to the repository.
     * @param patient The Patient object to be added.
     * @return The unique ID of the added patient, or -1 if the addition failed.
     * @throws IllegalArgumentException if the patient is null or invalid.
     */
    int AddPatient(Patient patient) throws IllegalArgumentException;

    /**
     * Removes a patient from the repository based on their unique ID.
     * @param PatientID The unique identifier of the patient to be removed.
     * @return The status of the removal operation. -1 if failed, 0 if successful.
     * @throws IllegalArgumentException if the patient ID is invalid or not found.
     */
    int RemovePatient(int PatientID) throws IllegalArgumentException;

    /**
     * Updates a specific attribute of a patient in the repository.
     * @param PatientID The unique identifier of the patient to be updated.
     * @param Newpatient The patient to update.
     * @return The status of the update operation. -1 if failed, patient id if successful.
     * @throws IllegalArgumentException if the patient ID is invalid or the new patient is null.
     */
    int UpdatePatient(int PatientID, Patient Newpatient) throws IllegalArgumentException;

    /**
     * Retrieves a patient from the repository based on their unique ID.
     * @param PatientID The unique identifier of the patient to retrieve.
     * @return The Patient object corresponding to the given ID, or null if not found.
     */
    Patient GetPatient(int PatientID);

    /**
     * Retrieves a list of all patients in the repository.
     * @return A List containing all Patient objects in the repository.
     */
    Set<Patient> GetAllPatients();
}

class Patient_Repository implements PatientRepository {
    // Singleton instance of Patient_Repository
    private static Patient_Repository instance = null;
    // Set to store patients
    private Set<Patient> PATIENTS; // Using Set for better search complexity

    // Private constructor to prevent instantiation from outside
    private Patient_Repository() {
        PATIENTS = new HashSet<>(); 
    }

    // Method to get the singleton instance of Patient_Repository
    public static Patient_Repository getInstance() {
        if (instance == null) {
            instance = new Patient_Repository();
        }
        return instance;
    }
    //works fine
    @Override
    public int AddPatient(Patient patient)  throws IllegalArgumentException{
        if(patient == null || !(patient instanceof Patient)) throw new IllegalArgumentException("Patient not found, you should add patient first or check the id");
        if(!PATIENTS.contains(patient)){
            PATIENTS.add(patient);
            return patient.getID();
        }else{
            return -1;
        }
    }
    //works fine
    @Override
    public int RemovePatient(int PatientID)  throws IllegalArgumentException{
        if(GetPatient(PatientID) == null) throw new IllegalArgumentException("Patient not found, you should add patient first or check the id");
        if(PATIENTS.contains(GetPatient(PatientID)) && GetPatient(PatientID) != null){
            PATIENTS.remove(GetPatient(PatientID));
            return 0; // Return 0 if successful
        }else{
            return -1; // Return -1 if the patient was not found
        }
    }
    //works fine
    @Override
    public int UpdatePatient(int PatientID, Patient Newpatient)  throws IllegalArgumentException{
        if(RemovePatient(PatientID) == -1) 
            return -1; // Return -1 if the patient was not found
        return AddPatient(Newpatient);
    }
    //works fine
    @Override
    public Patient GetPatient(int PatientID) {
        //implementation for getting a patient by ID
        if(PATIENTS.isEmpty()) return null; // Return null if the set is empty
        for(Patient p : PATIENTS){
            if(p.getID() == PatientID){
                return p;
            }
        }
        return null; // Return null if not found
    }
    //works fine
    @Override
    public Set<Patient> GetAllPatients() {
        // Convert the Set to a List and return it.
        return PATIENTS;
    }
    
}