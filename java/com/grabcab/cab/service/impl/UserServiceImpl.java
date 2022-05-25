package com.grabcab.cab.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grabcab.cab.domain.BookResponse;
import com.grabcab.cab.domain.Cab;
import com.grabcab.cab.domain.CabUser;
import com.grabcab.cab.domain.Lobby;
import com.grabcab.cab.repository.CabUserRepository;
import com.grabcab.cab.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	CabUserRepository userDao;

	@Override
	public List<CabUser> getAllUser() {
		return userDao.listAllUser();
	}

	@Override
	public void signup(String fname, String lname, String email, String phone, String pass) {
		userDao.signup(fname, lname, email, phone, pass);

	}

	@Override
	public CabUser login(String username, String password) {
		return userDao.login(username, password);
	}

	@Override
	public void entryPool(String username, String loc, String start, String vehicle) {
		int id = userDao.getUserId(username);
		// make entry in pool table
		userDao.poolEntry(start, loc, id, vehicle);
	}

	@Override
	public List<Lobby> getLobby(String vehicle, String loc, String start) {
		return userDao.getLobby(vehicle, loc, start);
	}

	@Override
	public void entryLobby(String start, String username, String vehicle, String lid, String loc, int cap,
			String active, String cx) {
		userDao.insertLobby(start, username, vehicle, lid, loc, cap, active, cx);

	}

	@Override
	public void disableCX(String prev_uname) {
		userDao.disableCX(prev_uname);

	}

	@Override
	public List<Lobby> getLobbyDetails(String username) {
		return userDao.getLobbyDetails(username);
	}

	@Override
	public List<Cab> getCabDetails(String loc, String vehicle) {
		return userDao.getCabDetails(loc, vehicle);
	}

	@Override
	public void insertBook(String username, String lid, String cid, String loc, String vehicle, String avail) {
		userDao.insertBook(username, lid, cid, loc, vehicle, avail);

	}

	@Override
	public void disableAvail(String cid) {
		userDao.disableAvail(cid);

	}

	@Override
	public List<BookResponse> getBookingJoin(String lid) {
		return userDao.getBookingJoin(lid);
	}

	@Override
	public BookResponse getCabDetail(String username) {
		return userDao.getCabDetail(username);
	}

	@Override
	public int getPassengers(String lid) {
		return userDao.getPassengers(lid);
	}

}
