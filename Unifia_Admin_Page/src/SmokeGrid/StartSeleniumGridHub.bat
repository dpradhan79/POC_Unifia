REM This will start selenium grid server and crome only node
cd %~dp0
start "HUB" /min cmd /k java -jar selenium-server-standalone-2.53.1.jar -role hub
Start "Node" /min cmd /k java -jar selenium-server-standalone-2.53.1.jar -role node -hub http://127.0.0.1:4444/grid/register -browser "browserName=chrome,version=ANY,maxInstances=10,platform=WINDOWS"