/**
 * The Payment class represents a payment transaction with details such as ID, amount, payday, 
 * payment method, and status. It provides methods to access and modify these details.
 */
package Class_model;

public class Payment {
    // Unique identifier for the payment
    private int ID;

    // The amount of the payment
    private double amount;

    // The date of the payment
    private String payday;

    // The method used for the payment (e.g., cash, credit card)
    private String Paymethod;

    // The status of the payment (e.g., Pending, Completed, Cancelled)
    private String status;

    private Order order;
    /**
     * Constructs a Payment object with the specified ID, amount, payday, and payment method.
     * The status is initialized to "Pending".
     *
     * @param ID        the unique identifier for the payment
     * @param amount    the amount of the payment
     * @param payday    the date of the payment
     * @param Paymethod the method used for the payment
     */
    public Payment(int ID, double amount, String payday, String Paymethod, Order order) {
        this.ID = ID;
        this.amount = amount;
        this.payday = payday;
        this.Paymethod = Paymethod;
        this.order = order;
        this.status = "Pending";
    }
    public Payment(){
        //default constructor
    }
    /**
     * Sets the status of the payment.
     *
     * @param status the new status of the payment
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * Retrieves the status of the payment.
     *
     * @return the current status of the payment
     */
    public String getStatus(){
        return status;
    }

    /**
     * Retrieves order that is associated with this payment
     * @return the current order. 
    */
    public Order getOrder(){
        return this.order;
    }

    /**
     * Sets the order associated with this payment.
     * @param order
     */
    public void setOrder(Order order){
        this.order = order;
    }
    /**
     * Retrieves the unique identifier of the payment.
     *
     * @return the ID of the payment
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets the unique identifier of the payment.
     *
     * @param ID the new ID of the payment
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Retrieves the amount of the payment.
     *
     * @return the amount of the payment
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the payment.
     *
     * @param amount the new amount of the payment
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Retrieves the date of the payment.
     *
     * @return the payday of the payment
     */
    public String getPayday() {
        return payday;
    }

    /**
     * Sets the date of the payment.
     *
     * @param payday the new payday of the payment
     */
    public void setPayday(String payday) {
        this.payday = payday;
    }

    /**
     * Retrieves the payment method used.
     *
     * @return the payment method
     */
    public String getPaymethod() {
        return Paymethod;
    }

    /**
     * Sets the payment method used.
     *
     * @param paymethod the new payment method
     */
    public void setPaymethod(String paymethod) {
        Paymethod = paymethod;
    }

    /**
     * Returns a string representation of the Payment object.
     *
     * @return a string containing the payment details
     */
    @Override
    public String toString() {
        return "Payment{" +
                "ID=" + ID +
                ", amount=" + amount +
                ", payday='" + payday + '\'' +
                ", Paymethod='" + Paymethod + '\'' +
                '}';
    }
}
