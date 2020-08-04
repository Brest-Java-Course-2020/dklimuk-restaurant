package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Item;
import org.springframework.data.domain.Page;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * The interface provides access to service methods.
 */
public interface ItemService {

    /**
     * Find all items.
     *
     * @return item list.
     */
    List<Item> findAllItem();



    List<Item> findAllItemPage(int pageNumber, int pageSize);
    /**
     * Find item by Id.
     *
     * @param itemId item Id.
     * @return item.
     */
    Optional<Item> findItemById(Integer itemId);

    Integer getItemTotalPages(int pageNumber, int pageSize);
    /**
     * Creates new item.
     *
     * @param item item.
     * @return created item id.
     */
    Integer createItem(Item item);

    /**
     * Generate items with fake data
     *
     * @param quantity
     * @return
     */
    Integer generateItem(int quantity, String language);

    /**
     * Update item.
     *
     * @param item item.
     * @return number of updated records in the database.
     */
    int updateItem(Item item);

    /**
     * Delete item.
     *
     * @param itemId item Id.
     * @return the number of rows affected.
     */
    int deleteItem(Integer itemId);

    /**
     * Delete all items
     */
    void deleteAllItems();

}
