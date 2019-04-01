package Utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;  // Import the LocalDateTime class
import java.time.format.DateTimeFormatter;  // Import the DateTimeFormatter class


public class HtmlReport {

    private static String ReportFilePath;
    private static String currTestCaseDesc;
    private static String currTestCaseName;
    private static String appUserName;

    private static String testingEnvironment;

    private static String ScreenShotName;
    private static String resFolderName;
    private static int testCaseStatus;

    private static String testStepNo;

    // Initialize test step count
    private static int testStepPassCount;
    private static int testStepFailCount;
    private static int testStepWarningCount;

    // Reset test case error message
    private static String lastFailedStep;
    private static String lastWarningMessage;
    private static boolean filterSubSteps;

    // Reset excel log error message
    private static String testCaseMessage;

    // Initialize step numbering variables
    private static int level1Value;
    private static int level2Value;
    private static int level3Value;

    // Note the execution start time
    private static Date tCStartTime;

    private static BufferedWriter bwHtmlIndexFile = null;
    private static BufferedWriter bwHtmlReportFile = null;

    public static void startTestCaseReport(String tcName, String tcDesc) throws Exception {

        // Test Case Details
        currTestCaseDesc = tcDesc;
        currTestCaseName = tcName;
        appUserName = System.getProperty("user.name");

        // java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
        // System.out.println("Hostname of local machine: " + localMachine.getHostName());

        if (ReportFilePath == null) {
            resFolderName = generateResultsFolderName();
            // Report file path
            ReportFilePath = new File("").getAbsolutePath() + "\\HTML_Test_Results\\" + resFolderName;

            // Delete folder
            File directory = new File(ReportFilePath);
            directory.deleteOnExit();

            // Create folder
            directory.mkdirs();
        }

        // Create report file
        File htmlReportFile = new File(ReportFilePath + "\\" + currTestCaseName + ".htm");
        if (!htmlReportFile.exists()) {
            htmlReportFile.createNewFile();
            bwHtmlReportFile = new BufferedWriter(new FileWriter(htmlReportFile));
        }

        // Initilize test case count
        TestCaseReportVaraibles.testCasePassCount = 0;
        TestCaseReportVaraibles.testCaseFailCount = 0;
        TestCaseReportVaraibles.testCaseWarningCount = 0;

        TestCaseReportVaraibles.testCaseNo = 1;

        // Note the batch execution start time
        TestCaseReportVaraibles.batchStartTime = new Date();

        // Write header
        writeReportHeader();

        testStepNo = "1";
        // Initialize test step count
        testStepPassCount = 0;
        testStepFailCount = 0;
        testStepWarningCount = 0;

        // Reset test case error message
        lastFailedStep = "";
        lastWarningMessage = "";
        filterSubSteps = false;

        // Reset excel log error message
        testCaseMessage = "";

        // Initialize step numbering variables
        level1Value = 0;
        level2Value = 0;
        level3Value = 0;

        // Note the execution start time
        tCStartTime = new Date();

    }       // end of function - startTestCaseReport


    private static void writeReportHeader() throws Exception {

        // HTML report header
        bwHtmlReportFile.append("<html>");
        bwHtmlReportFile
                .append("<head><link rel='stylesheet' type='text/css' href='../ReportInfo/report_theme.css'></head>");
        bwHtmlReportFile.append("<body>");

        // Page header including project title & company name
        writeGenericPageHeader(bwHtmlReportFile);

        // Test case name, execution date & time and user id
        bwHtmlReportFile.newLine();
        bwHtmlReportFile.append("<tab><table class='subhead' width=900px><tr>");
        bwHtmlReportFile.append("<tab><tab><td width=400px class='subhead'>Test Case</td>");
        bwHtmlReportFile.append("<tab><tab><td width=200px class='subhead'>Execution Date</td>");
        bwHtmlReportFile.append("<tab><tab><td width=100px align=right class='subhead'>User ID</td>");
        bwHtmlReportFile.append("<tab></tr>");
        bwHtmlReportFile.append("<tab><tr>");
        bwHtmlReportFile.append("<tab><tab><td width=400px class='subcont'>" + currTestCaseName + "</td>");
        bwHtmlReportFile.append("<tab><tab><td width=200px class='subcont'>" + new Date() + "</td>");
        bwHtmlReportFile.append("<tab><tab><td width=100px align=right class='subcont'>" + appUserName + "</td>");
        bwHtmlReportFile.append("<tab></tr></table> <hr class='divline'> <BR>");

        // Test case description
        bwHtmlReportFile.newLine();
        bwHtmlReportFile.append("<tab><table class='subhead' width=900px><tr>");
        bwHtmlReportFile.append("<tab><tr><td width=900px class='subhead'>Test Case Desription</td></tr>");
        bwHtmlReportFile.append("<tab><tr><td width=900px class='subcont'>" + currTestCaseDesc + "</td></tr>");
        bwHtmlReportFile.append("<tab></tr></table> <hr class='divline'> <BR>");

        // Test steps table header
        bwHtmlReportFile.append("<br><br><!-- Test steps --><BR>");
        bwHtmlReportFile.append("<tab><table width=900px class='tsteps'>");
        bwHtmlReportFile.append("<tab><tr>");
        bwHtmlReportFile.append("<tab><tab><td class='tshead' width=75px>Step #</td>");
        bwHtmlReportFile.append("<tab><tab><td class='tshead' width=155px>Step Description</td>");
        bwHtmlReportFile.append("<tab><tab><td class='tshead' width=285px>Expected Result</td>");
        bwHtmlReportFile.append("<tab><tab><td class='tshead' width=285px>Actual Result</td>");
        bwHtmlReportFile.append("<tab><tab><td class='tshead' width=50px>Status</td>");
        bwHtmlReportFile.append("<tab><tab><td class='tshead' width=50px>Screen Shot</td>");
        bwHtmlReportFile.append("<tab></tr>");


    }   // end of function - writeReportHeader

    public static void initializeReporter(String testEnvironment) throws Exception {

        testingEnvironment = testEnvironment;
        resFolderName = generateResultsFolderName();

        // Report file path
        ReportFilePath = new File("").getAbsolutePath() + "\\HTML_Test_Results\\" + resFolderName;

        // Delete folder
        File directory = new File(ReportFilePath);
        directory.deleteOnExit();

        // Create folder
        directory.mkdirs();

        // Create index file
        File htmlIndexFile = new File(ReportFilePath + "\\index_TestSuite.htm");
        if (!htmlIndexFile.exists()) {
            htmlIndexFile.createNewFile();
            bwHtmlIndexFile = new BufferedWriter(new FileWriter(htmlIndexFile));
        }
        // Write index header
        WriteIndexHeader();

    }       // end of function - initializeReporter


    private static String generateResultsFolderName() throws Exception {

        // Date d = new Date();
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return "Results_" + formattedDate;


    }

    private static void WriteIndexHeader() throws Exception {

        // HTML Report header
        bwHtmlIndexFile.append("<html>");
        bwHtmlIndexFile.append(
                "<head><link rel='stylesheet' type='text/css' href='../ReportInfo/report_theme.css' /></head> <body>");

        // Page header including project title + company name
        writeGenericPageHeader(bwHtmlIndexFile);


        // Test case name and execution date + time
        bwHtmlIndexFile.newLine();
        bwHtmlIndexFile.append("<tab><table class='subhead' width=900px><tr>");
        bwHtmlIndexFile.append("<tab><tab><td width=400px class='subhead' align=left>Test Run</td>");
        bwHtmlIndexFile.append("<tab><tab><td width=200px class='subhead'>Execution Date</td>");
        bwHtmlIndexFile.append("<tab><tab><td width=100px class='subhead' align=right>Test Region</td>");

        bwHtmlIndexFile.append("<tab></tr><tr>");
        bwHtmlIndexFile.append("<tab><tab><td widht=400px class='subcont' align=left>" + resFolderName + "</td>");
        bwHtmlIndexFile.append("<tab><tab><td width=300px class='subcont'>" + new Date() + "</td>");
        bwHtmlIndexFile.append("<tab><tab><td widht=100px class='subcont' align=right>" + testingEnvironment + "</td>");
        bwHtmlIndexFile.append("<tab></tr></table><hr class='divline'> <BR>");

        // Test steps table header
        bwHtmlIndexFile.append("<br><br><!-- Test steps --><BR>");
        bwHtmlIndexFile.append("<tab><table width=900 class='tsteps'>");
        bwHtmlIndexFile.append("<tab><tr>");
        bwHtmlIndexFile.append("<tab><tab><td class='tshead' width=50px>SNo</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tshead' width=200px>Test Case</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tshead' width=300px>Description</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tshead' width=50px>Steps</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tshead' width=50px>Passed</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tshead' width=50px>Warnings</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tshead' width=50px>Failed</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tshead' width=50px>Status</td>");
        bwHtmlIndexFile.append("<tab></tr>");

    }       // end of function - WriteIndexHeader

    // Write page header
    private static void writeGenericPageHeader(BufferedWriter hTMLFile) {

        try {
            hTMLFile.newLine();
            hTMLFile.append("<tab><hr class='divline'>");
            hTMLFile.append("<tab><table class='rephead' width='900px'><tr>");
            hTMLFile.append("<tab><tab><td height=63px>" + "Automation Project" + "</td>");
            hTMLFile.append(
                    "<tab><tab><td height=63px align=right><img src = ..\\ReportInfo\\Images\\information.gif></td>");
            hTMLFile.append("<tab></tr></table><hr class='divline'><BR>");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }   // end of function -writeGenericPageHeader

    private static void writeReportFooter() throws Exception {

        // Test steps table header
        bwHtmlReportFile.append("<tab></table>");

        // Blank line
        bwHtmlReportFile.append("<BR><BR>");

        // Link to index page and other links
        bwHtmlReportFile.append("<br><br><!-- links to index and other pages-->");
        bwHtmlReportFile.append("<tab><table class='tblinks' width=250px align=left>");
        bwHtmlReportFile.append("<tab><tab><tr><td class='tshead'>Links</td></tr>");
        bwHtmlReportFile
                .append("<tab><tab><tr><td class='pfind'><a href='index_TestSuite.htm'>Index Page</a></td></tr>");
        bwHtmlReportFile.append("<tab></table>");

        // TC Execution time
        bwHtmlReportFile.append("<br><br><!-- TC Execution time-->");
        bwHtmlReportFile.append("<tab><table width=250px class='tbtime'>");
        bwHtmlReportFile.append("<tab><tr><td colspan=2 class='tshead'>Execution Time</td></tr>");
        bwHtmlReportFile.append("<tab><tr>");
        bwHtmlReportFile.append("<tab><tab><td class='pfhead' width=120px>Start Time</td>");
        bwHtmlReportFile.append("<tab><tab><td class='pfind' width=130px>" + tCStartTime + "</td>");
        bwHtmlReportFile.append("<tab></tr><tr>");
        bwHtmlReportFile.append("<tab><tab><td class='pfhead' width=120px>End Time</td>");
        bwHtmlReportFile.append("<tab><tab><td class='pfind' width=130px>" + new Date() + "</td>");
        bwHtmlReportFile.append("<tab></tr><tr>");
        String duration = GetTimeDifference(new Date(), tCStartTime);
        bwHtmlReportFile.append("<tab><tab><td class='pfhead' width=120px>Duration</td>");
        bwHtmlReportFile.append("<tab><tab><td class='pfind' width=130px>" + duration + "</td>");
        bwHtmlReportFile.append("<tab></tr></table>");

        // Report pass faile count
        bwHtmlReportFile.append("<br><br><!-- pass fail count-->");
        bwHtmlReportFile.append("<tab><table width=250px class='pfsummary'>");
        bwHtmlReportFile.append("<tab><tr><td colspan=2 class='tshead'>Test Case Summary</td></tr>");
        bwHtmlReportFile.append("<tab><tr>");
        bwHtmlReportFile.append("<tab><tab><td class='pfhead' width=200px>Total Steps</td>");
        bwHtmlReportFile.append("<tab><tab><td class='pfind' width=50px>"
                + (testStepPassCount + testStepFailCount + testStepWarningCount) + "</td>");
        bwHtmlReportFile.append("<tab></tr><tr>");
        bwHtmlReportFile.append("<tab><tab><td class='pfhead' width=200px>Steps Passed</td>");
        bwHtmlReportFile.append("<tab><tab><td class='pfind' width=50px>" + testStepPassCount + "</td>");
        bwHtmlReportFile.append("<tab></tr><tr>");
        bwHtmlReportFile.append("<tab><tab><td class='pfhead' width=200px>Warnings</td>");
        bwHtmlReportFile.append("<tab><tab><td class='pfind' width=50px>" + testStepWarningCount + "</td>");
        bwHtmlReportFile.append("<tab></tr><tr>");
        bwHtmlReportFile.append("<tab><tab><td class='pfhead' width=200px>Steps Failed</td>");
        bwHtmlReportFile.append("<tab><tab><td class='pfind' width=50px>" + testStepFailCount + "</td>");
        bwHtmlReportFile.append("<tab></tr></table><BR><BR>");

        // HTML Footer
        bwHtmlReportFile.append("</body></html>");

    }       // end of function - writeReportFooter

    private static void reportTestCaseIndex() throws Exception {

        if (bwHtmlIndexFile == null)
            return;
        // Write index
        bwHtmlIndexFile.append("<tab><tr>");
        bwHtmlIndexFile
                .append("<tab><tab><td class='tsind' width=50px>" + TestCaseReportVaraibles.testCaseNo + "</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tsnorm' width=200px><a class='tcindex' href='" + currTestCaseName
                + ".htm'>" + currTestCaseName + "</a></td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tsnorm' width=300px>" + currTestCaseDesc + "</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tsind' width=50px>"
                + (testStepPassCount + testStepFailCount + testStepWarningCount) + "</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tsind' width=50px>" + testStepPassCount + "</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tsind' width=50px>" + testStepWarningCount + "</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='tsind' width=50px>" + testStepFailCount + "</td>");
        TestCaseReportVaraibles.testCaseNo = TestCaseReportVaraibles.testCaseNo + 1;


        // Increment count
        if ((testCaseStatus == TestCaseStatus.micPass) && (testStepWarningCount == 0)) {
            bwHtmlIndexFile.append(
                    "<tab><tab><td class='tsind' width=50px><img src = '../ReportInfo/images/pass.gif' width='20' height='20'></td>");
            TestCaseReportVaraibles.testCasePassCount = TestCaseReportVaraibles.testCasePassCount + 1;
        } else if (testCaseStatus == TestCaseStatus.micWarning
                || (testStepWarningCount > 0 && testCaseStatus == TestCaseStatus.micPass)) {
            bwHtmlIndexFile.append(
                    "<tab><tab><td class='tsind' width=50px><img src = '../ReportInfo/images/warning.gif' width='20' height='20'></td>");
            testCaseStatus = TestCaseStatus.micWarning;
            TestCaseReportVaraibles.testCaseWarningCount = TestCaseReportVaraibles.testCaseWarningCount + 1;
        } else {
            bwHtmlIndexFile.append(
                    "<tab><tab><td class='tsind' width=50px><img src = '../ReportInfo/images/fail.jpg' width='20' height='20'></td>");
            TestCaseReportVaraibles.testCaseFailCount = TestCaseReportVaraibles.testCaseFailCount + 1;
        }
        bwHtmlIndexFile.append("<tab></tr>");

    }       // end of function - reportTestCaseIndex


    private static void writeIndexFooter() throws Exception {

        // Test steps table header
        bwHtmlIndexFile.append("<tab></table>");

        // Line breaks
        bwHtmlIndexFile.append("<tab><tab><BR><BR>");


        // Batch Execution time
        bwHtmlIndexFile.append("<BR><BR><!-- Batch Execution time-->");
        bwHtmlIndexFile.append("<tab><table width=250px class='tbtime'>");
        bwHtmlIndexFile.append("<tab><tr><td colspan=2 class='tshead'>Execution Time</td></tr>");
        bwHtmlIndexFile.append("<tab><tr>");
        bwHtmlIndexFile.append("<tab><tab><td class='pfhead' width=120px>Start Time</td>");
        bwHtmlIndexFile
                .append("<tab><tab><td class='pfind' width=130px>" + TestCaseReportVaraibles.batchStartTime + "</td>");
        bwHtmlIndexFile.append("<tab></tr><tr>");
        bwHtmlIndexFile.append("<tab><tab><td class='pfhead' width=120px>End Time</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='pfind' width=130px>" + new Date() + "</td>");
        bwHtmlIndexFile.append("<tab></tr><tr>");
        String duration = GetTimeDifference(new Date(), TestCaseReportVaraibles.batchStartTime);
        bwHtmlIndexFile.append("<tab><tab><td class='pfhead' width=120px>Duration</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='pfind' width=130px>" + duration + "</td>");
        bwHtmlIndexFile.append("<tab></tr></table>");


        // Report pass fail count
        bwHtmlIndexFile.append("<br><br><!-- pass fail count-->");
        bwHtmlIndexFile.append("<tab><table width=250 class='pfsummary'>");
        bwHtmlIndexFile.append("<tab><tr><td colspan=2 class='tshead'>Test Cases Summary</td></tr>");
        bwHtmlIndexFile.append("<tab><tr>");
        bwHtmlIndexFile.append("<tab><tab><td class='pfhead' width=200px>Total Test Cases</td>");
        bwHtmlIndexFile.append("<tab><tab><td class='pfind' width=50px>" + (TestCaseReportVaraibles.testCasePassCount
                + TestCaseReportVaraibles.testCaseFailCount + TestCaseReportVaraibles.testCaseWarningCount) + "</td>");
        bwHtmlIndexFile.append("<tab></tr><tr>");
        bwHtmlIndexFile.append("<tab><tab><td class='pfhead' width=200px>Test Cases Passed</td>");
        bwHtmlIndexFile.append(
                "<tab><tab><td class='pfind' width=50px>" + TestCaseReportVaraibles.testCasePassCount + "</td>");
        bwHtmlIndexFile.append("<tab></tr><tr>");
        bwHtmlIndexFile.append("<tab><tab><td class='pfhead' width=200px>Passed with Warnings</td>");
        bwHtmlIndexFile.append(
                "<tab><tab><td class='pfind' width=50px>" + TestCaseReportVaraibles.testCaseWarningCount + "</td>");
        bwHtmlIndexFile.append("<tab></tr><tr>");
        bwHtmlIndexFile.append("<tab><tab><td class='pfhead' width=200px>Test Cases Failed</td>");
        bwHtmlIndexFile.append(
                "<tab><tab><td class='pfind' width=50px>" + TestCaseReportVaraibles.testCaseFailCount + "</td>");
        bwHtmlIndexFile.append("<tab></tr></table>");

        // HTML Footer
        bwHtmlIndexFile.append("</body> </html>");


    }       // end of function - writeIndexFooter

    private static String GetTimeDifference(Date startDate, Date endDate) {

        long duration = startDate.getTime() - endDate.getTime();
        double diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        double diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        return diffInMinutes + " min " + diffInSeconds + " sec";
    }

    public static void endTestCaseReport() throws Exception {
        // Complete the test case report
        writeReportFooter();

        // Close + release the report file
        bwHtmlReportFile.close();

        // Write the test case status to the index file
        reportTestCaseIndex();
    }

    public static void closeReporter() throws Exception {
        // Complete index information
        writeIndexFooter();

        // Close and release the index file
        bwHtmlIndexFile.close();
    }

    public static void reportHeaderStep(String headerStep) {
        try {
            HtmlReport.reportStep("1", headerStep, "", "");
        } catch (Exception e) {
            // Do nothing
        }
    }

    public static void reportStep(String StepName, String ExpectedResult, String ActualResult) {
        try {
            HtmlReport.reportStep("2", StepName, ExpectedResult, ActualResult);
        } catch (Exception e) {
            // Do nothing
        }
    }


    public static int reportStep(String StepLevel, String StepName, String ExpectedResult, String ActualResult)
            throws Exception {

        String ClassForStepNo, ClassForStepDesc, ClassForIcons;

        // Assign test step number
        testStepNo = getTestStepNo(StepLevel);
        // Initilize
        String CurrStepFailed = "";
        // Reporting of failed step in HTML report

        if (testCaseStatus == TestCaseStatus.micFail) {
            if (lastFailedStep == "") {
                if (lastWarningMessage != "")
                    ActualResult = ActualResult + "<br><BR>Message: " + lastWarningMessage;
                lastFailedStep = StepName + ", " + testStepNo;
                CurrStepFailed = "fail";
                if (StepLevel != "1") {
                    testStepPassCount = testStepPassCount - 1;
                    testStepFailCount = testStepFailCount + 1;
                }
                // Backup the failure message for excel log
                testCaseMessage = testCaseMessage + ActualResult;
            } else {
                if (StepLevel == "1")
                    filterSubSteps = true;
                else if (filterSubSteps == true) {
                    return TestCaseStatus.micPass;
                }
                // Mask failure step for sub setquent steps
                ActualResult = "Since step [" + lastFailedStep + "] failed, this step has not been executed";
            }
        } else if (testCaseStatus == TestCaseStatus.micWarning) {
            if (lastFailedStep == "") {
                if (lastWarningMessage != "")
                    ActualResult = ActualResult + " <BR><BR>Message: " + lastWarningMessage;

                lastFailedStep = StepName + ", " + testStepNo;
                CurrStepFailed = "fail";
                if (StepLevel != "1") {
                    testStepPassCount = testStepPassCount - 1;
                    testStepWarningCount = testStepWarningCount + 1;
                }
            } else
                ActualResult = "Since step [" + lastFailedStep + "] has not passed, this step has not been executed";
        }

        ClassForStepNo = "" + "tsind" + CurrStepFailed + "lvl" + StepLevel + "";
        if (StepLevel == "1") {
            ClassForStepDesc = "tsnorm" + CurrStepFailed + "lvl1";
            ClassForIcons = "tsind" + CurrStepFailed + "lvl1";
        } else {
            ClassForStepDesc = "tsnorm" + CurrStepFailed + "";
            ClassForIcons = "tsind" + CurrStepFailed + "";
        }

        bwHtmlReportFile.append("<tab><tr>");
        bwHtmlReportFile.append("<tab><tab><td class=" + ClassForStepNo + " width=75px>" + testStepNo + "</td>");
        bwHtmlReportFile.append("<tab><tab><td class=" + ClassForStepDesc + " width=155px>" + StepName + "</td>");
        bwHtmlReportFile.append("<tab><tab><td class=" + ClassForStepDesc + " width=285px>" + ExpectedResult + "</td>");
        bwHtmlReportFile.append("<tab><tab><td class=" + ClassForStepDesc + " width=285px>" + ActualResult + "</td>");
        if (testCaseStatus == TestCaseStatus.micPass) {
            bwHtmlReportFile.append("<tab><tab><td class=" + ClassForIcons
                    + " width=50px><img class='screen' src = '../ReportInfo/images/pass.gif'></td>");
            if (StepLevel == "1")
                testStepPassCount = testStepPassCount + 1;
        } else if (testCaseStatus == TestCaseStatus.micWarning) {
            bwHtmlReportFile.append("<tab><tab><td class=" + ClassForIcons
                    + " width=50px><img class='screen' src = '../ReportInfo/images/warning.gif'></td>");
            if (StepLevel == "1")
                testStepWarningCount = testStepWarningCount + 1;
        } else if (testCaseStatus == TestCaseStatus.micFail) {
            bwHtmlReportFile.append("<tab><tab><td class=" + ClassForIcons
                    + " width=50px><img class='screen' src = '../ReportInfo/images/fail.jpg'></td>");
            if (StepLevel == "1")
                testStepFailCount = testStepFailCount + 1;
        }

        if (ScreenShotName != "") {
            bwHtmlReportFile.append("<tab><tab><td class=" + ClassForIcons
                    + " width=50px><a target='_blank' class='anibutton' href='" + ScreenShotName
                    + "'><img class='screen' src = '../ReportInfo/images/screenshot.gif'></a></td>");
            ScreenShotName = "";
        } else {
            bwHtmlReportFile.append("<tab><tab><td class=" + ClassForIcons + " width=50px>&nbsp</td>");
        }
        bwHtmlReportFile.append("<tab></tr>");

        return TestCaseStatus.micPass;


    }   // end of function - reportStep


    public static int reportWarning(String WarningType, String WarningMessage) throws Exception {

        bwHtmlReportFile.append("<tab><tr>");
        if (WarningType == "vbCritical") {
            testStepWarningCount = testStepWarningCount + 1;
            bwHtmlReportFile.append("<tab><tab><td class=tsimgalert width=75px><img class=message src = '../ReportInfo/images/warning.gif'></td>");
            bwHtmlReportFile.append("<tab><tab><td colspan=5 class=tswarning width=825px><b class=highlight>[Step: " + getTestStepNo("1") + "] Warning!</b><BR>" + WarningMessage + "</td>");

            //Backup the failure message for excel log
            testCaseMessage = testCaseMessage + WarningMessage;
        } else {
            bwHtmlReportFile.append("<tab><tab><td class=tsimgalert width=75px><img class=message src = '../ReportInfo/images/information.gif'></td>");
            bwHtmlReportFile.append("<tab><tab><td colspan=5 class=tsinfo width=825px><b class=highlight>Information!</b><BR>" + WarningMessage + "</td>");
        }
        bwHtmlReportFile.append("<tab></tr>");

        return TestCaseStatus.micPass;

    }   // end of function -reportWarning

    private static String getTestStepNo(String StepLevel) {
        if (StepLevel == "1") {
            level1Value = level1Value + 1;
            level2Value = 0;
            level3Value = 0;
            return level1Value + "";
        } else if (StepLevel == "2") {
            level2Value = level2Value + 1;
            level3Value = 0;
            return level1Value + "." + level2Value;
        } else if (StepLevel == "3") {
            level3Value = level3Value + 1;
            return level1Value + "." + level2Value + "." + level3Value;
        } else {
            return "";
        }


    }       // end of function  -getTestStepNo


    public static void takeScreenShot(WebDriver driver) {
        testCaseStatus = 1;
        ScreenShotName = ReportFilePath + "\\" + currTestCaseName + ".png";
        if (new File(ScreenShotName).exists()) {
            ScreenShotName = ReportFilePath + "\\" + currTestCaseName + "_1.png";
        }
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(ScreenShotName));
        } catch (Exception e) {
            System.out.println("Failed to take browser screenshot :" + e.getMessage());
        }


    }       // end of function - takeScreenShot

}
     class TestCaseReportVaraibles{

        //Initilize test case count
         static int testCasePassCount;
         static int testCaseFailCount;
         static int testCaseWarningCount;

         static int	testCaseNo;

        //Note the batch execution start time
         static Date batchStartTime;
    }

    class TestCaseStatus{
        public static final int micFail=1;
        public static final int micPass=0;
        public static final int micWarning =3;
    }





