package service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import basetest.BaseTest;
import dto.ImageHolder;
import dto.ProductExecution;
import entity.Product;
import entity.User;

@SuppressWarnings("unused")
public class TestProductService extends BaseTest{
	
	@Autowired
	private ProductService prodectService;
	
	@Test
	public void testgetBase64ByUrl() {
		Product product = prodectService.selectByPrimaryKey(2);
		String base64 = prodectService.getBase64ByUrl(product);
		System.out.println(11111);
	}
	
	
	@Test
	@Ignore
	public void testQueryProductCondition() throws Exception {
		//User user = new User();
		//user.setId(5);
		
//		Category cate = new Category();
//		cate.setCategoryId(2);
		Product productCondition = new Product();
//		
//		//productCondition.setUser(user);
//		productCondition.setCategory(cate);
		productCondition.setProductName("");
		ProductExecution se = prodectService.getProductList(productCondition, 1, 2);
		List<Product> list = se.getProductList();
		System.out.println(list.get(0).getProductName());
		System.out.println(list.get(1).getProductName());
		//System.out.println(list.get(2).getProductName());
		System.out.println(list.size());
		
		
	}
	
	@Test
	@Ignore
	public void testSelectById()  {
		Product product = prodectService.selectByPrimaryKey(23);
		assertEquals("同仁堂", product.getUser().getName());
		
	}
	
	@Test
	@Ignore
	public void testModifyPro()  {
		Product product = new  Product();
		product.setProductName("怀地黄");
		product.setPrice((float) 330);
		//product.setProductId(1);
		//CommonsMultipartFile productImg = null;
		product.setProductId(11);
		
		File test = new File("C:\\Users\\fang\\Pictures\\123.jpg");
		
		
		//File test = new File("D:\\cmbCard\\imag\\tom.jpg");
		InputStream is = null;
		try {
			is = new FileInputStream(test);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageHolder imageHolder = new ImageHolder(is,"123.jpg");
		ProductExecution se =  prodectService.updateProduct(product, imageHolder);
		
		assertEquals("菊花", se.getProduct().getProductName());
		
	}
	
	
	@Test
	@Ignore
	public void testAddPro()  {
		Product product = new  Product();
		product.setProductName("怀牛膝");
		product.setPrice((float) 99);
		
		//CommonsMultipartFile productImg = null;
		User user = new User();
		user.setId(5);
		product.setUser(user);
		File test = new File("D:\\cmbCard\\imag\\tom.jpg");
		InputStream is = null;
		try {
			is = new FileInputStream(test);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageHolder imageHolder = new ImageHolder(is,"123.jpg");
		ProductExecution se =  prodectService.addProduct(product, imageHolder);
		
		assertEquals("怀牛膝", se.getProduct().getProductName());
		
	}
	
	
//	private static CommonsMultipartFile getMulFileByPath(String picPath) {
//		FileItem fileItem = createFileItem(picPath);
//		CommonsMultipartFile mfile = new CommonsMultipartFile(fileItem);
//		return mfile;
//	}
	
//	private static FileItem createFileItem(String filePath)
//    {
//        FileItemFactory factory = new DiskFileItemFactory(16, null);
//        String textFieldName = "textField";
//        int num = filePath.lastIndexOf(".");
//        String extFile = filePath.substring(num);
//        FileItem item = factory.createItem(textFieldName, "text/plain", true,
//            "MyFileName" + extFile);
//        File newfile = new File(filePath);
//        int bytesRead = 0;
//        byte[] buffer = new byte[8192];
//        try
//        {
//            FileInputStream fis = new FileInputStream(newfile);
//            OutputStream os = item.getOutputStream();
//            while ((bytesRead = fis.read(buffer, 0, 8192))
//                != -1)
//            {
//                os.write(buffer, 0, bytesRead);
//            }
//            os.close();
//            fis.close();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        return item;
//    }


}
