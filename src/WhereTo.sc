;;; Sierra Script 1.0 - (do not remove this comment)
;	WHERETO.SC
;
;	If debugging is enabled, the game brings up this prompt.
;	You can go immediately to any room in the game.
;
;

(script# WHERE_TO)
(include game.sh) (include "4.shm")
(use Main)
(use Intrface)
(use Print)
(use Game)
(use System)
(use String)

(public
	whereTo 0
)

(instance whereTo of Room
	(properties
		picture pSpeedTest
	)
	
	(method (init &tmp str nextRoom statLevel whichSkill whichSpell)
		(super init:)
		(= str (String new:))
		(= nextRoom 0)
		(= nextRoom
			(Print
				addText: N_ROOM NULL C_WHERETO 1 0 0 WHERE_TO
				addEdit: str 5 115 0
				init:
			)
		)
		(= nextRoom TITLE)
		(if str
			(= nextRoom (str asInteger:))
		)
		; set up debugging stats
		(= statLevel 200)
		(for ((= whichSkill 0)) (< whichSkill OPEN) ((++ whichSkill))
			(= [egoStats whichSkill] statLevel)
		)
		(userName format: {Unknown Hero})
		(= [egoStats EXPER] 1900)
		(= [egoStats HEALTH] (ego maxHealth:))
		(= [egoStats STAMINA] (ego maxStamina:))
		(= [egoStats MANA] (ego maxMana:))
;;;		(for ((= whichSpell 0)) (< whichSpell 22) ((++ whichSpell))
;;;			(= [egoStats (+ OPEN whichSpell)] statLevel)
;;;		)
		(self newRoom: nextRoom)
	)
)


