package com.xxjqr.aldfq.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxjqr.aldfq.po.JsonFile;
import com.xxjqr.aldfq.po.ServeItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class MainService {

    public static JsonFile jsonFile;
    private ObjectMapper objectMapper = new ObjectMapper();
    public static String curPath;
    public static List<ServeItem> items;

    public List<ServeItem> getServeItems (){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://ss.ishadowx.net/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = doc.getElementsByClass("hover-text");
        List<ServeItem> items = new ArrayList<>();
        int i = 0;
        int j = 0;
        for (Element ele:elements){
            Elements h4 = ele.getElementsByTag("h4");
            String server = h4.get(0).getElementsByTag("span").first().html();
            Integer server_port = Integer.valueOf(h4.get(1).html().split("：")[1]);
            String password = h4.get(2).getElementsByTag("span").first().html();
            String method = h4.get(3).html().split(":")[1];
            if (!password.equals("")){
                ServeItem item = new ServeItem(server,server_port,password,method);
                item.setIndex(j);
                items.add(item);
                j++;
            }
            i++;
            if (i>=9)
                break;
        }
        return items;
    }

    public void writeJsonFile(List<ServeItem> items){
        if (items!=null)
            jsonFile = new JsonFile(items);
        try {
            Files.write(Paths.get(curPath+"/gui-config.json"), objectMapper.writeValueAsString(jsonFile).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void excuteProgram() throws IOException, InterruptedException {
        //先关闭应用
        String cmd = "taskkill /f /im Shadowsocks.exe";

        Runtime rt = Runtime.getRuntime();
        rt.exec(cmd);

        Thread.currentThread().sleep(500);  //毫秒
        rt.exec(MainService.curPath+"/Shadowsocks.exe");
    }

}
