SET SRC_DIR=.
SET DST_DIR=..\main\java

protoc -I=%SRC_DIR% --java_out=%DST_DIR% %SRC_DIR%\yahoo.proto