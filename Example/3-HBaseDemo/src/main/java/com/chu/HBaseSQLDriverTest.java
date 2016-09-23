package com.chu;

/**
 * Created by chuguangming on 16/9/23.
 */

import java.sql.*;


class BaseDB {

    /**
     * name：getConnection
     * time：2015年5月6日 下午2:07:06
     * description： get JDBC connection
     *
     * @return connection
     */
    public static Connection getConnection() {
        try {
            // load driver
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");

            // get connection
            // jdbc 的 url 类似为 jdbc:phoenix [ :<zookeeper quorum> [ :<port number> ] [ :<root node> ] ]，
            // 需要引用三个参数：hbase.zookeeper.quorum、hbase.zookeeper.property.clientPort、and zookeeper.znode.parent，
            // 这些参数可以缺省不填而在 hbase-site.xml 中定义。
            return DriverManager.getConnection("jdbc:phoenix:hadoopmaster,hadoopslave1,hadoopslave2");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}


public class HBaseSQLDriverTest {
    public static void main(String[] args) throws SQLException {
        //Simpletest();
        //create();
        //upsert();
        //query();
        //delete();
    }

    /**
     * name：delete
     * time：2015年5月4日 下午4:03:11
     * description：delete data
     */
    public static void delete() {

        Connection conn = null;
        try {
            // get connection
            conn = BaseDB.getConnection();

            // check connection
            if (conn == null) {
                System.out.println("conn is null...");
                return;
            }

            // create sql
            String sql = "delete from user88888 where id='001'";

            PreparedStatement ps = conn.prepareStatement(sql);

            // execute upsert
            String msg = ps.executeUpdate() > 0 ? "delete success..."
                    : "delete fail...";

            // you must commit
            conn.commit();
            System.out.println(msg);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void query() {

        Connection conn = null;
        try {
            // get connection
            conn = BaseDB.getConnection();

            // check connection
            if (conn == null) {
                System.out.println("conn is null...");
                return;
            }

            // create sql
            String sql = "select * from user88888";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("id" + "\t" + "account" + "\t" + "passwd");
            System.out.println("======================");

            if (rs != null) {
                while (rs.next()) {
                    System.out.print(rs.getString("id") + "\t");
                    System.out.print(rs.getString("account") + "\t");
                    System.out.println(rs.getString("passwd"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * name：insert
     * time：2015年5月4日 下午2:59:11
     * description：
     */
    public static void upsert() {

        Connection conn = null;
        try {
            // get connection
            conn = BaseDB.getConnection();

            // check connection
            if (conn == null) {
                System.out.println("conn is null...");
                return;
            }

            // create sql
            String sql = "upsert into user88888(id, INFO.account, INFO.passwd) values('001', 'admin', 'admin')";

            PreparedStatement ps = conn.prepareStatement(sql);

            // execute upsert
            String msg = ps.executeUpdate() > 0 ? "insert success..."
                    : "insert fail...";

            // you must commit
            conn.commit();
            System.out.println(msg);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void create() {
        Connection conn = null;
        try {
            // get connection
            conn = BaseDB.getConnection();

            // check connection
            if (conn == null) {
                System.out.println("conn is null...");
                return;
            }

            // check if the table exist
            ResultSet rs = conn.getMetaData().getTables(null, null, "USER",
                    null);
            if (rs.next()) {
                System.out.println("table user is exist...");
                return;
            }
            // create sql
            String sql = "CREATE TABLE user88888 (id varchar PRIMARY KEY,INFO.account varchar ,INFO.passwd varchar)";

            PreparedStatement ps = conn.prepareStatement(sql);

            // execute
            ps.execute();
            System.out.println("create success...");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void Simpletest() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        String viewName = "\"US_POPULATION2\""; // 这是对HBase表"food:products"创建的Phoenix view

        System.err.println("\n[viewName = " + viewName + "]\n");

        /* ecs1.njzd.com:2181是zookeeper的某一个节点的ip:port
           即使集群中的ZooKeeper存在多个节点，这里也只需要写出一个节点的ip:port就可以了*/
        // 如果是Scala，还需要这一句
        //Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
        Connection conn = DriverManager.getConnection("jdbc:phoenix:hadoopmaster,hadoopslave1,hadoopslave2");

        /* 在Phoenix中，如果table name/view name、column name等字符串不加上双引号就会被认为是大写。所以，这里的brand_name要加上双引号  */
        PreparedStatement pstmt = conn.prepareStatement("select * from " + viewName);
        rs = pstmt.executeQuery();

        while (rs.next()) {

            System.err.println(rs.getString("STATE"));

            System.err.println("\n=========================================================");
        }
        /* 关闭资源*/
        rs.close();
        pstmt.close();
    }
}
