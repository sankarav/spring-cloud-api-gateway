package edu.san.tbx;

import static java.util.stream.Collectors.toList;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
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
    return InstanceInfo.Builder.newBuilder().setInstanceId(app.name + "instance1")
        .setAppName(app.name).setAppNameForDeser(app.name + "fordeser").setAppGroupName(app.name + "group")
        .setAppGroupNameForDeser(app.name + "group1fordeser").setHostName("app1host1")
        .setStatus(InstanceInfo.InstanceStatus.UP).setOverriddenStatus(InstanceInfo.InstanceStatus.DOWN)
        .setIPAddr("127.0.0.1").setSID("app1sid").setPort(app.port).setSecurePort(4443)
        .enablePort(InstanceInfo.PortType.UNSECURE, true).setHomePageUrl("/", "http://localhost/")
        .setHomePageUrlForDeser("http://localhost/").setStatusPageUrl("/status", "http://localhost/info")
        .setStatusPageUrlForDeser("http://localhost/status")
        .setHealthCheckUrls("/ping", "http://localhost/ping", null)
        .setHealthCheckUrlsForDeser("http://localhost/ping", null).setVIPAddress("localhost:8080")
        .setVIPAddressDeser("localhost:8080").setSecureVIPAddress("localhost:4443")
        .setSecureVIPAddressDeser("localhost:4443")
        .setDataCenterInfo(new MyDataCenterInfo(DataCenterInfo.Name.MyOwn))
        .setLeaseInfo(LeaseInfo.Builder.newBuilder().setDurationInSecs(30).setRenewalIntervalInSecs(30)
            .setEvictionTimestamp(System.currentTimeMillis() + 30000)
            .setRenewalTimestamp(System.currentTimeMillis() - 1000)
            .setRegistrationTimestamp(System.currentTimeMillis() - 2000).build())
        .add("metadatakey1", "metadatavalue1").setASGName("asg1").setIsCoordinatingDiscoveryServer(false)
        .setLastUpdatedTimestamp(System.currentTimeMillis()).setLastDirtyTimestamp(System.currentTimeMillis())
        .setActionType(InstanceInfo.ActionType.ADDED).setNamespace("namespace1").build();
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
