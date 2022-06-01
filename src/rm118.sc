;;; Sierra Script 1.0 - (do not remove this comment)
(script# 118)
(include game.sh) (include "801.shm")
(use Main)
(use rgForest)
(use Actor)
(use GameRoom)
;(use Encounter)
(use System)

(public
	rm118 0
)

(instance rm118 of HeroRoom ;EncRoom
	(properties
		picture 702
		east 119
		west 117
		;encChance 30
		;entrances (| reEAST reWEST)
	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(ego normalize:)
		(northBush init:)
		(southBush init:)
		(if (not (OneOf prevRoomNum vGoblin vGuard vRogue))
			(= egoX (ego x?))
			(= egoY (ego y?))
		)
		;(self setRegions: rgENCOUNTER)
	)
	
	(method (doVerb)
		(return FALSE)
	)
)

(instance northBush of Bush
	(properties
		x 85
		y 35
		loop 2
	)
)

(instance southBush of Bush
	(properties
		x 111
		y 167
		loop 3
		priority 15
	)
)
