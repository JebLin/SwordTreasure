package indi.sword.util.base.enums;

/**
 * @Description 启用与禁用、是与否、正确与错误
 * @Author:rd_jianbin_lin
 * @Date: 10:31 2017/9/22
 */
public enum StateEnum {

    // positive
    UP(1),

    // negative
    DOWN(0);

    private final int state;

    private StateEnum(int state) {
        this.state = state;
    }

    public int value(){
        return state;
    }

	public static void main(String[] args) {
		System.out.println(StateEnum.UP.value());
	}

}
