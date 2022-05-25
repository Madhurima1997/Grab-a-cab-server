package com.grabcab.cab.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cab {

	public String cid;
	public String type;
	public String destination;
	public String avail;

}
