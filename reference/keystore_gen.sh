#!/bin/bash
echo "start create keystore.jks !"
curTime=$(date "+%Y%m%d%H%M%S")
storePassword="123456"
alias="tom${curTime}"
password="123456"
dirName="keyStoreDir"
mkdir $dirName
fileName="$dirName/${alias}-keystore.jks"
echo "alias 是：${alias}"
echo "password 是：${password}"
echo "storePassword 是：${storePassword}"
keytool -genkey -dname "CN=XXX${curTime}, OU=XXX, O=XXX, L=XXX, ST=XXX, C=XXX" -deststoretype pkcs12 -v -keystore $fileName -storepass $storePassword -alias $alias -keypass $password -keyalg RSA -keysize 2048 -validity 35600
echo "生成${fileName}成功"
