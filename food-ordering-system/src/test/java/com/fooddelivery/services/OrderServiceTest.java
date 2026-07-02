package com.fooddelivery.services;

import com.fooddelivery.enums.CuisineType;
import com.fooddelivery.exceptions.DeliveryPartnerNotAvailable;
import com.fooddelivery.exceptions.InvalidUserTypeException;
import com.fooddelivery.model.*;
import com.fooddelivery.payment.CODPayment;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService orderService;
    private CartService cartService;

    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;
    private OrderRepository orderRepository;

    private Customer customerRajkot;
    private Customer customerSurat;

    private DeliveryPartner dpRajkot;
    private DeliveryPartner dpSurat;

    private Restaurant restaurantRajkot;
    private Restaurant restaurantSurat;

    private MenuItem pizza;
    private MenuItem locha;

    @BeforeEach
    void init() {
        userRepository       = new UserRepository();
        restaurantRepository = new RestaurantRepository();
        orderRepository      = new OrderRepository();

        orderService = new OrderService(orderRepository, userRepository, restaurantRepository);
        cartService  = new CartService(userRepository, restaurantRepository);
        
        customerRajkot = new Customer("Ravi",  "123",  "9876540001", "Rajkot");
        customerRajkot.addAddress(new Address("Primary", "Default Street", "Rajkot"));
        customerSurat = new Customer("Mital",  "123", "9876540002", "Surat");
        customerSurat.addAddress(new Address("Primary", "Default Street", "Surat"));
        
        dpRajkot = new DeliveryPartner("Nilesh",  "123",  "9988000001", "Rajkot");
        dpSurat     = new DeliveryPartner("Krish",   "123",  "9988000002", "Surat");
        
        restaurantRajkot = new Restaurant("Manek Chowk", "123","9000001111", "Rajkot", "Mavdi");
        pizza = new MenuItem("Gujarati Thali", 200.0, true, CuisineType.STREET_FOOD);
        restaurantRajkot.addMenuItem(pizza);

        restaurantSurat = new Restaurant("Surat Locha Corner", "123", "9000002222", "Surat", "Vastrapur");
        locha = new MenuItem("Surat Locha", 50.0, true, CuisineType.STREET_FOOD);
        restaurantSurat.addMenuItem(locha);
        
        userRepository.addUser(customerRajkot);
        userRepository.addUser(customerSurat);
        userRepository.addUser(dpRajkot);
        userRepository.addUser(dpSurat);
        restaurantRepository.addRestaurant(restaurantRajkot);
        restaurantRepository.addRestaurant(restaurantSurat);
    }

    private Order placeTestOrder(Customer customer, Restaurant restaurant, MenuItem item, int qty) {
        cartService.addItemToCart(customer, restaurant.getRestaurantId(), item.getMenuItemId(), qty);
        orderService.placeOrder(customer, new CODPayment(), customer.getAddresses().get(0));
        return customer.getOrderHistory().get(customer.getOrderHistory().size() - 1);
    }


    @Test
    void placeOrder_AddedInHistory() {
        placeTestOrder(customerRajkot, restaurantRajkot, pizza, 1);

        assertEquals(1, customerRajkot.getOrderHistory().size());
    }

    @Test
    void placeOrder_ShouldAssignDeliveryPartnerInSameCity() {
        Order order = placeTestOrder(customerRajkot, restaurantRajkot, pizza, 1);

        String partnerCity    = order.getAssignedDeliveryPartner().getCity();
        String restaurantCity = restaurantRajkot.getCity();

        assertEquals(restaurantCity, partnerCity);
    }


    @Test
    void placeOrder_SaveOrderToRepository() {
        Order order = placeTestOrder(customerRajkot, restaurantRajkot, pizza, 1);

        assertNotNull(orderRepository.getOrderMap().get(order.getOrderId()));
    }

    @Test
    void placeOrder_ApplyDiscount() {
        Order order = placeTestOrder(customerRajkot, restaurantRajkot, pizza, 3);

        assertEquals(50.0, order.getAppliedDiscount(), 0.01);
        assertEquals(550.0, order.getFinalAmount(), 0.01);
    }

    @Test
    void placeOrder_NoDiscount() {
        Order order = placeTestOrder(customerRajkot, restaurantRajkot, pizza, 2);

        assertEquals(0.0, order.getAppliedDiscount(), 0.01);
        assertEquals(400.0, order.getFinalAmount(), 0.01);
    }


    @Test
    void placeOrder_NoPartnerAvailable() {
        dpRajkot.setAvailable(false);

        cartService.addItemToCart(customerRajkot, restaurantRajkot.getRestaurantId(),
                pizza.getMenuItemId(), 1);

        assertThrows(DeliveryPartnerNotAvailable.class, () ->
                orderService.placeOrder(customerRajkot, new CODPayment(), customerRajkot.getAddresses().get(0))
        );
    }

    @Test
    void placeOrder_NoPartnerAvailableC2() {
        cartService.addItemToCart(customerRajkot, restaurantRajkot.getRestaurantId(),
                pizza.getMenuItemId(), 1);
        orderService.placeOrder(customerRajkot, new CODPayment(), customerRajkot.getAddresses().get(0));

        cartService.addItemToCart(customerRajkot, restaurantRajkot.getRestaurantId(),
                pizza.getMenuItemId(), 1);

        assertThrows(DeliveryPartnerNotAvailable.class, () ->
                orderService.placeOrder(customerRajkot, new CODPayment(), customerRajkot.getAddresses().get(0))
        );
    }

    @Test
    void placeOrder_NonCustomerUser() {
        assertThrows(InvalidUserTypeException.class, () ->
                orderService.placeOrder(dpRajkot, new CODPayment(), null)
        );
    }


    @Test
    void placeOrder_PartnerRestaurantSameCity() {
        dpRajkot.setAvailable(false);

        cartService.addItemToCart(customerRajkot, restaurantRajkot.getRestaurantId(),
                pizza.getMenuItemId(), 1);

        assertThrows(DeliveryPartnerNotAvailable.class, () ->
                orderService.placeOrder(customerRajkot, new CODPayment(), customerRajkot.getAddresses().get(0))
        );
    }

    @Test
    void placeOrder_CityMismatch_ShouldThrowException() {
        // Set up restaurant in Surat, pizza is added there
        pizza = new MenuItem("Surat Pizza", 200.0, true, CuisineType.STREET_FOOD);
        restaurantSurat.addMenuItem(pizza);
        
        // Add item from Surat restaurant to Rajkot customer's cart
        cartService.addItemToCart(customerRajkot, restaurantSurat.getRestaurantId(), pizza.getMenuItemId(), 1);

        // Rajkot customer's primary address is in Rajkot, while restaurant is in Surat. They should mismatch.
        assertThrows(com.fooddelivery.exceptions.InvalidOrderException.class, () ->
                orderService.placeOrder(customerRajkot, new CODPayment(), customerRajkot.getAddresses().get(0))
        );
    }
}