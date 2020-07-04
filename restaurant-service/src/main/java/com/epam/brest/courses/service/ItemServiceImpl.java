package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.ItemDao;
import com.epam.brest.courses.model.Item;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * A class that defines a set of operations
 * with an item model.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

    /**
     * A item data access object.
     */
    private final ItemDao itemDao;

    Faker enFaker = new Faker(new Locale("en_US"));
    Faker ruFaker = new Faker(new Locale("ru_RU"));

    /**
     * Constructor accepts dao layer object.
     * @param itemDao
     */
    public ItemServiceImpl(ItemDao itemDao) {
        this.itemDao = itemDao;
    }


    /**
     * Find all items.
     *
     * @return
     */
    @Override
    public List<Item> findAllItem() {
        return itemDao.findAll();
    }

    /**
     * Find item by Id.
     *
     * @param itemId item id.
     * @return item.
     */
    @Override
    public Optional<Item> findItemById(Integer itemId) {
        return itemDao.findById(itemId);
    }

    /**
     * Creates new item.
     *
     * @param item item.
     * @return created item id.
     */
    @Override
    public Integer createItem(Item item) {
        return itemDao.create(item);
    }

    /**
     * Generate items
     *
     * @param number
     * @param language
     * @return
     */
    @Override
    public Integer generateItem(int number, String language) {

        itemDao.deleteAllItems();
        Item item = new Item();

        for(int i = 0; i < number; i++){
            try {
                switch (language){
                    case "EN": item.setItemName(enFaker.name().firstName()); break;
                    case "RU": item.setItemName(ruFaker.name().firstName()); break;
                    default: item.setItemName(enFaker.name().firstName()); break;
                }

                LOGGER.info("number: ({})", number);
                LOGGER.info("hi: ({})", i);
                //todo
                item.setItemPrice(new BigDecimal(100));
                itemDao.create(item);
            } catch (Exception e){
                i--;
            }
        }
        return 1;
    }

    /**
     * Update item.
     *
     * @param item item.
     * @return number of updated records in the database.
     */
    @Override
    public int updateItem(Item item) {
        return itemDao.update(item);
    }

    /**
     * Delete item.
     *
     * @param itemId item Id.
     * @return the number of rows affected.
     */
    @Override
    public int deleteItem(Integer itemId) {
        return itemDao.delete(itemId);
    }

    @Override
    public void deleteAllItems() {
         itemDao.deleteAllItems();
    }
}
