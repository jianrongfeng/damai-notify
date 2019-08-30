package person.fengjr.damai;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

/**
 * @author fengjr
 * @date 2019/8/30.
 */
public class Application {
    public static void main(String[] args) throws Exception {
        final NotifyItem notifyItem = new NotifyItem();
        INotifyGuy notifyGuy = new NotifyGuyEmailImpl();
        String itemId = Optional.ofNullable(System.getProperty("item")).orElse("179108");

        while(true){
            String nowFormat = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
            if (notifyItem.check(itemId)){
                notifyGuy.notice(new String[]{"fengjr94@outlook.com"}, "您关注的演出已开放售票", null);
                System.out.println(String.format("time:%s,item:%s,state：已开放售票",nowFormat, itemId));
                break;
            } else {
                System.out.println(String.format("time:%s,item:%s,state：未开放",nowFormat, itemId));
                final Random random = new Random();
                Thread.sleep((3+random.nextInt(6)) * 1000);
            }
        }
    }

}
