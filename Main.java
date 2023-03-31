import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Client> clients = new ArrayList<>();
    public static ArrayList<Table> tables = new ArrayList<>();
    public static int table_id = 0;
    public static int table_id_1 = 0;
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Client temp = new Client();
        Update_Tables_To_Array_List();
        while(true){
            Update_Clients_To_Array_List();
            Menu();
            for (int i = 0 ; i < clients.size();i++){
                System.out.println(clients.get(i).name+ ' ' + clients.get(i).phonenumber+
                        ' ' + clients.get(i).time + ' ' + clients.get(i).clients_number + ' ' + clients.get(i).table.id + '\n');

            }
            int user_input = sc.nextInt();
            sc.nextLine();
            switch (user_input){
                case 1:
                    if(Check_Avaliability()){
                        System.out.println("Please type in your name and Family name: ");
                        temp.name = sc.nextLine();
                        do{
                            System.out.println("Please your number of guests(from 1 to 10):");
                            temp.clients_number = sc.nextInt();
                        }while(Check_Avaliability_of_Enough_Places(temp.clients_number));
                        System.out.println("Please input your phone number: ");
                        temp.phonenumber = sc.next();
                        System.out.println("Please input your time of arrival: ");
                        temp.time = sc.next();
                        temp.table.id = table_id;
                        table_id = 0;
                        clients.add(temp);
                        Update_Tables();
                        Update_Clients();
                        clients.clear();
                        System.out.println("Thank you for your reservation!");
                    }
                    else{
                        System.out.println("No free places, please come tomorrow");
                    }
                    break;
                case 2:
                    String name;
                    System.out.println("Please input your name: ");
                    name = sc.next();
                    sc.nextLine();
                    for(int i = 0; i < clients.size(); i++){
                        if(clients.get(i).name.equals(name)){
                            table_id_1 = clients.get(i).table.id;
                            clients.remove(i);
                        }
                    }
                    Update_Clients();
                    clients.clear();
                    for(int i = 0; i < tables.size(); i++){
                        if(tables.get(i).id == table_id_1){
                            tables.get(i).is_reserved = false;
                        }
                    }
                    Update_Tables();
                    table_id_1 = 0;
                    break;
                case 3:
                    int user_input_1 = 0;
                    System.out.println("Please input your name: ");
                    name = sc.next();
                    sc.nextLine();
                    boolean trigger = true;
                    while(trigger){
                        int client_index = 0;
                        Modify_Menu();
                        user_input_1 = sc.nextInt();
                        sc.nextLine();
                        switch (user_input_1){
                            case 1:
                                for(int i = 0; i < clients.size(); i++){
                                    if(clients.get(i).name.equals(name)){
                                        table_id = clients.get(i).table.id;
                                        client_index = i;
                                    }
                                }
                                for(int i = 0; i < tables.size(); i++){
                                    if(tables.get(i).id == table_id){
                                        tables.get(i).is_reserved = false;
                                    }
                                }
                                Update_Tables();
                                table_id = 0;
                                int client_number = 0;
                                do{
                                    System.out.println("Please your number of guests(from 1 to 10):");
                                    client_number = sc.nextInt();
                                }while(Check_Avaliability_of_Enough_Places(client_number));
                                Update_Tables();
                                clients.get(client_index).clients_number = client_number;
                                clients.get(client_index).table.id = table_id;
                                Update_Clients();
                                clients.clear();
                                Update_Clients_To_Array_List();
                                table_id = 0;
                                client_index = 0;
                                client_number = 0;
                                break;
                            case 2:
                                for(int i = 0; i < clients.size(); i++){
                                    if(clients.get(i).name.equals(name)){
                                        table_id = clients.get(i).table.id;
                                        client_index = i;
                                    }
                                }
                                clients.get(client_index).phonenumber = sc.next();
                                sc.nextLine();
                                Update_Clients();
                                clients.clear();
                                Update_Clients_To_Array_List();
                                client_index = 0;
                                break;
                            case 3:
                                for(int i = 0; i < clients.size(); i++){
                                    if(clients.get(i).name.equals(name)){
                                        table_id = clients.get(i).table.id;
                                        client_index = i;
                                    }
                                }
                                clients.get(client_index).time = sc.next();
                                sc.nextLine();
                                Update_Clients();
                                clients.clear();
                                Update_Clients_To_Array_List();
                                client_index = 0;
                                break;
                            case 4:
                                clients.clear();
                                trigger = false;
                                break;
                        }
                    }
                    break;
                default:
                    System.exit(120);
                    break;
            }
        }
    }
    public static void Menu(){
        System.out.println("Welcome to our restaurant bot! Please choose your function: ");
        System.out.println("1. Book a table");
        System.out.println("2. Cancel your reservation");
        System.out.println("3. Modify your reservation");
        System.out.println("4. Exit");
    }
    public static void Modify_Menu(){
        System.out.println("1. Modify your guest number");
        System.out.println("2. Modify your phone number");
        System.out.println("3. Modify your time of arrival");
        System.out.println("4. Exit");
    }
    public static void Update_Tables_To_Array_List() throws FileNotFoundException {
        File file = new File("C:\\Users\\Leonid\\Desktop\\restbot\\restbot\\src\\tables.txt");
        Scanner sc = new Scanner(file);
        while(sc.hasNext()){
            Table temp = new Table();
            temp.id = sc.nextInt();
            temp.Capacity = sc.nextInt();
            temp.is_reserved = sc.nextBoolean();
            tables.add(temp);
        }
    }
    public static void Update_Clients_To_Array_List() throws FileNotFoundException {
        File file = new File("C:\\Users\\Leonid\\Desktop\\restbot\\restbot\\src\\clients.txt");
        Scanner sc = new Scanner(file);
        while(sc.hasNext()){
            Client temp = new Client();
            temp.name = sc.next();
            temp.phonenumber = sc.next();
            temp.time = sc.next();
            temp.clients_number = sc.nextInt();
            temp.table.id = sc.nextInt();
            clients.add(temp);
        }
    }
    public static void Update_Clients() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Leonid\\Desktop\\restbot\\restbot\\src\\clients.txt");
        for (int i = 0 ; i < clients.size();i++){
            String temp = clients.get(i).name+ ' ' + clients.get(i).phonenumber+
                    ' ' + clients.get(i).time + ' ' + clients.get(i).clients_number + ' ' + clients.get(i).table.id + '\n';
            outputStream.write(temp.getBytes());
        }
        outputStream.close();
    }
    public static void Update_Tables()throws IOException {
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Leonid\\Desktop\\restbot\\restbot\\src\\tables.txt");
        for (int i = 0 ; i < tables.size();i++){
            String temp = Integer.toString(tables.get(i).id) + ' ' + Integer.toString(tables.get(i).Capacity) +
                    ' ' + Boolean.toString(tables.get(i).is_reserved) + '\n';
            outputStream.write(temp.getBytes());
        }
        outputStream.close();
    }
    public static boolean Check_Avaliability(){
        int avaliability = 0;
        for(int i = 0; i < tables.size(); i++){
            if(tables.get(i).is_reserved == false){
                avaliability++;
            }
        }
        if(avaliability > 0){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean Check_Avaliability_of_Enough_Places(int client_number){
        for(int i = 0; i < tables.size(); i++){
            if(tables.get(i).is_reserved != true && client_number <= tables.get(i).Capacity){
                tables.get(i).is_reserved = true;
                table_id = (i + 1);
                return false;
            }
        }
        return true;
    }
}