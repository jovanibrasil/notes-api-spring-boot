while (! nc -z mongo-database 27017) & (! nc -z auth-api 8080);
        do
          echo sleeping;
          sleep 1;
        done;
        echo Connected!;
catalina.sh run
