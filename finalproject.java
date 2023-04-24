
import java.util.Map;
import java.util.Hashtable;
import java.util.*;
import java.io.*;



class Admin {
    
    private String username;
    private String password;
    
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // getters and setters for the fields
    
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    public void addData(Map<Integer, String[]> transport,int id) {
        Scanner input=new Scanner(System.in);
        String s1=input.nextLine();
        String s2=input.nextLine();
        String s3=input.nextLine();
        transport.put(id, new String[] {s1, s2, s3});
    }
    public void editData(Map<Integer, String[]> transport,int id,int choiceedit) {
        System.out.println("edit");

        Scanner input=new Scanner(System.in);

        if(choiceedit==1){
            System.out.println("ENTER NEW DATA TO THE TRANSPORT NO="+id);
            String k1;
            k1=input.nextLine();
            String s2=input.nextLine();
            String s3=input.nextLine();

            String[] currentValue = transport.get(id);
            if (currentValue != null) {
                transport.put(id, new String[] {k1, s2, s3});
            }
            System.out.println("VALUES UPDATED SUCCESSFULLY");
        }
        else if(choiceedit==2){
            String[] value=transport.get(id);
            System.out.println("ENTER NEW ID=");
            int key=input.nextInt();
            transport.put(key,value);
            transport.remove(id);
            System.out.println("ID UPDATED SUCCESSFULLY");

        }

    }
        
    public void deleteData(Map<Integer, String[]> transport,int id) {
        transport.remove(id);
        System.out.println("REMOVED THE ELEMENT");

    }
}



// Base class for all users
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

// Class for registered users
class RegisteredUser extends User {
    public static final HashMap<String, String[]> users = new HashMap<>();

    static {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String key = parts[0];
                    String[] value = new String[]{parts[1]};
                    users.put(key, value);
                }
            }
            reader.close();
            System.out.println("Dictionary read from file");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    public RegisteredUser(String username, String password) {
        super(username, password);
    }

    public static boolean authenticate(String username, String password) {
        // Check if the user exists in the dictionary and the password is correct
        return users.containsKey(username) && users.get(username)[0].equals(password);
    }

    public static void addUser(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists");
            return;
        }
        users.put(username, new String[]{password});
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt", true))) {
            bw.write(username + "," + password);
            bw.newLine();
            System.out.println("New user created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

// Class for new users
class NewUser extends User {
    public NewUser(String username, String password) {
        super(username, password);
        RegisteredUser.addUser(username, password); // Add the new user to the dictionary
    }
}


interface displayfunc{
    void showdata( Map<Integer, String[]> transport );
    void userdisplay(Map<Integer, String[]> transport,int key);
}

class display implements displayfunc{
    public  void showdata( Map<Integer, String[]> transport ){
        for (Map.Entry<Integer, String[]> entry : transport.entrySet()) {
            int key = entry.getKey();
            String[] value = entry.getValue();
            System.out.println("TRANSPORT ID: " + key + "   DETAILS: " + Arrays.toString(value));
        }
    }
    public void userdisplay(Map<Integer, String[]> transport,int key){
        if (transport.containsKey(key)) {
            String[] value = transport.get(key);
            System.out.println("DETAILS:"+Arrays.toString(value));
        } else {
            System.out.println("Key not found in map.");
        }

    }

}


public class finalproject {
    public static void readdata(Map<Integer, String[]> transport, String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Integer key = Integer.parseInt(parts[0]);
                    String[] value = new String[]{parts[1], parts[2], parts[3]};
                    transport.put(key, value);
                }
            }
            reader.close();
            System.out.println("Dictionary read from file");
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void userreaddata(Map<String, String[]> userdetail) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String key = parts[0];
                    String[] value = new String[]{parts[1]};
                    userdetail.put(key, value);
                }
            }
            reader.close();
            System.out.println("Dictionary read from file");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    public static void userdeleteData(Map<String, String[]> userdetail, String id) {
        userdetail.remove(id);
        System.out.println("REMOVED THE ELEMENT");
    }

    public static void userappenddata(Map<String,String[]> userdetail){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt", true))) {
            for (String key : userdetail.keySet()) {
                String[] values = userdetail.get(key);
                String line = key + "," + values[0];

                // check if the line already exists in the file
                boolean lineExists = false;
                try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File("user.txt"))) {
                    while (scanner.hasNextLine()) {
                        String existingLine = scanner.nextLine();
                        if (existingLine.equals(line)) {
                            lineExists = true;
                            break;
                        }
                    }
                }

                // write the line to the file if it doesn't already exist
                if (!lineExists) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

    public static void appenddata(Map<Integer,String[]> transport,String filename){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            for (Integer key : transport.keySet()) {
                String[] values = transport.get(key);
                String line = key + "," + values[0] + "," + values[1]+ "," + values[2];

                // check if the line already exists in the file
                boolean lineExists = false;
                try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File(filename))) {
                    while (scanner.hasNextLine()) {
                        String existingLine = scanner.nextLine();
                        if (existingLine.equals(line)) {
                            lineExists = true;
                            break;
                        }
                    }
                }

                // write the line to the file if it doesn't already exist
                if (!lineExists) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

    public static void cleardata(String filename){
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(new byte[0]);
            fos.close();
         } catch (IOException e) {
            System.out.println("An error occurred while clearing the file: " + e.getMessage());
         }
    }


    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);

        Map<Integer, String[]> train = new Hashtable<>();
        Map<Integer, String[]> bus = new Hashtable<>();
        Map<Integer, String[]> airplane = new Hashtable<>();

        readdata(train,"train.txt");
        readdata(bus,"bus.txt");
        readdata(airplane,"airplane.txt");

        Map<String, String[]> userdetail = new Hashtable<>();
        userreaddata(userdetail);

        display d=new display();

        boolean choiceadimnuser=true;
        while(choiceadimnuser){
            System.out.println("=================ENTER YOUR CHOICE===============\n 1 TO LOGIN AS ADMIN \n 2 TO LOGIN AS USER \n========================================== ");

            int opt;
            opt=input.nextInt();
            input.nextLine();
            if(opt==1){
                Admin a1=new Admin("admin","abc123");
                System.out.println("===========<<ENTER ADMIN USER NAME AND PASSWARD:>>===========");
                String uname=input.nextLine();
                String passward=input.nextLine();
                boolean loginpass=(a1.authenticate(uname, passward));
                if(loginpass==true){
        
                    boolean conti=true;
                    while(conti){
        
                        System.out.println("==============<<SELECT>>=============\n 1: TO ADD DATA \n 2: TO EDIT DATA \n 3: TO DELETE DATA \n 4: TO DELETE USER \n OR ANY OTHER KEY TO EXIT \n=====================================");
        
                        int choice=input.nextInt();
                        input.nextLine();
                        if(choice==1){
                            System.out.println("==================<<ADDING DATA>>===================");
                            System.out.println("==============<<SELECT>>=============\n 1: FOR TRAIN \n 2: FOR BUS \n 3: FOR AIRPORT \n=====================================");
                            int select=input.nextInt();
                            if(select==1){
                                System.out.println("========<<ENTER TRAIN_NUMBER, NAME, DATE AND TIME>>=========");
                                int id=input.nextInt();
                                a1.addData(train,id);
                                appenddata(train,"train.txt");
        
                            }
                            else if(select==2){
                                System.out.println("========<<ENTER BUS_NUMBER, NAME, DATE AND TIME>>=========");
                                int id=input.nextInt();
                                a1.addData(bus,id);
                                appenddata(bus,"bus.txt");
                            }
                            else if(select==3){
                                System.out.println("========<<ENTER AIRPLANE_NUMBER, NAME, DATE AND TIME>>=========");
                                int id=input.nextInt();
                                a1.addData(airplane,id);
                                appenddata(airplane,"airplane.txt");
                            }
                            else{
                                System.out.println("Wrong choice");
                            }
                            
                        }
                        else if(choice==2){
                            System.out.println("==================<<EDITING DATA>>===================");
                            System.out.println("==============<<SELECT>>=============\n 1: FOR TRAIN \n 2: FOR BUS \n 3: FOR AIRPORT \n=====================================");
                            int select=input.nextInt();
                            if(select==1){
                                System.out.println("==================<<ENTER ID NUMBER>>===============");
                                int id=input.nextInt();
                                System.out.println("============<<ENTER>>=========\n 1: IF YOU WANT TO EDIT THE DETAILS AND \n 2: IF YOU WANT TO EDIT THE ID\n==========================");
                                int choiceedit=input.nextInt();
                                if (train.containsKey(id)) {
                                    a1.editData(train,id,choiceedit);
                                  } else {
                                    System.out.println(id + " is train number is not present in data base");
                                  }
                                cleardata("train.txt");
        
                               appenddata(train,"train.txt");
                            }
                            else if(select==2){
                                System.out.println("==================<<ENTER ID NUMBER>>===============");
                                int id=input.nextInt();
                                System.out.println("============<<ENTER>>=========\n 1: IF YOU WANT TO EDIT THE DETAILS AND \n 2: IF YOU WANT TO EDIT THE ID\n==========================");
                                int choiceedit=input.nextInt();
                                if (bus.containsKey(id)) {
                                    a1.editData(bus,id,choiceedit);
                                  } else {
                                    System.out.println(id + " is bus number is not present in data base");
                                  }
                                cleardata("bus.txt");
                                 appenddata(bus,"bus.txt");
                            }
                            else if(select==3){
                                System.out.println("==================<<ENTER ID NUMBER>>===============");
                                int id=input.nextInt();
                                System.out.println("============<<ENTER>>=========\n 1: IF YOU WANT TO EDIT THE DETAILS AND \n 2: IF YOU WANT TO EDIT THE ID\n==========================");
                                int choiceedit=input.nextInt();
                                if (airplane.containsKey(id)) {
                                    a1.editData(airplane,id,choiceedit);
                                  } else {
                                    System.out.println(id + " is airplane number is not present in data base");
                                  }
                                 cleardata("airplane.txt");
                                appenddata(airplane,"airplane.txt");
                            }
                            else{
                                System.out.println("Wrong choice");
                            }
                        }
                        else if(choice==3){
                            System.out.println("==================<<DELETING DATA>>===================");
                            System.out.println("==============<<SELECT>>=============\n 1: FOR TRAIN \n 2: FOR BUS \n 3: FOR AIRPORT \n=====================================");
                            int select=input.nextInt();
                            if(select==1){
                                System.out.println("========<<ENTER TRAIN_NUMBER>>=========");
                                int id=input.nextInt();
                                a1.deleteData(train,id);
                                 cleardata("train.txt");
                                appenddata(train,"train.txt");
        
                            }
                            else if(select==2){
                                System.out.println("========<<ENTER BUS_NUMBER>>=========");
                                int id=input.nextInt();
                                a1.deleteData(bus,id);
                                cleardata("bus.txt");
                                appenddata(bus,"bus.txt");
                            }
                            else if(select==3){
                                System.out.println("========<<ENTER AIRPLANE_NUMBER>>=========");
                                int id=input.nextInt();
                                a1.deleteData(airplane,id);
                                cleardata("airplane.txt");
                                appenddata(airplane,"airplane.txt");
                            }
                            else{
                                System.out.println("WRONG CHOICE");
                            }
                        }
                        else if(choice==4){
                            System.out.println("=======ENTER THE USER ID======");
                            String userid=input.nextLine();
                            userdeleteData(userdetail, userid);
                            cleardata("user.txt");
                            userappenddata(userdetail);
                        }
                        else{
                            conti=false;
                            
                        }
                    }
                }
                
                else{
                    System.out.println("=========<<WRONG USER NAME AND PASSWARD>>=========");
                }
                if(loginpass==true){
                    System.out.println("===<<SELECT 1,2,3 YOU WANT IF YOU WANT TO SEE THE RECORDS OF TRAIN BUS AIRPLANE RESPECTIVELY:>>==");
                    int s=input.nextInt();
                    if (s==1){
                        System.out.println("===========<<ALL TRAIN DETAILS>============");
                        d.showdata(train);
                    }
                    else if(s==2){
                        System.out.println("============<<ALL BUS DETAILS>>=============");
                        d.showdata(bus);
                    }
                    else if(s==3){
                        System.out.println("============<<ALL BUS DETAILS>>=============");
                        d.showdata(airplane);
                    }
                    else{
                        System.out.println("===========<<WRONG CHOICE>>=============");
                    }
        
                } 
            }
            else if(opt==2){
                boolean continuser=true;
                while(continuser){
                    System.out.println("==========ENTER 1 TO LOGIN USER ACCOUNT AND 2 TO CREATE ACCOUNT ===========");
                    int c = input.nextInt();
                    input.nextLine();
                    if(c==1){
                        System.out.println("=====ENTER USER NAME AND PASSWORD======");
                        String username = input.nextLine();
                        String password = input.nextLine();
                        if (RegisteredUser.authenticate(username,password)) {
                            System.out.println("Authentication successful");
                            System.out.println("===<<SELECT 1,2,3 YOU WANT IF YOU WANT TO SEE THE RECORDS OF TRAIN BUS AIRPLANE RESPECTIVELY:>>== ");
                            int cho=input.nextInt();
                            input.nextLine();
                            if(cho==1){
                                System.out.println("=======ENTER THE TRANSPORT ID=======");
                                int tid=input.nextInt();
                                d.userdisplay(train,tid);
                            }
                            else if(cho==2){
                                System.out.println("=======ENTER THE TRANSPORT ID=======");
                                int tid=input.nextInt();
                                d.userdisplay(bus,tid);
                            }
                            else if(cho==3){
                                System.out.println("=======ENTER THE TRANSPORT ID=======");
                                int tid=input.nextInt();
                                d.userdisplay(airplane,tid);
                            }
                            else{
                                System.out.println("===========WRONG CHOICE=============");
                            }
                            
                        } else {
                            System.out.println("Authentication failed");
                        }

                    }
                    else if(c==2){
                        System.out.println("=====ENTER USER NAME AND PASSWORD======");
                        String newUsername = input.nextLine();
                        String newPassword = input.nextLine();
                        NewUser newUser = new NewUser(newUsername, newPassword);
                    }
                    else{
                        System.out.println("=====WRONG CHOICE=====");
                    }
                    System.out.println("======ENTER 1 IF YOU WANT TO EXIT USER BLOCK======");
                    int a=input.nextInt();
                    if(a==1){
                        continuser=false;
                    }
                }
        
            }
            else{
                System.out.println("===========<<WRONG CHOICE>>============= ");
            }
            System.out.println("=========WOULD YOU LIKE TO EXIT OR CONTINUE==========\n #####ENTER 1 IF YOU WANT TO EXIT.");
            int s=input.nextInt();
            if(s==1){
                choiceadimnuser=false;
            }
    
        }



    }

}
