package org.elevenfifty.shoppinglist.repositories;

import java.util.ArrayList;


import org.elevenfifty.shoppinglist.beans.ShoppingListItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListItemRepository extends CrudRepository<ShoppingListItem, Long> {

	ArrayList<ShoppingListItem> findByShoppingListId(long shoppingListId);

	ArrayList<ShoppingListItem> findByShoppingListIdOrderByNameAsc(long shoppingListId);

	ArrayList<ShoppingListItem> findByShoppingListIdOrderByNameDesc(long shoppingListId);

	ArrayList<ShoppingListItem> findByShoppingListIdOrderByShoppingListItemPriorityIdDesc(long shoppingListId);

	

}
