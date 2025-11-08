import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class FetchWithProgress {

    public static void main(String[] args) {
        try {
            // Temporary folder
            File tempDir = new File("C:\\Program Files\\TempSetup");
            File bin = new File(tempDir, "bin");
            File libs = new File(tempDir, "libs");
            File natives = new File(tempDir, "natives");
            File src = new File(tempDir, "src");

            bin.mkdirs();
            libs.mkdirs();
            natives.mkdirs();
            src.mkdirs();

            // Files to download
            String[][] downloads = {
                {"https://repo1.maven.org/maven2/org/json/json/20230227/json-20230227.jar", "libs/json.jar"},
                {"https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar", "libs/gson.jar"},
                {"https://repo1.maven.org/maven2/com/formdev/flatlaf/3.1/flatlaf-3.1.jar", "libs/flatlaf-3.1.jar"},
                {"https://repo.lwjgl.org/lwjgl/lwjgl/3.3.1/lwjgl-3.3.1.jar", "libs/lwjgl-3.3.1.jar"},
                {"https://repo.lwjgl.org/lwjgl/lwjgl-opengl/3.3.1/lwjgl-opengl-3.3.1.jar", "libs/lwjgl-opengl-3.3.1.jar"},
                {"https://repo.lwjgl.org/lwjgl/lwjgl-stb/3.3.1/lwjgl-stb-3.3.1.jar", "libs/lwjgl-stb-3.3.1.jar"},
                {"https://repo.lwjgl.org/lwjgl/lwjgl/3.3.1/native/windows/lwjgl.dll", "natives/lwjgl.dll"},
                {"https://repo.lwjgl.org/lwjgl/lwjgl-opengl/3.3.1/native/windows/lwjgl_opengl.dll", "natives/lwjgl_opengl.dll"},
                {"https://repo.lwjgl.org/lwjgl/lwjgl-stb/3.3.1/native/windows/lwjgl_stb.dll", "natives/lwjgl_stb.dll"},
                {"https://raw.githubusercontent.com/yourusername/yourrepo/main/bin/MyIDE.class", "bin/MyIDE.class"},
                {"https://raw.githubusercontent.com/yourusername/yourrepo/main/src/MyIDE.java", "src/MyIDE.java"}
            };

            // Setup progress bar
            JProgressBar progressBar = new JProgressBar(0, downloads.length);
            progressBar.setStringPainted(true);
            JFrame frame = new JFrame("Downloading MyIDE Files...");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 80);
            frame.setLayout(new BorderLayout());
            frame.add(progressBar, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Download files
            int count = 0;
            for (String[] file : downloads) {
                String url = file[0];
                File dest = new File(tempDir, file[1]);
                downloadFile(url, dest);
                count++;
                progressBar.setValue(count);
                progressBar.setString("Downloaded " + count + " of " + downloads.length);
            }

            frame.dispose();
            System.out.println("All files downloaded to " + tempDir.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void downloadFile(String urlStr, File dest) throws IOException {
        System.out.println("Downloading: " + dest.getName());
        dest.getParentFile().mkdirs();
        URL url = new URL(urlStr);
        try (InputStream in = url.openStream()) {
            Files.copy(in, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
