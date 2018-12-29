package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CategoryDao;
import entity.Category;
import service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao category;

	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		return category.selectAllCategory();
	}

}
