;;; Sierra Script 1.0 - (do not remove this comment)
(script# 144)
(include game.sh)
(use Main)
;(use Encounter)
(use rgForest)
(use GameRoom)
(use Actor)
(use System)

(public
	rm144 0
)

(instance rm144 of HeroRoom ;EncRoom
	(properties
		picture 704
		east 145
		south 161
		;encChance 20
		;entrances (| reSOUTH reEAST)
	)
	
	;NOTE: This room's colors are messed up for some reason.
	
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
