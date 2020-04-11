 # **Symphonia**
<br>

## 1-Installation:

  &nbsp;&nbsp;&nbsp; Just Download [Android Studio](https://developer.android.com/studio?hl=ru#downloads), it's available for different OS.
  <br>
  
## 2-Install packages
  
  &nbsp;&nbsp;&nbsp; 1-Open **Android studio** then select **File** and press **open** then choose project directory <br>
  &nbsp;&nbsp;&nbsp; 2-Wait untill **gradle files** are setup automatically  <br>
  &nbsp;&nbsp;&nbsp; 3-Click in top left on item **1:project** <br>
  &nbsp;&nbsp;&nbsp; 3-then select **Android** from the dropdown list <br>
  &nbsp;&nbsp;&nbsp; 4-select **Gradle Scripts**, then open **build.gradle(Module:App)** <br>
  &nbsp;&nbsp;&nbsp; 5-on top right, select **sync now**, if it's not visible, so all packages are installed. <br>
  
## 3-Running project
  
  &nbsp;&nbsp; **NOTE**: make sure your phone is connected with USB and open **developer/debugger mode** in your mobile settings
  
  &nbsp;&nbsp;&nbsp; 1-In middle top of screen choose **app** from the dropdown list  <br> 
  &nbsp;&nbsp;&nbsp; 2-Select **Device** to run app on it *(If your phone is not available, choose built-in emulator)* <br>
  &nbsp;&nbsp;&nbsp; 3-Press in **make** item, finally press on **run** item (takes few minutes in first time)
  <br>
  
## 4-Running Unit Tests, Code Coverage and Report generation 
   #### 4.1-Running Unit Tests:
   &nbsp;&nbsp;&nbsp; in **explorer** >> **app** >> **Java** >> **com.example.symphonia** >> right click >> **run Tests**
  
   #### 4.2-Coverage Report Generation:
   &nbsp;&nbsp;&nbsp; Choose **Gradle** in top right corner >> double click on **app** >> click on **execute gradle task** icon <br>
   &nbsp;&nbsp;&nbsp; Write this command **createDebugAndroidTestCoverageReport** and wait untill building is finshed <br>
   &nbsp;&nbsp;&nbsp; Report generated in >> **app** >> **build** >> **reports** >> **coverage** >> **debug** open **index.html**
  <br>
  
## 5- Generate function documentation

  &nbsp;&nbsp;&nbsp; Click on **Tools** >> choose **Generate JavaDoc** >> select **Whole Project** >> **OK**
  <br>
   
