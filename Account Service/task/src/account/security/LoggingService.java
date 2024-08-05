package account.security;

import account.dao.LogRepo;
import account.model.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoggingService {
    private final LogRepo logRepo;

    public LoggingService( LogRepo logRepo) {
        this.logRepo = logRepo;
    }

    public void saveEntry(Log entry) {
        logRepo.save(entry);
    }

    public List<Log> getAllEntries() {
        return logRepo.findAll();
    }
}
