package per.itachi.java.restful.ftp.jsch.infra.sftp;

import java.util.List;
import java.util.Map;

/**
 * The basic thinking of sftp port:
 * The main entities:
 * The main actions:
 * */
public interface SftpPort {

    /**
     * @param toDownloadFileName only file name without path, remote file name.
     * @param localDownloadedPath local path
     * @return key = toDownloadFileName, value = the local path of successfully downloaded file
     * */
    Map<String, String> downloadFile(String toDownloadFileName, String targetHostPath, String localDownloadedPath);

    /**
     * @return key = toDownloadFileName, value = the local path of successfully downloaded file
     * */
    Map<String, String> downloadFiles(List<String> toDownloadFileName, String targetHostPath, String localDownloadedPath);

    void uploadFile(String toUploadFilePath, String targetHostPath);

    void uploadFile(String toUploadFilePath, List<String> targetHostPaths);

    void uploadFiles(List<String> toUploadFilePath, List<String> targetHostPaths);
}
