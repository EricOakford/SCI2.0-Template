;;; Sierra Script 1.0 - (do not remove this comment)
(script# 162)
(include game.sh)
(use Main)
;(use Encounter)
(use rgForest)
(use GameRoom)
(use Actor)
(use System)

(public
	rm162 0
)

(instance rm162 of HeroRoom ;EncRoom
	(properties
		picture 702
		horizon 55
		north 145
		east 163
		south rFairyRing
		west 161
		;encChance 15
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
