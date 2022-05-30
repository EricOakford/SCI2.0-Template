;;; Sierra Script 1.0 - (do not remove this comment)
(script# NOTICE)
(include game.sh) (include "18.shm")
(use Main)
(use Game)
(use System)

(public
	noticeRoom 0
)

(instance noticeRoom of Room
	(properties
		picture scriptNumber
		style SHOW_VSHUTTER_IN
	)
	
	(method (init)
		(super init:)
		(theMusic
			number: sOpening
			play:
		)
		(self setScript: sayRights)
	)
)

(instance sayRights of Script
	(method (changeState newState)
		(switchto (= state newState)
			(
				(= cycles 2)
			)
			(
				(messager say: N_ROOM NULL C_RIGHTS 0 self)
			)
			(
				(curRoom newRoom: TITLE)
			)
		)
	)
)