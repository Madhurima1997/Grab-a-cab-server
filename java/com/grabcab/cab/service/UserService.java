package com.grabcab.cab.service;

import java.util.List;

import com.grabcab.cab.domain.BookResponse;
import com.grabcab.cab.domain.Cab;
import com.grabcab.cab.domain.CabUser;
import com.grabcab.cab.domain.Lobby;

public interface UserService {

	List<CabUser> getAllUser();

	public void signup(String fname, String lname, String email, String phone, String pass);

	public CabUser login(String username, String password);

	public void entryPool(String username, String loc, String start, String vehicle);

	public List<Lobby> getLobby(String vehicle, String loc, String start);

	public void entryLobby(String start, String username, String vehicle, String lid, String loc, int cap,
			String active, String cx);

	public void disableCX(String prev_uname);

	public List<Lobby> getLobbyDetails(String username);

	public List<Cab> getCabDetails(String loc, String vehicle);

	public void insertBook(String username, String lid, String cid, String loc, String vehicle, String avail);

	void disableAvail(String cid);

	List<BookResponse> getBookingJoin(String lid);

	BookResponse getCabDetail(String username);

	int getPassengers(String lid);

}
