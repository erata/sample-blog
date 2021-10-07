package blog.controller;

import blog.BlogApplication;
import blog.service.CategoryService;
import blog.service.dto.CategoryListItemView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(BlogApplication.class);
    private final CategoryService categoryService;

    public CategoryController(CategoryService service) {
        this.categoryService = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoryListItemView>> getAllCategories() {
        List<CategoryListItemView> categories = categoryService.getAllCategories();

        logger.debug("getAllCategories(): Found " + categories.size() + " Categories");
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}