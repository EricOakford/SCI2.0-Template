;;; Sierra Script 1.0 - (do not remove this comment)
(script# 136)
(include game.sh)
(use Main)
;(use Encounter)
(use GameRoom)
(use rgForest)
(use Actor)
(use System)

(public
	rm136 0
)

(instance rm136 of HeroRoom ;EncRoom
	(properties
		picture 701
		horizon 55
		north 125
		south rFarm
		west 135
		;encChance 20
		;entrances (| reSOUTH reWEST reNORTH)

	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(eastBush ignoreActors: setPri: 6 init:)
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

(instance eastBush of Bush
	(properties
		x 227
		y 75
		loop 4
	)
)
