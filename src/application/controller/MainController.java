package application.controller;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.po.Jason;
import application.po.ServeItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainController implements Initializable{

    private List<ServeItem> items;
    private String curPath;
    private Jason jason;
    private ObjectMapper objectMapper = new ObjectMapper();

    @FXML
    private ComboBox serveCbx;
    @FXML
    private TextField ipText;
    @FXML
    private TextField portText;
    @FXML
    private TextField passwdText;
    @FXML
    private TextField methodText;

	@Override
	public void initialize(URL location, ResourceBundle resources){
		// TODO Auto-generated method stub

        //得到服务数据
        items = getServeItems();

        //判断是否有数据
        if (items==null || items.size()<=0)
            return;

        //得出当前路径
        curPath = System.getProperty("user.dir");

        //创建jason对象
        jason = new Jason(items);

        //填充列表项目
        for (ServeItem item:items){
            serveCbx.getItems().add(item.getServer());
        }

        //默认选中第一个
        serveCbx.setValue(items.get(0).getServer());
        try {
            selectItem(items.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //监听
        serveCbx.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    selectItem(getItemByVL((String) newValue));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
	}

	private void excuteProgram() throws IOException, InterruptedException {
	    //先关闭应用
        String cmd = "taskkill /f /im Shadowsocks.exe";

        Runtime rt = Runtime.getRuntime();
        rt.exec(cmd);

        Thread.currentThread().sleep(500);  //毫秒
        rt.exec(curPath+"/Shadowsocks.exe");
    }

	private void writeJsonFile(){
        try {
            Files.write(Paths.get(curPath+"/gui-config.json"), objectMapper.writeValueAsString(jason).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private ServeItem getItemByVL(String value){
        for (ServeItem item:items){
            if (item.getServer().equals(value))
                return item;
        }
        return null;
    }

    private void selectItem(ServeItem item) throws IOException, InterruptedException {
	    ipText.setText(item.getServer());
	    portText.setText(String.valueOf(item.getServer_port()));
        passwdText.setText(item.getPassword());
        methodText.setText(item.getMethod());

        //选择项目
        jason.setIndex(item.getIndex());
        writeJsonFile();

        //启动项目
        excuteProgram();
    }

	private List<ServeItem> getServeItems (){
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
                break;;
        }
        return items;
    }


}
