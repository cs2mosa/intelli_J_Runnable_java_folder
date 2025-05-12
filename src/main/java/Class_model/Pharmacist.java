/**
 * The Pharmacist class represents a pharmacist user in the system.
 * It extends the User class and includes additional attributes and methods
 * specific to a pharmacist, such as salary and medication safety checks.
 */
package Class_model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class Pharmacist extends User{
    
    private double salary;

    public Pharmacist(int UserId, String Username, String Password, String User_Email, String PhoneNumber, Set<Role> Roles) {
        super(UserId,Username,  Password, User_Email, PhoneNumber,Roles);
    }

    public Pharmacist(){

    }
    
    public int setSalary(double salary, boolean is_admin){
        if(is_admin){
            this.salary = salary;
            return 0;
        }
        return -1;
    }


    public double getSalary(){
        return salary;
    }

    //check if the medic is safe to the given patient
    public  boolean is_safe(Item item, Patient patient){
        Set<String> side_effects = item.getSideEffects();
        Set<String> allergies = patient.getAllergies();
        for (String s : side_effects){
            if(allergies.contains(s) && (item.getExpireDate().compareTo(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) > 0)){
                return false;
            }
        }
        return true;
    }

    // Method to calculate the annual salary of the pharmacist
    public double calculateAnnualSalary() {
        return salary * 12;
    }

    @Override
    public boolean equals(Object obj){
        if(this==obj && obj !=null && this.getClass() == obj.getClass()){
            return true;
        }
        else{
            return false;
        }
    }
    
}