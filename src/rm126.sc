;;; Sierra Script 1.0 - (do not remove this comment)
(script# 126)
(include game.sh) (include "801.shm")
(use Main)
(use rgForest)
(use GameRoom)
;(use Encounter)
(use Actor)
(use System)

(public
	rm126 0
)

;Ego escapes from the castle and into this room.

(instance rm126 of HeroRoom ;EncRoom
	(properties
		picture 700
		horizon 60
		north 119
		east 127
		south 142
		;encChance 20
		;entrances (| reNORTH reEAST reSOUTH)
	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(westBush setPri: 7 ignoreActors: init:)
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

(instance westBush of Bush
	(properties
		y 59
		loop 4
	)
)

