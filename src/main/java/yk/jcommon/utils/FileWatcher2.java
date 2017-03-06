package yk.jcommon.utils;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 05/11/15
 * Time: 17:19
 */
public class FileWatcher2 {
    private String fileName;
    private File file;
    private long lastModified;

    public boolean exists;
    public boolean isDir;
    public boolean justChanged;
    public boolean justCreated;
    public boolean justRemoved;

    public FileWatcher2(String path) {
        try {
            fileName = path;
            file = new File(path);

            exists = file.exists();
            isDir = file.isDirectory();
            lastModified = file.lastModified();
        } catch (Exception e) {
            throw new BadException(e);
        }
    }

    public boolean isJustChanged() {
        tick();
        return justChanged;
    }
    

    
    public boolean tick() {
//        file = new File(fileName);

        justCreated = false;
        justRemoved = false;

        boolean newExists = file.exists();
        if (newExists && !this.exists) justCreated = true;
        if (!newExists && this.exists) justRemoved = true;
        this.exists = newExists;
        isDir = file.isDirectory();

        if (this.exists && !isDir) {
//            try {
//                BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
//
//                long lastModified = basicFileAttributes.lastModifiedTime().toMillis();
//                justChanged = this.lastModified == lastModified;
//                this.lastModified = lastModified;
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

            long lastModified = file.lastModified();
            justChanged = this.lastModified != lastModified;
            this.lastModified = lastModified;
        }
        return justChanged;
    }

    @Override
    public String toString() {
        return "FileWatcher2{" +
                "fileName='" + fileName + '\'' +
                ", lastModified=" + lastModified +
                ", exists=" + exists +
                ", isDir=" + isDir +
                ", justChanged=" + justChanged +
                ", justCreated=" + justCreated +
                ", justRemoved=" + justRemoved +
                '}';
    }

    public static void main(String[] args) {
        //press recompile this .java, to see changes
        FileWatcher2 fw = new FileWatcher2("target/classes/yk/jcommon/utils/FileWatcher2.class");
        Threads.thread(500, false, () -> {
            fw.tick();
            System.out.println(fw);

            return true;
        });

    }
}
