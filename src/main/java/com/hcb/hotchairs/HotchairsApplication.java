package com.hcb.hotchairs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;
import java.util.Objects;

@SpringBootApplication
public class HotchairsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotchairsApplication.class, args);
	}

}
