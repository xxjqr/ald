
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.xxjqr.aldfq.service.MainService;

import java.io.IOException;


public class Main extends Application{

    public void start(Stage primaryStage) throws Exception {
//        String test = getClass().getResource("main.myScene.fxml").getPath();
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        ApplicationContext ctx= new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    public static void main(String[] args) throws IOException {
        //得出当前路径
        MainService.curPath = System.getProperty("user.dir");
        launch(args);
    }
}
