package blog.service;

import blog.model.Category;
import blog.repository.CategoryRepository;
import blog.service.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryListItemView> getAllCategories()
    {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryListItemView> categoryListItemViewList = new ArrayList<>();

        categoryList.forEach(category -> {
            int count = (int)category.getPosts().stream().filter(n -> n.getIsPublished()).count();

            if(count > 0) {
                categoryListItemViewList.add(new CategoryListItemView(category.getName(), count));
            }
        });

        return categoryListItemViewList;
    }
}