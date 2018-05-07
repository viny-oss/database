package com.vinyoss.cassandraavailabilitychecker;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class Main {
    public static void main(String[] args) {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            Session session = cluster.connect();

            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            if (row.getString("release_version") != null) {
                System.out.println("Successfully connected");
                System.exit(0);
            } else {
                System.out.print("Connection failed");
                System.exit(1);
            }
        } catch (Exception unused) {
            System.out.print("Connection failed");
            System.exit(1);
        } finally {
            if (cluster != null) cluster.close();
        }
    }
}
