package com.xxjqr.aldfq.schedule;

import com.xxjqr.aldfq.controller.MainController;
import com.xxjqr.aldfq.po.ServeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.xxjqr.aldfq.service.MainService;

import javax.annotation.Resource;
import java.util.List;

@Component
@Configurable
@EnableScheduling
public class MainSchedule {

    @Resource
    private MainService mainService;

    @Autowired
    private MainController mainController;

    @Scheduled(cron = "* * * * * ?")
    public void test(){
        List<ServeItem> items =  mainService.getServeItems();
        if (MainService.items == null){
            MainService.items = items;
            if (items.size()>0){
                mainService.writeJsonFile(items);
                mainController.initItems(items);
            }
        }else if (items.size()>0 && MainService.items.size()>0){
            if (!items.get(0).getPassword().equals(MainService.items.get(0).getPassword())){
                mainService.writeJsonFile(items);
            }
        }
    }

}
