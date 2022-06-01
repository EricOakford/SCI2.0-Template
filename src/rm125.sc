;;; Sierra Script 1.0 - (do not remove this comment)
(script# 125)
(include game.sh) (include "801.shm")
(use Main)
(use GameRoom)
;(use Encounter)
(use rgForest)
(use Actor)
(use System)

(public
	rm125 0
)

(instance rm125 of HeroRoom ;EncRoom
	(properties
		picture 702
		horizon 55
		north 117
		south 136
		west 124
		;encChance 30
		;entrances (| reNORTH reSOUTH reWEST)
	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(eastBush ignoreActors: setPri: 6 init:)
		(ego normalize:)
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

(instance eastBush of Bush
	(properties
		x 228
		y 74
		loop 4
	)
)
