package fr.cs.authentificationproject.repositories;

/**
 * @author Slimane
 * @Project ApiEmail
 */
public interface EmailSenderRepository {


    void sendEmail(String to, String email);
}
