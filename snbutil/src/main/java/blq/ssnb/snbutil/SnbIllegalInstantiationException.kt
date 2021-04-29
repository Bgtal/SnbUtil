package blq.ssnb.snbutil

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2018/7/13
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 党调用静态类的实例化方法时会提示该错误
 * ================================================
</pre> *
 */
class SnbIllegalInstantiationException : IllegalAccessError {
    constructor() : super("无法实例化,请直接调用静态方法/n Cannot be instantiated. Please call the static method directly") {}
    constructor(msg: String?) : super(msg) {}
}