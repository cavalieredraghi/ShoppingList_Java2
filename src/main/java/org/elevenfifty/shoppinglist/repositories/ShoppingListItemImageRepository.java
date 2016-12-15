package org.elevenfifty.shoppinglist.repositories;

import org.elevenfifty.shoppinglist.beans.ShoppingListItemImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListItemImageRepository extends CrudRepository<ShoppingListItemImage, Long>{
 ShoppingListItemImage findByShoppingListItemId(long id);
}
