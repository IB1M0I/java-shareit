package ru.practicum.shareit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Главный класс приложения ShareIt - сервис для аренды вещей
@SpringBootApplication
public class ShareItApp {

	// Точка входа в приложение
	public static void main(String[] args) {
		SpringApplication.run(ShareItApp.class, args);
	}

}
