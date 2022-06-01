;;; Sierra Script 1.0 - (do not remove this comment)
(script# 152)
(include game.sh)
(use Main)
(use rgForest)
;(use Encounter)
(use GameRoom)
(use Actor)
(use System)

(public
	rm152 0
)

(instance rm152 of HeroRoom ;EncRoom
	(properties
		picture 702
		horizon 55
		north 135
		east rFarm
		west 151
		;encChance 15
	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(ego normalize:)
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

(instance southBush of Bush
	(properties
		x 111
		y 167
		loop 3
		priority 15
	)
)
