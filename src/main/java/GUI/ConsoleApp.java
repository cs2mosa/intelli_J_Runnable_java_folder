package GUI;

import Service_Interfaces.*;
import Class_model.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Headless replacement for the whole JavaFX GUI.
 * One class, one Scanner, no external deps except your existing service layer.
 */
public class ConsoleApp {

    private static final Scanner IN = new Scanner(System.in);
    private static String currentUser = null;          // logged-in username
    private static boolean isPharmacist = false;       // role flag

    public static void main(String[] args) {
        printHeader();
        while (true) rootMenu();
    }

    /* -------------------------- TOP-LEVEL MENUS -------------------------- */

    private static void rootMenu() {
        println("\n========== Pharmacy CLI ==========");
        println("1  Patient  login");
        println("2  Pharmacist login");
        println("0  Exit");
        int c = readInt("Select > ");
        switch (c) {
            case 1 -> patientLogin();
            case 2 -> pharmacistLogin();
            case 0 -> exit();
            default -> println("Invalid choice");
        }
    }

    private static void patientMenu() {
        while (currentUser != null && !isPharmacist) {
            println("\n---------- Patient Menu ----------");
            println("1  Place order");
            println("2  View my orders");
            println("3  Pay pending order");
            println("4  View balance");
            println("5  Edit profile");
            println("0  Logout");
            switch (readInt("Select > ")) {
                case 1 -> placeOrder();
                case 2 -> viewOrders();
                case 3 -> payOrder();
                case 4 -> viewBalance();
                case 5 -> editPatientProfile();
                case 0 -> logout();
                default -> println("Invalid choice");
            }
        }
    }

    private static void pharmacistMenu() {
        while (currentUser != null && isPharmacist) {
            println("\n-------- Pharmacist Menu ---------");
            println("1  Inventory management");
            println("2  View received orders");
            println("3  Edit profile");
            println("0  Logout");
            switch (readInt("Select > ")) {
                case 1 -> inventoryCrud();
                case 2 -> viewReceivedOrders();
                case 3 -> editPharmacistProfile();
                case 0 -> logout();
                default -> println("Invalid choice");
            }
        }
    }

    /* -------------------------- AUTH -------------------------- */

    private static void patientLogin() {
        String u = readLine("Username : ");
        String p = readLine("Password : ");
        if (Patient_Service.getInstance().AuthenticatePatient(u, p)) {
            currentUser = u;
            isPharmacist = false;
            patientMenu();
        } else {
            println("Invalid credentials");
            // NEW -------------------------------------------------
            while (true) {
                println("\n1  Try again");
                println("2  Patient sign-up");
                println("0  Exit");
                switch (readInt("Select > ")) {
                    case 1 -> { patientLogin(); return; } // recurse once, then return
                    case 2 -> { patientSignup(); return; } // uses existing method below
                    case 0 -> exit();
                    default -> println("Invalid choice");
                }
            }
            // -----------------------------------------------------
        }
    }

    private static void pharmacistLogin() {
        String u = readLine("Username : ");
        String p = readLine("Password : ");
        Pharmacist ph = (Pharmacist) User_Service.getInstance().GetByUsername(u);
        if (ph != null && ph.getPassword().equals(p)) {
            currentUser = u;
            isPharmacist = true;
            pharmacistMenu();
        } else {
            println("Invalid credentials");
            // NEW -------------------------------------------------
            while (true) {
                println("\n1  Try again");
                println("2  Pharmacist sign-up");
                println("0  Exit");
                switch (readInt("Select > ")) {
                    case 1 -> { pharmacistLogin(); return; }
                    case 2 -> { pharmacistSignup(); return; }
                    case 0 -> exit();
                    default -> println("Invalid choice");
                }
            }
            // -----------------------------------------------------
        }
    }

    private static void logout() {
        currentUser = null;
        isPharmacist = false;
        println("Logged out.");
    }

    /* -------------------------- PATIENT ACTIONS -------------------------- */

    private static void placeOrder() {
        List<Item> items = Inventory_service.getInstance().GetAllItems();
        if (items == null || items.isEmpty()) {
            println("No medicines in stock.");
            return;
        }
        Map<String, Integer> cart = new LinkedHashMap<>();
        while (true) {
            println("\nAvailable items:");
            for (int i = 0; i < items.size(); i++) {
                Item it = items.get(i);
                println((i + 1) + ") " + it.getMedicName() +
                        "  | cat:" + it.getCategory() +
                        "  | price:$" + it.getPrice() +
                        "  | stock:" + it.getQuantity());
            }
            println("0  Finish order");
            int ch = readInt("Add item # (0 to finish) > ");
            if (ch == 0) break;
            if (ch < 1 || ch > items.size()) {
                println("Invalid index");
                continue;
            }
            Item sel = items.get(ch - 1);
            int qty = readInt("Quantity > ");
            if (qty <= 0 || qty > sel.getQuantity()) {
                println("Invalid quantity or not enough stock");
                continue;
            }
            cart.merge(sel.getMedicName(), qty, Integer::sum);
            println("Added to cart.");
        }
        if (cart.isEmpty()) {
            println("Empty cart – order cancelled.");
            return;
        }
        // dummy pharmacist (same as original GUI)
        Pharmacist dummy = new Pharmacist();
        dummy.setUsername("ahmed");
        dummy.setID(new Random().nextInt(10000));
        int orderId = Patient_Service.getInstance()
                .PlaceOrder(cart, Patient_Service.getInstance().GetPatient(currentUser).getID(), dummy);
        if (orderId > 0) println("Order placed. ID = " + orderId);
        else println("Failed to place order.");
    }

    private static void viewOrders() {
        List<Order> list = Order_Service.getInstance().GetByCustomer(currentUser);
        if (list == null || list.isEmpty()) {
            println("No orders found.");
            return;
        }
        list.forEach(o ->
                println("ID:" + o.getOrderId() +
                        "  | total:$" + o.getTotalPrice() +
                        "  | status:" + o.getStatus()));
    }

    private static void payOrder() {
        List<Order> orders = Order_Service.getInstance().GetByCustomer(currentUser);
        Order pending = null;
        for (Order o : orders)
            if ("Pending".equals(o.getStatus())) { pending = o; break; }
        if (pending == null) {
            println("No pending order.");
            return;
        }
        println("Pending order ID " + pending.getOrderId() +
                "  amount $" + pending.getTotalPrice());
        double bal = Patient_Service.getInstance().GetPatientBalance(currentUser);
        println("Your balance $" + bal);
        double amt = readDouble("Enter payment amount > ");
        if (amt < pending.getTotalPrice()) {
            println("Amount too small.");
            return;
        }
        Payment p = new Payment();
        p.setAmount(pending.getTotalPrice());
        p.setPayday(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        p.setPaymethod("Cash"); // console default
        p.setOrder(pending);
        p.setStatus("Pending");
        p.setID(pending.getOrderId());
        int payId = Payment_service.getInstance().AddPayment(
                Patient_Service.getInstance().GetPatient(currentUser).getID(), p);
        if (Payment_service.getInstance().ProcessPayment(
                Patient_Service.getInstance().GetPatient(currentUser).getID(), payId) == payId)
            println("Payment successful – order now Paid.");
        else
            println("Payment failed (check balance).");
    }

    private static void viewBalance() {
        double b = Patient_Service.getInstance().GetPatientBalance(currentUser);
        println("Current balance: $" + b);
    }

    private static void editPatientProfile() {
        Patient pat = Patient_Service.getInstance().GetPatient(currentUser);
        println("Leave blank to keep current value.");
        String name  = readLine("New name (" + pat.getUsername() + ") : ");
        String ageS  = readLine("New age (" + pat.getAge() + ") : ");
        String addr  = readLine("New address (" + pat.getAddress() + ") : ");
        if (!name.isBlank())
            Patient_Service.getInstance().UpdatePatient(currentUser, pat.getPassword(), "username", name);
        if (!ageS.isBlank())
            Patient_Service.getInstance().UpdatePatient(currentUser, pat.getPassword(), "age", Float.parseFloat(ageS));
        if (!addr.isBlank())
            Patient_Service.getInstance().UpdatePatient(currentUser, pat.getPassword(), "address", addr);
        println("Profile updated.");
    }

    /* -------------------------- PHARMACIST ACTIONS -------------------------- */

    private static void inventoryCrud() {
        while (true) {
            println("\n---- Inventory ----");
            println("1  Add item");
            println("2  Remove item");
            println("3  Update price");
            println("4  Low-stock report");
            println("0  Back");
            switch (readInt("Select > ")) {
                case 1 -> addItem();
                case 2 -> removeItem();
                case 3 -> updatePrice();
                case 4 -> lowStock();
                case 0 -> { return; }
            }
        }
    }

    private static void addItem() {
        String name = readLine("Item name  : ");
        String cat  = readLine("Category   : ");
        double price = readDouble("Price      : ");
        int    qty   = readInt("Quantity   : ");
        Item i = new Item.builder()
                .setMedicName(name)
                .setCategory(cat)
                .setPrice(price)
                .setQuantity(qty)
                .setExpireDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
        int rc = Inventory_service.getInstance().AddNewItem(i);
        println(rc == 0 ? "Added." : "Failed.");
    }

    private static void removeItem() {
        String name = readLine("Item name to remove : ");
        int rc = Inventory_service.getInstance().RemoveItemByName(name);
        println(rc == 0 ? "Removed." : "Not found.");
    }

    private static void updatePrice() {
        String name  = readLine("Item name : ");
        double price = readDouble("New price : ");
        int rc = Inventory_service.getInstance().UpdateItemPrice(name, (float) price);
        println(rc == 0 ? "Price updated." : "Not found.");
    }

    private static void lowStock() {
        List<String> list = Inventory_service.getInstance().getLowStockItems();
        if (list == null || list.isEmpty()) {
            println("No low-stock items.");
            return;
        }
        list.forEach(System.out::println);
    }

    private static void viewReceivedOrders() {
        println("ReceivedOrders screen not implemented yet.");
    }

    private static void editPharmacistProfile() {
        Pharmacist ph = (Pharmacist) User_Service.getInstance().GetByUsername(currentUser);
        println("Leave blank to keep current value.");
        String name  = readLine("Name (" + ph.getUsername() + ") : ");
        String email = readLine("Email (" + ph.getUserEmail() + ") : ");
        String phone = readLine("Phone (" + ph.getPhoneNumber() + ") : ");
        if (!name.isBlank())
            User_Service.getInstance().UpdateUser(currentUser, "username", name);
        if (!email.isBlank())
            User_Service.getInstance().UpdateUser(currentUser, "email", email);
        if (!phone.isBlank())
            User_Service.getInstance().UpdateUser(currentUser, "phone", phone);
        println("Profile updated.");
    }

    /* -------------------------- HELPERS -------------------------- */

    private static void printHeader() {
        println("=== Pharmacy Management System (CLI) ===");
    }

    private static void exit() {
        println("Goodbye.");
        System.exit(0);
    }

    private static String readLine(String prompt) {
        print(prompt);
        return IN.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                println("Not a number, try again.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readLine(prompt));
            } catch (NumberFormatException e) {
                println("Not a number, try again.");
            }
        }
    }

    private static void print(String s) { System.out.print(s); }
    private static void println(String s) { System.out.println(s); }

    private static void patientSignup() {
        // reuse the exact code you already have
        String username = readLine("Username : ");
        String password = readLine("Password : ");
        String email = readLine("email : ");
        String phone = readLine("phone : ");
        float age = Float.parseFloat(readLine("age : "));
        String address = readLine("address : ");
            // Generate a random patientId
        Random random = new Random();
        int patientId = random.nextInt(10000);

        // Create a new Patient object using the constructor
        Patient patient = new Patient(patientId, username, password, email, phone, age, address, new HashSet<>(), new ArrayList<>(), new HashSet<>());

        // Add the patient using PatientService
        Patient_Service.getInstance().AddPatient(patient);

    }

    private static void pharmacistSignup() {
        // reuse the exact code you already have
        String username = readLine("Username : ");
        String password = readLine("password : ");
        String email = readLine("email : ");
        String phone = readLine("phone : ");
        String code = readLine("code : ");
        //validation code
        Pharmacist pharmacist = new Pharmacist();
        pharmacist.setUsername(username);
        pharmacist.setPassword(password);
        pharmacist.setUserEmail(email);
        pharmacist.setPhoneNumber(phone);
        pharmacist.setRoles(new HashSet<>());

        Random random = new Random();
        pharmacist.setID(random.nextInt(10000));
        if (User_Service.getInstance().AddUser(pharmacist) > 0) {
            println("success");
        }
    }
}