package com.grabcab.cab.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lobby {
	public int rec_id;
	public String start_time;
	public String username;
	public String vehicle;
	public String lob_id;
	public String loc;
	public int capacity;
	public String active;
	public String cx;
}
