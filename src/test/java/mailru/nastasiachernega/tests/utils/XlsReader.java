package mailru.nastasiachernega.tests.utils;

import com.codeborne.xlstest.XLS;
import mailru.nastasiachernega.tests.data.testData.TestData;

import java.io.InputStream;

public class XlsReader {

    ClassLoader classLoader = TestData.class.getClassLoader();

    public String getCellContent(String testDataFile, int exampleNumber, int columnNumber) throws Exception {
        try (InputStream stream = classLoader.getResourceAsStream(testDataFile)) {
            XLS xls = new XLS(stream);
            return xls.excel.getSheetAt(0).getRow(exampleNumber+1).
                    getCell(columnNumber).getStringCellValue();
        }

    }

}
