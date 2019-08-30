package person.fengjr.damai;

/**
 * @author fengjr
 * @date 2019/8/30.
 */
public class NotifyGuyEmailImpl implements INotifyGuy {

    @Override
    public void notice(String[] guyAccount, String content, Object serviceDto) {
        final SmtpSender smtpSender = SmtpSender.getInstance();

        smtpSender.config(
                GlobalConfig.getString("mail.smtp.host"),
                GlobalConfig.getString("mail.smtp.username"),
                GlobalConfig.getString("mail.smtp.password"))
                .subject("测试消息提示--damai")
                .from("damai演出开放检测工具")
                .to(guyAccount)
                .text(content)
                .send();
    }


}
