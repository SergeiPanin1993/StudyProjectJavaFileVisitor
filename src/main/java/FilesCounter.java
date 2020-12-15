import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FilesCounter {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String directoryName = reader.readLine();
        Path path = Paths.get(directoryName);
        if (!path.isAbsolute()) {
            path = path.toAbsolutePath();
            path = path.normalize();
        }
        if(!Files.isDirectory(path)){
            System.out.println(path.toString() + " - не папка");
            return;
        }
        MyFileVisitor myFileVisitor = new MyFileVisitor(path);
        Files.walkFileTree(path, myFileVisitor);
        System.out.println("Всего папок - " + myFileVisitor.getCountDirectories());
        System.out.println("Всего файлов - " + myFileVisitor.getCountFiles());
        System.out.println("Общий размер - " + myFileVisitor.getCountBytes());


    }
    public static class MyFileVisitor extends SimpleFileVisitor<Path>{
        private Path directory;
        private int countDirectories;
        private int countFiles;
        private long countBytes;
        public MyFileVisitor(Path path){
            this.directory = path;
        }
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr){
            if(!dir.toString().equals(directory.toString())) {
                countDirectories++;
            }
            return FileVisitResult.CONTINUE;
        }
        public FileVisitResult visitFile(Path dir, BasicFileAttributes attr) throws IOException {
            countFiles++;
            countBytes = countBytes + Files.readAllBytes(dir).length;
            return FileVisitResult.CONTINUE;
        }

        public int getCountDirectories() {
            return countDirectories;
        }

        public int getCountFiles() {
            return countFiles;
        }

        public long getCountBytes() {
            return countBytes;
        }
    }
}
