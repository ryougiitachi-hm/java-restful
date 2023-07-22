package per.itachi.java.restful.ftp.jsch.infra.sftp;

import com.jcraft.jsch.SftpProgressMonitor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Not easy to make it thread-safe.
 * */
@Slf4j
@Component
public class DefaultSftpProgressMonitor implements SftpProgressMonitor {

    private ConcurrentMap<String, String> context = new ConcurrentHashMap<>();

    @Override
    public void init(int op, String src, String dest, long max) {
        log.debug("op={}, src={}, dest={}, max={}",
                op, src, dest, max);
    }

    @Override
    public boolean count(long count) {
        // TODO: not sure whether true or false should be returned.
        log.debug("count={}", count);
        return false;
    }

    @Override
    public void end() {
        log.debug("end");
    }
}
