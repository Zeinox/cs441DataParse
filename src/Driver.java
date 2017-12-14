import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Driver {

  public stock parseData(String data){
    String[] splits = data.split(",");

    stock nStock = new stock();

    nStock.Date = splits[0];
    nStock.Time = splits[1];
    nStock.Open = Double.parseDouble(splits[2]);
    nStock.High = Double.parseDouble(splits[3]);
    nStock.Low = Double.parseDouble(splits[4]);
    nStock.Close = Double.parseDouble(splits[5]);
    nStock.Volume = Double.parseDouble(splits[6]);

    return  nStock;
  }

  public static void main(String[] args) {

    //1. parse the csv data
    //2. run the calculations
    //3. sleep(1)
    //4. do for all 7 at the same time
    File folder = new File("./stocks");
    File[] listOfFiles = folder.listFiles();

    Map<String, ArrayList> stocks = new HashMap<String, ArrayList>();
    //parse data into dictionary between name and stocks throughout the time period

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        System.out.println("File " + listOfFiles[i].getName());
        File file = new File("./stocks/"+listOfFiles[i].getName());
        try {
          Scanner scnr = new Scanner(file);
          stocks.put(listOfFiles[i].getName(), new ArrayList<stock>());
          int j = 0;
          scnr.nextLine();
          while (scnr.hasNextLine()) {
            String line = scnr.nextLine();
            stocks.get(listOfFiles[i].getName()).add(new Driver().parseData(line));
            if(j>0)
            {
              ArrayList<stock> a = stocks.get(listOfFiles[i].getName());
              a.get(j).percentChange = ((a.get(j).High+a.get(j).Low)/2 - (a.get(j-1).High+a.get(j-1).Low)/2)/((a.get(j-1).High+a.get(j-1).Low)/2);
              //System.out.println("Percent change: "+ a.get(j).percentChange);
            }
            ++j;
            //System.out.println(line);
          }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
      } else if (listOfFiles[i].isDirectory()) {
        System.out.println("Directory " + listOfFiles[i].getName());
      }
    }

    //Now time to run the simulation

  }
}
