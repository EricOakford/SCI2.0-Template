;;; Sierra Script 1.0 - (do not remove this comment)
(script# 135)
(include game.sh)
(use Main)
(use GameRoom)
;(use Encounter)
(use Talker)
(use Actor)
(use System)

(public
	rm135 0
	jimTalker 1
)

;NOTE: This room is supposed to contain the treasure hunter's encampment at night.

(instance rm135 of HeroRoom ;EncRoom
	(properties
		picture 703
		horizon 55
		north 124
		east 136
		south 152
		west 134
		;encChance 20
	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(ego normalize:)
;;;		(if Night
;;;			;campers are protected from monsters
;;;			(= encChance 0)
;;;		)
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

(instance jimTalker of Talker
	(properties
		x 10
		y 10
		view tlkBigJim
		talkWidth 260
		textX 15
		textY 110
	)
	
	(method (init)
		(= font userFont)
		(= fore myTextColor)
		(= back myBackColor)
		(super init: jimBust jimEyes jimMouth &rest)
	)
)

(instance jimBust of Prop
	(properties
		view tlkBigJim
	)
)

(instance jimMouth of Prop
	(properties
		nsTop 33
		nsLeft 11
		view tlkBigJim
		loop 1
	)
)

(instance jimEyes of Prop
	(properties
		nsTop 21
		nsLeft 13
		view tlkBigJim
		loop 2
	)
)