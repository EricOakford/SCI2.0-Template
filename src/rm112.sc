;;; Sierra Script 1.0 - (do not remove this comment)
(script# 112)
(include game.sh) (include "801.shm")
(use Main)
(use GameRoom)
;(use Encounter)
(use rgForest)
(use Actor)
(use System)

(public
	rm112 0
)

(instance rm112 of HeroRoom ;EncRoom
	(properties
		picture 700
		horizon 43
		north rEranasPeace
		west 111
		east rOgreArea
		;encChance 30
		;entrances (| reEAST reSOUTH)
		
	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(southBush init:)
		(southBush2 init:)
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

(instance southBush of Bush
	(properties
		x 126
		y 165
		loop 1
		priority 15
	)
)

(instance southBush2 of Bush
	(properties
		x 46
		y 167
		loop 1
		priority 15
	)
)
