import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductReader {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;

        try
        {
            // use the toolkit to get the current working directory of the IDE
            // Not sure if the toolkit is thread safe...
            File workingDirectory = new File(System.getProperty("user.dir"));

            // Typically, we want the user to pick the file, so we use a file chooser
            // kind of ugly code to make the chooser work with NIO.
            // Because the chooser is part of Swing it should be thread safe.

            chooser.setCurrentDirectory(workingDirectory);

            // Using the chooser adds some complexity to the code.
            // We have to code the complete program within the conditional return of
            // the FileChooser because the user can close it without picking a file

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                // Typical java pattern of inherited classes
                // we wrap a BufferedReader around a lower level BufferedOutputStream

                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                // Finally we can read the file LOL!

                String tableHeader = "ID#" + "           " + "Name" + "             " + "Description" + "                  " + "Cost";
                String headerSeperator = "==================================================================";

                System.out.println();
                System.out.println(tableHeader);
                System.out.println(headerSeperator);

                while(reader.ready())
                {
                    String rec = reader.readLine();
                    String[] fields = rec.split(",", 0);

                    String productID = fields[0];
                    String productName = fields[1];
                    String productDesc = fields[2];
                    String productCost = fields[3];

                    System.out.printf("%-12s", productID);
                    System.out.printf("%-16s", productName);
                    System.out.printf("%-30s", productDesc);
                    System.out.printf("%-6s", productCost);
                    System.out.println();
                }
                reader.close(); // must close the file to seal it and flush buffer
                System.out.println("\n\nData file read!");
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("File not found!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
