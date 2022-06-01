;;; Sierra Script 1.0 - (do not remove this comment)
(script# 157)
(include game.sh)
(use Main)
(use rgForest)
;(use Encounter)
(use GameRoom)
(use Actor)
(use System)

(public
	rm157 0
)

(instance rm157 of HeroRoom ;EncRoom
	(properties
		picture 703
		horizon 54
		north 143
		east rFrostGiant
		south 167
		west 156
		;encChance 15
	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(ego normalize:)
		(snow2 setPri: 15 init:)
		(snow5 setPri: 1 ignoreActors: init:)
		(snow6 setPri: 1 init:)
		(snow3 setPri: 15 ignoreActors: init:)
		(snow4 setPri: 1 ignoreActors: init:)
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

(instance snow2 of Snow
	(properties
		x 292
		y 37
		loop 1
	)
)

(instance snow3 of Snow
	(properties
		x 240
		y 44
		loop 2
	)
)

(instance snow4 of Snow
	(properties
		x 235
		y 95
		loop 3
	)
)

(instance snow5 of Snow
	(properties
		x 259
		y 128
		loop 4
	)
)

(instance snow6 of Snow
	(properties
		x 315
		y 94
		loop 5
	)
)
