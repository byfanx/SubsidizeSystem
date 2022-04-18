# !/usr/bin/env bash
# subsidizeSystem服务自动检测jar，然后构建并部署
function reDeploy() {
        executePath=$1
        logFile=$2

        cd $executePath
        echo "docker stop subsidizeSystem ==>"  >> $logFile
        docker stop subsidizeSystem  >> $logFile
        echo "docker rm subsidizeSystem ==>"  >> $logFile
        docker rm subsidizeSystem  >> $logFile
        echo "docker rmi subsidize_system:1.0 ==>"  >> $logFile
        docker rmi subsidize_system:1.0  >> $logFile
        echo "docker build -t subsidize_system:1.0 . ==>"  >> $logFile
        docker build -t subsidize_system:1.0 .  >> $logFile
        echo "docker run ==>"  >> $logFile
        dockerId=$(docker run -d -p 9000:9000 -v $executePath/static:/home --name subsidizeSystem subsidize_system:1.0)
        echo $dockerId >> $logFile
}


function main() {
        path="/root/subsidizeSystem"
        file="SubsidizeSystem-0.0.1-SNAPSHOT.jar"
        logFile=$path/automaticDeployment.log
        fileMd5=$(md5sum $file | awk '{print $1}')
        i=1
        while [ true ]
        do
                md5=$(md5sum $file | awk '{print $1}')
                if [ "$fileMd5" != "$md5" ]; then
                        # echo "fileMd5 = $fileMd5" >> $logFile
                        # echo "md5 = $md5" >> $logFile
                        echo "$(date +'%Y-%m-%d %H:%M:%S')      第 $i 次发版  ----------------------------------------------" >> $logFile
                        reDeploy $path $logFile
                        i=$[$i+1]
                        fileMd5=$md5
                fi
                sleep 60s
        done
}

main "$@"
