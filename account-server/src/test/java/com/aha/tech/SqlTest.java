package com.aha.tech;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class SqlTest {


    @Test
    public void testTargetProperty() throws SQLException {
        List<String> sqls = Lists.newArrayList(
          "delete from h_member where user_id = 2498243;",
                  "delete from h_member_privilege_level where user_id = 2498243;" ,
                  "delete from h_member_contract where user_id = 2498243;" ,
                  "delete from h_member_order where user_id = 2498243;" ,
                  "delete from h_member_withhold_log where member_contract_id = 2225;" ,
                  "delete from h_message_log where body like '%2498243%';" ,
                  "delete from h_member_privilege_group where user_id = 2498243;" ,
                  "delete from h_member_privilege_group_log where user_id = 2498243;" ,
                  "INSERT INTO `member`.`h_member` (`id`, `user_id`, `user_name`, `status`, `contract_status`, `expire_time`, `created_at`, `updated_at`) "+
                  "VALUES (3141, '2498243', 'aha2498243', '2', '1', '2019-09-14 23:59:59', '2019-07-14 06:52:23', '2020-01-10 17:38:02');" ,
                  " INSERT INTO `member`.`h_member_privilege_level` (`id`, `user_id`, `member_id`, `level_id`, `start_time`, `expire_time`, `created_at`, `updated_at`, `contract_channel`, `source`, `goods_member_type`, `contract_status`) VALUES " +
                  " (3192, '2498243', '3141', '6', '2019-07-14 06:52:23', '2019-09-14 23:59:59', '2019-07-14 06:52:23', '2020-01-09 21:57:06', '6', '3', '1', '1');" ,
                  "INSERT INTO `member`.`h_member_contract` (`id`, `user_id`, `privilege_level_id`, `member_privilege_level_id`, `contract_status`, `contract_time`, `cancel_contract_time`, `contract_type`, `contract_time_unit`, `next_withhold_time`, `withhold_status`, `withhold_channel`, `contract_code`, `openid`, `contract_amount`, `created_at`, `updated_at`, `retry_count`, `sku_title`, `platform_id`)" +
                  " VALUES (2225, '2498243', '6', '3192', '1', '2019-07-14 06:52:23', NULL, '2', '1', '2019-09-14 23:55:00', '1', '6', 'SIGNP20CUEURW3FR09', '', '2800', '2019-07-14 06:52:23', '2020-01-09 21:57:06', '0', '连续包月', '6');" ,
                  " INSERT INTO `member`.`h_member_order` " +
                  " (`id`, `user_id`, `member_order_type`, `member_status`, `contract_status`, `start_time`, `expire_time`, `privilege_level_id`, `contract_time`, `contract_type`, `contract_time_unit`, `withhold_channel`, `contract_code`, `openid`, `contract_amount`, `sku_title`, `goods_member_type`, `order_id`, `order_no`, `created_at`, `updated_at`, `price`, `order_id_real`, `revert_account_id`, `revert_status`, `revert_reason`, `independent_start_time`, `account_id`, `first_discount`, `first_discount_price`, `platform_id`) VALUES" +
                  " ('0', '2498243', '5', '2', '1', NULL, NULL, '6', '2019-07-14 06:52:23', '2', '1', '6', 'SIGNP20CUEURW3FR09', '', '2800', '连续包月', '1', '0', '', '2019-07-14 06:52:23', '2019-12-31 11:38:55', '0', '1544', '0', '2', '', '2019-07-14 06:52:23', '0', '2', '0', '6')," +
                  " ('0', '2498243', '1', '1', '1', '2019-07-14 06:52:24', '2019-08-14 23:59:59', '6', NULL, '2', '1', '15', '', '', '0', '连续包月', '1', '5508092', 'P20CUEURKBFR08', '2019-07-14 06:52:23', '2019-12-31 11:39:31', '2800', '5508092', '0', '2', '', '2019-07-14 06:52:24', '0', '2', '0', '6'), " +
                  " ('0', '2498243', '3', '1', '1', '2019-07-14 06:52:24', '2019-09-14 23:59:59', '6', '2019-07-14 06:52:23', '2', '1', '6', 'SIGNP20CUEURW3FR09', '', '2800', '连续包月', '1', '-1', 'SIGNP20CUEURW3FR0920190813', '2019-08-14 00:10:00', '2019-12-31 11:38:55', '2800', '3733', '0', '2', '', '2019-08-14 23:59:59', '0', '2', '0', '6');" ,
                  "INSERT INTO `member`.`h_member_withhold_log`" +
                  "(`member_contract_id`," +
                  "`out_trade_no`," +
                  "`withhold_log_status`)" +
                  "VALUES" +
                  "(2225,'SIGNP20CUEURW3FR0920200109',1)," +
                  "(2225,'SIGNP20CUEURW3FR0920190913',1);"
        );

        String msg = "{\"action\":\"INSERT\",\"app\":2,\"batchNo\":\"157857822566139d55d6f-37dd-4c46-bb85-ce81827089ac\",\"eventId\":4000561,\"source\":5,\"target\":null,\"targetId\":35652,\"targetJson\":\"{\\\"contractCode\\\":\\\"SIGNP20CUEURW3FR09\\\",\\\"openId\\\":null,\\\"outTradeNo\\\":\\\"SIGNP20CUEURW3FR0920190913\\\",\\\"platformId\\\":6,\\\"status\\\":1,\\\"userId\\\":2498243}\",\"targetType\":2,\"topic\":\"pay_withhold_created\",\"uuid\":\"9a662492-e62e-4fbd-89af-24330a484f2f\"}";

        Connection connection = getConn();

        Producer<String, String> kafkaProducer = getKafkaProducer();

        boolean flag = true;

        while (flag) {
            exec(connection, sqls);

            sendKafkaMsg(kafkaProducer, msg);

            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LocalDate expireTime = getExecPostResult(connection);


            if (expireTime.getMonthValue() == 9) {
                System.out.println("==========bingo========");
                System.out.println(expireTime.getMonthValue() + ":" + expireTime.getDayOfMonth());
                flag = false;
            } else {
                System.out.println(expireTime.getMonthValue() + ":" + expireTime.getDayOfMonth());
            }

        }


        connection.close();

        kafkaProducer.close();
    }

    public void exec(Connection connection, List<String> sqls) {

        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            for (String sql : sqls) {
                statement.addBatch(sql);
            }
            statement.executeBatch();
            statement.clearBatch();
            statement.close();

            connection.commit();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public LocalDate getExecPostResult(Connection connection) {
        try {
            Statement statement1 = connection.createStatement();
            statement1.execute("select expire_time from h_member where user_id = 2498243");
            ResultSet resultSet = statement1.getResultSet();
            resultSet.next();

            return resultSet.getDate("expire_time").toLocalDate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Producer<String, String> getKafkaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "test4-basics:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer<String, String>(props);
    }

    public void sendKafkaMsg(Producer<String,String> producer,String msg) {
        producer.send(new ProducerRecord<String, String>("pay_withhold_insert", null, msg));
        producer.flush();
    }


    public Connection getConn() {
        try {
            Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection("jdbc:mysql://test4-basics:3306/member?useUnicode=true&characterEncoding=utf8","hjm_dev","hjm_dev");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

