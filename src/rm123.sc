;;; Sierra Script 1.0 - (do not remove this comment)
(script# 123)
(include game.sh)
(use Main)
(use GameRoom)
;(use Encounter)
(use System)

(public
	rm123 0
)

(instance rm123 of HeroRoom ;EncRoom
	(properties
		picture 704
		horizon 55
		east 124
		south 134
		;encChance 30
		;entrances (| reEAST reSOUTH)
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