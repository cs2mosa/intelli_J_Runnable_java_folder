/**
 * The Casher class represents a cashier user in the system.
 * It extends the User class and includes additional attributes and methods
 * specific to a cashier, such as salary management.
 * 
 * <p>This class is part of the Class_model package.</p>
 * 
 * <p><strong>Note:</strong> The setSalary method should only be used with restricted access
 * to ensure proper control over salary modifications.</p>
 * 
 * @see User
 */
package Class_model;

import java.util.Set;

public class Casher extends User{
    private double salary;
    public Casher(int UserId, String Username, String Password, String User_Email, String PhoneNumber,double salary, Set<Role> Roles) {
        super(UserId,Username, Password, User_Email, PhoneNumber,Roles);
        this.salary = salary;
    }

    /**
     * this function should only be used with restricted access
     */
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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Casher)) return false;
        Casher other = (Casher) obj;
        return Double.compare(other.salary, salary) == 0 && super.equals(obj);
    }
}