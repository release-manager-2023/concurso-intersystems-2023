package br.com.releasemanger.product_version.service;

import jakarta.enterprise.context.ApplicationScoped;
import net.datafaker.Faker;

@ApplicationScoped
public class UsernameFaker {

	private Faker faker = new Faker();

	public String getUsername() {
		return faker.friends().character();
	}
}