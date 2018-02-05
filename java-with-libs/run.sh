#!/usr/bin/env bash

#
# compile and run with jdk selected on command line
#

jdk8_home=/usr/java/1.8
jdk9_home=/usr/java/1.9
openjdk8_home=/usr/lib/jvm/java-8-openjdk-amd64
openjdk9_home=/usr/lib/jvm/java-9-openjdk-amd64
doclean=0
profile=
mvn_options='-T 2.0C -Dmaven.test.skip=true'

script=$(basename $0)

function dohelp() {
    local exit_code=0
    [ $# -gt 0 ] && exit_code=$1
    cat << DOHELP
${script} [-h|--help] : this text
${script} jdk8|jdk9|openjdk8|openjdk9 [clean] : jdk selection and possible clean
            before compilation
DOHELP
    exit ${exit_code}
}

function onerror() {
    local exit_code=$1
    shift
    case ${exit_code} in
        0)
            echo "WARNING: $@" 1>&2
            ;;
        *)
            echo "ERROR: $@" 1>&2
            exit ${exit_code}
            ;;
    esac
}

[ $# -eq 0 ] && dohelp
while [ $# -ne 0 ]
do
    case $1 in
        -h|--help)
            dohelp
            ;;
        jdk8)
            export JAVA_HOME=${jdk8_home}
            profile=sunjdk8
            ;;
        jdk9)
            export JAVA_HOME=${jdk9_home}
            profile=sunjdk9
            ;;
        openjdk8)
            export JAVA_HOME=${openjdk8_home}
            profile=openjdk8
            ;;
        openjdk9)
            export JAVA_HOME=${openjdk9_home}
            profile=openjdk9
            ;;
        clean)
            doclean=1
            ;;
        *)
            onerror 1 "Unkown parameter '$1'"
            ;;
    esac
    shift
done

[[ -n ${profile} ]] && profile="-P $profile" && export PATH=${JAVA_HOME}/bin:$PATH
if [ ${doclean} -ne 0 ]
then
    mvn ${profile} clean || onerror "mvn clean failed"
fi
mvn ${mvn_options} ${profile} package || onerror 1 "mvn package failed"

here=${HOME}/git/recallMeJava/java-with-libs
m2=/home/bernard/.m2/repository

java -Xmx412m -classpath ${here}/target/recallMeJava-with-libs-1.0.0.jar:$m2/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:$m2/io/vavr/vavr/0.9.2/vavr-0.9.2.jar:$m2/io/vavr/vavr-match/0.9.2/vavr-match-0.9.2.jar bernard.tatin.JavaMemoryForTheKids


