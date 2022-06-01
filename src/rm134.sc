;;; Sierra Script 1.0 - (do not remove this comment)
(script# 134)
(include game.sh)
(use Main)
;(use Encounter)
(use rgForest)
(use GameRoom)
(use Actor)
(use System)

(public
	rm134 0
)

(instance rm134 of HeroRoom ;EncRoom
	(properties
		picture 700
		horizon 55
		north 123
		east 135
		south 151
		west 133
		;encChance 20
	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
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