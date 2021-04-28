package edu.san.tbx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TbxApplication {

	@Autowired
	InstanceInfoManager instanceInfoManager;

	public static void main(String[] args) {
		SpringApplication.run(TbxApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void registerAllApps() {
		System.err.println("Application STARTED****************************************");
		instanceInfoManager.registerAllApps();
	}

	@EventListener(ContextStoppedEvent.class)
	public void deRegisterAllApps() {
		instanceInfoManager.cancelAllApps();
	}

}
