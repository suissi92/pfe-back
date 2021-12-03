package app.com.cms2.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "line")
public class Line {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id ;
	
	@NotBlank
    @Size(min=3, max = 50)
	private String name ;
    
	
    @NotBlank
    @Size(min=3, max = 50)

	private String description ;
    
    @JsonManagedReference
	@OneToMany(mappedBy = "line", cascade = CascadeType.ALL,  fetch = FetchType.LAZY )
	private Set<Machine> machines = new HashSet<Machine>();
    
    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
	private  Set<ReservationLine> reservationsLine = new HashSet<ReservationLine>();

    public Line addMachine(Machine m) {
    	this.machines.add(m);
    	m.setLine(this);
    	return this;
    }

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


	public Set<Machine> getMachines() {
		return machines;
	}



	public void setMachines(Set<Machine> machines) {
		this.machines = machines;
	}



	public Line(String name, String description) {

		this.name = name;
		this.description = description;
	}
	
	public Line() {
	}



	public Line(String name,String description, Set<Machine> machines) {
		
		this.name = name;
		this.description = description;
		this.machines = machines;
	}



	
	
	
	
	
	
	
	

}
