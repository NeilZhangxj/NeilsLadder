import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ZhangXiaoJie on 2016/4/18.
 */
public class ShadowSocksJob implements Job {
//    private static Logger logger = Logger.getLogger(ShadowSocksJob.class);
    private static SchedulerFactory sf = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "group1";
    private static String TRIGGER_GROUP_NAME = "trigger1";

    public static void main(String[] args) throws Exception {
        while(!inInternet()){
            Thread.sleep(60000);
        }

        exeJob(); //启动时执行一次

        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String startedTime = DateFormat.format(d);
        ShadowSocksJob job = new ShadowSocksJob();

        System.out.println(startedTime + "【系统启动】");
        addJob("EpicJob", job, "0 1 6/6 * * ?");

    }

    public static void addJob(String jobName,Job job,String time)
            throws SchedulerException, ParseException {
        Scheduler scheduler = sf.getScheduler();
        JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job.getClass());
        CronTrigger  trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);
        trigger.setCronExpression(time);
        scheduler.scheduleJob(jobDetail,trigger);
        //启动
        if(!scheduler.isShutdown())
            scheduler.start();
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        try {
            exeJob();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exeJob() throws IOException{
        Map<String, Object> data = spiderMan();

        String cmd = "cmd /c del e:\\shadowsocks\\gui-config.json";
        Runtime.getRuntime().exec(cmd);

        Gson s = new Gson();
        String fileName = "e:\\shadowsocks\\gui-config.json";

        File file = new File(fileName);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(s.toJson(data));
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().exec("taskkill /F /IM Shadowsocks.exe");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().exec("e:\\shadowsocks\\Shadowsocks.exe");
    }

    public static Map<String, Object > spiderMan() throws IOException {
        Document doc = Jsoup.connect("http://www.ishadowsocks.net").get();
        Elements targetElements = doc.select("div.col-lg-4.text-center");
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> data = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            Elements e = targetElements.get(i).children();
            String status = e.get(4).text().substring(3);
            if (status.equals("正常")){
                data.put("server", e.get(0).text().substring(7));
                data.put("server_port", e.get(1).text().substring(3));
                data.put("password", e.get(2).text().substring(4));
                data.put("method", e.get(3).text().substring(5));
                data.put("remarks", e.get(2).text().substring(4));
                break;
            }
        }
        list.add(data);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("configs", list);
        returnMap.put("strategy", null);
        returnMap.put("index", 0);
        returnMap.put("global", false);
        returnMap.put("enabled", true);
        returnMap.put("shareOverLan", false);
        returnMap.put("isDefault", false);
        returnMap.put("pacUrl", null);
        returnMap.put("localPort", 1080);
        returnMap.put("useOnlinePac", false);
        returnMap.put("availabilityStatistics", false);

        return returnMap;
    }

    public static boolean inInternet(){
        try {
            Jsoup.connect("www.baidu.com");
            return true;
        } catch (Exception e){
            System.out.println("网络连接失败");
            return false;
        }
    }
}
