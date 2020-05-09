package com.pycogroup.pizza.product.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.pycogroup.pizza.product.common.ErrorResponseBody;
import com.pycogroup.pizza.product.common.GenericResponse;
import com.pycogroup.pizza.product.common.GenericResponseCollection;
import com.pycogroup.pizza.product.model.AdditionalOption;
import com.pycogroup.pizza.product.model.Category;
import com.pycogroup.pizza.product.model.Option;
import com.pycogroup.pizza.product.model.Pricing;
import com.pycogroup.pizza.product.model.Product;
import com.pycogroup.pizza.product.model.Size;
import com.pycogroup.pizza.product.repository.ProductRepository;

@SpringBootTest
public class ProductServiceTest {


  @Autowired
  ProductService productService;
  
  @Autowired
  ProductRepository productRepository;

  @BeforeEach
  public void init() {
    List<Size> size = new ArrayList<Size>();
    size.add(Size.SMALL);
    size.add(Size.MEDIUM);
    size.add(Size.LARGE);
    List<Integer> price = new ArrayList<Integer>();
    price.add(100000);
    price.add(200000);
    price.add(250000);
    List<Option> option = new ArrayList<Option>();
    option.add(Option.NONE);
    option.add(Option.EXTRA_CHEESE);
    List<Integer> optionPrice = new ArrayList<Integer>();
    optionPrice.add(0);
    optionPrice.add(40000);
    String servingSizes = new String("SMALL: 3 people, MEDIUM: 4 people, LARGE: 5 people");
    productRepository.save(Product.builder()
                                 .name("Octopus Pizza")
                                 .description("Good for health")
                                 .imageURL("https://goodle.com/octopus")
                                 .pricing(new Pricing(size,price,0)) 
                                 .additionalOption(new AdditionalOption(option,optionPrice,0))
                                 .servingSize(servingSizes)
                                 .category(Category.PIZZA)
                                 .build());
   
    option.add(Option.DOUBLE_CHEESE);
    optionPrice.add(90000);
    productRepository.save(Product.builder()
                                 .name("Shrim Pizza")
                                 .description("Good for health")
                                 .imageURL("https://goodle.com/octopus")
                                 .pricing(new Pricing(size,price,0)) 
                                 .additionalOption(new AdditionalOption(option,optionPrice,0))
                                 .servingSize(servingSizes)
                                 .category(Category.PIZZA)
                                 .build());
  }
  
  @AfterEach
  public void cleaning() {
    productRepository.deleteAll();
  }

  @DisplayName("getProductById test function")
  @Test
  void getProductByIdTest() {

    //given
    List<Size> size = new ArrayList<Size>();
    size.add(Size.SMALL);
    size.add(Size.MEDIUM);
    List<Integer> price = new ArrayList<Integer>();
    price.add(100000);
    price.add(130000);
    List<Option> option = new ArrayList<Option>();
    option.add(Option.NONE);
    option.add(Option.EXTRA_CHEESE);
    List<Integer> optionPrice = new ArrayList<Integer>();
    optionPrice.add(0);
    optionPrice.add(40000);
    Product product = productRepository.save(Product.builder()
                                 .name("Fish Pizza")
                                 .description("Good for health")
                                 .imageURL("https://goodle.com/octopus")
                                 .pricing(new Pricing(size,price,0)) 
                                 .additionalOption(new AdditionalOption(option,optionPrice,0))
                                 .category(Category.PIZZA)
                                 .build());
    Product createdProduct = productRepository.save(product);

    //when
    GenericResponse success = productService.getProductById(createdProduct.getId());
    GenericResponse error = productService.getProductById("5eaa7c73a29b0b777bd8b7e3");
    Object objectSuccess = success.getData();
    Object objectError = error.getData();

    //then
    Assertions.assertTrue(objectSuccess instanceof Product);
    Assertions.assertEquals(((Product)objectSuccess).getName(),"Fish Pizza");
    Assertions.assertTrue(objectError instanceof ErrorResponseBody);
    if (objectError instanceof ErrorResponseBody) {
      ErrorResponseBody erb = (ErrorResponseBody) objectError;
      Assertions.assertEquals(erb.getCode(),HttpStatus.NOT_FOUND.value());
      Assertions.assertEquals(erb.getStatus(),HttpStatus.NOT_FOUND);
      Assertions.assertTrue(erb.getWhere() instanceof LinkedHashMap<?,?>);
    }
    
  }

  @DisplayName("getProductCardsByCategoryWithPagination test function")
  @Test
  void getProductCardsByCategoryWithPagination() {

    //given
    GenericResponseCollection success = productService.getProductCardsByCategoryWithPagination(Category.PIZZA,2,1);
    GenericResponseCollection empty1 = productService.getProductCardsByCategoryWithPagination(Category.DRINK,2,1);
    GenericResponseCollection empty2 = productService.getProductCardsByCategoryWithPagination(Category.DRINK,2,4);

    //when
    Object objectSuccess = success.getData();
    Object objectEmpty1 = empty1.getData();
    Object objectEmpty2 = empty2.getData();

    //then
    Assertions.assertTrue(objectSuccess instanceof ArrayList<?>);
    Assertions.assertTrue(objectEmpty1 instanceof ArrayList<?>);
    Assertions.assertTrue(objectEmpty2 instanceof ArrayList<?>);
    if (objectSuccess instanceof ArrayList<?>) {
      Assertions.assertEquals(((ArrayList<?>)objectSuccess).size(),2);
      Assertions.assertTrue(((ArrayList<?>)objectEmpty1).isEmpty());
      Assertions.assertTrue(((ArrayList<?>)objectEmpty2).isEmpty());
    }
  }

  @DisplayName("getProductCardsWithPagination test function")
  @Test
  void getAllProductCardsWithPagination() {

    //given
    GenericResponseCollection success = productService.getAllProductCardsWithPagination(2,1);
    GenericResponseCollection empty1 = productService.getAllProductCardsWithPagination(2,2);

    //when
    Object objectSuccess = success.getData();
    Object objectEmpty1 = empty1.getData();

    //then
    Assertions.assertTrue(objectSuccess instanceof ArrayList<?>);
    Assertions.assertTrue(objectEmpty1 instanceof ArrayList<?>);
    if (objectSuccess instanceof ArrayList<?>) {
      Assertions.assertEquals(((ArrayList<?>)objectSuccess).size(),2);
      Assertions.assertTrue(((ArrayList<?>)objectEmpty1).isEmpty());
    }
  }
}
