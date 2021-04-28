package edu.san.tbx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TbxApplication {

	public static void main(String[] args) {
		SpringApplication.run(TbxApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void registerAllApps(InstanceInfoManager instanceInfoManager) {
		System.err.println("Application STARTED****************************************");
		instanceInfoManager.registerAllApps();
	}

	@EventListener(ContextStoppedEvent.class)
	public void deRegisterAllApps(InstanceInfoManager instanceInfoManager) {
		instanceInfoManager.cancelAllApps();
	}

}
