package com.xxjqr.aldfq.controller;
import java.io.*;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;


import com.xxjqr.aldfq.po.ServeItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.xxjqr.aldfq.service.MainService;

import javax.annotation.Resource;

@Component
@Scope("singleton")
public class MainController implements Initializable{

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

    @Resource
    private MainService mainService;

	public void initialize(URL location, ResourceBundle resources){
		// TODO Auto-generated method stub

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


	private ServeItem getItemByVL(String value){
        for (ServeItem item:MainService.items){
            if (item.getServer().equals(value))
                return item;
        }
        return null;
    }

    /**
     * 选择项目时
     * @param item
     * @throws IOException
     * @throws InterruptedException
     */
    private void selectItem(ServeItem item) throws IOException, InterruptedException {
	    ipText.setText(item.getServer());
	    portText.setText(String.valueOf(item.getServer_port()));
        passwdText.setText(item.getPassword());
        methodText.setText(item.getMethod());

        //选择项目
        MainService.jsonFile.setIndex(item.getIndex());
        mainService.writeJsonFile(null);

        //启动项目
        mainService.excuteProgram();
    }


    public void initItems(List<ServeItem> items){
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
    }

}
