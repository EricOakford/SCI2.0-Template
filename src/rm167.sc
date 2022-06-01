;;; Sierra Script 1.0 - (do not remove this comment)
(script# 167)
(include game.sh) (include "801.shm")
(use Main)
(use Feature)
(use Polygon)
(use Game)
(use GameRoom)

(public
	rm167 0
)

(instance rm167 of HeroRoom
	(properties
		picture scriptNumber
		horizon 55
		north 157
		east 168
		west 166
	)

	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(self addObstacle:
			(&getpoly {NorthWall})
			(&getpoly {SouthWall})
		)
		(floor
			init:
			setPolygon: (&getpoly {Floor})
		)
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