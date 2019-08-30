package person.fengjr.damai;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

/**
 * @author fengjr
 * @date 2019/8/30.
 */
public class SmtpSender {

    private Properties props;
    private Session session;
    private MimeMessage msg;

    private String username;
    private String password;

    private String text;
    private String html;

    private SmtpSender(){}

    private SmtpSender(Properties props){this.props = props;}

    public static SmtpSender getInstance(){
        return new SmtpSender(defaultConfig(true));
    }
    public static SmtpSender getInstance(Properties props){
        return new SmtpSender(props);
    }

    public static Properties defaultConfig(Boolean debug) {
        Properties props = new Properties();
        props.put(SmptProperties.AUTH.name, "true");
        props.put(SmptProperties.SSL_ENABLE.name, "true");
        props.put(SmptProperties.PROTOCOL.name, "smtp");
        props.put(SmptProperties.DEBUG.name, null != debug ? debug.toString() : "false");
        props.put(SmptProperties.TIMEOUT.name, "10000");
        props.put(SmptProperties.PORT.name, "465");
        return props;
    }
    public SmtpSender config(String host, String username, String password) {
        props.put(SmptProperties.HOST.name, host);
        this.username = username;
        this.password = password;
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        return this;
    }

    /**
     * 设置主题
     * @param subject 主题内容
     */
    public SmtpSender subject(String subject) throws SendMailException {
        this.msg = new MimeMessage(session);

        try {
            this.msg.setSubject(subject, "UTF-8");
            return this;
        } catch (Exception var3) {
            throw new SendMailException(var3);
        }
    }

    public SmtpSender from(String nickName) throws SendMailException {
        return this.from(nickName, username);
    }

    public SmtpSender from(String nickName, String from) throws SendMailException {
        try {
            String encodeNickName = MimeUtility.encodeText(nickName);
            this.msg.setFrom(new InternetAddress(encodeNickName + " <" + from + ">"));
            return this;
        } catch (Exception var4) {
            throw new SendMailException(var4);
        }
    }

    public SmtpSender to(String... to) throws SendMailException {
        try {
            return this.addRecipients(to, Message.RecipientType.TO);
        } catch (MessagingException var3) {
            throw new SendMailException(var3);
        }
    }

    public SmtpSender to(String to) throws SendMailException {
        try {
            return this.addRecipient(to, Message.RecipientType.TO);
        } catch (MessagingException var3) {
            throw new SendMailException(var3);
        }
    }

    public SmtpSender text(String text) {
        this.text = text;
        return this;
    }

    public SmtpSender html(String html) {
        this.html = html;
        return this;
    }

    private SmtpSender addRecipient(String recipient, Message.RecipientType type) throws MessagingException {
        this.msg.setRecipients(type, InternetAddress.parse(recipient.replace(";", ",")));
        return this;
    }
    private SmtpSender addRecipients(String[] recipients, Message.RecipientType type) throws MessagingException {
        String result = Arrays.asList(recipients).toString().replaceAll("^\\[(.*)\\]$", "$1").replaceAll(" ", "");
        this.msg.setRecipients(type, InternetAddress.parse(result));
        return this;
    }

    public void send() throws SendMailException {
        if (this.text == null && this.html == null) {
            throw new IllegalArgumentException("At least one context has to be provided: Text or Html");
        } else {

            try {
                MimeMultipart cover;
                if (this.html == null) {
                    cover = new MimeMultipart("mixed");
                    cover.addBodyPart(this.textPart());
                } else if (this.text == null) {
                    cover = new MimeMultipart("mixed");
                    cover.addBodyPart(this.htmlPart());
                } else {
                    cover = new MimeMultipart("alternative");
                    cover.addBodyPart(this.textPart());
                    cover.addBodyPart(this.htmlPart());
                }

                MimeMultipart content = cover;
                // 附件在此添加
//                if (usingAlternative && hasAttachments) {
//                    content = new MimeMultipart("mixed");
//                    content.addBodyPart(this.toBodyPart(cover));
//                }
//                while(attachments.hasNext()) {
//                    MimeBodyPart attachment = (MimeBodyPart)attachments.next();
//                    content.addBodyPart(attachment);
//                }

                this.msg.setContent(content);
                this.msg.setSentDate(new Date());
                Transport.send(this.msg);
            } catch (Exception var7) {
                throw new SendMailException(var7);
            }
        }
    }

    private MimeBodyPart toBodyPart(MimeMultipart cover) throws MessagingException {
        MimeBodyPart wrap = new MimeBodyPart();
        wrap.setContent(cover);
        return wrap;
    }

    private MimeBodyPart textPart() throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(this.text);
        return bodyPart;
    }

    private MimeBodyPart htmlPart() throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(this.html, "text/html; charset=utf-8");
        return bodyPart;
    }

    public enum SmptProperties{
        HOST("mail.smtp.host"),
        PORT("mail.smtp.port"),
        AUTH("mail.smtp.auth"),
        SSL_ENABLE("mail.smtp.ssl.enable"),
        PROTOCOL("mail.transport.protocol"),
        DEBUG("mail.debug"),
        TIMEOUT("mail.smtp.timeout");

        private String name;
        SmptProperties(String name){
            this.name = name;
        }
    }
}
