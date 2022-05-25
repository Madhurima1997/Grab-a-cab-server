package com.grabcab.cab.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grabcab.cab.domain.BookResponse;
import com.grabcab.cab.domain.Cab;
import com.grabcab.cab.domain.CabUser;
import com.grabcab.cab.domain.Lobby;
import com.grabcab.cab.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/grabcab")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/getAllUsers")
	public List<CabUser> getAllUsers() {
		return userService.getAllUser();
	}

	@GetMapping("/Hi")
	public String message() {
		return "Hello World";
	}

	@PostMapping("/signUp")
	public Map<String, String> signup(@RequestParam("fname") String fname, @RequestParam("lname") String lname,
			@RequestParam("email") String email, @RequestParam("phone") String phone,
			@RequestParam("password") String pass) {
		Map<String, String> response = new HashMap<>();
		try {
			userService.signup(fname, lname, email, phone, pass);
			response.put("status", "Success");
		} catch (Exception e) {
			log.debug(e.toString());
			response.put("status", "Failure");
		}
		return response;
	}

	@GetMapping("/login")
	public Map<String, String> login(@RequestParam("username") String username, @RequestParam("password") String pass) {
		CabUser user = new CabUser();
		Map<String, String> response = new HashMap<>();
		try {
			user = userService.login(username, pass);
			if (user != null) {
				response.put("status", "Success");
			} else
				response.put("status", "Failure");
		} catch (Exception e) {
			log.info(e.getLocalizedMessage());
		}
		return response;
	}

	@PostMapping("/booklobby")
	public Map<String, String> book(@RequestParam("username") String username, @RequestParam("loc") String loc,
			@RequestParam("start") String start, @RequestParam("vehicle") String vehicle) {

		start = start.replace("-", " ");
		start = start.replace("/", "-");

		log.info(start.toString() + "");

		log.info("Insid e controler");
		int new_entry = 0;
		int join_entry = 0;
		int cap = 0;
		String lid = "";
		String prev_uname = "";

		log.info(vehicle.toString() + "");
		if (vehicle.equalsIgnoreCase("auto")) {
			cap = 3;
		} else {
			cap = 4;
		}
		Map<String, String> response = new HashMap<>();

		List<Lobby> lobby = new ArrayList<>();
		lobby = userService.getLobby(vehicle, loc, start);
		if (lobby.size() == 0) {
			// make first entry in lobby table
			new_entry = 1;
		} else {
			log.info(lobby.toString() + "");
			// check capacity
			for (Lobby obj : lobby) {
				if (obj.getCapacity() > 0) {
					join_entry = 1;
					cap = cap - 1;
					lid = obj.getLob_id();
					log.info(obj.getLob_id());
					prev_uname = obj.getUsername();
					break;
				} else {
					new_entry = 1;
					break;
				}
			}
		}
		log.info(new_entry + " " + join_entry);

		response.put("status", "Failure");
		try {
			if (new_entry == 1) {
				log.info("New entry");
				lid = "L" + UUID.randomUUID().toString().substring(0, 13);
				userService.entryLobby(start, username, vehicle, lid, loc, cap - 1, "Y", "Y");
				List<Cab> c = new ArrayList<>();
				log.info(c.toString() + "");
				c = userService.getCabDetails(loc, vehicle);
				log.debug(c.get(0).toString() + "");
				// entwr in book table
				try {
					userService.insertBook(username, lid, c.get(0).getCid(), loc, vehicle, "Y");
				} catch (Exception e) {
					log.info("Error here: " + e.getLocalizedMessage() + "");
				}
				// make avail flag N
				userService.disableAvail(c.get(0).getCid());
				response.put("status", "Success");

			}
			if (join_entry == 1) {
				userService.entryLobby(start, username, vehicle, lid, loc, cap, "Y", "Y");
				userService.disableCX(prev_uname);
				response.put("status", "Success");
				// getbook of same lobby
				List<BookResponse> book = new ArrayList<>();
				book = userService.getBookingJoin(lid);
				userService.insertBook(username, lid, book.get(0).getCid(), loc, vehicle, "Y");
			}
		} catch (Exception e) {
			log.info(e.getLocalizedMessage());
		}

		return response;
	}

	@GetMapping("/getLobby")
	List<Lobby> getLobby(@RequestParam("username") String username) {

		return userService.getLobbyDetails(username);
	}

	@GetMapping("/getBook")
	public List<BookResponse> getCabDetails(@RequestParam("username") String username) {
		BookResponse r = new BookResponse();
		r = userService.getCabDetail(username);
		int capacity = userService.getPassengers(r.getLid());
		r.setCapacity(capacity);
		List<BookResponse> response = new ArrayList<>();
		response.add(r);
		return response;
	}
}
