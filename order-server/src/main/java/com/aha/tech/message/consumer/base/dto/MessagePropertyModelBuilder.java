package com.aha.tech.message.consumer.base.dto;

import lombok.Data;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author ahaschool
 */
@Data
public class MessagePropertyModelBuilder<T> {
    private MessagePropertyModel<T> model;

    private MessagePropertyModelBuilder() {
        this.model = new MessagePropertyModel<>();
    }

    public static <T> MessagePropertyModelBuilder<T> builder() {
        return new MessagePropertyModelBuilder<>();
    }

    public MessagePropertyModelBuilder<T> event(String event) {
        model.setEvent(event);
        return this;
    }

    public MessagePropertyModelBuilder<T> properties(T properties) {
        model.setProperties(properties);
        return this;
    }

    public MessagePropertyModelBuilder<T> userId(Long userId) {
        model.setUserId(userId);
        return this;
    }

    public MessagePropertyModelBuilder<T> topic(String topic) {
        model.setTopic(topic);
        return this;
    }

    public MessagePropertyModelBuilder<T> messagePropertyModelConsumer(Consumer<MessagePropertyModel<T>> consumer) {
        consumer.accept(model);
        return this;
    }


    public MessagePropertyModel<T> build() {
        model.setTime(String.valueOf(System.currentTimeMillis()));
        model.setUuid(UUID.randomUUID().toString());
        return model;
    }

}
