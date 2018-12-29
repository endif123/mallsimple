package enums;

public enum ProductStateEnum {

	CHECK(0, "无"), OFFLINE(-1, "非法操作"), SUCCESS(1, "操作成功"), PASS(2, "通过认证"), INNER_ERROR(-1001,
			"操作失败"), NULL_SHOPID(-1002, "Id为空"), NULL_SHOP_INFO(-1003, "传入了空的信息");

	private int state;

	private String stateInfo;

	// 私有，当做常量
	private ProductStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}
	// 不能要
	// public void setState(int state) {
	// this.state = state;
	// }

	public String getStateInfo() {
		return stateInfo;
	}
	// 不能要
	// public void setStateInfo(String stateInfo) {
	// this.stateInfo = stateInfo;
	// }

	public static ProductStateEnum stateOf(int index) {
		for (ProductStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
