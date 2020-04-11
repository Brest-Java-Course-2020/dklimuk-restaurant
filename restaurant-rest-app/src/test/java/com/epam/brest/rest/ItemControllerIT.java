package com.epam.brest.rest;

import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.rest.ItemController;
import com.epam.brest.courses.rest.exception.CustomExceptionHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.ItemConstants.ITEM_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class ItemControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemControllerIT.class);

    public static final String ITEMS_ENDPOINT = "/items";
    private final BigDecimal ITEM_PRICE = new BigDecimal(100);

    @Autowired
    private ItemController itemController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcItemService itemService = new MockMvcItemService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldFindAllItems() throws Exception {

        List<Item> departments = itemService.findAll();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
    }

    @Test
    public void shouldFindItemById() throws Exception {

        // given
        Item item = new Item()
                .setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(ITEM_PRICE);
        Integer id = itemService.create(item);

        // when
        Optional<Item> optionalItem = itemService.findById(id);

        // then
        assertTrue(optionalItem.isPresent());
        assertEquals(optionalItem.get().getItemId(), id);
        assertEquals(optionalItem.get().getItemName(), item.getItemName());
        assertEquals(optionalItem.get().getItemPrice(), item.getItemPrice());
    }

    @Test
    public void shouldCreateItem() throws Exception {
        Item item = new Item()
                .setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(ITEM_PRICE);
        Integer id = itemService.create(item);
        assertNotNull(id);
    }

    @Test
    public void shouldUpdateItem() throws Exception {

        // given
        Item item = new Item()
                .setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(ITEM_PRICE);
        Integer id = itemService.create(item);
        assertNotNull(id);

        Optional<Item> itemOptional = itemService.findById(id);
        assertTrue(itemOptional.isPresent());

        itemOptional.get().
                setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE));

        // when
        int result = itemService.update(itemOptional.get());

        // then
        assertTrue(1 == result);

        Optional<Item> updatedItemOptional = itemService.findById(id);
        assertTrue(updatedItemOptional.isPresent());
        assertEquals(updatedItemOptional.get().getItemId(), id);
        assertEquals(updatedItemOptional.get().getItemName(),itemOptional.get().getItemName());
        assertEquals(updatedItemOptional.get().getItemPrice(), itemOptional.get().getItemPrice());

    }

    @Test
    public void shouldDeleteItem() throws Exception {
        // given
        Item item = new Item()
                .setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(ITEM_PRICE);
        Integer id = itemService.create(item);

        List<Item> items = itemService.findAll();
        assertNotNull(item);

        // when
        int result = itemService.delete(id);

        // then
        assertTrue(1 == result);

        List<Item> currentItem = itemService.findAll();
        assertNotNull(currentItem);

        assertTrue(items.size()-1 == currentItem.size());
    }

    class MockMvcItemService {

    public List<Item> findAll() throws Exception {
        LOGGER.debug("findAll()");
        MockHttpServletResponse response = mockMvc.perform(get(ITEMS_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn().getResponse();
        assertNotNull(response);

        return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Item>>() {});
        }

        public Optional<Item> findById(Integer itemId) {
            return Optional.empty();
        }

        public Integer create(Item item) throws Exception {
            LOGGER.debug("create({})", item);
            String json = objectMapper.writeValueAsString(item);
            MockHttpServletResponse response =
                    mockMvc.perform(post(ITEMS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public int update(Item item) {
            return 0;
        }

        public int delete(Integer itemId) {
            return 0;
        }
    }

}
