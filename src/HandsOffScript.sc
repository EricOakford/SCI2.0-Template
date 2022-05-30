;;; Sierra Script 1.0 - (do not remove this comment)
(script# HANDSOFFSCRIPT)
(include game.sh)
(use Main)
(use System)


(class HandsOffScript of Script
	(properties
		saveIgnrAct 0
	)
	
	(method (init)
		(theGame handsOff:)
		(= saveIgnrAct (& (ego signal?) ignrAct))
		(ego ignoreActors: TRUE)
		(super init: &rest)
	)
	
	(method (dispose)
		(theGame handsOn:)
		(ego ignoreActors: saveIgnrAct)
		(super dispose:)
	)
)

