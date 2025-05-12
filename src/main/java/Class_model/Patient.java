/**
 * The Patient class represents a patient in the system, extending the User class.
 * It includes additional attributes specific to patients such as age, address, allergies, and orders.
 * 
 * Attributes:
 * - Age: The age of the patient.
 * - address: The address of the patient.
 * - allergies: A set of allergies the patient has.
 * - orders: A list of orders associated with the patient.
 * 
 * Constructors:
 * - Patient(String Username, String Password, String User_Email, String PhoneNumber, float Age, 
 *           String address, Set<String> allergies, List<Order> orders, Set<Role> Roles):
 *   Initializes a new Patient object with the provided attributes.
 * 
 * Methods:
 * - getAge(): Returns the age of the patient.
 * - setAge(float Age): Sets the age of the patient.
 * - getAddress(): Returns the address of the patient.
 * - setAddress(String address): Sets the address of the patient.
 * - getAllergies(): Returns the set of allergies of the patient.
 * - setAllergies(Set<String> allergies): Sets the set of allergies of the patient.
 * - Add_allergy(String allergy): Adds a new allergy to the patient's allergy set.
 * - Remove_allergy(String allergy): Removes an allergy from the patient's allergy set.
 * - Add_order(Order order): Adds a new order to the patient's order list.
 * - Remove_order(Order order): Removes an order from the patient's order list.
 */
package Class_model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a Patient, which is a type of User.
 */
public class Patient extends User {
    /**
     * The age of the patient.
     */
    private float Age;

    /**
     * The address of the patient.
     */
    private String address;

    /**
     * A set of allergies the patient has.
     */
    private Set<String> allergies = new HashSet<>();

    /**
     * A list of orders associated with the patient.
     */
    private List<Order> orders = new ArrayList<>();

    private double PatientBalance;

    /**
     * Constructs a Patient with the specified details.
     *
     * @param Username   The username of the patient.
     * @param Password   The password of the patient.
     * @param User_Email The email of the patient.
     * @param PhoneNumber The phone number of the patient.
     * @param Age        The age of the patient.
     * @param address    The address of the patient.
     * @param allergies  The set of allergies of the patient.
     * @param orders     The list of orders of the patient.
     * @param Roles      The roles assigned to the patient.
     */
    public Patient(int UserId,String Username, String Password, String User_Email, String PhoneNumber, float Age, String address, Set<String> allergies, List<Order> orders, Set<Role> Roles) {
        super(UserId,Username, Password, User_Email, PhoneNumber, Roles);
        this.Age = Age;
        this.address = address;
        this.allergies = allergies;
        this.orders = orders;
        this.PatientBalance = 0.0;
    }
    public Patient(){
        this.Age = 0;
        this.address = " ";
        this.PatientBalance = 0.0;
    }
    /**
     * Sets the balance of the patient.
     *
     * @param balance The balance to set.
     */
    public void SetBalance(double balance){
        this.PatientBalance = balance;
    }

    /**
     * Gets the balance of the patient.
     *
     * @return The balance of the patient.
     */
    public double GetBalance(){
        return PatientBalance;
    }

    /**
     * Gets the age of the patient.
     *
     * @return The age of the patient.
     */
    public float getAge() {
        return Age;
    }

    /**
     * Sets the age of the patient.
     *
     * @param Age The age to set.
     */
    public void setAge(float Age) {
        this.Age = Age;
    }

    /**
     * Gets the address of the patient.
     *
     * @return The address of the patient.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the patient.
     *
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the set of allergies of the patient.
     *
     * @return The set of allergies.
     */
    public Set<String> getAllergies() {
        return allergies;
    }

    /**
     * Sets the set of allergies of the patient.
     *
     * @param allergies The set of allergies to set.
     */
    public void setAllergies(Set<String> allergies) {
        this.allergies = allergies;
    }

    /**
     * Adds an allergy to the patient's allergy set.
     *
     * @param allergy The allergy to add.
     */
    public void Add_allergy(String allergy) {
        this.allergies.add(allergy);
    }

    /**
     * Removes an allergy from the patient's allergy set.
     *
     * @param allergy The allergy to remove.
     * @return True if the allergy was removed, false otherwise.
     */
    public boolean Remove_allergy(String allergy) {
        return this.allergies.remove(allergy);
    }

    /**
     * Adds an order to the patient's order list.
     *
     * @param order The order to add.
     */
    public void Add_order(Order order) {
        this.orders.add(order);
    }

    /**
     * Removes an order from the patient's order list.
     *
     * @param order The order to remove.
     * @return True if the order was removed, false otherwise.
     */
    public boolean Remove_order(Order order) {
        return this.orders.remove(order);
    }

    public List<Order> getOrders(){
        return this.orders;
    }
    
}
