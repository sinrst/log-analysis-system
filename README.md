Steps to download and test log-analysis-system application:
1. Clone github repository into some working directory like "C:\dev"
   git clone https://github.com/sinrst/log-analysis-system.git
2. Create sts project by importing maven project from 'C:\dev\log-analysis-system'
1. Download hsqldb-2.6.1.zip from https://sourceforge.net/projects/hsqldb/files/latest/download
2. Unzip dowloaded hsqldb-2.6.1.zip into to some working directory like "C:\dev"
3. Copy server.properties file from project root direcpty 'C:\dev\log-analysis-system' to 
   "C:\dev\hsqldb-2.6.1\hsqldb" directory
4. Goto 'C:\dev\hsqldb-2.6.1\hsqldb' location and run follwoing command in cmd\gitbash to create database and dbname
    java -classpath lib/hsqldb.jar org.hsqldb.server.Server
5. Now hsqldb is started and ready to use.
5. Run C:\cs\log-analysis-system\src\test\java\com\cs\analyser\service\EventServiceTest.java unit test to test the functionality. 
