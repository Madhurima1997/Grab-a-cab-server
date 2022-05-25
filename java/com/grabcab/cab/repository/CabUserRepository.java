package com.grabcab.cab.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.grabcab.cab.domain.BookResponse;
import com.grabcab.cab.domain.Cab;
import com.grabcab.cab.domain.CabUser;
import com.grabcab.cab.domain.Lobby;

@Mapper
@Repository
public interface CabUserRepository {
	@Select("select * from cab_user")
	public List<CabUser> listAllUser();

	@Insert("insert into cab_user(fname,lname,email,phone,pass) values (#{fname}, #{lname}, #{email}, #{phone}, #{pass})")
	public void signup(String fname, String lname, String email, String phone, String pass);

	@Select("select * from cab_user where email=#{username} and pass=#{password}")
	public CabUser login(String username, String password);

	@Select("select id from cab_user where email=#{username}")
	public int getUserId(String username);

	@Insert("Insert into pool_tab(start_time,loc,id,vehicle) values (TO_DATE(#{start},'YYYY-MM-DD HH24:MI'), #{loc}, #{id}, #{vehicle})")
	public void poolEntry(String start, String loc, int id, String vehicle);

	@Select("select * from lobby where active='Y' and cx='Y' and vehicle=#{vehicle} and loc=#{loc} and start_time=TO_DATE(#{start},'YYYY-MM-DD HH24:MI')")
	public List<Lobby> getLobby(String vehicle, String loc, String start);

	@Insert("insert into lobby (start_time, vehicle, lob_id, loc, capacity, active, cx, username ) values (TO_DATE(#{start},'YYYY-MM-DD HH24:MI:SS'), #{vehicle}, #{lid}, #{loc}, #{cap}, #{active}, #{cx}, #{username})")
	public void insertLobby(String start, String username, String vehicle, String lid, String loc, int cap,
			String active, String cx);

	@Update("update lobby set cx='N' where username = #{prev_uname}")
	public void disableCX(String prev_uname);

	@Select("select * from lobby where active='Y' and username=#{username} and start_time >= TO_DATE(sysdate,'YYYY-MM-DD')")
	public List<Lobby> getLobbyDetails(String username);

	@Select("select * from vehicle where type=#{vehicle} and destination=#{loc} and avail='Y'")
	public List<Cab> getCabDetails(String loc, String vehicle);

	@Insert("insert into book (username,lid,cid,loc,type,trip) values(#{username}, #{lid}, #{cid},#{loc},#{vehicle},#{avail})")
	public void insertBook(String username, String lid, String cid, String loc, String vehicle, String avail);

	@Update("update vehicle set avail='N' where cid = #{cid}")
	public void disableAvail(String cid);

	@Select("select * from book where lid=#{lid}")
	public List<BookResponse> getBookingJoin(String lid);

	@Select("select * from book where username=#{username} and trip='Y'")
	public BookResponse getCabDetail(String username);

	@Select("select count(*) from lobby where lob_id=#{lid}")
	public int getPassengers(String lid);
}
