import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    @BeforeEach
    public void setupForEachTestCase() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("16:30:00"));
        boolean isOpen = spyRestaurant.isRestaurantOpen();
        assertTrue(isOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("06:40:00"));
        boolean isOpen = spyRestaurant.isRestaurantOpen();
        assertFalse(isOpen);

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //<<<<<<<<<<<<<<<<<<<<<<<TOTAL ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


//    when no item added for the order i.e. empty list is passed to the method
//          The total order value should be 0
//
    @Test
    public void when_no_item_is_added_in_the_order_then_the_total_order_value_should_be_0() {
        List<String> itemsInOrder = new ArrayList<String>();
        int totalOrderValue = restaurant.getTotalOrderValue(itemsInOrder);
        assertEquals(0, totalOrderValue);
    }


//    when 1 item ("Sweet corn soup") is added for the order,
//          The total order value should be 119 (as per the restaurant's menu)
//              then remove the item to get the total order value as 0.qq1
//
    @Test
    public void when_Sweet_corn_soup_item_is_added_in_the_order_then_the_total_order_value_should_be_119_and_on_removal_should_be_0() {
        List<String> itemsInOrder = new ArrayList<String>();
        itemsInOrder.add("Sweet corn soup");
        int totalOrderValue = restaurant.getTotalOrderValue(itemsInOrder);
        assertEquals(119, totalOrderValue);


        itemsInOrder.remove("Sweet corn soup");
        totalOrderValue = restaurant.getTotalOrderValue(itemsInOrder);
        assertEquals(0, totalOrderValue);
    }



    //<<<<<<<<<<<<<<<<<<<<<<<TOTAL ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}