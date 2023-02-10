#!/bin/bash
echo "start create keystore.jks !"
keytool -genkey -dname "CN=XXX, OU=XXX, O=XXX, L=XXX, ST=XXX, C=XXX" -v -keystore keystore.jks -storepass 123456 -alias test -keypass 123456 -keyalg RSA -keysize 2048 -validity 35600
