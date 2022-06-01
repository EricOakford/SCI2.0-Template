;;; Sierra Script 1.0 - (do not remove this comment)
(script# 143)
(include game.sh)
(use Main)
(use rgForest)
;(use Encounter)
(use GameRoom)
(use Actor)
(use System)

(public
	rm143 0
)

(instance rm143 of HeroRoom ;EncRoom
	(properties
		picture 705
		south 157
		west 142
		;encChance 20
		;entrances (| reSOUTH reWEST)

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