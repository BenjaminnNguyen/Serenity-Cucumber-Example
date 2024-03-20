package tasks.common;

import au.com.bytecode.opencsv.CSVReader;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.actions.Upload;
import net.serenitybdd.screenplay.targets.Target;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommonFile {
    public static Performable upload(String fileName, Target target) {
        String path = "src/test/resources/Images/";
        File file = new File(path);
        Path path2 = Paths.get(file.getAbsolutePath() + "/" + fileName);
        try {
            return Upload.theFile(path2).to(target);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Performable upload2(String fileName, Target target) {
        String path = "src/test/resources/Images/";
        File file = new File(path);
        Path path2 = Paths.get(file.getAbsolutePath() + "/" + fileName);
        try {
            return Upload.theFile(path2).to(target);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Performable upload1(String fileName, Target target) {
        String path = "src/test/resources/files/data/";
        File file = new File(path);
        Path path2 = Paths.get(file.getAbsolutePath() + "/" + fileName);
        try {
            return Upload.theFile(path2).to(target);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Performable uploadAll(String fileName, Target target) {
        String path = "src/test/resources/files/";
        File file = new File(path);
        Path path2 = Paths.get(file.getAbsolutePath() + "/" + fileName);
        try {
            return Upload.theFile(path2).to(target);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Performable waitForTheFileToDownload(String fileName, int... timeWait) {
        return new Performable() {
            @Override
            public <T extends Actor> void performAs(T t) {
                int time = timeWait.length != 0 ? timeWait[0] : 10000;
                File dir = new File("target/");
                for (int i = 0; i < 20; i++) {
                    File[] dirContents = dir.listFiles();
                    Boolean check = false;
                    for (File f : dirContents) {
                        if (f.getName().equalsIgnoreCase(fileName))
                            check = true;
                    }
                    if (check == false) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("File " + fileName + " is downloaded");
                        break;
                    }
                }
            }
        };
    }

    public static Performable deleteFileToDownload(String fileName) {
        return new Performable() {
            @Override
            public <T extends Actor> void performAs(T t) {
                try {
                    File dir = new File("target/");
                    File[] dirContents = dir.listFiles();
                    for (int i = 0; i < dirContents.length; i++) {
                        if (dirContents[i].getName().equals(fileName)) {
                            dirContents[i].delete();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static Performable deleteFileToDownload2(String containsFileName) {
        return new Performable() {
            @Override
            public <T extends Actor> void performAs(T t) {
                try {
                    File dir = new File("target/");
                    File[] dirContents = dir.listFiles();
                    for (int i = 0; i < dirContents.length; i++) {
                        if (dirContents[i].getName().contains(containsFileName)) {
                            dirContents[i].delete();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static Question<Boolean> isFileDownloaded(String fileName) {

        return new Question<Boolean>() {
            @Override
            public Boolean answeredBy(Actor actor) {
                File dir = new File("target/");
                File[] dirContents = dir.listFiles();
                Boolean check = false;

                for (int i = 0; i < dirContents.length; i++) {
                    System.out.println(dirContents[i].getName());
                    if (dirContents[i].getName().equals(fileName)) {
                        System.out.println("File is downloaded");
                        check = true;
                        return check;
                    }
                }
                return check;
            }
        };
    }

    /***
     * return content of file CSV
     * @param file
     * @return
     */
    public static List<String[]> readDataLineByLine(String file) {
        List<String[]> allData = new ArrayList<>();
        try {
            // Create an object of filereader class
            // with CSV file as a parameter.
            FileReader filereader = new FileReader(file);
            // create csvReader object
            // and skip first Line
            CSVReader csvReader = new CSVReader(filereader);
//            FileReader fileReader = new
            allData = csvReader.readAll();
//            Map<String, String> map = allData.
            Serenity.setSessionVariable("CSV File").to(allData);
            // print Data
//            for (String[] row : allData) {
//                for (String cell : row) {
////                    System.out.print(cell + "\t");
//                }
//                System.out.println();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allData;
    }


    public static List<String[]> readDataExcelLineByLine(String file_) {
        List<String[]> allData = new ArrayList<>();
        try {
            File file = new File(file_);
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
            //creating Workbook instance that refers to .xlsx file
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file
            while (itr.hasNext()) {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
                List<String> ele = new ArrayList<>();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int a = cell.getCellType();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING://field that represents string cell type
                            ele.add(cell.getStringCellValue());
                            System.out.print(cell.getStringCellValue() + "\t\t\t");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type
                            ele.add(String.valueOf(cell.getNumericCellValue()));
                            System.out.print(cell.getNumericCellValue() + "\t\t\t");
                            break;
                        case Cell.CELL_TYPE_FORMULA:    //field that represents number cell type
                            ele.add(String.valueOf(cell.getCellFormula()));
                            System.out.print(cell.getNumericCellValue() + "\t\t\t");
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:    //field that represents number cell type
                            ele.add(String.valueOf(cell.getBooleanCellValue()));
                            System.out.print(cell.getNumericCellValue() + "\t\t\t");
                            break;
                        default:
                    }
                }
                allData.add(ele.toArray(new String[0]));
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return allData;
    }
}
