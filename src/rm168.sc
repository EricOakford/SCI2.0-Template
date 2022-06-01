;;; Sierra Script 1.0 - (do not remove this comment)
(script# 168)
(include game.sh) (include "168.shm") (include "801.shm")
(use Main)
(use Print)
(use Feature)
(use HandsOffScript)
(use Polygon)
(use GameRoom)
(use Procs)
(use Motion)
(use Game)
(use Actor)
(use StopWalk)
(use System)

(public
	rm168 0
)

(instance rm168 of Room ;HeroRoom
	(properties
		picture scriptNumber
		noun N_ROOM
		east rNearValley
		west 167
	)
	
	(method (init)
		;(self setRegions: rgFOREST)
		(super init:)
		(self addObstacle:
			(&getpoly {NorthWall})
			(&getpoly {SouthWall})
		)
		(floor
			init:
			setPolygon: (&getpoly {Floor})
		)
		(if (not (Btst fEnteredSpielburg))
			(ego setScript: firstTime)
			;set to ego, not the current room so that we stay handsOff during this sequence
		else
			(ego normalize:)
		)
	)
)

(instance firstTime of HandsOffScript
	(method (changeState newState)
		(switch (= state newState)
			(0
				(ego
					setLoop: loopW
					posn: 310 102
					init:
					normalize:
				)	
				(barnard
					setLoop: loopE
					setCycle: StopWalk -1
					init:
				)
				(guard1
					setLoop: loopS
					setCycle: StopWalk -1
					init:
				)
				(guard2
					setLoop: loopE
					setCycle: StopWalk -1
					init:
				)
				(Bset fEnteredSpielburg)
				(= seconds 2)
			)
			(1
				(messager say: N_ROOM NULL C_FIRST 0 self)
			)
			(2
				(ego setMotion: MoveTo 232 100 self)
			)
			(3
				(messager say: N_ROOM NULL C_BARNARD_GREETS 0 self)
			)
			(4
				(guard1
					setLoop: loopW
					setMotion: MoveTo -20 90
				)
				(guard2
					setLoop: loopW
					setMotion: MoveTo -20 120
				)
				(barnard
					setLoop: loopW
					setMotion: MoveTo -20 110
					self
				)
			)
			(5
				(barnard dispose:)
				(guard1 dispose:)
				(guard2 dispose:)
				(messager say: N_ROOM NULL C_AFTER_BARNARD 0 self)
			)
			(6
				(ego
					setMotion: MoveTo 233 100
					self
				)
			)
			(7
				(messager say: N_ROOM NULL C_BRIGHT_SIDE 0 self)
			)
			(8
				(self dispose:)
			)
		)
	)
)

(instance floor of Feature
	(properties
		noun N_FLOOR
		modNum rgFOREST
	)
)

(instance barnard of Actor
	(properties
		view vBarnard
		x 130
		y 100
	)
)

(instance guard1 of Actor
	(properties
		view vGuard
		x 136
		y 79
		signal ignrAct
	)
)

(instance guard2 of Actor
	(properties
		view vGuard
		x 113
		y 118
		signal ignrAct
	)
)