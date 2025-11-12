SET MONGODB_HOME=D:\DEVEL\stage\opt\mongodb-7.0.4
SET PATH=%MONGODB_HOME%\bin;%PATH%
%MONGODB_HOME%\bin\mongod --replSet rs0 --dbpath=%MONGODB_HOME%\data\db

mongo-cli.bat:
> rs.initiate();