;;; Sierra Script 1.0 - (do not remove this comment)
(script# PROCS)
(include game.sh) (include "17.shm")
(use Main)
(use Print)
(use GameRoom)

(public
	Bset 0
	Bclr 1
	Btst 2
	Face 3
	EgoDead 4
	NextDay 5
	SkillUsed 6
	EatMeal 7
	TakeDamage 8
	UseStamina 9
	UseMana 10
	MaxHealth 11
	MaxStamina 12
	MaxMana 13
	ShowTime 14
	FixTime 15
	AreYouSure 16
	SolvePuzzle 17
	Rand300 18
	YesOrNo	19
)


;Most of these procs will be replaced with macro defines once those are supported
(procedure (Bset flagEnum)
	(gameFlags set: flagEnum)
)

(procedure (Bclr flagEnum)
	(gameFlags clear: flagEnum)
)

(procedure (Btst flagEnum)
	(gameFlags test: flagEnum)
)

(procedure (Face actor1 actor2 both whoToCue &tmp ang1To2 theX theY i)
	;This makes one actor face another.
	(= i 0)
	(if (not (> argc 3))
		(= theX (actor2 x?))
		(= theY (actor2 y?))
		(if (== argc 3) (= i both))
	else
		(= theX actor2)
		(= theY both)
		(if (== argc 4) (= i whoToCue))
	)
	(= ang1To2
		(GetAngle (actor1 x?) (actor1 y?) theX theY)
	)
	(actor1 setHeading: ang1To2 i)
)

(procedure (EgoDead theReason)
	;This procedure handles when ego dies. It closely matches that of SQ4, SQ5 and KQ6.
	;If a specific message is not given, the game will use a default message.
	(if (not argc)
		(= deathReason deathGENERIC)
	else
		(= deathReason theReason)
	)
	(curRoom newRoom: DEATH)
)

(procedure (NextDay)
	(Bclr fTendedFarmToday)
	(Bclr fPaidForFarmWorkToday)
	(Bclr fDidLaundryToday)
	(Bclr fPaidForLaundryWorkToday)
	(return (++ Day))
)

(procedure (SkillUsed skillNum learnValue)
	(ego useSkill: skillNum learnValue)
)


(procedure (EatMeal)
	(ego eatMeal:)
)

(procedure (TakeDamage damage)
	(ego takeDamage: damage)
)

(procedure (UseStamina pointsUsed)
	(ego useStamina: pointsUsed)
)

(procedure (UseMana pointsUsed)
	(ego useMana: pointsUsed)
)

(procedure (MaxHealth)
	(ego maxHealth:)
)

(procedure (MaxStamina)
	(ego maxStamina:)
)

(procedure (MaxMana)
	(ego maxMana:)
)

(procedure (ShowTime)
	((ScriptID TIME 3) init:)
)

(procedure (FixTime newClock newMinutes)
	((ScriptID TIME 4) init: newClock newMinutes)
)

(procedure (AreYouSure)
	(return
		(Print
			font: userFont
			width: 100
			mode: teJustCenter
			addText: N_ARE_YOU_SURE NULL NULL 1 0 0 PROCS
			addButton:	TRUE N_ARE_YOU_SURE NULL C_YES 1 0 25 PROCS
			addButton:	FALSE N_ARE_YOU_SURE NULL C_NO 1 75 25 PROCS		
			init:
		)
	)
)

(procedure (SolvePuzzle pValue pFlag)
	(theGame solvePuzzle: pValue pFlag)
)

(procedure (Rand300)
	(return (+ 1 (/ (Random 0 2999) 10)))
)

(procedure (YesOrNo question &tmp oldCur)
	;this brings up a "yes or no" dialog choice.
	(= oldCur ((theIconBar curIcon?) getCursor:))
	(theGame setCursor: normalCursor)
	(return
		(Print
			font:		userFont
			width:		100
			mode:		teJustCenter
			addText:	question NULL NULL 1 0 0 MAIN
			addButton:	TRUE N_ARE_YOU_SURE NULL C_YES 1 0 25 scriptNumber
			addButton:	FALSE N_ARE_YOU_SURE NULL C_NO 1 75 25 scriptNumber
			init:
		)
	)
	(theGame setCursor: oldCur)
)

