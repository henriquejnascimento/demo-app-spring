package com.henriquenascimento.demo.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalConstant {

    public static final String REQUIRED_FIELD = "Required field";

    public static final String PREFIX_QUEUE_CONTAINER = "container_";

    // Queue
    public static final String EXCHANGE_DEFAULT = "demo-app-spring.exchange-default";
    public static final String QUEUE_PRODUCT_IMPORT_DATA = "demo-app-spring.product.import-data.queue"; // Queue and routing key

    // Queue SQS
    public static final String QUEUE_SQS_DEMO_TEST = "demo-app-spring_sqs-demo_test";

}
