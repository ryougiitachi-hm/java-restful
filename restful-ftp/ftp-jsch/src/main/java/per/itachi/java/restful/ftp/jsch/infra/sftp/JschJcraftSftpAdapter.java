package per.itachi.java.restful.ftp.jsch.infra.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.management.monitor.Monitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import per.itachi.java.restful.ftp.jsch.infra.sftp.config.RemoteHostPathProperties;
import per.itachi.java.restful.ftp.jsch.infra.sftp.config.SftpProperties;

@Slf4j
@Component
public class JschJcraftSftpAdapter implements SftpPort{

    @Autowired
    private SftpProperties sftpProperties;

    @Override
    public Map<String, String> downloadFile(String toDownloadFileName, String targetHostPath, String localDownloadedPath) {
        return downloadFiles(Collections.singletonList(toDownloadFileName), targetHostPath, localDownloadedPath);
    }

    @Override
    public Map<String, String> downloadFiles(List<String> toDownloadFileName, String targetHostPath, String localDownloadedPath) {
        if (CollectionUtils.isEmpty(sftpProperties.getRemoteHostPaths())) {
            log.warn("No sftp remote host path found. No action required. ");
            return Collections.emptyMap();
        }
        RemoteHostPathProperties properties = sftpProperties.getRemoteHostPaths().get(targetHostPath);
        if (properties == null) {
            log.error("The {} is not available remote host path config, skipped downloading. ", targetHostPath);
            return Collections.emptyMap();
            // TODO: customized action. E.g. skip? throw? others?
        }

        // start initialzing jsch ssh session
        Session session = null;
        try {
            session = createJschSession(properties);
            return downloadFilesViaSession(toDownloadFileName, properties, localDownloadedPath, session);
        }
        catch (JSchException e) {
            log.error("Error occurred when connecting to remote host, remoteHost={}, remotePort={}, remoteUser={}",
                    properties.getRemoteHost(), properties.getRemotePort(),
                    properties.getRemoteUser(), e);
            return Collections.emptyMap();
        }
        finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
                log.debug("The ssh session has disconnected. ");
            }
        }
    }

    private Map<String, String> downloadFilesViaSession(List<String> toDownloadFileNames,
                                                        RemoteHostPathProperties remoteHostPathProperties,
                                                        String localDownloadedPath, Session session) {
        // start initialzing jsch ssh channel
        ChannelSftp channel = null;
        try {
            log.info("Started downloading files. ");
            channel = (ChannelSftp)session.openChannel("sftp");
            // check whether local directory exists
            if (Files.notExists(Paths.get(localDownloadedPath))) {
                try {
                    Files.createDirectories(Paths.get(localDownloadedPath));
                }
                catch (IOException e) {
                    log.error("Failed to create local download directory, path={}", localDownloadedPath, e);
                    return Collections.emptyMap();
                }
            }
            Map<String, String> result = new HashMap<>();
            for (String strDownloadFileName : toDownloadFileNames) {
                log.info("Started downloading file {}", strDownloadFileName);
                Path remoteFilePath = Paths.get(remoteHostPathProperties.getRemotePath(), strDownloadFileName);
                try {
                    SftpATTRS sftpATTRS = channel.lstat(remoteFilePath.toAbsolutePath().toString());
                    if (sftpATTRS == null || !sftpATTRS.isFifo()) {
                        log.warn("The remote file doesn't exist, skipped downloading, remotePath={}", remoteFilePath);
                        continue;
                    }
                    Path localFilePath = Paths.get(localDownloadedPath, strDownloadFileName);
                    try(OutputStream fos = new BufferedOutputStream(Files.newOutputStream(localFilePath),
                            sftpProperties.getBufferSize())) {
                        channel.get(remoteFilePath.toAbsolutePath().toString(), fos);
                        result.put(strDownloadFileName, localFilePath.toAbsolutePath().toString());
                    }
                }
                catch (IOException | SftpException e) {
                    log.error("Failed to download file via sftp due to exception, file={}. ", strDownloadFileName, e);
                }
            }
            return result;
        }
        catch (JSchException e) {
            log.error("Error occurred when using channel, remoteHost={}, remotePort={}, remoteUser={}",
                    remoteHostPathProperties.getRemoteHost(), remoteHostPathProperties.getRemotePort(),
                    remoteHostPathProperties.getRemoteUser(), e);
            return Collections.emptyMap();
        }
        finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
                log.debug("The ssh session channel has disconnected. ");
            }
        }
    }
    @Override
    public void uploadFile(String toUploadFilePath, String targetHostPath) {
        uploadFiles(Collections.singletonList(toUploadFilePath), Collections.singletonList(targetHostPath));
    }

    @Override
    public void uploadFile(String toUploadFilePath, List<String> targetHostPaths) {
        uploadFiles(Collections.singletonList(toUploadFilePath), targetHostPaths);
    }

    @Override
    public void uploadFiles(List<String> toUploadFilePath, List<String> targetHostPaths) {
        if (CollectionUtils.isEmpty(sftpProperties.getRemoteHostPaths())) {
            log.warn("No sftp remote host path found. No action required. ");
            return;
        }
        List<RemoteHostPathProperties> remoteHostPathPropertiesList = new ArrayList<>();
        for (String item : toUploadFilePath) {
            RemoteHostPathProperties properties = sftpProperties.getRemoteHostPaths().get(item);
            if (properties == null) {
                log.warn("The {} is not available remote host path config, skipped. ", item);
                // TODO: customized action. E.g. skip? throw? others?
            }
            else {
                remoteHostPathPropertiesList.add(properties);
            }
        }

        for (RemoteHostPathProperties remoteHostPathProperties : remoteHostPathPropertiesList) {
            // start initialzing jsch ssh session
            Session session = null;
            try {
                session = createJschSession(remoteHostPathProperties);
                uploadFilesViaSession(toUploadFilePath, remoteHostPathProperties, session);
            }
            catch (JSchException e) {
                log.error("Error occurred when connecting to remote host, remoteHost={}, remotePort={}, remoteUser={}",
                        remoteHostPathProperties.getRemoteHost(), remoteHostPathProperties.getRemotePort(),
                        remoteHostPathProperties.getRemoteUser(), e);
            }
            finally {
                if (session != null && session.isConnected()) {
                    session.disconnect();
                    log.debug("The ssh session has disconnected. ");
                }
            }
        }
    }

    private void uploadFilesViaSession(List<String> toUploadFilePath,
                                       RemoteHostPathProperties remoteHostPathProperties, Session session) {
        // start initialzing jsch ssh channel
        ChannelSftp channel = null;
        try {
            log.info("Started uploading files. ");
            channel = (ChannelSftp)session.openChannel("sftp");
            // TODO: verify remote path does exist.
            for (String strfilePath : toUploadFilePath) {
                Path filePath = Paths.get(strfilePath);
                if (Files.notExists(filePath)) {
                    log.error("The file to upload doesn't exist, path={}. ", strfilePath); // warn is ok? depends on scenario.
                    continue;
                }
                try(InputStream is = Files.newInputStream(filePath)) {
//            channel.put(is, dst, monitor, mode);
                    channel.put(is, remoteHostPathProperties.getRemotePath());
                    log.info("Completed uploading file {}", strfilePath);
                }
                catch (IOException | SftpException e) {
                    log.error("Failed to upload file via sftp due to exception, file={}. ", strfilePath, e);
                }
            }
        }
        catch (JSchException e) {
            log.error("Error occurred when using channel, remoteHost={}, remotePort={}, remoteUser={}",
                    remoteHostPathProperties.getRemoteHost(), remoteHostPathProperties.getRemotePort(),
                    remoteHostPathProperties.getRemoteUser(), e);
        }
        finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
                log.debug("The ssh session channel has disconnected. ");
            }
        }
    }
    
    private Session createJschSession(RemoteHostPathProperties remoteHostPathProperties) throws JSchException {
        // start initialzing jsch ssh session
        JSch jSch = new JSch();
        Session session = null;
        if (Files.notExists(Paths.get(remoteHostPathProperties.getLocalPrivateKeyPath()))) {
            // set private key file before initializing ssh session.
            log.error("The specific local private key file doesn't exist, path={}",
                    remoteHostPathProperties.getLocalPrivateKeyPath());
        }
        else {
            jSch.addIdentity(remoteHostPathProperties.getLocalPrivateKeyPath());
        }
        session = jSch.getSession(remoteHostPathProperties.getRemoteUser(),
                remoteHostPathProperties.getRemoteHost(), remoteHostPathProperties.getRemotePort());
        switch (remoteHostPathProperties.getRemoteAuthType()) {
            case "password" -> {
                session.setConfig("PreferredAuthentications", "password");
                session.setPassword(remoteHostPathProperties.getRemotePassword());
            }
            case "publickey" -> session.setConfig("PreferredAuthentications", "publickey");
            default -> {
            }
        }
        session.setConfig("userauth.gssapi-with-mic", "no"); // buzhidao
        session.setConfig("StrictHostKeyChecking", "yes"); // check whether known hosts or not. Sometimes no.
        session.connect(remoteHostPathProperties.getRemoteConnectTimeoutMs());
        return session;
    }
}
