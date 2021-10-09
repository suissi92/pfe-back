package app.com.cms2.message.request;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import app.com.cms2.model.Line;
import app.com.cms2.model.Machine;
import app.com.cms2.model.User;

public class AddReservationLineForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Line line;

	private User user;

	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	@Column(name = "start_date")
	private Date start_date;

	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	@Column(name = "finish_date")
	private Date finish_date;

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getFinish_date() {
		return finish_date;
	}

	public void setFinish_date(Date finish_date) {
		this.finish_date = finish_date;
	}

	public AddReservationLineForm(Line line, User user, Date start_date, Date finish_date) {
		super();
		this.line = line;
		this.user = user;
		this.start_date = start_date;
		this.finish_date = finish_date;
	}

	
}
