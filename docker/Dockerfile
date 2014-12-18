from ubuntu:14.04
MAINTAINER kjansen
run echo "create DataCollector container"
run ping -c 1 www.google.com
run echo "Network is oke"
run sudo apt-get update
run apt-get -y install openjdk-7-jdk
run apt-get -y install openssh-server
run service ssh start
run useradd datacollector -m -d /home/datacollector -N -p "datacollector"
run echo 'export JAVA_HOME=/usr/lib/jvm/jre-1.7.0-openjdk.x86_64' > /etc/profile.d/java.sh
COPY ../buid/libs/*.jar /tmp

CMD ["java", "-jar", "/tmp/*.jar"]

EXPOSE 22