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

public class student {
    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String userName = scanner.next();
        System.out.println("Enter password: ");
        String password = scanner.next();

        if (studentLogin(userName, password)) {
            System.out.println("Welcome to the quiz! We will throw you 10 questions. Each MCQ mark is 1, and there is no negative marking. Are you ready? Press 's' to start.");

            String verify = scanner.next();
            if ("s".equalsIgnoreCase(verify)) {
                takeQuiz();
            } else {
                System.out.println("Invalid input. Quiz aborted.");
            }
        } else {
            System.out.println("Login failed. Please check your username and password.");
        }
    }

    public static boolean studentLogin(String userName, String password) throws IOException, ParseException {
        String fileName = "./src/main/resources/user.json";
        JSONParser jsonParser = new JSONParser();
        JSONArray userArray = (JSONArray) jsonParser.parse(new FileReader(fileName));

        for (Object obj : userArray) {
            JSONObject user = (JSONObject) obj;
            if (user.get("username").equals(userName) && user.get("password").equals(password) && "student".equals(user.get("role"))) {
                return true;
            }
        }
        return false;
    }

    public static void takeQuiz() throws IOException, ParseException {
        String fileLocation = "./src/main/resources/quiz.json";
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(fileLocation));

        int score = 0;
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 10; i++) {
            JSONObject question = (JSONObject) jsonArray.get((int) (Math.random() * jsonArray.size()));

            System.out.println("\n[Question " + (i + 1) + "] " + question.get("question"));
            for (int j = 1; j <= 4; j++) {
                System.out.println(j + ". " + question.get("option " + j));
            }

            System.out.print("Your answer: ");
            int userAnswer;
            try {
                userAnswer = scanner.nextInt();
                if (userAnswer < 1 || userAnswer > 4) {
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                    i--;
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                i--;
                continue;
            }

            if (userAnswer == (long) question.get("answerkey")) {
                score++;
            }
        }

        System.out.println("\nQuiz completed! You scored " + score + " out of 10.");

        if (score >= 8) {
            System.out.println("Excellent! You have got " + score + " out of 10.");
        } else if (score >= 5) {
            System.out.println("Good. You have got " + score + " out of 10.");
        } else if (score >= 2) {
            System.out.println("Very poor! You have got " + score + " out of 10.");
        } else {
            System.out.println("Very sorry, you failed. You have got " + score + " out of 10.");
        }

        System.out.println("Would you like to start again? Press 's' for start or 'q' for quit.");
        String restart = scanner.next();
        if ("s".equalsIgnoreCase(restart)) {
            takeQuiz();
        } else {
            System.out.println("Quiz session ended. Goodbye!");
        }
    }

}
