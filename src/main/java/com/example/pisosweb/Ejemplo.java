package com.example.pisosweb;

import org.springframework.data.annotation.Id;


public class Ejemplo {
	  @Id
	  public String id;

	  public String firstName;
	  public String lastName;

	  public Ejemplo() {}

	  public Ejemplo(String firstName, String lastName) {
	    this.firstName = firstName;
	    this.lastName = lastName;
	  }

	  @Override
	  public String toString() {
	    return String.format(
	        "Customer[id=%s, firstName='%s', lastName='%s']",
	        id, firstName, lastName);
	  }

	
}
