package com.siaron.hbase.example01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author xielongwang
 * @create 2019-04-1222:41
 * @email xielong.wang@nvr-china.com
 * @description https://blog.csdn.net/kongxx/article/details/87996363
 */
public class HBasePhoenixDemo {

    public static void main(String[] args) throws Exception {
        // 这里配置 zookeeper 的地址，可以是域名或者 ip，可配置单个或者多个节点(用","分隔)
        // 语法jdbc:phoenix:[comma-separated ZooKeeper Quorum [:port [:hbase root znode [:kerberos_principal [:path to kerberos keytab] ] ] ]
        String url = "jdbc:phoenix:master:2181";
        Connection conn = DriverManager.getConnection(url);
        Statement statement = conn.createStatement();
        statement.execute("upsert INTO \"PERSON\" VALUES ('222','jone')");
        //此处必须显式调用commit,才能插入数据
        conn.commit();

        // 这里执行SQL语句
        ResultSet rs = statement.executeQuery("SELECT * FROM \"PERSON\" LIMIT 5");
        while (rs.next()) {
            // 例子
            String row = rs.getString("ID");
            String name = rs.getString("NAME");
            System.out.println("ROW=" + row + ",name=" + name);
        }
        // 关闭连接
        rs.close();
        statement.close();
        conn.close();
    }
}