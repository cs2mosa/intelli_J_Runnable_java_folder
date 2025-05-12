package Service_Interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Class_model.*;

/**
 * PaymentRepository is an abstract interface that defines the contract for managing payment operations.
 * It provides methods to add, withdraw, update, and retrieve payment details.
 * NOTE : Payment Id should be the same as Order Id.
 */
abstract interface PaymentRepository {

    /**
     * Adds a new payment to the repository.
     * @param payment The Payment object to be added.
     * @return payment id on successs, -1 else
     */
    int AddPayment(int PatientId, Payment payment); 

    /**
     * Withdraws a payment from the repository using its unique identifier.
     * @param PatientId The unique identifier of the patient associated with the payment.
     * @param PaymentId The unique identifier of the payment to be withdrawn.
     * @return The status of the withdrawal operation. -1 if failed mostly because patient not found, 0 if successful.
     */
    int DeletePayment(int PatientId, int PaymentId); 

    /**
     * Updates a specific field of a payment record in the repository.
     * 
     * @param PatientId The unique identifier of the patient associated with the payment.
     * @param Newpayment The new payment to be updated.
     * @return status code of payment id on success, and -1 else.
     */
    int UpdatePayment(int PatientId, Payment Newpayment); 

    /**
     * Retrieves a payment from the repository by its unique identifier.
     * 
     * @param PaymentId The unique identifier of the payment to be retrieved.
     * @return The Payment object corresponding to the given PaymentId.
     */
    List<Payment> GetById(int PatientId); 

    /**
     * Retrieves a list of all payments in the repository.
     * 
     * @return A list of all Payment objects.
     */
    List<Payment> GetAllPayments();
    
    /**
     * Retrieves a payment from the repository by its unique identifier.
     * 
     * @param PaymentId The unique identifier of the payment to be retrieved.
     * @return The Payment object corresponding to the given PaymentId. null if not found.
     */
    Payment GetPayment(int PaymentId); 
}

class Payment_Repository implements PaymentRepository {
    // Singleton instance of Payment_Repository
    private static Payment_Repository instance = null;
    // Map to store payments:-> Integer : UserId.
    private static Map<Integer,List<Payment>> PAYMENTS; // Using Set for better search complexity

    // Private constructor to prevent instantiation from outside
    private Payment_Repository() {
        //private constructor for singleton design.
        PAYMENTS = new HashMap<>(); 
    }

    // Method to get the singleton instance of Payment_Repository
    public static PaymentRepository GetInstance(){
        if(instance ==null){
            instance  =  new Payment_Repository();
            return instance;
        }
        else{
            return instance;
        }
    }

    //works fine
    @Override
    public int AddPayment(int PatientId, Payment payment) {
        // Implementation to add a payment
        if (payment == null ) throw new IllegalArgumentException("payment should be of type Payment");
        if(Order_Service.getInstance().GetById(payment.getID()) == null) throw new IllegalArgumentException("Order not found, you should add order first or check the id");
        if(Patient_Repository.getInstance().GetPatient(PatientId) == null) throw new IllegalArgumentException("Patient not found, you should add patient first or check the id");
        if(PAYMENTS.containsKey(PatientId)){
            List<Payment> payments = PAYMENTS.get(PatientId);
            if(payments == null) return -1;
            for(Payment p : payments){
                if(p.getID() == payment.getID()){
                    return -1; // Payment already exists, return -1
                }
            }
            payments.add(payment);
        }else{
            List<Payment> payments = new ArrayList<>();
            payments.add(payment);
            PAYMENTS.put(PatientId, payments);
        }
        // Set the payment ID and status
        payment.setStatus("Pending");
        return payment.getID();
    }

    //works fine.
    @Override
    public int DeletePayment(int PatientId, int PaymentId) {
        // Implementation to withdraw a payment by ID
        if (Patient_Repository.getInstance().GetPatient(PatientId) == null)
            throw new IllegalArgumentException("Patient not found, you should add patient first or check the id");
        if(Payment_Repository.GetInstance().GetById(PatientId) == null)
            return -1;
        if(PAYMENTS.containsKey(PatientId)){
            List<Payment> payments =  PAYMENTS.get(PatientId);
            payments.removeIf(payment -> payment.getID() == PaymentId);
            return 0;
        }
        else{
            return -1; // Payment not found, return -1
        }
    }

    //works fine
    @Override
    public int UpdatePayment(int PatientId, Payment Newpayment) {
        // Implementation to update payment details
        DeletePayment(PatientId, Newpayment.getID());
        return AddPayment(PatientId, Newpayment);
    }

    //not used
    @Override
    public List<Payment> GetById(int PatientId) {
        // Implementation to get a payment by ID
        return PAYMENTS.get(PatientId); 
    }

    //not used
    @Override  
    public List<Payment> GetAllPayments() {
        // Implementation to get all payments
        List<Payment> allPayments = new ArrayList<>();
        for(List<Payment> paymentList : PAYMENTS.values()){
            allPayments.addAll(paymentList);
        }
        return allPayments;
    }

    //works fine
    @Override
    public Payment GetPayment(int PaymentId) {
        // Implementation to get a payment by ID
        for(List<Payment> paymentList : PAYMENTS.values()){
            for(Payment payment : paymentList){
                if(payment.getID() == PaymentId){
                    return payment;
                }
            }
        }
        return null; // Placeholder return statement if not found
    }
}
