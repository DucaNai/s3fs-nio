package org.carlspring.cloud.storage.s3fs.spike;

import java.io.IOException;
import java.nio.file.*;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PathSpecTest2 {

    FileSystem fs;

    FileSystem fsWindows;


    @BeforeEach
    public void setup()
            throws IOException {
        fs = MemoryFileSystemBuilder.newLinux().build("linux");
        fsWindows = MemoryFileSystemBuilder.newWindows().build("windows");
    }

    @AfterEach
    public void close()
            throws IOException {
        try {
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fsWindows.close();
    }
    // file name

    @Test
    void getFileName()
            throws IOException {
        try (FileSystem windows = MemoryFileSystemBuilder.newWindows().build("widows")) {
            Path fileName = windows.getPath("C:/file").getFileName();
            Path rootName = windows.getPath("C:/").getFileName();

            assertEquals(windows.getPath("file"), fileName);
            assertNull(rootName);
        }
    }

    @Test
    void getFileNameRootIsNull() {
        Path fileNameRoot = fs.getRootDirectories().iterator().next().getFileName();

        assertNull(fileNameRoot);
    }

    // root

    @Test
    void getRootReturnBucket() {
        assertEquals(get("/"), get("/dir/dir/file").getRoot());
    }

    @Test
    void fileWithSameNameAsDir()
            throws IOException {
        // We're expecting an exception here to be thrown
        Exception exception = assertThrows(FileAlreadyExistsException.class, () -> {
            Files.createFile(fs.getPath("/tmp"));
            Files.createDirectory(fs.getPath("/tmp/"));
        });

        assertNotNull(exception);
    }

    @Test
    void dirWithSameNameAsFile() {
        // We're expecting an exception here to be thrown
        Exception exception = assertThrows(FileAlreadyExistsException.class, () -> {
            Files.createDirectories(fs.getPath("/tmp/"));
            Files.createFile(fs.getPath("/tmp"));
        });

        assertNotNull(exception);
    }

    @Test
    void createDirWithoutEndSlash()
            throws IOException {
        Path dir = Files.createDirectory(fs.getPath("/tmp"));
        Files.isDirectory(dir);
    }

    @Test
    void getRootRelativeReturnNull() {
        assertNull(get("dir/file").getRoot());
    }

    @Test
    void getRoot() {
        System.out.println("Default:");
        System.out.println("-------");

        for (Path root : FileSystems.getDefault().getRootDirectories()) {
            System.out.println("- " + root);
        }

        System.out.println("\nLinux:");
        System.out.println("-----");

        for (Path root : fs.getRootDirectories()) {
            System.out.println("- " + root);
        }

        System.out.println("\nWindows:");
        System.out.println("-------");

        for (Path root : fsWindows.getRootDirectories()) {
            System.out.println("- " + root);
        }
    }
    // ~ helpers methods

    private Path get(String path)
    {
        return fs.getPath(path);
    }
}
