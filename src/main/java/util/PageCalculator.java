package util;

public class PageCalculator {
	public static int calculatePageCount(int totalCount, int pageSize) {
		int idealPage = totalCount / pageSize;
		int totalPage = (totalCount % pageSize == 0) ? idealPage
				: (idealPage + 1);
		return totalPage;
	}
/**
 *   把前端的页数换成，首页，末页，的值
 * @param pageIndex 要第几页
 * @param pageSize  一页几个啊
 * @return
 */
	public static int calculateRowIndex(int pageIndex, int pageSize) {
		return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
	}
}
