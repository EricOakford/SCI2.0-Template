;;; Sierra Script 1.0 - (do not remove this comment)
(script# 111)
(include game.sh)
(use Main)
;(use Encounter)
(use GameRoom)
(use System)

(public
	rm111 0
)

(instance rm111 of HeroRoom ;EncRoom
	(properties
		picture 704
		east 112
		south 117
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

