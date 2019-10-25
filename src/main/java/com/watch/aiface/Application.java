package com.watch.aiface;

import com.watch.aiface.base.repository.factory.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.watch")
@EnableJpaRepositories(basePackages = { "com.watch" }, repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@EnableJpaAuditing()
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
