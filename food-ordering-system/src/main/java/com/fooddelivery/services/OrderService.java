package com.fooddelivery.services;

import com.fooddelivery.exceptions.DeliveryPartnerNotAvailable;
import com.fooddelivery.exceptions.InvalidOrderException;
import com.fooddelivery.exceptions.InvalidUserTypeException;
import com.fooddelivery.model.*;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;
    private Random random = new Random();

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public void placeOrder(User user){
        if(!(user instanceof Customer)){
            throw new InvalidUserTypeException(user.getClass().getName() + " is not supported the place order operation");
        }

        Customer customer = (Customer) user;
        Cart cart = customer.getCart();

        if(cart == null){
            System.out.println("Cart not found, you can't place an order");
            return;
        }

        if(cart.getCurrentRestaurantId() == null){
            throw new InvalidOrderException("Order failed: Restaurant is not available");
        }

        String restaurantId = cart.getCurrentRestaurantId();
        String restaurantName = restaurantRepository.findRestaurantById(restaurantId).getRestaurantName();

        Map<String, CartItem> cartMap = cart.getCartItemMap();

        if(cartMap.isEmpty()){
            System.out.println("Cart is empty, you can't place an order");
            return;
        }

        double cartTotal = 0;
        for(CartItem cartItem : cartMap.values()){
            cartTotal += cartItem.getQuantity() * cartItem.getMenuItem().getPrice();
        }

        double appliedDiscount = 0;
        if (cartTotal > 500) {
            appliedDiscount = 50.0;
        }

        double finalAmount = cartTotal - appliedDiscount;

        DeliveryPartner assingedDeliveryPartner = assignRandomDeliveryPartner();
        assingedDeliveryPartner.setAvailable(false);

        if(assingedDeliveryPartner == null){
            throw new DeliveryPartnerNotAvailable("Delivery Partner is Not available now, try again after some time");
        }

        Order order = new Order(customer, restaurantId, restaurantName, assingedDeliveryPartner, cart, cartTotal, appliedDiscount, finalAmount);

        orderRepository.addOrder(order);

        customer.addOrderHistory(order);

        printInvoice(order, customer);

        customer.getCart().setCurrentRestaurantId(null);
        customer.getCart().getCartItemMap().clear();
    }

    public DeliveryPartner assignRandomDeliveryPartner(){
        ArrayList<DeliveryPartner> driverList = new ArrayList<>();

        Map<String, User> userMap = userRepository.getAllUserMap();

        for (User user : userMap.values()){
            if(user instanceof DeliveryPartner && ((DeliveryPartner) user).isAvailable() ){
                driverList.add((DeliveryPartner)user);
            }
        }
        if(driverList.isEmpty()) return null;

        return driverList.get(random.nextInt(driverList.size()));
    }

    private void printInvoice(Order order, Customer customer) {
        System.out.println("\n+---------------------------------------------------+");
        System.out.println("|                  ORDER INVOICE                    |");
        System.out.println("+---------------------------------------------------+");
        System.out.printf("| Order ID: %-15s | Customer: %-13s |\n", order.getOrderId(), customer.getUserName());
        System.out.println("+---------------------------------------------------+");
        System.out.printf("| %-20s | %-4s | %-10s | %-8s |\n", "Item Name", "Qty", "Price", "Total");
        System.out.println("+---------------------------------------------------+");
        for (CartItem ci : order.getListOfItem().getCartItemMap().values()) {
            double cost = ci.getMenuItem().getPrice() * ci.getQuantity();
            System.out.printf("| %-20s | %-4d | ₹%-9.2f | ₹%-7.2f |\n",
                    ci.getMenuItem().getItemName(), ci.getQuantity(), ci.getMenuItem().getPrice(), cost);
        }
        System.out.println("+---------------------------------------------------+");
        System.out.printf("| Subtotal:                                 ₹%-7.2f |\n", order.getTotalAmount());
        System.out.printf("| Discount Applied:                        -₹%-7.2f |\n", order.getAppliedDiscount());
        System.out.printf("| GRAND TOTAL:                              ₹%-7.2f |\n", order.getFinalAmount());
        System.out.println("+---------------------------------------------------+");
        System.out.printf("| Payment Mode: %-35s |\n", order.getPaymentMode());
        System.out.printf("| Assigned Delivery Executive: %-20s |\n", order.getAssingedDeliveryPartner().getUserName());
        System.out.printf("| Current State: %-34s |\n", order.getOrderStatus());
        System.out.println("+---------------------------------------------------+");
    }

    public void displayOrderHistory(Customer customer){
        List<Order> orderHistoryList = customer.getOrderHistory();

        if(orderHistoryList.isEmpty()){
            System.out.println("Your haven't ordered anything yet");
            return;
        }

        for (Order order : orderHistoryList) {

            System.out.println("Order ID   : " + order.getOrderId());
            System.out.println("Status     : " + order.getOrderStatus());
            System.out.println("Payment    : " + order.getPaymentMode());
            System.out.println("Items Purchased:");

            System.out.printf("   Final Bill : ₹%.2f\n", order.getFinalAmount());
            System.out.println("-----------------------------------------------------");
        }

        System.out.println("=====================================================");
    }

    public void displayOrderById(Customer customer, String orderId){
        List<Order> orderHistoryList = customer.getOrderHistory();

        if(orderHistoryList.isEmpty()){
            System.out.println("Your haven't ordered anything yet");
            return;
        }


        for (Order order : orderHistoryList) {
            if(order.getOrderId().equals(orderId)){
                System.out.println("Order ID   : " + order.getOrderId());
                System.out.println("Status     : " + order.getOrderStatus());
                System.out.println("Payment    : " + order.getPaymentMode());
                System.out.println("Items Purchased:");

                for (CartItem ci : order.getListOfItem().getCartItemMap().values()) {
                    System.out.printf("     - %-15s x %-3d\n", ci.getMenuItem().getItemName(), ci.getQuantity());
                }

                System.out.printf("Final Bill : ₹%.2f\n", order.getFinalAmount());
                System.out.println("-----------------------------------------------------");
                return;
            }

        }

        System.out.println("=====================================================");
    }
}
