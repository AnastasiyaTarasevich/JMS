package Publisher;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Random;
import java.util.Scanner;

@Stateless
@LocalBean
public class Publisher {
   @Resource(name="java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;
   @Resource(name="java:/topic/test")
    private Destination destination;
    private int messageCount = 0;
//   @Schedule(hour="*", minute = "*", second = "*/5", persistent = false)
   public void produceMessage()
   {
       if (messageCount >= 10) {

           return;
       }
       try{
           Connection connection=connectionFactory.createConnection();
           Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
           MessageProducer messageProducer=session.createProducer(destination);
          // messageProducer.send(session.createTextMessage("Hello MDB"));
           String randomMessage = generateRandomMessage();

           TextMessage message = session.createTextMessage(randomMessage);
           messageProducer.send(message);
           connection.close();
           session.close();
           messageCount++;

           if (messageCount >= 10) {
               // Все сообщения отправлены, остановка программы
               System.exit(0);}
       }catch (JMSException e)
       {
           e.printStackTrace();
       }
   }
    private String generateRandomMessage() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        int messageLength = 10;

        for (int i = 0; i < messageLength; i++) {
            char randomChar = (char) (random.nextInt(32) + 'а');
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
