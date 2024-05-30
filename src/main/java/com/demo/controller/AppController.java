package com.demo.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.entity.Customers;
import com.demo.entity.Movies;
import com.demo.entity.Orders;
import com.demo.entity.Seats;
import com.demo.service.CustomerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AppController {

	@Autowired
	private CustomerService cService;

	// index page controller
	@GetMapping("/")
	public String home(Model m, HttpSession session) {

		String movie = (String) session.getAttribute("movieName");
	    System.out.println(movie);
		List<Movies> list = cService.getAllMovie();
		m.addAttribute("movieList", list);
		m.addAttribute("menu", "home");

		return "index";
	}

	// seat booking mapping
	@GetMapping("/booking")
	public String bookingCheck(@RequestParam("name") String movieName, Model m, HttpSession session) {
		List<Movies> list = cService.getAllMovie();
		List<String> checkMovie = new ArrayList<>();
		for (Movies string : list) {
			checkMovie.add(string.getName());
		}
		if (checkMovie.contains(movieName)) {
			session.setAttribute("movieName", movieName);
			// System.out.println(movieName);
			LocalDate now = LocalDate.now();
			LocalDate monthLimit = LocalDate.now();
			String time = "09:00 am";
			List<String> seatNo1 = new ArrayList<String>();
			List<Seats> all = cService.getAllSeat(now, time);

			for (Seats s : all) {
				for (String s1 : s.getSeatNo()) {
					seatNo1.add(s1);
				}
			}

			m.addAttribute("date", now);
			m.addAttribute("max", monthLimit.plusMonths(1));
			m.addAttribute("min", monthLimit);
			m.addAttribute("time", time);
			m.addAttribute("seats", seatNo1);
			return "home";

		} else {
			return "redirect:/";
		}
	}

	// Seat booking process
	@PostMapping("/book-seat")
	public String bookSeat(@ModelAttribute("Seat") Seats seat, @RequestParam("movieName") String movieName,
			HttpSession session, Model m) {
		LocalDate currentDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date todayDate = Date.from(currentDate.atStartOfDay(defaultZoneId).toInstant());
		LocalDate date = (LocalDate) session.getAttribute("bookingdate");
		String time = (String) session.getAttribute("bookingtime");
		// System.out.println(seat.getSeatNo().equals(null) + " wooo" +
		// movieName.equals(null));
		Customers object = (Customers) session.getAttribute("user");

		if (object == null) {
			return "redirect:/loginForm";
		} else if ((seat.getSeatNo().isEmpty()) && (movieName.equals(null))) {
			System.out.println("Seat is null");
			return "redirect:/home";
		} else if (date == null) {
			date = currentDate;
			time = "09:00 am";
			if (((date.isAfter(currentDate)) || (date.equals(currentDate)))
					&& (date.isBefore(currentDate.plusMonths(1)) || date.equals(currentDate.plusMonths(1)))) {

				Date date2 = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
				List<Double> price = new ArrayList<Double>();
				int numberOfSeats = seat.getSeatNo().size();
				double sum = 0;
				double p = 250.22d;

				for (int i = 0; i < numberOfSeats; i++) {
					sum = sum + p;
					price.add(p);
				}

				seat.setTotal(sum);
				seat.setPrice(price);

				Orders history = new Orders(seat.getSeatNo(), price, sum, movieName, todayDate, date2, time, object);
				cService.saveSeat(seat, object, date2, time);
				cService.saveHistory(history, object);
				List<String> seatNo1 = new ArrayList<String>();
				List<Customers> all = cService.getAll();
				for (Customers c : all) {
					for (Seats s : c.getSeat()) {
						for (String s1 : s.getSeatNo()) {
							seatNo1.add(s1);
						}

					}
				}

				m.addAttribute("seats", seatNo1);
				session.setAttribute("user", object);
				session.setAttribute("msg", "your seat booked successfully");
				return "redirect:/home";

			} else {
				//System.out.println("ye date current date se pahle ki date hai");
				return "redirect:/booking-seat?movieName=" + movieName;

			}
		} else {
			if (((date.isAfter(currentDate)) || (date.equals(currentDate)))
					&& (date.isBefore(currentDate.plusMonths(1)) || date.equals(currentDate.plusMonths(1)))) {
				Date date2 = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
				int numberOfSeats = seat.getSeatNo().size();
				double sum = 0;
				double p = 250.22d;
				List<Double> price = new ArrayList<>();

				for (int i = 0; i < numberOfSeats; i++) {
					sum = sum + p;
					price.add(p);
				}

				seat.setTotal(sum);
				seat.setPrice(price);

				Orders history = new Orders(seat.getSeatNo(), price, sum, movieName, todayDate, date2, time, object);
				cService.saveSeat(seat, object, date2, time);
				cService.saveHistory(history, object);
				List<String> list = new ArrayList<String>();
				List<Customers> all = cService.getAll();
				for (Customers c : all) {
					for (Seats s : c.getSeat()) {
						for (String s1 : s.getSeatNo()) {
							list.add(s1);
						}

					}
				}

				m.addAttribute("seats", list);
				session.setAttribute("user", object);
				session.setAttribute("msg", "your seat book successsfully");
				return "redirect:/home";

			} else {

				return "redirect:/booking-seat?movieName=" + movieName;

			}
		}

	}

	// user login
	@GetMapping("/loginForm")
	public String loginForm(Model m) {
		m.addAttribute("menu", "login");
		return "login";
	}

	// Register User
	@GetMapping("/register")
	public String register(Model m) {

		m.addAttribute("menu", "register");
		return "register";
	}

	// cutomer saving process
	@PostMapping("/save")
	public String save(@ModelAttribute("customer") Customers customer) {
		cService.save(customer);
		return "redirect:/loginForm";

	}

	// chacking date and time for seat booking
	@PostMapping("/check")
	public String checkDate(@RequestParam("localdate") String date, @RequestParam("localtime") String time, Model m,
			HttpSession session) {
		Customers object = (Customers) session.getAttribute("user");
		String movie = (String) session.getAttribute("movieName");
		LocalDate monthLimit = LocalDate.now();
		if (movie.equals(null)) {
			return "home";

		} else if (object == null) {
			LocalDate now = LocalDate.parse(date);
			List<String> list = new ArrayList<String>();
			List<Seats> all = cService.getAllSeat(now, time);

			for (Seats s : all) {
				for (String s1 : s.getSeatNo()) {
					list.add(s1);
				}

			}

			session.setAttribute("bookingdate", now);
			session.setAttribute("bookingtime", time);
			m.addAttribute("date", now);
			m.addAttribute("max", monthLimit.plusMonths(1));
			m.addAttribute("min", monthLimit);
			m.addAttribute("time", time);
			m.addAttribute("seats", list);

			return "home";
		} else {
			LocalDate now = LocalDate.parse(date);
			List<String> seatNo1 = new ArrayList<String>();
			List<Seats> all = cService.getAllSeat(now, time);

			for (Seats s : all) {
				for (String s1 : s.getSeatNo()) {
					seatNo1.add(s1);
				}

			}

			session.setAttribute("bookingdate", now);
			session.setAttribute("bookingtime", time);
			m.addAttribute("date", now);
			m.addAttribute("max", monthLimit.plusMonths(1));
			m.addAttribute("min", monthLimit);
			m.addAttribute("time", time);
			m.addAttribute("seats", seatNo1);
			return "dashboard";
		}
	}

	// Login process (checking user exist or not)
	@PostMapping("/processing")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, Model m) {

		Customers object = (Customers) session.getAttribute("user");
		if (object != null) {
			return "redirect:/booking-seat";
		} else {

			Customers customer = cService.Login(email, password);

			if (customer == null) {
				m.addAttribute("failed",
						"OOPS, cant find the user in our database. Please click the link given below to register");
				return "login";
			} else {
				session.setAttribute("user", customer);
			}
			return "redirect:/home";
		}
	}

	// User Dashboard page
	@GetMapping("/booking-seat")
	public String getUser(@RequestParam("movieName") String movieName, HttpSession session, Model m) {
		List<Movies> movie2 = cService.getAllMovie();
		List<String> checkMovie = new ArrayList<>();
		for (Movies string : movie2) {
			checkMovie.add(string.getName());
		}
		if (checkMovie.contains(movieName)) {
			session.setAttribute("movieName", movieName);

			LocalDate now = LocalDate.now();
			LocalDate monthLimit = LocalDate.now();
			String time = "09:00 am";

			Customers customer = (Customers) session.getAttribute("user");
			List<String> seatNo1 = new ArrayList<String>();
			List<Seats> seat = customer.getSeat();

			List<Seats> all = cService.getAllSeat(now, time);

			for (Seats s : all) {
				for (String s1 : s.getSeatNo()) {
					seatNo1.add(s1);
				}

			}

			m.addAttribute("date", now);
			m.addAttribute("time", time);
			m.addAttribute("max", monthLimit.plusMonths(1));
			m.addAttribute("min", monthLimit);
			m.addAttribute("seats", seatNo1);
			m.addAttribute("seat", seat);
			session.setAttribute("user", customer);
			return "dashboard";
		} else {
			return "redirect:/home";
		}

	}

	// user home page
	@GetMapping("/home")
	public String mainDashboard(HttpSession session, Model m) {
		session.removeAttribute("bookingdate");
		session.removeAttribute("bookingtime");
		session.removeAttribute("movieName");
		m.addAttribute("menu", "home");

		String message = (String) session.getAttribute("msg");
		m.addAttribute("message", message);
		session.removeAttribute("msg");
		// System.out.println(message);
		List<Movies> movie2 = cService.getAllMovie();
		m.addAttribute("listMovie", movie2);

		return "main-dashboard";
	}

//	User Setting
	@GetMapping("/setting")
	public String getSetting(Model m, HttpSession session) {
		Customers customer = (Customers) session.getAttribute("user");
		m.addAttribute("user", customer);
		m.addAttribute("menu", "setting");
		return "setting";
	}

//	User update form
	@GetMapping("/setting/update/{id}")
	public String updateForm(@PathVariable("id") long id, Model m) {
		System.out.println(id);
		m.addAttribute("menu", "setting");
		return "update-details";

	}

//	update Details
	@PostMapping("/setting/update-details")
	public String updateDetails(@ModelAttribute("customer") Customers cust, HttpSession session) {
		String name = cust.getName();
		String email = cust.getEmail();
		Customers customer = (Customers) session.getAttribute("user");
		customer.setName(name);
		customer.setEmail(email);
		cService.updateDetail(customer);

		return "redirect:/setting";

	}

//	See all customers
	@GetMapping("/all-customers-records")
	public String allRecords(Model m, HttpSession session) {
		Customers object = (Customers) session.getAttribute("user");
		long cId = object.getcId();
		if (cId == 1) {
			List<Customers> all = cService.getAll();
			m.addAttribute("records", all);
			m.addAttribute("menu", "allusers");
			return "user_records";
		} else {
			return "redirect:/booking-seat";
		}
	}

//	Order history
	@GetMapping("/order-history")
	public String history(HttpSession session, Model m) {
		Date todayDate = new Date();
		Customers object = (Customers) session.getAttribute("user");
		session.setAttribute("user", object);
		List<Orders> list = cService.getAllHistory(object.getcId());
		m.addAttribute("hList", list);
		m.addAttribute("todaydate", todayDate);

		LocalDate date = (LocalDate) session.getAttribute("bookingdate");
		System.out.println(date);
		m.addAttribute("menu", "order");
		return "history";
	}

//	see all Customers and their seats
	@GetMapping("/all-seats/{id}")
	public String allSeats(@PathVariable("id") long id, Model m, HttpSession session) {
		Customers object = (Customers) session.getAttribute("user");
		long cId = object.getcId();
		if (cId == 1) {
			List<Orders> list = cService.getAllHistory(id);
			m.addAttribute("seatRecords", list);
			m.addAttribute("menu", "allusers");
			return "seat-records";
		} else {
			return "redirect:/booking-seat";
		}
	}

	// user about us page

	@GetMapping("/about-us")
	public String aboutUs(Model m) {
		m.addAttribute("menu", "about");
		return "about-us";
	}

	// user contact page
	@GetMapping("/contact-us")
	public String contactUs(Model m) {
		m.addAttribute("menu", "contact");
		return "contact-us";
	}

	// index about us page
	@GetMapping("/about-us-index")
	public String aboutUsIndex(Model m) {
		m.addAttribute("menu", "index-about");
		return "about-us-index";
	}

	// index contact page
	@GetMapping("/contact-us-index")
	public String contactUsIndex(Model m) {
		m.addAttribute("menu", "index-contact");
		return "contact-us-index";
	}

//	Logout process
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");

		session.removeAttribute("bookingdate");
		session.removeAttribute("bookingtime");
		session.removeAttribute("movieName");

		return "redirect:/";
	}

//	Exception handling
	@ExceptionHandler(Exception.class)
	public String handleError(Exception ex, Model m, HttpSession session) {
		Customers object = (Customers) session.getAttribute("user");
		if (object == null) {
			return "redirect:/loginForm";
		} else {
			return "redirect:/home";
		}
	}

}
