;;; Sierra Script 1.0 - (do not remove this comment)
(script# 142)
(include game.sh)
(use Main)
(use rgForest)
;(use Encounter)
(use GameRoom)
(use Actor)
(use System)

(public
	rm142 0
)

(instance rm142 of HeroRoom ;EncRoom
	(properties
		picture 703
		horizon 57
		north 126
		east 143
		south 156
		;encChance 20
		;entrances (| reNORTH reEAST reSOUTH)
	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(ego normalize:)
		(westBush setPri: 6 ignoreActors: init:)
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
		x 15
		y 75
		loop 4
	)
)
