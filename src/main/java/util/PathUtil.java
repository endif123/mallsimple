package util;

public class PathUtil {
	//获取分隔符
	private static String seperator = System.getProperty("file.separator");
	//搞到图片根路径
	public static String getImgBasePath() {
		//拿到操作系统名称
		String os = System.getProperty("os.name");
		String basePath = "";
		//如果是win
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/cmbCard/image/";
		} else {
			basePath = "/home/image/";
		}
		//这个是担心分隔符有问题
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	//相对的路径
	public static String getProductImagePath(int productId) {
		
		String imagePath = "upload/item/shop/" + productId + "/";
		return imagePath.replace("/", seperator);
		
//		StringBuilder shopImagePathBuilder = new StringBuilder();
//		shopImagePathBuilder.append("/upload/images/item/shop/");
//		shopImagePathBuilder.append(shopId);
//		shopImagePathBuilder.append("/");
//		String shopImagePath = shopImagePathBuilder.toString().replace("/",
//				seperator);
//		return shopImagePath;
	}
	
}
