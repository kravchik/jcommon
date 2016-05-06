package yk.jcommon.utils;

import java.nio.file.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 05/11/15
 * Time: 17:19
 */
public class FileWatcher {

    private WatchKey watchKey;
    private String fileName;

    public FileWatcher(String path) {
        try {
            int sepIndex = path.lastIndexOf(FileSystems.getDefault().getSeparator());
            String dir = path.substring(0, sepIndex);
            fileName = path.substring(sepIndex + 1);
            Path pPath = FileSystems.getDefault().getPath(dir);
            WatchService watchService = FileSystems.getDefault().newWatchService();
            watchKey = pPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (Exception e) {
            BadException.die(e);
        }
    }


    public boolean isChanged() {
        List<WatchEvent<?>> pollEvents = watchKey.pollEvents();
        for (int i = 0, pollEventsSize = pollEvents.size(); i < pollEventsSize; i++) {
            if (pollEvents.get(i).context().toString().contains(fileName)) return true;
        }
        return false;
    }
}
