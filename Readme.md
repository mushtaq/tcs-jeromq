
TCS JeroMQ Demo
===============

Downing
-------

Use VisualVM to identify and mark a node down after you make it crash from the command line


Run Hcd
-------

	./activator "runMain tmt.demo.hcd.HcdService --port 2552"

Run Command Client
------------------

	./activator "runMain tmt.demo.apps.command_client.DemoApp --port 2542"
	./activator "runMain tmt.demo.apps.command_client.McsSimulator --system mcs"

Run Event Publisher
-------------------

	./activator "runMain tmt.demo.apps.event_publisher.DemoApp --port 2543"
	./activator "runMain tmt.demo.apps.event_publisher.McsSimulator --system mcs"

Run Event Subscriber
--------------------

	./activator "runMain tmt.demo.apps.event_subscriber.DemoApp --port 2544"
	./activator "runMain tmt.demo.apps.event_subscriber.McsSimulator --system mcs"


Command Client via Shell
------------------------

	import tcsstr2.Transition
	import tmt.app.configs.{Params, Assembly}

	val assembly = new Assembly(Params(port = 2772))
	import assembly._

	commandsClient

	commandsClient.lifecycle(Transition.REBOOT).onComplete(println)
