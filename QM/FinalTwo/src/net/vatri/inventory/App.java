package net.vatri.inventory;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.vatri.inventory.libs.FxPage;
import net.vatri.inventory.libs.FxPageSwitcher;
import net.vatri.inventory.libs.FxView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class App extends Application {

    private static BorderPane mainPane = new BorderPane();
    private static Parent mainMenu;
    private static Map<String, String> _config;
    /**
     * Variable for the singleton pattern...
     **/
    private static App instance = null;

    static {
        _config = new HashMap<String, String>();
        _config.put("db_connection", "jdbc:sqlite:InvMan.sqlite3");
    }

    /**
     * Data repository for exchange between controllers.
     **/
    public Map<String, String> repository = new HashMap<>();
    private FxPageSwitcher pageSwitcher;

    public static void showPage(String page) {
        if (!page.equals("login")) {
            mainMenu.setVisible(true);
        }
        getInstance().pageSwitcher.showPage(page);
    }

    public static String getConfig(String item) {
        return _config.get(item);
    }

    /**
     * Return signleton instance...
     **/
    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        getInstance().pageSwitcher = new FxPageSwitcher((node) -> mainPane.setCenter(node), Arrays.asList(
                new FxPage("login", "LoginView"),
                new FxPage("products", "ProductsView"),
                new FxPage("newProduct", "AddEditProductView"),
                new FxPage("orders", "OrdersView"),
                new FxPage("addEditOrder", "AddEditOrderView")
        ));

        mainMenu = new FxView("Menu").get();
        mainMenu.setVisible(false);

        mainPane.setLeft(mainMenu);

        primaryStage.setScene(new Scene(mainPane, 800, 600));
        primaryStage.setTitle("Inventory Management");
        primaryStage.show();

        getInstance().pageSwitcher.showPage("login");
    }
}
