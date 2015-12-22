package br.com.vote.no.restaurante.aspect;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinicius on 21/12/15.
 */
@XmlRootElement(name = "Message")
public class Message {

    @XmlElement(required = true)
    private MessageType type;
    @XmlElement
    private String description;
    @XmlElement
    private List<String> notifications = new ArrayList<>();

    public Message() {
    }

    public Message(MessageType type, String description) {
        super();
        this.type = type;
        this.description = description;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public Message addNotification(String notification) {
        getNotifications().add(notification);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object objectToCompare) {
        return EqualsBuilder.reflectionEquals(this, objectToCompare);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}