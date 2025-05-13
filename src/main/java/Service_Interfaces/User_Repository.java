package Service_Interfaces;

import java.util.HashSet;
import java.util.Set;

import Class_model.Casher;
import Class_model.Pharmacist;
import Class_model.User;

/**
 * UserRepository is an interface that defines the contract for managing User entities.
 * It provides methods to add, update, delete, and retrieve users by their username or ID.
 */
abstract interface UserRepository {

    /**
     * Adds a new user to the repository.
     * @param user The User object to be added.
     * @return The unique identifier of the newly added user. -1 if the user already exists.
     * @throws IllegalArgumentException if the user is null or has invalid properties.
     */
    int Add(User user) throws IllegalArgumentException;

    /**
     * Updates an existing user in the repository.
     * @param user The User object with updated information.
     * @return The unique identifier of the updated user. -1 if the user does not exist.
     * @throws IllegalArgumentException if the user is null or has invalid properties.
     */
    int Update(User user) throws IllegalArgumentException;

    /**
     * Deletes a user from the repository.
     * @param user The User object to be removed.
     * @return 0 if the user was successfully deleted, -1 if the user does not exist.
     * @throws IllegalArgumentException if the user is null or has invalid properties.
     */
    int Delete(int UserId) throws IllegalArgumentException;

    /**
     * Retrieves a user by their username.
     * @param username The username of the user to retrieve.
     * @return The User object corresponding to the given username, or null if not found.
     * @throws IllegalArgumentException if the username is null or empty.
     */
    User GetByUsername(String username) throws IllegalArgumentException;

    /**
     * Retrieves a user by their unique ID.
     * @param ID The unique identifier of the user to retrieve.
     * @return The User object corresponding to the given ID, or null if not found.
     */
    User GetByID(int ID) ;
}

class User_Repository implements UserRepository {

    private static User_Repository instance = null;
    private static Set<User> USERS; // Using Set for better search complexity

    private User_Repository(){
        // Private constructor to prevent instantiation from outside
        USERS = new HashSet<>();
    }

    public static User_Repository GetInstance(){
        if (instance == null) {
            instance = new User_Repository();
            return instance;
        } else {
            return instance;
        }
    }

    @Override
    public int Add(User user) throws IllegalArgumentException {
        // Implementation to add a user

        if (user == null) throw new IllegalArgumentException("User cannot be null");
        if (user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) throw new IllegalArgumentException("Username and password cannot be null nor empty");
        if(!USERS.contains(user) && GetByUsername(user.getUsername()) == null) {
            if(user instanceof Pharmacist){
                USERS.add((Pharmacist)user);
            }else if(user instanceof Casher){
                USERS.add((Casher)user);
            }
            return user.getID();
        }else{
            return -1;
        }
    }

    @Override
    public int Delete(int UserId)  throws IllegalArgumentException{
        // Implementation to delete a user
        User user_indata = GetByID(UserId);
        if (user_indata == null)throw new IllegalArgumentException("User cannot be null");
        if(USERS.contains(user_indata) == false) {
            return -1; // User not found, cannot delete
        }
        USERS.remove(user_indata);
        return 0;
    }

    @Override
    public int Update(User Newuser)  throws IllegalArgumentException{
        // Implementation to update a user
        if(Delete(Newuser.getID()) == -1) {
            return -1; // User not found, cannot update
        }
        return Add(Newuser);
    }

    @Override
    public User GetByUsername(String username)  throws IllegalArgumentException{
        // Implementation to get a user by username
        if(username == null || username.isEmpty()) throw new IllegalArgumentException("Username cannot be null nor empty");
        if(USERS.isEmpty()) return null;
        for (User user : USERS) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User GetByID(int ID) {
        // Implementation to get a user by ID
        if(USERS.isEmpty()) return null;
        for (User user : USERS) {
            if (user.getID() == ID) {
                return user;
            }
        }
        return null;
    }

}