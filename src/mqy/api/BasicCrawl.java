package mqy.api;

import java.io.IOException;

/**
 * Created by maoqiyun on 16/6/1.
 */
public class BasicCrawl {
//    static String startURL = "http://www.baidu.com";
//
//    static Queue<String> taskQueue = new ConcurrentLinkedQueue<>();
//
//    public static void main(String[] args) throws IOException {
//        init();
//    }
//
//    private static void init() throws IOException {
//        taskQueue.add(startURL);
//        startTasks();
//    }

    /**
     * 开始任务，任务不断的从任务队列中拿到接下来需要采集的URL
     * @throws IOException
     */
//    private static void startTasks() throws IOException {
//        String url = null;
//        while((url = taskQueue.poll())!=null){
//            String content = Jsoup.connect(url).get().html();
//            System.out.println("DownLoaded url:"+url);
//            parse(content);
//            System.out.println("parsed url:"+url);
//        }
//    }
//    private static void parse(String htmlContent){
//        Document doc = Jsoup.parse(htmlContent);
//        //得到需要继续采集的URL，一般是写规则，我为了简单就直接获得所有的URL
//        Elements allATag = doc.select("a");
//        for(Element ele:allATag){
//            taskQueue.add(ele.absUrl("href"));
//        }
//        //接下来是处理网页，我简单就是自己获得title输出
//        String title = doc.title();
//        System.out.println("The title is:"+title);
//    }


}
