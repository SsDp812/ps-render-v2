FROM ubuntu:20.04
WORKDIR /app
COPY ./target/psapp-0.0.1-SNAPSHOT.jar /app
EXPOSE 8084

# Установка Java 11
RUN apt-get update && \
    apt-get install -y openjdk-11-jdk

# Установка Chrome
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt install -y ./google-chrome-stable_current_amd64.deb

# Установка ChromeDriver
RUN apt-get install -y unzip && \
    wget https://chromedriver.storage.googleapis.com/93.0.4577.15/chromedriver_linux64.zip && \
    unzip chromedriver_linux64.zip && \
    mv chromedriver /usr/local/bin/chromedriver && \
    chmod +x /usr/local/bin/chromedriver


# Set up Selenium WebDriver
ENV CHROME_BIN=/usr/bin/google-chrome
ENV CHROME_DRIVER=/usr/local/bin/chromedriver

# Command to run the application
CMD ["java", "-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "psapp-0.0.1-SNAPSHOT.jar"]
