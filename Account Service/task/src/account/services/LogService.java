package account.services;

import account.enums.EventAction;
import account.model.Log;

import java.util.List;

public interface LogService {
    void logAction(EventAction action, String subject, String object, String path);
    List<Log> findAllLogs();
}
