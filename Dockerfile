FROM gradle:8.1.1-jdk17

WORKDIR /app

COPY /app .

RUN gradle installDist

CMD /Users/uliaegorycheva/java-project-72/app