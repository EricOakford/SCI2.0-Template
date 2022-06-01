;;; Sierra Script 1.0 - (do not remove this comment)
(script# 151)
(include game.sh)
(use Main)
;(use Encounter)
(use rgForest)
(use GameRoom)
(use Actor)
(use System)

(public
	rm151 0
)

(instance rm151 of HeroRoom ;EncRoom
	(properties
		picture 703
		horizon 55
		north 134
		east 152
		south 163
		west 145
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