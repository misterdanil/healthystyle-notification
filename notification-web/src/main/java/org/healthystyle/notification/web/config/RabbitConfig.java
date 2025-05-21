package org.healthystyle.notification.web.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
	public static final String NOTIFICATION_SAVE_ROUTING_KEY = "notification.save";
	public static final String NOTIFICATION_SAVE_QUEUE = "notification-save-queue";

	public static final String NOTIFICATION_DIRECT_EXCHANGE = "notification-direct-exchange";

	public static final String EVENT_DELETED_ROUTING_KEY = "event.deleted";
	public static final String EVENT_DIRECT_EXCHANGE = "event-direct-exchange";

	public static final String USER_CREATED_QUEUE = "user-created-queue";
	public static final String USER_DELETED_QUEUE = "user-deleted-queue";

	@Bean
	public Jackson2JsonMessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public DirectExchange notificationDirectExchange() {
		return new DirectExchange(NOTIFICATION_DIRECT_EXCHANGE, true, false);
	}

	@Bean
	public Queue notificationSaveQueue() {
		return new Queue(NOTIFICATION_SAVE_QUEUE, true, false, false);
	}

	@Bean
	public Binding notificationSaveBinding() {
		return BindingBuilder.bind(notificationSaveQueue()).to(notificationDirectExchange())
				.with(NOTIFICATION_SAVE_ROUTING_KEY);
	}
}
