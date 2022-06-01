;;; Sierra Script 1.0 - (do not remove this comment)
(script# rIntro1)
(include game.sh) (include "600.shm")
(use Main)
(use DoDisp)
(use HandsOffScript)
(use String)
(use DText)
(use Print)
(use PolyPath)
(use Game)
(use System)

(public
	intro1 0
)

(local
	str
)

(instance headingPrint of Print
	(properties
		modeless DLG_MODELESS
		back 0
		font 8
		fore 48
	)
)

(instance intro1 of Room
	(properties
		picture scriptNumber
	)
	
	(method (init)
		(super init:)
		(ego
			posn: 155 132
			setLoop: loopN
			init:
			normalize:
		)
		(self setScript: doTheIntro)
	)
)



(instance doTheIntro of HandsOffScript
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= seconds 5)
			)
			(1
				;(Prints {Sometime between QFG4 and QFG5...})
				(= str (String new:))
				(Message MsgGet scriptNumber N_INTRO NULL C_HEADING 1 (str data?))
				(headingPrint
					posn: -1 40
					mode: teJustCenter
					ticks: 200
					addText: (str data?)
					init:
				)
				(= ticks 240)
			)
			(2
				(messager say: N_INTRO NULL C_SUCCESSES 0 self)
			)
			(3
				(messager say: N_INTRO NULL C_FLOPS 0 self)
			)
			
			(4
				(messager say: N_INTRO NULL C_HEAD_OUT 0 self)
			)
			(5
				(ego setMotion: PolyPath -10 133 self)
			)
			(6
				(curRoom newRoom: rIntro2)
			)
		)
	)
)
