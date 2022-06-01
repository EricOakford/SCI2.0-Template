;;; Sierra Script 1.0 - (do not remove this comment)
(script# 161)
(include game.sh)
(use Main)
(use rgForest)
(use GameRoom)
;(use Encounter)
(use Actor)
(use System)

(public
	rm161 0
)

(instance rm161 of HeroRoom ;EncRoom
	(properties
		picture 700
		horizon 60
		north 144
		east 162
		south 169
		west rMeeps
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