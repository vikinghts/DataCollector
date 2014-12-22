DataCollector
=============

Get smartmeter data through the com interface

follow this link http://eclipsesource.com/blogs/2012/10/17/serial-communication-in-java-with-raspberry-pi-and-rxtx/

and this one http://jlog.org/rxtx-mac.html

yum install rxtx

when problems read this : http://stackoverflow.com/questions/22164784/noclassdeffounderror-could-not-initialize-class

if you get : java.lang.NoClassDefFoundError: Could not initialize class gnu.io

Make sure this or something similar is done : ln -s /usr/share/java/RXTXcomm.jar /usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/RXTXcomm.jar
