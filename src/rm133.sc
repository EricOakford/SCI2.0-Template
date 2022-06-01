;;; Sierra Script 1.0 - (do not remove this comment)
(script# 133)
(include game.sh)
(use Main)
(use rgForest)
;(use Encounter)
(use GameRoom)
(use Actor)
(use System)

(public
	rm133 0
)

(instance rm133 of HeroRoom ;EncRoom
	(properties
		picture 702
		horizon 56
		north 122
		east 134
		south 145
		;encChance 35
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
		x 11
		y 92
		loop 4
	)
)