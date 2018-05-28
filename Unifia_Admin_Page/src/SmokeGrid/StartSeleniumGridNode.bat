REM This will start selenium grid Internet explorer node

cd "C:\Program Files (x86)\Java\jdk1.8.0_101\bin"

Start "Node" /min cmd /k java -jar %~dp0selenium-server-standalone-2.53.1.jar -role node -hub http://10.166.21.14:4444/grid/register -browser "browserName=internet explorer,version=11,platform=WINDOWS,maxInstances=10" -Dwebdriver.ie.driver=%~dp0IEDriverServer.exe