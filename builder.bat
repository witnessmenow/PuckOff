
@echo off

:start
cls
ECHO ============MAIN MENU============
ECHO -------------------------------------
echo 1. Build Desktop Application
echo 2. Run Desktop Application
echo 3. Package HTML Application
echo 4. Build and Package Desktop app for Sharing
echo 5. Build Android
echo 6. Build and Deploy Android
echo 8. Maven Clean
ECHO -------------------------------------
set INPUT=
set /P INPUT=Please select an option:

IF /I '%INPUT%'=='1' GOTO BuildDesktop
IF /I '%INPUT%'=='2' GOTO runDesktop
IF /I '%INPUT%'=='3' GOTO buildHtml
IF /I '%INPUT%'=='4' GOTO package
IF /I '%INPUT%'=='5' GOTO BuildAndroid
IF /I '%INPUT%'=='6' GOTO Android
IF /I '%INPUT%'=='8' GOTO MavenClean

ECHO ============INVALID INPUT============
ECHO -------------------------------------
ECHO Please select a number from the Main
echo Menu [1-9] or select 'Q' to quit.
ECHO -------------------------------------
ECHO ======PRESS ANY KEY TO CONTINUE======

PAUSE > NUL
GOTO start

:Android
call %M2%/mvn -Pandroid install
pause
GOTO start

:BuildAndroid
call %M2%/mvn -Pandroid package
pause
GOTO start

:BuildDesktop

call %M2%/mvn -Pdesktop package

ECHO ============POST DESKTOP INSTALL MENU============
ECHO -------------------------------------
echo 1. Run Desktop Application
echo 2. Re-Build Desktop Application
echo Or Select Any Other Option To Return To Main Menu
ECHO -------------------------------------
set INPUT=
set /P INPUT=Please select an option:
IF /I '%INPUT%'=='1' GOTO runDesktop
IF /I '%INPUT%'=='2' GOTO BuildDesktop
GOTO start

:runDesktop

call "%JAVA_HOME%/bin/java" -jar desktop/target/puckoff-desktop-1.0-SNAPSHOT-jar-with-dependencies.jar
pause
GOTO start

:buildHtml
call %M2%/mvn -Phtml package > output.txt
pause
GOTO start


:MavenClean
call %M2%/mvn clean
pause
GOTO start

:package
call %M2%/mvn -Pdesktop package
mkdir game
xcopy Launcher.bat game
xcopy "desktop/target/soccar-desktop-1.0-SNAPSHOT-jar-with-dependencies.jar" game
pause
GOTO start
