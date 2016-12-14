package org.elevenfifty.shoppinglist.repositories;

import java.util.ArrayList;


import org.elevenfifty.shoppinglist.beans.ShoppingList;
import org.elevenfifty.shoppinglist.beans.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends CrudRepository<ShoppingList, Long> {

	ArrayList<ShoppingList> findAllByUser(User u);

	ArrayList<ShoppingList> findByCategoryContainsOrNameContainsAllIgnoreCase(String categoryPart, String namePart);


}
