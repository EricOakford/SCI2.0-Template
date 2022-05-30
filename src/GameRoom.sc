;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEROOM.SC
;
;	This is a custom room class for the game.
;

(script# GAMEROOM)
(include game.sh)
(use Main)
(use Game)

(class HeroRoom kindof Room
	(properties
		timePasses TRUE	;if FALSE, time stands still and you can't rest
		nightPalette 0
		canSleep FALSE	;if TRUE, ego can sleep here
	)
	
	(method (init)
		(super init:)
		(if (and timePasses (not nightPalette))
			(= nightPalette (curRoom picture?))
		)
		;pre-load the night palette so that missing palettes can be picked up
		; at any time.
		;All rooms that can be entered at night MUST have a night palette resource,
		; even if it is just a duplicate of the day palette.
		(Load RES_PALETTE nightPalette)
	)
	
	(method	(doit &tmp nRoom)
		;
		; Send the doit: to any script

		(if script
			(script doit:)
		)
		;Change room on edge hit, UNLESS ego or the room have a script that changes it
		(if (= nRoom (self edgeToRoom: ((user alterEgo?) edgeHit?)))
			(if (and (not (ego script?)) (not (curRoom script?)))
				(self newRoom: nRoom)
			)
		)
	)
	
	(method (dispose)
		(= nightPalette 0)	;zero out the palette if a talker modified it
		(super dispose:)
	)
)