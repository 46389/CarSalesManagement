/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author Chew Jin Ni
 */

public class Validation{
    public static boolean validateIC(String ic){
        String identityRegex = "^[0-9]{12}$";
        Pattern identityPattern = Pattern.compile(identityRegex, Pattern.CASE_INSENSITIVE);
        Matcher identityMatch = identityPattern.matcher(ic);
        return identityMatch.find();
    }
    
    public static boolean validateContact(String contact){
        String contactRegex = "^[0-9]{1,3}+\\-[0-9]{7,8}";
        Pattern contactPattern = Pattern.compile(contactRegex, Pattern.CASE_INSENSITIVE);
        Matcher matchContact = contactPattern.matcher(contact);
        return matchContact.find();
    }
    
    public static boolean validateEmail(String email){
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }
    
    public static boolean validateName(String name){
        String nameRegex = "[a-zA-Z\\\\s]+";
        Pattern namePattern = Pattern.compile(nameRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherName = namePattern.matcher(name);
        return matcherName.find();
    }
    
    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    
    public static boolean checkPassword(String password, String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }
    
    public static boolean validatePassword(String password) {
        // Define special characters as a HashSet for faster lookup
        Set<Character> specialCharSet = new HashSet<>();
        for (char c : "~!@#$%^&*()-_+=<>?. ,/[]{}|`\\".toCharArray()) {
            specialCharSet.add(c);
        }

        // Check length
        if (password.length() < 8 || password.length() > 18) {
            return false;
        }

        // Counters for different character types
        int upperNum = 0, lowerNum = 0, digitNum = 0, scNum = 0;

        // Loop through characters
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                upperNum++;
            } else if (Character.isLowerCase(ch)) {
                lowerNum++;
            } else if (Character.isDigit(ch)) {
                digitNum++;
            } else if (specialCharSet.contains(ch)) {
                scNum++;
            }
        }

        // Validate all conditions
        return upperNum > 0 && lowerNum > 0 && digitNum > 0 && scNum > 0;
    }
    
    public static java.nio.file.Path setFilepath(String type, String upload_path, String originalFileName) throws IOException {
        if (!Files.exists(Paths.get(upload_path))) {
            Files.createDirectories(Paths.get(upload_path));
        }
        // Extract the file extension from the original file name
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFileName.substring(dotIndex);
        }
        // Generate a unique file name with the extension
        String unique_filename = type + "_" + UUID.randomUUID() + extension;
        return Paths.get(upload_path, unique_filename);
}
    
    public static void saveFile(InputStream file_content, Path file_path) throws IOException{
        Files.copy(file_content, file_path, StandardCopyOption.REPLACE_EXISTING);
    }
    
    public static String formatLocalDateTime(LocalDateTime localDateTime, String outputPattern) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputPattern);
        return localDateTime.format(outputFormatter);
    }
    
    public static String getMonthName(int monthNumber) {
        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        return months[monthNumber - 1];
    }
    
    public static int getSafeLongValue(Object value) {
        if (value == null) {
            return 0; // Return 0 if the value is null
        }
        if (value instanceof Number) {
            return ((Number) value).intValue(); // Convert any numeric type to integer
        }
        throw new IllegalArgumentException("Unexpected type for value: " + value.getClass());
    }
}