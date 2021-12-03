package app.com.cms2.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
 

import com.fasterxml.jackson.annotation.JsonFormat;

 


@Entity
@Table(name = "reservation_machine")
public class ReservationMachine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	User user;

	@ManyToOne
	@JoinColumn(name = "machine_id", nullable = false)
	Machine machine;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date start_date;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "finish_date")
	private Date finish_date;

	@CreatedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created")
	private Date created = new Date();

	@CreatedBy
	@Column(name = "createdBy")
	private String createdBy;

	@LastModifiedBy
	@Column(name = "modifiedBy")
	private String modifiedBy;

	@LastModifiedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "lastUpdate")
	private Date lastUpdate = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
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

	public ReservationMachine(User user, Machine machine, @NotBlank Date start_date, @NotBlank Date finish_date) {

		this.user = user;
		this.machine = machine;
		this.start_date = start_date;
		this.finish_date = finish_date;
	}

	public ReservationMachine() {

	}

}
