mvn clean package -Pprod -Dmaven.test.skip=true
sleep 2
scp /Users/renjie/IdeaProjects/YcAPI/target/YcApi.jar master@10.8.8.18:/home/master/yangcong/api-auto
ssh master@10.8.8.18
cd /home/master/yangcong/api-auto
./restart.sh
