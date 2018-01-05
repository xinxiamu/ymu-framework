package com.ymu.framework.sample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.ymu")
public class YmuFrameworkSampleApp implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(YmuFrameworkSampleApp.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println(">>>>>初始化");
	}

	/*@Bean(name = "ftlCfg")
	public Configuration initFreemarker() throws IOException {
		//初始化freemarker
		System.out.println(">>>>>初始化freemarker");
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);

		String path = getClass().getClassLoader().getResource("templates").getPath();
		cfg.setDirectoryForTemplateLoading(new File(path));

		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);

		return cfg;
	}*/


/*	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor(){
		return new MethodValidationPostProcessor();
	}*/
}
