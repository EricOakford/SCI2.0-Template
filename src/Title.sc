;;; Sierra Script 1.0 - (do not remove this comment)
;
;	TITLE.SC
;
;	Show the title screen, then the startup menu.
;
;

(script# TITLE)
(include game.sh) (include "100.shm")
(use Main)
(use Game)
(use Print)
(use Motion)
(use Actor)
(use System)

(public
	titleRm			0
)

(enum
	buttonStart
	buttonRestore
	buttonQuit
	buttonImport
	buttonIntro
)

(instance titleRm of Room
	(properties
		style			SHOW_VSHUTTER_IN
		picture		scriptNumber
	)
	(method (init)
		(super init: &rest)
		(self setScript:	sTitle)
	)
)

(instance sTitle of Script
	(method (changeState ns &tmp str nextRoom)
		(switchto (= state ns)
			(
				(= cycles 2)
			)
			(			
				(= seconds 3)
				(theGame setCursor: normalCursor TRUE)
				(repeat
					(switch
						((= str (Print new:))
							posn: 175 -1
							font: 8
							addButton: buttonIntro N_ROOM NULL C_INTRO 1 0 0 TITLE
							addButton: buttonStart N_ROOM NULL C_NEW_HERO 1 0 25 TITLE
							addButton: buttonImport N_ROOM NULL C_IMPORT 1 0 50 TITLE
							addButton: buttonRestore N_ROOM 0 C_CONTINUE 1 0 75 TITLE
							init:
						)
						(buttonStart
							(curRoom newRoom: CHARSEL)
							(break)
						)
						(buttonRestore
							(theGame restore:)
						)
						(buttonQuit
							(theGame quitGame: 1)
							(break)
						)
						(buttonIntro
							(curRoom newRoom: rIntro1)
							(break)
						)
					)
				)
			)
		)
	)
)