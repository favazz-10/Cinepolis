package com.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.entity.Seats;

@Repository
public interface SeatsRepo extends JpaRepository<Seats, Long> {

	@Query(value = "select * from seats where customer_c_id=?", nativeQuery = true)
	public List<Seats> getAllSeat(long id);

	@Query(value = "select * from seats inner join seats_seat_no" + " on seats.s_id = seats_seat_no.seats_s_id"
			+ " inner join timings" + " on seats.timing_t_id = timings.t_id"
			+ " where show_date = ? and show_time = ?", nativeQuery = true)
	public List<Seats> getAllByDate(LocalDate date, String time);

}
