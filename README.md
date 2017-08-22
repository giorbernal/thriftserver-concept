# thriftserver-concept
This is a test project to test a spark-hive-thrift-server from a simple .csv
To start the server follow next steps:
1. Change *Constants.work_path* to the folder that contains your *.csv* file.
2. Locate your CSV in previous folder and change *Constants.csv_file*
3. Choose a name for the work table and change *Constants.table_name*

Start Spark-Thrift-Server launching the Job:
> sbt run

This server can be accessed by using this [client](https://github.com/giorbernal/thriftclient)