;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMERESTART.SC
;
;	This script cleans up the whole game when restarting.
;	As SCI32 does not support restarting from the kernel, this is necessary.
;
(script# GAME_RESTART)
(include game.sh)
(use Main)
(use Game)
(use Print)
(use System)

(public
	restartRoom 0
)

(local
	i
	nextRoom
)

(instance restartRoom of Room
	(properties
		picture pBlack
		style SHOW_IRIS_OUT
	)
	
	(method (init)
		(for ((= i 0)) (< i (sounds size:)) ((++ i))
			((sounds at: i) client: 0)
		)
		(sounds eachElementDo: #stop)
		(super init:)
		(theGame handsOff:)
		(self setScript: doTheRestart)
	)
)

(enum
	clearEverything
	startTheGame
)

(instance doTheRestart of Script	
	(method (changeState newState)
		(switch (= state newState)
			(clearEverything
				;clean up the palette
				(if (PalVary PalVaryInfo)
					(PalVary PalVaryKill)
				)
				;clean up the event handlers
				(if (keyDownHandler size:)
					(keyDownHandler release:)
				)
				(if (mouseDownHandler size:)
					(mouseDownHandler release:)
				)
				(if (walkHandler size:)
					(walkHandler release:)
				)
				(if (and cuees (cuees size:))
					(cuees dispose:)
					(= cuees 0)
				)
				;drop all inventory items
				(for ((= i 0)) (< i iLastInvItem) ((++ i))
					((inventory at: i) owner: 0 state: 0)
				)
				;clear all flags
				(for ((= i 0)) (< i NUMFLAGS) ((++ i))
					(gameFlags clear: i)
				)
				;zero out all game-specific globals
				(for ((= i 100)) (<= i 200) ((++ i))
					;EXCEPT for those meant to be retained
					(if (OneOf i 100 101 102 103 104 105 106 107 108 109 110)
						0
					else
						(= [ego i] 0)
					)
				)
				;re-init the colors, since they were all zeroed-out
				((ScriptID COLOR_INIT 0) doit:)
				
				;run the game initialization code again
				((ScriptID GAME_INIT 0) doit:)
				(DisposeScript GAME_INIT)
				(= cycles 2)
			)
			(startTheGame
				(= nextRoom (if debugging WHERE_TO else rTitle))
				(curRoom newRoom: nextRoom)
			)
		)
	)
)