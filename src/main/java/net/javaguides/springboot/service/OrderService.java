package net.javaguides.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.javaguides.springboot.model.Order;

public interface OrderService {
	List<Order> getAllOrders();
	void saveOrder(Order order);
	Order getOrderById(long id);
	void deleteOrderById(long id);
	Page<Order> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
