package app.com.cms2.message.request;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import app.com.cms2.model.Machine;
import app.com.cms2.model.User;

public class AddReservationMachineForm {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	
	private Machine machine;
	
	private User user;
	
    
	 @Temporal(TemporalType.DATE)
	 @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
	 
	    @Column(name = "start_date")
	    private Date start_date ;
	    
	    @Temporal(TemporalType.DATE)
	    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
	   
	    @Column(name = "finish_date")
	    private Date finish_date ;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
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

	public AddReservationMachineForm(Machine machine, User user, @NotBlank Date start_date,
			@NotBlank Date finish_date) {
		super();
		this.machine = machine;
		this.user = user;
		this.start_date = start_date;
		this.finish_date = finish_date;
	}

	

	

	

	
    
    

}
