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

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name = "machine_session")
public class MachineSession {		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@ManyToOne
		@JoinColumn(name = "machine_id", nullable = false)
		private Machine machine;
		
		@ManyToOne
		@JoinColumn(name = "user_id", nullable = false)
		private User user;
		
		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@Column(name = "start_date")
		@Temporal(TemporalType.TIMESTAMP)
		private Date start_date;

		@Temporal(TemporalType.TIMESTAMP)
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@Column(name = "finish_date")
		private Date finish_date;
		
		private boolean status;

		public boolean isStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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

		public MachineSession() {
			super();
		}

		public MachineSession(Long id,User user , Machine machine, Date start_date, Date finish_date , boolean status) {
			this.user = user;
			this.machine = machine;
			this.start_date = start_date;
			this.finish_date = finish_date;
			this.status = status;
		}
		
		
	

}
