package com.adonis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author adonis
 *
 * @createDate Aug 13, 2015
 */
public class IdiomDAO {

    public void query() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://192.168.30.3:3306/test?" + "user=root&password=root");
            Statement createStatement = conn.createStatement();
            ResultSet executeQuery = createStatement.executeQuery("select * from subject");
            while (executeQuery.next()) {
                System.out.println(executeQuery.getString("id"));
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void insert(int id, String word, String pronunciation, String explanation,
            String source) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://192.168.30.3:3306/test?" + "user=root&password=root");
            PreparedStatement prepareStatement = conn.prepareStatement(
                    "insert ignore into idiom (id , word , pronunciation , explanation , source) values (? , ? , ? , ? , ?)");
            prepareStatement.setInt(1, id);
            prepareStatement.setString(2, word);
            prepareStatement.setString(3, pronunciation);
            prepareStatement.setString(4, explanation);
            prepareStatement.setString(5, source);
            prepareStatement.execute();
            prepareStatement.close();
            conn.close();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        IdiomDAO idiomDAO = new IdiomDAO();
        //        idiomDAO.insert("睹微知著", "dǔ wēi zhī zhù", "微：细小；著：显著。看到细小的征兆便知道其性质及发展趋势。",
        //                "《三国志·吕布臧洪传》：“仆中不敏，又素不能原始见终，睹微知著，窃度主人之心。，岂谓三子宜死，罚当刑中哉。？”");

    }
}
