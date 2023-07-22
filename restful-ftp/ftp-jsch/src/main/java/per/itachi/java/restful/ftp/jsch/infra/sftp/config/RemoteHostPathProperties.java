package per.itachi.java.restful.ftp.jsch.infra.sftp.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RemoteHostPathProperties {

    private int uploadOrder = Integer.MAX_VALUE;

    private String localPrivateKeyPath;

    private String remoteAuthType = "publickey";

    private String remoteHost;

    /**
     * The default port for ssh/sftp is 22.
     * */
    private int remotePort = 22;

    private String remoteUser;

    private String remotePassword;

    private int remoteConnectTimeoutMs = 0;

    private String remotePath;
}
