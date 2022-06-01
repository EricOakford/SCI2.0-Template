;;; Sierra Script 1.0 - (do not remove this comment)
(script# 166)
(include game.sh) (include "801.shm") (include "166.shm")
(use Main)
(use Feature)
(use Polygon)
(use GameRoom)
(use Procs)
(use System)

(public
	rm166 0
)

(instance rm166 of HeroRoom
	(properties
		picture scriptNumber
		horizon 54
		north 156
		east 167
		south 175
		west rTownOutside
	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(self addObstacle:
			(&getpoly {NorthWall})
			(&getpoly {SWWall})
			(&getpoly {SEWall})
		)
		(floor
			init:
			setPolygon: (&getpoly {Floor})
		)
		(if (and (ego has: iFingerBone) (== cleoState cleoGaveMoney))
			(self setScript: fingerBone)
		)
		(ego normalize:)
	)
	
	(method (doVerb)
		(return FALSE)
	)
)

(instance floor of Feature
	(properties
		noun N_FLOOR
		modNum rgFOREST
	)
)

(instance fingerBone of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= ticks 20)
			)
			(1
				(messager say: N_ROOM NULL C_FINGERBONE 0 self)
			)
			(2
				(self dispose:)
			)
		)
	)
)