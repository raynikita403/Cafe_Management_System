package in.nikita.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nikita.entity.Cafe_Categories;
import in.nikita.repository.Cafe_CategoriesRepository;
@Service
public class Cafe_CategoriesServiceImpli implements Cafe_CategoriesService{
	@Autowired 
	Cafe_CategoriesRepository repo;
	@Override
	public Cafe_Categories saveCategories(Cafe_Categories categories) {
		return repo.save(categories);
	}

	@Override
	public List<Cafe_Categories> getAllCategories() {
		return repo.findAll();
	}

	@Override
	public Cafe_Categories getCategoryById(int categoryId) {
		return repo.getById(categoryId);
	}

}
