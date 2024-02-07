package Subscriber;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name="Subscriber", activationConfig = {@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/topic/test")})
public class Subscriber implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage=(TextMessage) message;
        try{
            String messageText = textMessage.getText();
            int vowelCount = countVowels(messageText);
            System.out.println("Сообщение: " + messageText);
            System.out.println("Количество гласных букв: " + vowelCount);

        }catch (JMSException e)
        {
            e.printStackTrace();
        }
    }
    private int countVowels(String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            char ch = Character.toLowerCase(text.charAt(i));
            if (ch == 'а' || ch == 'е' || ch == 'и' || ch == 'о' || ch == 'у' || ch == 'я'|| ch == 'ю'|| ch == 'э'|| ch == 'ы'|| ch == 'ё') {
                count++;
            }
        }
        return count;
    }
}
