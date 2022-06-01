;;; Sierra Script 1.0 - (do not remove this comment)
(script# 117)
(include game.sh)
(use Main)
(use GameRoom)
;(use Encounter)
(use System)

(public
	rm117 0
)

(instance rm117 of HeroRoom ;EncRoom
	(properties
		picture 703
		horizon 57
		north 111
		east 118
		south 125
		west rSpireaLedge
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
