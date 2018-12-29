package dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import basetest.BaseTest;
import entity.Category;

public class CategoryDaoTest extends BaseTest{
	
	@Autowired
	private CategoryDao CategoryDao;
	
	@Test
	@Ignore
	public void testSelectById() throws Exception {
		Category list = CategoryDao.selectByPrimaryKey(1);
		
		assertEquals("食品", list.getCategoryName());
		
	}
	
	@Test
	
	public void testSelectAll() throws Exception {
		List<Category> list = CategoryDao.selectAllCategory();
		
		assertEquals("日用品", list.get(1).getCategoryName());
		
	}

}
