import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductWriter {
    public static void main(String[] args) {

        // Variable initialization
        Scanner in = new Scanner(System.in);
        ArrayList<String> products = new ArrayList<>();

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\ProductTestData.txt");

        String productRec = "";
        String productID = "";
        String productName = "";
        String productDesc = "";
        double productCost = 0.0;
        boolean done = false;

        do {

            // Assigns values
            productID = SafeInput.getNonZeroLenString(in, "ID [6 digits]");
            productName = SafeInput.getNonZeroLenString(in, "Name");
            productDesc = SafeInput.getNonZeroLenString(in, "Description");
            productCost = SafeInput.getDouble(in, "Cost");

            // Adds record
            productRec = productID + ", " + productName + ", " + productDesc + ", " + productCost;
            products.add(productRec);

            done = SafeInput.getYNConfirm(in, "Done?");
        } while (!done);

        for (String product : products) {
            System.out.println(product);
        }
        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!

            for(String product : products)
            {
                writer.write(product, 0, product.length());  // stupid syntax for write rec
                // 0 is where to start (1st char) the write
                // rec. length() is how many chars to write (all)
                writer.newLine();  // adds the new line

            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("\n" + "Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }



    }
}