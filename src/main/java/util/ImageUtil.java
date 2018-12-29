package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

//import org.springframework.web.multipart.commons.CommonsMultipartFile;

//import ch.qos.logback.core.util.FileUtil;
import dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings({ "unused", "restriction" })
public class ImageUtil {
//	public static void main(String[] args) {
//		String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//		Thumbnails.of(new File("D:\\eclipse_eee\\work\\image\\stone.jpg")).size(200,200).watermark(Positions.BOTTOM_RIGHT,arg1,arg2);
//	}
	//当前线程的路径
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	//时间格式
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss"); // 时间格式化的格式
	//生成的随机数
	private static final Random r = new Random();
	
	/**
	 * //缩略图的获取程序
	 * @param thumbnail spring的文件流格式文件
	 * @param targetAddr 目标地址
	 * @return 生成的图片的地址
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		//========新文件的名字
		//先搞一个随机名
		String realFileName = getRandomFileName();
		//拓展名
		String extension = getFileExtension(thumbnail.getImageName());
		//创建目录
		makeDirPath(targetAddr);
		//相对路径
		String relativeAddr = targetAddr + realFileName + extension;
		//============名字搞定
		
		//这是我们新生成的文件
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		//=============开始搞水印图片
		try {
			Thumbnails.of(thumbnail.getImage()).size(200, 200)          //先搞到图片
			.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/watermarker2.png")),0.25f)  //再加水印
			.outputQuality(0.8f).toFile(dest);   //再压缩储存
		} catch (IOException e) {//总搞水印图片的
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}
	
	public static String getRandomFileName() {
		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
		String nowTimeStr = sDateFormat.format(new Date()); // 当前时间
		return nowTimeStr + rannum;
	}
	//拓展名的获取
	private static String getFileExtension(String fileName) {
		//String originalFileName = cFile.getOriginalFilename();
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	//创建目标路径所涉及到的目录
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			//创建一路上的文件夹
			dirPath.mkdirs();
		}
	}
	//删除目录下的文件，或者删除路径下的所有文件
	public static void deleteFile(String storePath) {
		File file = new File(PathUtil.getImgBasePath() + storePath);//获取全部路径
		if (file.exists()) {
			if (file.isDirectory()) {               //是目录，就删掉所有文件
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			file.delete();                        //是文件，删掉
		}
	}
	
	/**
	 * @Title: GetImageStrFromPath
	 * @Description: TODO(将一张本地图片转化成Base64字符串)
	 * @param imgPath
	 * @return
	 */
	
	public static String GetImageStrFromPath(String imgPath) {
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgPath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		// 返回Base64编码过的字节数组字符串
		return encoder.encode(data);
	}


}
