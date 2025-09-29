package in.nikita.service;

import java.util.List;

import in.nikita.entity.Cafe_Categories;

public interface Cafe_CategoriesService {
		Cafe_Categories saveCategories(Cafe_Categories categories);
		List<Cafe_Categories>getAllCategories();
		Cafe_Categories getCategoryById(int categoryId);
}
