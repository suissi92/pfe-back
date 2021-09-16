package app.com.cms2.message.request;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import app.com.cms2.model.Machine;
import app.com.cms2.model.Role;

public class AffectMachinesToLineForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Machine machine;

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

}
