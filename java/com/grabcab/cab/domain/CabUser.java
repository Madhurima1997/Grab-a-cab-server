package com.grabcab.cab.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CabUser {

	public long id;
	public String fName;
	public String lName;
	public String email;
	public String phone;
	public String pass;
}
