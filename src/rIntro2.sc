;;; Sierra Script 1.0 - (do not remove this comment)
(script# rIntro2)
(include game.sh) (include "601.shm")
(use Actor)
(use HandsOffScript)
(use String)
(use Game)
(use Print)
(use Main)
(use PolyPath)
(use Scaler)
(use System)

(public
	intro2 0
)

(local
	str
)

(instance headingPrint of Print
	(properties
		back 0
		font 8
		fore 48
	)
)

(instance intro2 of Room
	(properties
		picture scriptNumber
	)
	
	(method (init)
		(super init:)
		(ego
			posn: 0 151
			init:
			normalize:
			setScale: 175
		)
		(self setScript: doTheIntro)
	)
)

(instance doTheIntro of HandsOffScript
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= str (String new:))
				(ego
					setMotion: PolyPath 55 151
					self
				)
			)
			(1
				(Message MsgGet scriptNumber N_INTRO NULL NULL 1 (str data?))
				(headingPrint
					modeless: DLG_MODELESS
					posn: -1 40
					mode: teJustCenter
					addText: (str data?)
					init:
				)
				(ego
					setMotion: PolyPath 177 149
					self
				)
			)
			(2
				(Message MsgGet scriptNumber N_INTRO NULL NULL 2 (str data?))
				(headingPrint
					modeless: DLG_MODELESS
					posn: -1 40
					mode: teJustCenter
					addText: (str data?)
					init:
				)
				(ego
					setMotion: PolyPath 255 161
					self
				)
			)
			(3
				(Message MsgGet scriptNumber N_TITLE NULL NULL 1 (str data?))
				(headingPrint
					modeless: DLG_MODELESS
					posn: -1 40
					mode: teJustCenter
					addText: (str data?)
					init:
				)
				(ego
					setMotion: PolyPath 330 175
					self
				)
			)
			(4
				(ego setScale: 0)
				(curRoom newRoom: CHARSEL)
			)
		)
	)
)
