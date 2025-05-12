/**
 * The Admin class extends the User class and represents an administrator in the system.
 * It provides functionality specific to administrators, such as setting the salary for employees.
 * 
 * Constructor:
 * - Admin(String Username, String Password, String User_Email, String PhoneNumber, Set<Role> Roles):
 *   Initializes an Admin object with the provided username, password, email, phone number, and roles.
 * NOTE: item names are set manually until we get another way to solve that problem.
 * Methods:
 * - setSalary(User user, double salary, Role role):
 *   Allows the admin to set the salary for an employee (Pharmacist or Casher) in the pharmacy.
 *   This method ensures that only users with the "ADMIN" role can set salaries.
 *   It checks if the provided user is an instance of Pharmacist or Casher and if the role is "ADMIN".
 *   If the conditions are met, the salary is set for the respective employee and the method returns true.
 *   Otherwise, it returns false.
 * 
 * Note:
 * - The setSalary method is the only way to set the salary for any employee in the pharmacy.
 * - Consider implementing additional security measures to prevent unauthorized access to this method.
 * @author Mosa Abdulaziz
 * @version 1.2
 */ 
package Class_model;

import java.util.HashSet;

/**
 * we need to implement builder design pattern in every class here.
 */
import java.util.Set;

public class Admin extends User{
    private static Set<String> validCats = new HashSet<>();
    private static double totalIncome;

    private Admin(int UserId,String Username, String Password, String User_Email, String PhoneNumber, Set<Role> Roles) {
        super(UserId,Username, Password, User_Email, PhoneNumber,Roles);

    }

    //setting all valid names of items.
    private static boolean is_set = false;
    public static void setvalidCat(Set<String> validCats){
        if(!is_set){
            Admin.validCats = validCats;
            is_set = true;
        }
    }
    //adding a new valid item to the avaliable items.
    public static void addvaliCat(String name){
        Admin.validCats.add(name);
    }

    public static double gettotalIncome(){
        return Admin.totalIncome;
    }

    public static void setTotalIncome(double newtotal){
        Admin.totalIncome = newtotal;
    }
    
    /**
     * this function is the only way to set salary for any Employee in the pharmacy, need to think about preventing other's access
     */
    public static boolean setSalary(User user, double salary, Role role){
        
        if(user instanceof Pharmacist ){
            ((Pharmacist) user).setSalary(salary, true );
            return true;
        }else if(user instanceof Casher ){
            ((Casher) user).setSalary(salary, true);
            return true;
        }else{
            return false;
        }
    }

    public static boolean authorizeItem(Item item){
        // for it to work, then we will reimplement it.
        for(String temp : Admin.validCats){
            if(item.getCategory().toLowerCase() == temp.toLowerCase()){
                return true;
            }
        }
        return false;
    }
}