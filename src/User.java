import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class User {
    static User user;
    private boolean clubMember;
    private boolean admin;
    private final String userName;
    private final String hashedPassword;

    public User(String userName, String hashedPassword, boolean clubMember, boolean Admin){
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.clubMember = clubMember;
        this.admin = Admin;
    }
    public User(String userName, String Password){
        this.userName = userName;
        this.hashedPassword = hashPassword(Password);
        this.clubMember = false;
        this.admin = false;
    }

    public static boolean match(String userName, String password) {
        String hash = hashPassword(password);
        for (Object ob: Main.data.get(0)){
            User user = (User) ob;
            if (user.getUserName().equals(userName) && user.getHashedPassword().equals(hash)){
                User.user = user;
                return true;
            }
        }
        return false;
    }

    public boolean isClubMember() {
        return clubMember;
    }
    public boolean isAdmin() {
        return admin;
    }
    public String getUserName() {
        return userName;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setClubMember(boolean clubMember) {
        this.clubMember = clubMember;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "user\t" +
                userName + "\t" +
                hashedPassword + "\t" +
                clubMember + "\t" +
                admin + "\t" +
                "\n";
    }
    private static String hashPassword(String password){
        byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
        byte[] md5Digest;
        try {
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword);
        }catch (NoSuchAlgorithmException e){
            return null;
        }
        return Base64.getEncoder().encodeToString(md5Digest);
    }

} // end of class