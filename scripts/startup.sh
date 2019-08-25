while ! nc -z mongo-database 27017;
        do
          echo sleeping;
          sleep 1;
        done;
        echo Connected!;
catalina.sh run
