package person.fengjr.damai;

/**
 * @author fengjr
 * @date 2019/8/30.
 */
public interface INotifyGuy {

    /**
     * 给指定账号发送通知
     * @param guyAccount 多个账号
     * @param content 通知内容
     * @param serviceDto 业务对象, 不使用则不传
     */
    void notice(String[] guyAccount, String content, Object serviceDto);
}
