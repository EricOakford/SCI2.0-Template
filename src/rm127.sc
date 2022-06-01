;;; Sierra Script 1.0 - (do not remove this comment)
(script# 127)
(include game.sh) (include "801.shm")
(use Main)
(use GameRoom)
;(use Encounter)
(use rgForest)
(use Actor)
(use System)

(public
	rm127 0
)

(instance rm127 of HeroRoom ;EncRoom
	(properties
		picture 702
		horizon 90
		east 128
		west 126
		;encChance 20
		;entrances (| reEAST reWEST)

	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(northBush init:)
		(southBush init:)
		(snow1 init:)
		(snow2 init:)
		(snow3 init:)
		(snow4 init:)
		(snow5 init:)
		(snow6 init:)
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

(instance northBush of Bush
	(properties
		x 85
		y 35
		loop 2
	)
)

(instance southBush of Bush
	(properties
		x 111
		y 167
		loop 3
		priority 15
	)
)

(instance snow1 of Snow
	(properties
		x 310
		y 8
	)
)

(instance snow2 of Snow
	(properties
		x 310
		y 38
		loop 1
		priority 15
		signal ignrAct
	)
)

(instance snow3 of Snow
	(properties
		x 240
		y 44
		loop 2
		priority 15
		signal ignrAct
	)
)

(instance snow4 of Snow
	(properties
		x 239
		y 80
		loop 3
		signal ignrAct
	)
)

(instance snow5 of Snow
	(properties
		x 253
		y 126
		loop 4
		signal ignrAct
	)
)

(instance snow6 of Snow
	(properties
		x 313
		y 58
		loop 4
		cel 1
		priority 15
		signal ignrAct
	)
)
