;;; Sierra Script 1.0 - (do not remove this comment)
;
;	DEATH.SC
;
;	This script handles death scenes, and is triggered by calling
;	the EgoDead procedure with a parameter corresponding to a death message..
;
;

(script# DEATH)
(include game.sh) (include "10.shm")
(use Main)
(use Print)
(use LoadMany)
(use Dialog)
(use DCIcon)
(use Sound)
(use Array)
(use Motion)
(use Game)
(use System)

(public
	deathRoom 0
)

(enum
	waitABit
	setItUp
	showMessage
)

(local
	msgX
	msgY
	height
	deadView
	deadLoop
	deadCel
)

(instance deathRoom of Room
	(properties
		picture pBlack
	)
	
	(method (init)
		(theMusic fade:)
		(theMusic2 fade:)
		(theIconBar disable:)
		(theGame setCursor: normalCursor TRUE)
		(super init:)
		(curRoom setScript: deathScript)
	)
)

(instance deathScript of Script
	(method (changeState newState &tmp case)
		(switch (= state newState)
			(waitABit
				(= cycles 2)
			)
			(setItUp
				;set the message and title based on the reason for death
				(= case
					(switch deathReason
						(deathGENERIC C_GENERIC)
						(else C_GENERIC)
					)
				)
				(= deadView
					(switch deathReason
						(deathGENERIC vDeathSkull)
						(else vDeathSkull)
					)
				)
				(deathMusic number: sDeath play:)
				(= ticks 20)
			)
			(showMessage
				(repeat
					(switch
						(deathPrint
							back: 5
							fore: 0
							font: userFont
							width: 200
							addTitle: N_DEATH NULL case 2 DEATH
							addText: N_DEATH NULL case 1 DEATH
							addButton: 1 N_DEATH NULL C_RESTORE 1 30 30 DEATH
							addButton: 2 N_DEATH NULL C_RESTART 1 80 30 DEATH
							addButton: 3 N_DEATH NULL C_QUIT 1 130 30 DEATH
							init:
						)
						(1
							(theGame restore:)
						)
						(2
							(theGame restart: 1)
							(break)
						)
						(3
							(= quit TRUE)
							(break)
						)
					)
				)
			)
		)
	)
)

(instance deathMusic of Sound
	(properties
		flags mNOPAUSE
	)
)

(instance deathPrint of Print)