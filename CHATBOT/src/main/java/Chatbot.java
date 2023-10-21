import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chatbot extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public Chatbot() {
        setTitle("Chatbot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("MATHBOT");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.GREEN);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        displayGreeting();
    }

    private void displayGreeting() {
        appendToChat("Math-bot: Hello! I'm Math-bot. How can I help you today?");
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = inputField.getText();
            if (!input.isEmpty()) {
                appendToChat("User: " + input);
                String response = generateResponse(input);
                slowPrint("Math-bot: " + response, 70); 
                inputField.setText("");
            }
        }
    }

    private void appendToChat(String message) {
        chatArea.append(message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private String generateResponse(String input) {
        String response;

      
        String lowercaseInput = input.toLowerCase();

        if (lowercaseInput.contains("hello") || lowercaseInput.contains("hi")) {
            response = "Hello! How can I assist you today?";
        } 
        
        else if (lowercaseInput.contains("identity")) {
            
            response = "I'm just a computer program, so I don't have feelings, but I'm here to help!";
        } 
        
        else if (lowercaseInput.contains("goodbye")) {
            response = "Goodbye! Have a great day!";
        } 
        
        else if (lowercaseInput.contains("what is your purpose?")) {
            response = "My purpose is to assist students that has questions related to Simplification of Boolean Algebra";
        } 
        
        else if (lowercaseInput.contains("how can you help me?")) {
            response = "I can help you by answering your math-related questions.";
        } 
        
        else if (lowercaseInput.contains("what is your name?")) {
            response = "I'm Mathbot, a chatbot designed to assist with math questions.";
        } 
        
        else {
            response = "I'm sorry, I don't understand. Please ask a question only related to Simplification of Boolean Algebra";
        }

        return response;
    }

    private void slowPrint(final String message, final int millisPerChar) {
        inputField.setEditable(false);
        inputField.setFocusable(false);

        String botName = "Math-bot:";
        final String response = message.replace(botName, "");

        chatArea.append(botName); 

        Timer timer = new Timer(millisPerChar, null);
        timer.addActionListener(new ActionListener() {
            int counter = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter < response.length()) {
                    chatArea.append(response.substring(counter, counter + 1));
                    counter++;
                } else {
                    ((Timer) e.getSource()).stop();
                    inputField.setEditable(true);
                    inputField.setFocusable(true);
                    inputField.requestFocusInWindow();
                    appendToChat(""); 
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Chatbot chatbot = new Chatbot();
            chatbot.setVisible(true);
        });
    }
}
