package org.healthystyle.notification.web.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

public class RabbitConfig {
	public static final String EVENT_CREATED_ROUTING_KEY = "event.created";
	public static final String EVENT_CREATED_QUEUE = "notification-event-created-queue";

	public static final String EVENT_DELETED_ROUTING_KEY = "event.deleted";
	public static final String EVENT_DIRECT_EXCHANGE = "event-direct-exchange";

	public static final String USER_CREATED_QUEUE = "user-created-queue";
	public static final String USER_DELETED_QUEUE = "user-deleted-queue";

	@Bean
	public DirectExchange eventDirectExchange() {
		return new DirectExchange(EVENT_DIRECT_EXCHANGE);
	}

	@Bean
	public Queue eventCreatedQueue() {
		return new Queue(EVENT_CREATED_QUEUE);
	}

	@Bean
	public Binding eventCreatedBinding() {
		return BindingBuilder.bind(eventCreatedQueue()).to(eventDirectExchange()).with(EVENT_CREATED_ROUTING_KEY);
	}
}
