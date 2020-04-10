nohup java -Xms1024m -Xmx2448m -jar ./YcApi.jar >/dev/null  &
echo ------------------------------------------------------------------------------------------------------------------------
sudo netstat -tunlp|grep 8081
echo "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\\n\n"
tail -f -n 3000 logs/runtime.$(date +%Y-%m-%d).log
