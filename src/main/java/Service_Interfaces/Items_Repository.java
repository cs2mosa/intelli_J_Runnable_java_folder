package Service_Interfaces;

import java.util.*;
/*
 * we need to implement singleton design pattern in every repository here.
 */
import Class_model.Item;

//item repo is a Set of Items, used Set for better complexity in searching.
/**
 * The ItemsRepository interface defines the contract for managing a collection of items.
 * It provides methods to add, remove, update, and retrieve items, as well as query items by category
 * and get the total number of items.
 */
abstract interface ItemsRepository {
    
    /**
     * retrives item based on its name.
     * @param ItemName
     * @return 
     */
    Item GetItemByName(String ItemName);
    /**
     * Adds a new item to the repository.
     * @param item The item to be added.
     */
    void AddNewItem(Item item);

    /**
     * Removes an item from the repository by its name.
     * @param Itemname The name of the item to be removed.
     */
    void RemoveItemByName(String Itemname);

    /**
     * Updates an existing item in the repository based on a query and a new value.
     * @param itemName name of the item to be updated
     * @param newItem  the updated item to register
     */
    void UpdateItem(String itemName, Item newItem);

    /**
     * Retrieves all items from the repository.
     * @return A list of all items.
     */
    List<Item> GetAllItems();

    /**
     * Gets the total number of items in the repository.
     * @return The number of items.
     */
    long GetNumberOfItems();

    /**
     * Retrieves a list of items that belong to a specific category.
     * @param category The category to filter items by.
     * @return A list of items in the specified category.
     */
    List<Item> GetItemsByCategory(String category);
}

class Items_Repository implements ItemsRepository{

    // Singleton instance of Items_Repository
    private static Items_Repository instance = null;

    // Set to store items
    private Set<Item> ITEMS;

    private Items_Repository(){
        // Private constructor to prevent instantiation from outside
        ITEMS = new HashSet<>();
    }
    
    public static Items_Repository GetInstance(){
        if(instance == null){
            instance = new Items_Repository();
            return instance;
        }
        else{
            return instance;
        }
    }

    @Override
    public Item GetItemByName(String ItemName){
        if(ItemName == null) return null;
        for(Item itm : ITEMS){
            if(itm.getMedicName().equals(ItemName)){
                return itm;
            }
        }
        return null;
    }

    @Override
    public void AddNewItem(Item item){
        if(item == null || GetItemByName(item.getMedicName()) != null) throw new IllegalArgumentException("Item already exists or null item");
        ITEMS.add(item); // Adds the item to the set
    }

    @Override
    public void RemoveItemByName(String Itemname){
        if(Itemname == null) throw new IllegalArgumentException("Item name cannot be null");
        Item temp  = GetItemByName(Itemname);
        if(temp != null){
            ITEMS.remove(temp);
        }
    }

    @Override
    public void UpdateItem(String itemName, Item newItem){
        RemoveItemByName(itemName);
        AddNewItem(newItem);
    }

    @Override
    public List<Item> GetAllItems(){
        return new ArrayList<>(ITEMS); // Placeholder return value, should return a list of all items
    }

    @Override
    public long GetNumberOfItems(){
        return ITEMS.size(); // Returns the number of items in the repository
    }

    @Override
    public List<Item> GetItemsByCategory(String category){
        List<Item> templist = new ArrayList<>();
        for(Item itm : ITEMS){
            if(itm.getMedicName().equals(category)){
                templist.add(itm);
            }
        }
        return templist; // Placeholder return value, should return a list of items in the specified category
    }
}