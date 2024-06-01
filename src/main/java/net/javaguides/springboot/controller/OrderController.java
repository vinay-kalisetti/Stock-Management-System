package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.model.Order;
import net.javaguides.springboot.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	// display list of orders
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "name", "asc", model);		
	}
	
	@GetMapping("/showNewOrderForm")
	public String showNewOrderForm(Model model) {
		// create model attribute to bind form data
		Order order = new Order();
		model.addAttribute("order", order);
		return "new_order";
	}
	
	@PostMapping("/saveOrder")
	public String saveOrder(@ModelAttribute("order") Order order) {
		// save order to database
		orderService.saveOrder(order);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get order from the service
		Order order = orderService.getOrderById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("order", order);
		return "update_order";
	}
	
	@GetMapping("/deleteOrder/{id}")
	public String deleteOrder(@PathVariable (value = "id") long id) {
		
		// call order employee method 
		this.orderService.deleteOrderById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Order> page = orderService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Order> listOrders = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listOrders", listOrders);
		return "index";
	}
}
