package la.archimedes.jse;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 使用自定义类加载器实现动态加载不同版本的驱动包
 * @author la
 */
public class MultiVersionDriver {
    public static void main(String[] args) throws SQLException {
        DriverManager.getConnection("jdbc:mysql:/localhost:8080/my");
    }
}
