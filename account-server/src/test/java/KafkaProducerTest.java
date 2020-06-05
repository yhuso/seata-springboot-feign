package com.aha.tech;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 描述:
 *
 * @Auther: bianjie
 * @Date: 2019/11/12
 */
public class KafkaProducerTest {


    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "test4-basics:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        String msg = "{\"action\":\"INSERT\",\"app\":2,\"batchNo\":\"157857822566139d55d6f-37dd-4c46-bb85-ce81827089ac\",\"eventId\":4000561,\"source\":5,\"target\":null,\"targetId\":35652,\"targetJson\":\"{\\\"contractCode\\\":\\\"SIGNP20CUEURW3FR09\\\",\\\"openId\\\":null,\\\"outTradeNo\\\":\\\"SIGNP20CUEURW3FR0920190913\\\",\\\"platformId\\\":6,\\\"status\\\":1,\\\"userId\\\":2498243}\",\"targetType\":2,\"topic\":\"pay_withhold_created\",\"uuid\":\"9a662492-e62e-4fbd-89af-24330a484f2f\"}";

        String msg2 = "{\n" +
                "    \"action\":\"DELETE\",\n" +
                "    \"app\":1,\n" +
                "    \"batchNo\":\"157180225414436c36372-7ea6-41bf-ba35-e867ced76df1\",\n" +
                "    \"eventId\":15274040,\n" +
                "    \"source\":100,\n" +
                "    \"targetId\":4225194,\n" +
                "    \"targetJson\":\"{\"accessTokenId\":null,\"address\":null,\"ahaPrice\":0.00,\"appPaymentStatus\":0,\"appType\":3,\"applyMode\":3,\"cancelFrom\":4,\"cardGroupId\":null,\"cityName\":\"上海市\",\"couponId\":0,\"couponPrice\":0.00,\"createdAt\":\"2019-11-21 15:25:59\",\"cutAt\":null,\"cutStatus\":1,\"deliverIndex\":null,\"deliveryType\":3,\"expiredAt\":\"2019-11-21 15:55:59\",\"expressStatus\":null,\"firstMjGroup\":null,\"firstOpenGroup\":null,\"form1\":\"\",\"form2\":\"\",\"giftId\":0,\"giftNo\":\"\",\"giftTitle\":null,\"groupStat\":null,\"groupbuy\":null,\"groupbuyId\":0,\"isMj\":0,\"isNewUser\":null,\"isVip\":2,\"leftSeconds\":null,\"openGid\":\"\",\"openGroup\":2,\"optionId\":32468,\"optionName\":\"\",\"optionPrice\":0.10,\"orderBody\":\"\",\"orderCount\":1,\"orderCourses\":[],\"orderDeliverList\":null,\"orderId\":42227453,\"orderMemberType\":1,\"orderNo\":\"T2DGDS6FB48R00\",\"orderPrice\":0.10,\"orderStat\":{\"appType\":3,\"channel\":\"\",\"createdAt\":\"2019-11-21 15:25:59\",\"groupbuyId\":0,\"guniqid\":\"cc112aca9236daa0\",\"id\":76572,\"orderId\":42227453,\"pd\":\"\",\"pk\":\"/23085\",\"pp\":\"\",\"productId\":506039,\"updatedAt\":\"2019-11-21 15:25:59\",\"userId\":23085,\"utmCampaign\":\"\",\"utmContent\":\"\",\"utmMedium\":\"\",\"utmSource\":\"\",\"utmTerm\":\"join_member_mymanagement\"},\"orderStatus\":6,\"orderTime\":\"2019-11-21 15:25:59\",\"orderTitle\":\"安卓体验\",\"orderType\":6,\"originPrice\":0.10,\"pageSource\":\"member_detail\",\"patternId\":0,\"patternType\":null,\"paymentFrom\":1,\"paymentId\":0,\"paymentStatus\":3,\"platformId\":0,\"postagePrice\":0.00,\"prepayId\":\"\",\"productId\":506039,\"referee\":\"\",\"refundStatus\":1,\"remark\":\"\",\"remoteIp\":\"180.171.161.234\",\"returnAmount\":0.00,\"returnTime\":null,\"sku\":465,\"specialPriceFlag\":0,\"strangerType\":0,\"systemFrom\":0,\"taskPlan\":0,\"updatedAt\":\"2019-11-21 15:26:36\",\"userId\":23085,\"userType\":null,\"videoGroupIds\":\"\",\"videoPermissionHours\":867240,\"vipDiscount\":0.00}\",\n" +
                "    \"targetType\":1,\n" +
                "    \"topic\":\"test_order_topic\",\n" +
                "    \"uuid\":\"72c7e016-ecb1-412a-a31a-360790ccf910\"\n" +
                "}";
        System.out.println(msg);

        producer.send(new ProducerRecord<String, String>("pay_withhold_insert", null, msg));
        producer.close();
    }
}
