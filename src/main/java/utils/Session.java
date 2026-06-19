package utils;

public class Session {

    private static int userId;
    private static String username;
    private static String role;
    private static String email;
    private static String numPhone;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Session.userId = userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        Session.role = role;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Session.email = email;
    }

    public static String getNumPhone() {
        return numPhone;
    }

    public static void setNumPhone(String numPhone) {
        Session.numPhone = numPhone;
    }
}