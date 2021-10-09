package app.com.cms2.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import ch.qos.logback.core.rolling.helper.TimeBasedArchiveRemover;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;



@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table (name = "reservation_line")
public class ReservationLine  {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable = false)
	User user;
	
	@ManyToOne
	@JoinColumn(name="line_id", nullable = false)
	Line line;
	

	@Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_date")
    private Date start_date ;
    
	@Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "finish_date")
    private Date finish_date ;
    
   
    @LastModifiedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "lastUpdate")
    private Date lastUpdate = new Date();
    
    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created")
    private Date created = new Date();
    
    @CreatedBy
    @Column(name = "createdBy")
    private String createdBy ;
    
    @LastModifiedBy
    @Column(name = "modifiedBy")
    private String modifiedBy ;

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

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
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

	public ReservationLine(User user, Line line, Date date, Date date2) {
		
		this.user = user;
		this.line = line;
		this.start_date = date;
		this.finish_date = date2;
	}

	public ReservationLine() {
		
	}
    

    
}
