import com.inventory.dao.DatabaseConnection;
import com.inventory.ui.Menu;

public class Main {

    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();

        Menu menu = new Menu();
        menu.start();
    }
}
