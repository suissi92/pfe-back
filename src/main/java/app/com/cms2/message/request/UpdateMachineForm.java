package app.com.cms2.message.request;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import app.com.cms2.model.MType;

public class UpdateMachineForm {
	@NotBlank
    @Size(min=3, max = 50)
	private String name ;
    
	
    @NotBlank
    @Size(min=3, max = 50)
	private String description ;
    
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_debut;
    
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_fin;
 
	private boolean status ;
    
	@Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
	private MType mtype;
	
	
	private boolean fese;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public MType getMtype() {
		return mtype;
	}

	public void setMtype(MType mtype) {
		this.mtype = mtype;
	}

	public boolean isFese() {
		return fese;
	}

	public void setFese(boolean fese) {
		this.fese = fese;
	}
	public Date getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}

	public Date getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}
	
	

}
