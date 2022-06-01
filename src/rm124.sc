;;; Sierra Script 1.0 - (do not remove this comment)
(script# 124)
(include game.sh)
(use Main)
(use GameRoom)
;(use Encounter)
(use System)

(public
	rm124 0
)

(instance rm124 of HeroRoom ;EncRoom
	(properties
		picture 700
		horizon 65
		north rSpireaLedge
		east 125
		south 135
		west 123
		;encChance 30
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