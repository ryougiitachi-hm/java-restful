package per.itachi.java.restful.ftp.jsch.infra.sftp.config;

import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SftpProperties {

    /**
     * The data unit is byte.
     * */
    private int bufferSize = 32768;

    private Map<String, RemoteHostPathProperties> remoteHostPaths = Collections.emptyMap();
}
