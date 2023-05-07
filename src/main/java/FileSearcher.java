import java.io.File;
import java.util.List;

public class FileSearcher {

    public static void searchFiles(File rootFile, List<File> fileList) {
        if (rootFile.isDirectory()) {
            File[] directoryFiles = rootFile.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (file.isDirectory()) {
                        searchFiles(file, fileList);
                    } else {
                        if (file.getName().toLowerCase().endsWith(".json")||
                                file.getName().toLowerCase().endsWith(".csv")) {
                            fileList.add(file);
                        }
                    }
                }
            }
        }

    }
}
