package app.com.cms2.message.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddLineForm {
	
	@NotBlank
    @Size(min=3, max = 50)
	private String name ;
    
	
    @NotBlank
    @Size(min=3, max = 50)
	private String description ;


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
 
    
    

}
