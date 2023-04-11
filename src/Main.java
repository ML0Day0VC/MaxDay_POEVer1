import API.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            /**
             *   Strcture
             *   need firstly a lognin or signup that will encrypt
             *   then have a list of which table u want to see
             *   load that table
             *   link this shit to the fucking main
             *  constantly update the
             *
             *  color coding and stuff
             *
             *  only cli no gui omg
             *
             * random improant characters like ✓ and ☐
             *
             *
             * opens program
             *   welcome to program
             *     ligin or signup
             *     login enter username then pass
             *
             *
             *
             *
             */

            TableManager tableManager = new TableManager();

            tableManager.table();


            MainPage mainPage = new MainPage();
            mainPage.mainPage();


            //  try {
            //    System.out.println(deepEncrypt.valid("Max","max"));
            //   } catch (Exception e) {
            //        throw new RuntimeException(e);
            //    }


            //    AsciiArtTable aat = new AsciiArtTable();
            //      aat.addHeaderCols("title","task name","due date", "has been completed");

            //    aat.add("test 1"," soemthing ghere ","ndbwkdnaw"," ahghhhh" );
            //  aat.add(1, 2, 3., "a very long thing");
            // have a visual impression (not part of the test)
            //    aat.print(System.out);


            //  FileManager fManager = new FileManager();
            //      fManager.createDirectory();
            //     fManager.writeFile(aat.getOutput());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}