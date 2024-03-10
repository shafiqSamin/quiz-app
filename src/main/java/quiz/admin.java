package quiz;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class admin {
    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username : ");
        String userName = scanner.next();
        System.out.println("Enter password : ");
        String password = scanner.next();
        adminLogin(userName,password);
    }
    public static void adminLogin (String userName, String password) throws IOException, ParseException {
        String fileName = "./src/main/resources/admin.json";
        JSONParser jsonParser = new JSONParser();
        JSONArray empArray = (JSONArray) jsonParser.parse(new FileReader(fileName));
        for (int i = 0; i < empArray.size(); i++) {
            JSONObject empObj = (JSONObject) empArray.get(i);
            if (empObj.get("username").equals(userName) && empObj.get("password").equals(password)) {
                System.out.println("Welcome admin! Please create new questions in the question bank.\n");;
                questionCreate();
            }
            else {
                System.out.println("Wrong! Please enter the username or password again");
            }
        }
    }
    public static void questionCreate () throws IOException, ParseException {
        String fileLocation = "./src/main/resources/quiz.json";

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = new JSONObject();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter The question");


        System.out.println("Enter the Option 1:");
        System.out.println("Enter the Option 2:");
        System.out.println("Enter the Option 3:");
        System.out.println("Enter the Option 4:");
        System.out.println("Enter the answer:");


        String question = scanner.nextLine();
        String option1 =scanner.nextLine();
        String option2 =scanner.nextLine();
        String option3 =scanner.nextLine();
        String option4 =scanner.nextLine();
        int answerkey =scanner.nextInt();

        System.out.println();


        jsonObject.put("question", question);
        jsonObject.put("option 1", option1);
        jsonObject.put("option 2", option2);
        jsonObject.put("option 3", option3);
        jsonObject.put("option 4", option4);
        jsonObject.put("answerkey", answerkey);

        JSONArray empArray = (JSONArray) jsonParser.parse(new FileReader(fileLocation));
        empArray.add(jsonObject);
        FileWriter fw = new FileWriter(fileLocation);
        fw.write(empArray.toJSONString());
        fw.flush();
        fw.close();


    }
}
