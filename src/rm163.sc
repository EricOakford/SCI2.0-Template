;;; Sierra Script 1.0 - (do not remove this comment)
(script# 163)
(include game.sh) (include "163.shm")
(use Main)
(use rgForest)
;(use Encounter)
(use HandsOffScript)
(use GameRoom)
(use Procs)
(use Motion)
(use Actor)
(use System)

(public
	rm163 0
)

(local
	bellaCantAttack
)

;This is where vampire Bella hangs out at night.
; If ego isn't wearing the undead badge then, he's history.

(instance rm163 of HeroRoom ;EncRoom
	(properties
		picture 701
		horizon 58
		north 151
		east rGraveyard
		south 171
		west 162
		;encChance 15
	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(ego normalize:)
		(if (and Night (== bellaState bellaVampire))
			;(= encChance 0)
			(= monsterNum 0)
			(= canSleep FALSE)
			(= timePasses FALSE)
			(bella
				init:
				setLoop: loopE
			)
			(self setScript: bellaAttacks)
		)
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

(instance bella of Actor
	(properties
		view vBella
		x 135
		y 110
	)
)

(enum
	moveToHer
	sheTalks
	sheMoves
	isHeProtected
	yesHeIs
	noHesNot
)

(instance bellaAttacks of HandsOffScript
	(method (changeState newState)
		(switch (= state newState)
			(moveToHer
				(ego
					setMotion: MoveTo 190 110
					self
				)
			)
			(sheTalks
				(messager say: N_BELLA NULL C_BELLA_ATTACKS 0 self)
			)
			(sheMoves
				(bella
					setMotion: MoveTo 185 110
					self
				)
			)
			(isHeProtected
				(if (== (ego badgeWorn?) badgeUNDEAD)
					(self cue:)
				else
					(self changeState: noHesNot)
				)
			)
			(yesHeIs
				(messager say: N_BELLA NULL C_BELLA_STOPPED)
				(= bellaCantAttack TRUE)
				(self dispose:)
			)
			(noHesNot
				(EgoDead deathBELLA_VAMPIRE)
			)
		)
	)
)