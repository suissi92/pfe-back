package app.com.cms2.model;




import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity


public class Machine {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

	private long id ;
	
	@NotBlank
    @Size(min=3, max = 50)
	private String name ;
    
	
    @NotBlank
    @Size(min=3, max = 50)
	private String description ;
    

   
    
	private boolean status ;
    
	
	@Enumerated(EnumType.STRING)
    @Column(length = 60)
	private MType mtype;
	
	private boolean fese;
	
	
	  @JsonBackReference
	  @ManyToOne(fetch = FetchType.LAZY, optional = false)
	  @JoinColumn(name="line_id", nullable = false)
	  private Line line;
	  
	  @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL)
	  private  Set<ReservationMachine> reservationsMachine = new HashSet<ReservationMachine>();
	  
	
	  public Machine(long id,  String name, String description, boolean status, 
			MType mtype, boolean fese) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.mtype = mtype;
		this.fese = fese;
		
	}

	

	

	
	

	public Machine(  String name, String description,boolean status, MType mtype,
			 boolean fese) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.mtype = mtype;
		this.fese = fese;
	}
	
	public Machine() {}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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



	



	public Line getLine() {
		return line;
	}



	public void setLine(Line line) {
		this.line = line;
	}



	
	
}
