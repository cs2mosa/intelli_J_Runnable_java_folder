package Class_model;

/**
 * Represents a Role with an ID, name, permissions level, and description.
 */
public final class Role {
    /**
     * Unique identifier for the role.
     */
    private String ID;

    /**
     * Name of the role.
     */
    private String RoleName;

    /**
     * Permissions level associated with the role.
     */
    private int PermissionsLevel;

    /**
     * Description of the role.
     */
    private String Description;

    /**
     * Constructs a Role with the specified ID, name, permissions level, and description.
     * 
     * @param ID              Unique identifier for the role.
     * @param RoleName        Name of the role.
     * @param PermissionsLevel Permissions level associated with the role.
     * @param Description     Description of the role.
     */
    public Role(String ID, String RoleName, int PermissionsLevel, String Description) {
        this.Description = Description;
        this.ID = ID;
        this.PermissionsLevel = PermissionsLevel;
        this.RoleName = RoleName;
    }

    /**
     * Gets the unique identifier for the role.
     * 
     * @return The ID of the role.
     */
    public String getID() {
        return ID;
    }

    /**
     * Gets the name of the role.
     * 
     * @return The name of the role.
     */
    public String getRoleName() {
        return RoleName;
    }

    /**
     * Gets the permissions level associated with the role.
     * 
     * @return The permissions level of the role.
     */
    public int getPermissionsLevel() {
        return PermissionsLevel;
    }

    /**
     * Gets the description of the role.
     * 
     * @return The description of the role.
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Sets the unique identifier for the role.
     * 
     * @param ID The new ID for the role.
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Sets the name of the role.
     * 
     * @param RoleName The new name for the role.
     */
    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }

    /**
     * Sets the permissions level associated with the role.
     * 
     * @param PermissionsLevel The new permissions level for the role.
     */
    public void setPermissionsLevel(int PermissionsLevel) {
        this.PermissionsLevel = PermissionsLevel;
    }

    /**
     * Sets the description of the role.
     * 
     * @param Description The new description for the role.
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * Compares this role to another object for equality based on permissions level.
     * 
     * @param obj The object to compare with.
     * @return True if the permissions level matches and the object is a Role, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            Role role = (Role) obj;
            return (PermissionsLevel == role.PermissionsLevel);
        } else if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        } else {
            return false;
        }
    }
}
