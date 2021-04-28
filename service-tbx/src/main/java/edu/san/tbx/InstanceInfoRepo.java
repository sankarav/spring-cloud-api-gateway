package edu.san.tbx;

import static java.util.stream.Collectors.toList;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.DataCenterInfo.Name;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.appinfo.MyDataCenterInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
public class InstanceInfoRepo {

  private final static List<Application> apps =
      List.of(
          Application.of("country", 9090),
          Application.of("messenger", 9091)
      );

  private final static List<InstanceInfo> instanceInfos;

  static {
    instanceInfos = createAllInstanceInfo(apps);
  }

  public List<InstanceInfo> getAll() {
    return instanceInfos;
  }

  private final static List<InstanceInfo> createAllInstanceInfo(List<Application> apps) {
    return apps.stream()
        .map(app -> createInstanceInfo(app))
        .collect(toList());
  }

  private final static InstanceInfo createInstanceInfo(Application app) {
    InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder()
        .setInstanceId(String.format("sankars-mbp:%s:%d", app.name, app.port))
        .setAppName(app.name)
        .setHostName("sankars-mbp")
        .setStatus(InstanceStatus.UP)
        .setIPAddr("192.168.1.158")
        .setPort(app.port)
        .setSecurePort(4443)
        .setDataCenterInfo(new MyDataCenterInfo(Name.MyOwn))
        .build();

    System.out.println(instanceInfo);

    return instanceInfo;
  }

  @Data @Builder @AllArgsConstructor @NoArgsConstructor
  private static class Application {
    private String name;
    private int port;

    private static Application of(String name, int port) {
      return new Application(name, port);
    }
  }


}
