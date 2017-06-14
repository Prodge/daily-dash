
figwheel:
	lein figwheel

clean:
	lein clean

build: clean
	lein cljsbuild once min

remote-startup:
	ssh pi3@192.168.1.18 echo '"firefox /home/pi3/daily-dash/index.html" > startup.sh; chmod +x startup.sh'

remote-folders:
	-ssh pi3@192.168.1.18 "mkdir daily-dash"

deploy: build remote-folders remote-startup
	-ssh pi3@192.168.1.18 "rm -r ~/daily-dash/js/"
	scp -r resources/public/* pi3@192.168.1.18:~/daily-dash/

open-local:
	chromium resources/public/index.html
