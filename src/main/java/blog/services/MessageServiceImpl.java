package blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service()
public class MessageServiceImpl implements MessageService {

    public static final String NOTIFY_MSG_SESSION_KEY = "siteMessages";

    @Autowired
    private HttpSession httpSession;

    @Override
    public void addInfoMessage(String msg) {
        addMessage(MessageType.INFO, msg);
    }

    @Override
    public void addErrorMessage(String msg) {
        addMessage(MessageType.ERROR, msg);
    }

    private void addMessage(MessageType type, String msg) {
        List<Message> notifyMessages = (List<Message>)
                httpSession.getAttribute(NOTIFY_MSG_SESSION_KEY);
        if (notifyMessages == null) {
            notifyMessages = new ArrayList<Message>();
        }
        notifyMessages.add(new Message(type, msg));
        httpSession.setAttribute(NOTIFY_MSG_SESSION_KEY, notifyMessages);
    }

    public enum MessageType {
        INFO,
        ERROR
    }

    public class Message {
        MessageType type;
        String text;

        public Message(MessageType type, String text) {
            this.type = type;
            this.text = text;
        }

        public MessageType getType() {
            return type;
        }

        public String getText() {
            return text;
        }
    }
}