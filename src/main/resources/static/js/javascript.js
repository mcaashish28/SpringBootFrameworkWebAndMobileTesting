function getTestCaseName(){
    var testcase1 = document.getElementById("testcase1").value;
    var testcase2 = document.getElementById("testcase2").value;
    alert("Testcase1" + testcase1 + " : Testcase2" + testcase2);
    var inputtext = document.getElementById("text1");
    inputtext.value= testcase1+testcase2;
}