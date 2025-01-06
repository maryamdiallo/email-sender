package org.example;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class EmailSender {

    private static void sendEmail(String recipient, String subject, String messageText) {
        // Sender's email credentials
        String senderEmail = "unitedyearup@gmail.com";
        String password = "lnok onon nldc grvg";

        // SMTP server configuration
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            // Create the email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(messageText);

            // Send the email
            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Email sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to send email: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Create the GUI frame
        JFrame frame = new JFrame("Email Sender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Create input fields and labels
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel recipientLabel = new JLabel("Recipient:");
        JTextField recipientField = new JTextField();
        JLabel subjectLabel = new JLabel("Subject:");
        JTextField subjectField = new JTextField();
        JLabel messageLabel = new JLabel("Message:");
        JTextArea messageArea = new JTextArea();
        JScrollPane messageScrollPane = new JScrollPane(messageArea);

        // Add components to the panel
        panel.add(recipientLabel);
        panel.add(recipientField);
        panel.add(subjectLabel);
        panel.add(subjectField);
        panel.add(messageLabel);
        panel.add(messageScrollPane);

        // Create send button
        JButton sendButton = new JButton("Send Email");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recipient = recipientField.getText();
                String subject = subjectField.getText();
                String messageText = messageArea.getText();

                if (recipient.isEmpty() || subject.isEmpty() || messageText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill out all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    sendEmail(recipient, subject, messageText);
                }
            }
        });

        // Add components to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(sendButton, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }
}
