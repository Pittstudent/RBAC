package com.mkyong.xml.dom;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import org.w3c.dom.*;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class App {
    private static boolean test = false;

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);
        int check;
        String userInput = "", usernameInput, accessInput, rwInput;
        System.out.println("Hello CVS Employee. Welcome to CVS Access Control Checker."); // Welcome message

        do {

            do {
                test = false;
                System.out.println(
                        "Enter the number that coresponds to the query you'd like to run. Enter '4' if you'd like to leave. ");
                System.out.println(
                        "    1: Can user access payroll,cashier drawer, breakroom, punch in clock, or training course with read or write permission");
                System.out.println(
                        "    2: Is user currently fulfilling a role of a/an owner, manager, lead, or associate");
                System.out.println("    3: Run Test");
                System.out.println("    4: Terminate");
                System.out.println();
                userInput = input.nextLine();
                check = Integer.parseInt(userInput);

                if (check == 4) { // exit
                    input.close();
                    System.out.println("Have a good day!");
                    return;
                }
                if (check < 0 || check > 4) // error check
                    System.out.println("Invalid number. Please enter a valid number");
                else
                    break;
                System.out.println();
            } while (true);
            switch (check) {
                case 1:
                    do {
                        System.out.println(
                                "Enter the user name you'd like to check. Select from the following users below: ");
                        System.out.println("Rodrick");
                        System.out.println("Tim");
                        System.out.println("Bill");
                        System.out.println("Sherif");
                        System.out.println("John");
                        System.out.println("Jarret");
                        System.out.println("Wonsun");
                        System.out.println("Luis");
                        System.out.println();
                        usernameInput = input.nextLine();
                        if (usernameInput.equals("Rodrick") || usernameInput.equals("Tim") // error check
                                || usernameInput.equals("Bill") || usernameInput.equals("Sherif")
                                || usernameInput.equals("John") || usernameInput.equals("Jarret")
                                || usernameInput.equals("Wonsun") || usernameInput.equals("Luis"))
                            break;
                        else
                            System.out.println("Invalid name, try again");
                        System.out.println();
                    } while (true);
                    do {
                        System.out.println();
                        System.out.println("Enter the access you'd like to check: ");
                        System.out.println("payroll");
                        System.out.println("cashierdrawer");
                        System.out.println("punchinclock");
                        System.out.println("breakroom");
                        System.out.println("trainingcourse");
                        System.out.println();

                        accessInput = input.nextLine();
                        if (accessInput.equals("payroll") || accessInput.equals("cashierdrawer") // error check
                                || accessInput.equals("punchinclock") || accessInput.equals("breakroom")
                                || accessInput.equals("trainingcourse")) {
                            break;
                        } else
                            System.out.println("Invalid acceess name, try again");
                        System.out.println();
                    } while (true);
                    do {
                        System.out.println("Select whether you'd like to check if they're capable of read or write ");
                        System.out.println("read");
                        System.out.println("write");
                        System.out.println();

                        rwInput = input.nextLine();
                        if (rwInput.equals("read") || rwInput.equals("write"))
                            break;
                        else
                            System.out.println("Invalid number, try again");
                        System.out.println();
                    } while (true);
                    System.out.println();
                    accessChecker(usernameInput, accessInput, rwInput);
                    break;
                case 2:
                    do {
                        System.out.println(
                                "Enter the user name you'd like to check. Select from the following users below: ");
                        System.out.println("Rodrick");
                        System.out.println("Tim");
                        System.out.println("Bill");
                        System.out.println("Sherif");
                        System.out.println("John");
                        System.out.println("Jarret");
                        System.out.println("Wonsun");
                        System.out.println("Luis");
                        System.out.println();
                        usernameInput = input.nextLine();
                        if (usernameInput.equals("Rodrick") || usernameInput.equals("Tim") // error check
                                || usernameInput.equals("Bill") || usernameInput.equals("Sherif")
                                || usernameInput.equals("John") || usernameInput.equals("Jarret")
                                || usernameInput.equals("Wonsun") || usernameInput.equals("Luis"))
                            break;
                        else
                            System.out.println("Invalid name, try again");
                        System.out.println();
                    } while (true);
                    do {
                        System.out.println();
                        System.out.println("Enter the role you'd like to check: ");
                        System.out.println("owner");
                        System.out.println("manager");
                        System.out.println("lead");
                        System.out.println("associate");
                        System.out.println();

                        accessInput = input.nextLine();
                        if (accessInput.equals("owner") || accessInput.equals("manager") // error check
                                || accessInput.equals("lead") || accessInput.equals("associate")) {
                            break;
                        } else
                            System.out.println("Invalid role, try again");
                        System.out.println();
                    } while (true);
                    System.out.println();
                    checkRole(usernameInput, accessInput);
                case 3:
                    test = true;
                    System.out.println();
                    accessTester();
                    break;
                case 4:
                    input.close();
                    return;
            }
        } while (true);
    }

    public static Boolean checkAccess(String rwInput, String nom, String Type, String accessInput) // return false if
                                                                                                   // not found else
                                                                                                   // true
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        String domain = "";
        String function = "";
        String accessLevel = "";
        String name = "";
        String type = "";

        try {
            // parse XML file
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();

            // read from a project's resources folder
            Document doc = db.parse(new File("/Users/rodrick/P2/src/policy.xml"));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("attribute");
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node nodes = nodeMap.item(i);
                        switch (i) {
                            case 0:
                                accessLevel = nodes.getNodeValue();
                            case 1:
                                domain = nodes.getNodeValue();
                            case 2:
                                function = nodes.getNodeValue();
                            case 3:
                                name = nodes.getNodeValue();
                            case 4:
                                type = nodes.getNodeValue();
                        }
                    }
                    // System.out.print(" " + "Name " + name + " " + "AccessInput " + domain + " " +
                    // "AccessLevel "
                    // + rwInput + " " + "Type " + type + " ");
                    if (name.equals(nom) && domain.equals(accessInput) && accessLevel.equals(rwInput)) {
                        if (nodeMap.getLength() == 5) {
                            if (Type.equals(type))
                                return true;
                        } else
                            return true;
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void accessChecker(String usernameInput, String accessInput, String rwInput)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        String domain = "";
        String name = "";
        String constant = "";
        String type = "";
        boolean result;

        try {
            // parse XML file
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();

            // read in xml
            Document doc = db.parse(new File("/Users/rodrick/P2/src/employee.xml"));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("attribute");
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node nodes = nodeMap.item(i);
                        switch (i) {
                            case 0:
                                constant = nodes.getNodeValue();
                            case 1:
                                domain = nodes.getNodeValue();
                            case 2:
                                name = nodes.getNodeValue();
                            case 3:
                                type = nodes.getNodeValue();
                        }
                    }
                    if (constant.equals(usernameInput)) {
                        break;
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        result = checkAccess(rwInput, name, type, accessInput);
        if (result) {
            if (!test) {
                System.out.println(" " + constant + " has " + rwInput + " " + "access to " + accessInput + ". ");
                System.out.println();
            }
        } else {
            if (!test) {
                System.out.println(" " + constant + " does not have " + rwInput + " access to " + accessInput + ". ");
                System.out.println();
            }
        }
    }

    public static void checkRole(String user, String role)
            throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        String name = "";
        String constant = "";

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();

            // read in xml
            Document doc = db.parse(new File("/Users/rodrick/P2/src/employee.xml"));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("attribute");
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node nodes = nodeMap.item(i);
                        if (i == 0)
                            constant = nodes.getNodeValue();
                        if (i == 2)
                            name = nodes.getNodeValue();
                        // switch (i) {
                        // case 0:
                        // constant = nodes.getNodeValue();
                        // case 1:
                        // domain = nodes.getNodeValue();
                        // case 2:
                        // name = nodes.getNodeValue();
                        // case 3:
                        // type = nodes.getNodeValue();
                        // }
                    }
                    if (constant.equals(user)) {
                        break;
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        if (name.equals(role)) {
            if (!test)
                System.out.println(" " + constant + " is indeed occupying the role of " + role + ". ");
        } else {
            if (!test)
                System.out.println(" " + constant + " is not occupying the role of " + role + ". ");
        }
        System.out.println();
    }

    public static void accessTester() throws ParserConfigurationException, SAXException, IOException { // tests for C6
        long[] testUn = new long[20], testDeux = new long[20], testTrois = new long[20],
                testQuatre = new long[20], testCinq = new long[20], testSix = new long[20], testSept = new long[20],
                testHuit = new long[20], testNeuf = new long[20], testDix = new long[20];
        long avg1 = 0, avg2 = 0, avg3 = 0, avg4 = 0, avg5 = 0, avg6 = 0, avg7 = 0, avg8 = 0, avg9 = 0,
                avg10 = 0;
        long startTime, endTime;

        // Tests I'm conduction
        // "Is Rodrick an owner";
        // "Is Sherif a lead";
        // "Can Tim access payroll with write permission";
        // "Can Bill access training course with read permission";
        // "Is Wonsun an associate";
        // "Can Wonsun 3access cashier drawer with write permission";
        // "Is Jarret an associate";
        // "Is Bill a manager";
        // "Can John access breakroom with write permission";
        // "Can Jarret access punch in clock with read permission";
        // accessChecker(usernameInput, accessInput, rwInput, check)
        for (int i = 0; i < 10; i++) {
            startTime = System.nanoTime();
            checkRole("Rodrick", "owner");
            endTime = System.nanoTime();
            testUn[i] = (endTime - startTime);

            startTime = System.nanoTime();
            checkRole("Sherif", "lead");
            endTime = System.nanoTime();
            testDeux[i] = (endTime - startTime);

            startTime = System.nanoTime();
            accessChecker("Tim", "payroll", "write");
            endTime = System.nanoTime();
            testTrois[i] = (endTime - startTime);

            startTime = System.nanoTime();
            accessChecker("Bill", "trainingcourse", "read");
            endTime = System.nanoTime();
            testQuatre[i] = (endTime - startTime);

            startTime = System.nanoTime();
            checkRole("Wonsun", "associate");
            endTime = System.nanoTime();
            testCinq[i] = (endTime - startTime);

            startTime = System.nanoTime();
            accessChecker("Wonsun", "cashierdrawer", "write");
            endTime = System.nanoTime();
            testSix[i] = (endTime - startTime);

            startTime = System.nanoTime();
            checkRole("Jarret", "associate");
            endTime = System.nanoTime();
            testSept[i] = (endTime - startTime);

            startTime = System.nanoTime();
            checkRole("Bill", "manager");
            endTime = System.nanoTime();
            testHuit[i] = (endTime - startTime);

            startTime = System.nanoTime();
            accessChecker("John", "breakroom", "write");
            endTime = System.nanoTime();
            testNeuf[i] = (endTime - startTime);

            startTime = System.nanoTime();
            accessChecker("Jarret", "punchinclock", "read");
            endTime = System.nanoTime();
            testDix[i] = (endTime - startTime);

        }

        for (int i = 0; i < 10; i++) {
            avg1 += testUn[i];
            avg2 += testDeux[i];
            avg3 += testTrois[i];
            avg4 += testQuatre[i];
            avg5 += testCinq[i];
            avg6 += testSix[i];
            avg7 += testSept[i];
            avg8 += testHuit[i];
            avg9 += testNeuf[i];
            avg10 += testDix[i];

        }

        avg1 /= 10.0;
        avg2 /= 10.0;
        avg3 /= 10.0;
        avg4 /= 10.0;
        avg5 /= 10.0;
        avg6 /= 10.0;
        avg7 /= 10.0;
        avg8 /= 10.0;
        avg9 /= 10.0;
        avg10 /= 10.0;

        System.out.println("Average Time of Test One: " + avg1 / 1000000.0);
        System.out.println("Average Time of Test Two: " + avg2 / 1000000.0);
        System.out.println("Average Time of Test Three: " + avg3 / 1000000.0);
        System.out.println("Average Time of Test Four: " + avg4 / 1000000.0);
        System.out.println("Average Time of Test Five: " + avg5 / 1000000.0);
        System.out.println("Average Time of Test Six: " + avg6 / 1000000.0);
        System.out.println("Average Time of Test Seven: " + avg7 / 1000000.0);
        System.out.println("Average Time of Test Eight: " + avg8 / 1000000.0);
        System.out.println("Average Time of Test Nine: " + avg9 / 1000000.0);
        System.out.println("Average Time of Test Ten: " + avg10 / 1000000.0);
        System.out.println();
    }

}
