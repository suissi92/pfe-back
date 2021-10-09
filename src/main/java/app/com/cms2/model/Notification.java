package app.com.cms2.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long notificationId;

	private Date dateCreation;

	private boolean consulted;

	private String message;

	@ManyToOne
	@JsonIgnore
	private User user;
	
	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public boolean isConsulted() {
		return consulted;
	}

	public void setConsulted(boolean consulted) {
		this.consulted = consulted;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Notification(Long notificationId, Date dateCreation, boolean consulted, String message) {
		super();
		this.notificationId = notificationId;
		this.dateCreation = dateCreation;
		this.consulted = consulted;
		this.message = message;
	}

	public Notification() {
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
