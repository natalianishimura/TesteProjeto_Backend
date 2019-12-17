package com.projeto.funancial.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ConfigurationPropertyFactory {	
    private static ApplicationConfig config;    
    
	public static ApplicationConfig getConfiguration() {		
		if(config == null) {
			loadConfig();
		}
		
		return config;
	}
	
	private static void loadConfig() {
		Logger logger = LogManager.getLogger(ConfigurationPropertyFactory.class);
		
		try(InputStream input = new FileInputStream(ConfigurationPropertyFactory.class
				.getClassLoader()
				.getResource("application.properties")
				.getFile())) {
			Properties properties = new Properties();
			properties.load(input);
			
			config = new ApplicationConfig(properties.getProperty("jwt.secret"));
		} catch(IOException e) {
			logger.error("Erro durante o carregamento de configurações do arquivo application.properties");
		}
	}

}
