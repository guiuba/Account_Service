package account.services;

import account.dao.LogRepo;
import account.enums.EventAction;
import account.model.Log;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl implements LogService{

    private final LogRepo logRepo;

    public LogServiceImpl(LogRepo logRepo) {
        this.logRepo = logRepo;
    }

    @Override
    public void logAction(EventAction action, String subject, String object, String path) {
        Log log = Log.builder()
                .date(new Date(System.currentTimeMillis()))
                .action(action)
                .subject(subject)
                .object(object)
                .path(path).build();

        logRepo.save(log);

    }

    @Override
    public List<Log> findAllLogs() {
        return logRepo.findAll();
    }
}
