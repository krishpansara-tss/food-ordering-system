package com.fooddelivery.services;

import com.fooddelivery.enums.OrderStatusType;
import com.fooddelivery.exceptions.*;
import com.fooddelivery.interfaces.PaymentMode;
import com.fooddelivery.model.*;
import com.fooddelivery.payment.CODPayment;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;

import java.util.*;

public class OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;
    private Random random = new Random();

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public void placeOrder(User user, PaymentMode paymentMode, Address deliveryAddress){
        if(!(user instanceof Customer)){
            throw new InvalidUserTypeException(user.getClass().getName() + " is not supported the place order operation");
        }

        Customer customer = (Customer) user;
        Cart cart = customer.getCart();

        if(cart == null){
            throw new InvalidOrderException("Order failed: Cart not found, you can't place an order");
        }

        if(cart.getCurrentRestaurantId() == null){
            throw new InvalidOrderException("Order failed: Restaurant is not available");
        }

        String restaurantId = cart.getCurrentRestaurantId();
        String restaurantName = restaurantRepository.findRestaurantById(restaurantId).getRestaurantName();

        Map<String, CartItem> cartMap = cart.getCartItemMap();

        if(cartMap.isEmpty()){
            throw new InvalidOrderException("Order failed: Cart is empty, you can't place an order");
        }

        double cartTotal = 0;
        for(CartItem cartItem : cartMap.values()){
            if(cartItem.getQuantity() > 10){
                throw new InvalidOrderException("Order failed: Maximum item quantity is 10");
            }
            cartTotal += cartItem.getQuantity() * cartItem.getMenuItem().getPrice();
        }

        double appliedDiscount = calculateDiscount(cartTotal);

        double finalAmount = cartTotal - appliedDiscount;

        if (deliveryAddress == null) {
            throw new InvalidOrderException("Order failed: Delivery address is required.");
        }

        String restaurantCity = restaurantRepository.findRestaurantById(restaurantId).getCity();
        if (!restaurantCity.equalsIgnoreCase(deliveryAddress.getCity())) {
            throw new InvalidOrderException("Order failed: Restaurant is in " + restaurantCity + ", but your delivery address is in " + deliveryAddress.getCity() + ". They must be in the same city.");
        }

        DeliveryPartner assignedDeliveryPartner = assignDeliveryPartnerByCity(deliveryAddress.getCity());

        if(assignedDeliveryPartner == null){
            throw new DeliveryPartnerNotAvailable("Delivery Partner is Not available now, try again after some time");
        }
        assignedDeliveryPartner.setAvailable(false);

        Cart cartSnapshot = new Cart();
        cartSnapshot.setCurrentRestaurantId(cart.getCurrentRestaurantId());
        cartSnapshot.setCartItemMap(new HashMap<>(cart.getCartItemMap()));

        List<OrderItem> orderItemList = new ArrayList<>();
        for(CartItem cartItem : cartMap.values()){
            orderItemList.add(new OrderItem(cartItem.getMenuItem(), cartItem.getQuantity()));
        }

        Order order = new Order.Builder()
                .customer(customer)
                .restaurantId(restaurantId)
                .restaurantName(restaurantName)
                .assignedDeliveryPartner(assignedDeliveryPartner)
//                .listOfItem(cartSnapshot)
                .totalAmount(cartTotal)
                .orderItem(orderItemList)
                .appliedDiscount(appliedDiscount)
                .finalAmount(finalAmount)
                .paymentMode(paymentMode)
                .deliveryAddress(deliveryAddress)
                .build();


        orderRepository.addOrder(order);
        customer.addOrderHistory(order);

        assignedDeliveryPartner.setCurrentOrder(order);
        assignedDeliveryPartner.getAssignedOrderList().add(order);
        assignedDeliveryPartner.setEarning(assignedDeliveryPartner.getEarning() + 10.0);
        paymentMode.pay(finalAmount);

        printInvoice(order, customer);

        customer.getCart().setCurrentRestaurantId(null);
        customer.getCart().getCartItemMap().clear();
    }

    private double calculateDiscount(double cartTotal) {
        if (cartTotal > 2000) return cartTotal * 0.20;
        if (cartTotal > 1000) return cartTotal * 0.10;
        if (cartTotal > 500) return 50;
        return 0;
    }

    public DeliveryPartner assignDeliveryPartnerByCity(String city) {
        ArrayList<DeliveryPartner> driverList = new ArrayList<>();

        Map<String, User> userMap = userRepository.getAllUserMap();

        for (User user : userMap.values()) {
            if (user instanceof DeliveryPartner) {
                DeliveryPartner dp = (DeliveryPartner) user;
                if (dp.isAvailable() && dp.getCity().equalsIgnoreCase(city)) {
                    driverList.add(dp);
                }
            }
        }
        if (driverList.isEmpty()) return null;

        return driverList.get(random.nextInt(driverList.size()));
    }

    private void printInvoice(Order order, Customer customer) {
        System.out.println("\n+---------------------------------------------------+");
        System.out.println("                  ORDER INVOICE                    ");
        System.out.println("+---------------------------------------------------+");
        System.out.printf(" Order ID: %-15s | Customer: %-13s \n", order.getOrderId(), customer.getUserName());
        System.out.printf(" Order Date: %-39s \n", order.getOrderDate());
        System.out.printf(" Delivery Address: %-32s \n", order.getDeliveryAddress() != null ? order.getDeliveryAddress().toString() : "N/A");
        System.out.println("+---------------------------------------------------+");
        System.out.printf(" %-20s | %-4s | %-10s | %-8s \n", "Item Name", "Qty", "Price", "Total");
        System.out.println("+---------------------------------------------------+");
        for (OrderItem oi : order.getListOfOrderItem()) {
            double cost = oi.getMenuItem().getPrice() * oi.getQuantity();
            System.out.printf(" %-20s | %-4d | ₹%-9.2f | ₹%-7.2f \n",
                    oi.getMenuItemName(), oi.getQuantity(), oi.getPriceAtPurchase(), cost);
        }
        System.out.println("+---------------------------------------------------+");
        System.out.printf("  Subtotal:                                 ₹%-7.2f  \n", order.getTotalAmount());
        System.out.printf("  Discount Applied:                        -₹%-7.2f  \n", order.getAppliedDiscount());
        System.out.printf("  GRAND TOTAL:                              ₹%-7.2f  \n", order.getFinalAmount());
        System.out.println("+---------------------------------------------------+");
        System.out.printf("  Payment Mode: %-35s \n", order.getPaymentMode());
        System.out.printf("  Assigned Delivery Executive: %-20s \n", order.getAssignedDeliveryPartner().getUserName());
        System.out.printf("  Current State: %-34s \n", order.getOrderStatus());
        System.out.println("+---------------------------------------------------+");
    }

    public void displayOrderHistory(Customer customer){
        List<Order> orderHistoryList = customer.getOrderHistory();

        if(orderHistoryList.isEmpty()){
            System.out.println("You haven't ordered anything yet");
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
            if(order.getOrderId().equals(orderId.toUpperCase())){
                System.out.println("Order ID   : " + order.getOrderId());
                System.out.println("Status     : " + order.getOrderStatus());
                System.out.println("Payment    : " + order.getPaymentMode());
                System.out.println("Items Purchased:");

                for (OrderItem oi : order.getListOfOrderItem()) {
                    System.out.printf("     - %-15s = %-10.2f x %-3d%n", oi.getMenuItemName(), oi.getPriceAtPurchase(), oi.getQuantity());
                }

                System.out.printf("Final Bill  : ₹%.2f\n", order.getFinalAmount());
                System.out.println("-----------------------------------------------------");
                return;
            }

        }
        System.out.println("=====================================================");
    }


    public void displayActiveOrdersForRestaurant(String restaurantId) {
        Map<String, Order> orders = orderRepository.getOrderMap();
        boolean found = false;

        System.out.println("\n================= ORDERS FOR RESTAURANT =================");
        for (Order order : orders.values()) {
            if (order.getRestaurantId().equalsIgnoreCase(restaurantId) && order.getOrderStatus().getStatus() == OrderStatusType.APPROVED_BY_RESTAURANT) {
                found = true;
                System.out.println("Order ID   : " + order.getOrderId());
                System.out.println("Customer   : " + order.getCustomer().getUserName());
                System.out.println("Amount     : ₹" + order.getFinalAmount());
                System.out.println("Status     : " + order.getOrderStatus());
                System.out.println("Items:");
                for (OrderItem oi : order.getListOfOrderItem()) {
                    System.out.printf("  - %-20s x %-3d\n", oi.getMenuItemName(), oi.getQuantity());
                }
                System.out.println("---------------------------------------------------------");
            }
        }
        if (!found) {
            System.out.println("No active orders found for this restaurant yet.");
        }
        System.out.println("=========================================================");
    }

    public void displayAllOrdersForRestaurant(String restaurantId) {
        Map<String, Order> orders = orderRepository.getOrderMap();
        boolean found = false;

        System.out.println("\n================= ORDERS FOR RESTAURANT =================");
        for (Order order : orders.values()) {
            if (order.getRestaurantId().equalsIgnoreCase(restaurantId)) {
                found = true;
                System.out.println("Order ID   : " + order.getOrderId());
                System.out.println("Customer   : " + order.getCustomer().getUserName());
                System.out.println("Amount     : ₹" + order.getFinalAmount());
                System.out.println("Status     : " + order.getOrderStatus());
                System.out.println("Items:");
                for (OrderItem oi : order.getListOfOrderItem()) {
                    System.out.printf("  - %-20s x %-3d\n", oi.getMenuItemName(), oi.getQuantity());
                }
                System.out.println("---------------------------------------------------------");
            }
        }
        if (!found) {
            System.out.println("No orders found for this restaurant yet.");
        }
        System.out.println("=========================================================");
    }

    public Order findOrderById(String orderId) {
        Order order = orderRepository.findOrderById(orderId);
        if(order == null){
            throw new OrderNotFoundException("Order having ID: " + orderId + " not found.");
        }
        return order;
    }

    public void getYourOrderStatus(String orderId){
        Order order = orderRepository.findOrderById(orderId);

        if(order == null){
            throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
        }

        System.out.println("Your order ID       : " + orderId);
        System.out.println("Your order Status   : " + order.getOrderStatus());
    }

    public void updateOrderStatus(String orderId) {
        Order order = findOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
        }
        OrderStatusType currentStatus = order.getOrderStatus().getStatus();
        if (currentStatus == OrderStatusType.DELIVERED) {
            System.out.println("Order with ID " + orderId + " is already DELIVERED. Cannot update status further.");
            return;
        }
        try{
            order.nextState();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        OrderStatusType currType = order.getOrderStatus().getStatus();

        System.out.println("Order status updated successfully! Order ID: " + orderId + " is now " + currType);
        if (currType == OrderStatusType.DELIVERED && order.getAssignedDeliveryPartner() != null) {
            if(order.getPaymentMode() instanceof CODPayment){
                ((CODPayment) order.getPaymentMode()).paymentDoneOnCOD(order.getFinalAmount());
            }
            order.getAssignedDeliveryPartner().setAvailable(true);
            System.out.println("Delivery partner " + order.getAssignedDeliveryPartner().getUserName() + " is now marked AVAILABLE.");
        }
    }

    public void printOrderDetails(Order order) {
        System.out.println("\n============= CURRENT ACTIVE ORDER =============");
        System.out.println("Order ID          : " + order.getOrderId());
        System.out.println("Restaurant        : " + order.getRestaurantName());
        System.out.println("Customer          : " + order.getCustomer().getUserName());
        System.out.println("Delivery Address  : " + (order.getDeliveryAddress() != null ? order.getDeliveryAddress().toString() : "N/A"));
        System.out.println("Status            : " + order.getOrderStatus());
        System.out.println("Payment Mode      : " + order.getPaymentMode());
        System.out.println("Items:");
        for (OrderItem oi : order.getListOfOrderItem()) {
            System.out.printf("  - %-20s x %d\n", oi.getMenuItemName(), oi.getQuantity());
        }
        System.out.printf("Total Amount : ₹%.2f\n", order.getFinalAmount());
        System.out.println("================================================");
    }

    public void placeOrderFlow(Customer customer, PaymentMode paymentMode, Address deliveryAddress) {
        if (customer.getCart() == null || customer.getCart().getCartItemMap().isEmpty()) {
            System.out.println("Cannot place order: Your cart is empty.");
            return;
        }

        if(paymentMode == null){
            System.out.println("You haven't selected any payment mode. Please try again after selecting the Payment Mode.");
            return;
        }

        try {
            placeOrder(customer, paymentMode, deliveryAddress);
            System.out.println("Order has been placed successfully!");
        } catch (Exception e) {
            System.out.println("Failed to place order: " + e.getMessage());
        }
    }
}
