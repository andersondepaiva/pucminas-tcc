package br.com.andersondepaiva.cadastroativos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = { "br.com.andersondepaiva" })
@EnableFeignClients(basePackages = "br.com.andersondepaiva")
@Configuration
@EnableSwagger2
@EnableEurekaClient
public class CadastroAtivosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroAtivosApplication.class, args);
	}
}
