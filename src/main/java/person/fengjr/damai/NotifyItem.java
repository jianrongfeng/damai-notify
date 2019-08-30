package person.fengjr.damai;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * @author fengjr
 * @date 2019/8/30.
 */
public class NotifyItem {
    private final String notifyListenUrl = "https://detail.damai.cn/item.htm?id={0}";

    /**
     * 检查是否开售
     * @param itemId 项目id 进入某个项目详情,其<code>url</code>参数中的<code>id</code>字段
     * @return 是否开售
     */
    public boolean check(String itemId) throws IOException {
        final String itemUrl = notifyListenUrl.replace("{0}", itemId);
        final Document htmlResult = Jsoup.connect(itemUrl).get();
        final Element element = htmlResult.selectFirst("#app").nextElementSibling();

        /*
        <div id="error" style="display: none">
         {"errorCode":"2000","error":"ERROR"}
        </div>
         */
        return "dataDefault".equals(element.attr("id"));
    }
}
