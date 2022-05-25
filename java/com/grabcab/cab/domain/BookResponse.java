package com.grabcab.cab.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

	public String username;
	public String lid;
	public String cid;
	public String loc;
	public String trip;
	public String type;
	public int capacity;

}
