;;; Sierra Script 1.0 - (do not remove this comment)
;**************************************************************
;***
;***	GAME.SH--
;***
;**************************************************************


(include pics.sh) (include views.sh) ;graphical defines
(include system.sh) (include sci2.sh) ;system and kernel functions
(include talkers.sh) (include verbs.sh) ;message defines
(include icons.sh)
(include nums.sh)
(include flags.sh)

; General Game Defines
(define QG1_NUM_ATTRIBS 25)
(define	QG2_NUM_ATTRIBS 30)
(define QG3_NUM_ATTRIBS 34)
(define QG4_NUM_ATTRIBS 42)
(define NUM_ATTRIBS 25)
(define NUM_SPELLS 8)

(define GAMEDAY 3600)	;number of ticks per game day
(define GAMEHOUR 150) 	;number of ticks per game hour

(define STAM_RATE	20)		;recovery rate for stamina
(define HEAL_RATE	15)		;recovery rate for health
(define MANA_RATE	5)		;recovery rate for mana

(define STARTTELL 0)
(define ENDTELL	999)

; Character Classes
(enum
	FIGHTER
	MAGIC_USER
	THIEF
)

; Battle results
(enum
	battleEGOLOST
	battleEGOWON
	battleEGORUNS
)

; Ego Moving Modes
(enum
	MOVE_WALK		;0
	MOVE_RUN		;1
	MOVE_SNEAK		;2
)

; Skills
(enum
	STR			;0
	INT			;1
	AGIL		;2
	VIT			;3
	LUCK		;4
	WEAPON		;5
	PARRY		;6
	DODGE		;7
	STEALTH		;8
	PICK		;9
	THROW		;10
	CLIMB		;11
	MAGIC		;12
	HONOR		;13
	EXPER		;14
	HEALTH		;15
	STAMINA		;16
	MANA		;17
; Magic Spells
	OPEN
	DETMAGIC
	DAZZLE
	ZAP
	FLAMEDART
	;QFG4.5 exclusive spells. They should be last.
	HEAL
	LOVE
	LEPGOLD	
)

;Inventory items
(enum
	iMoney
	iFood
	iSkeletonKey
	iSword
	iShield
	iLaundryNote
	iAmulet
	iVase
	iCandelabra
	iHellCandle
	iPearls
	iEvidence
	iBonehead
	iHealingPotion
	iVigorPotion
	iManaPotion
	iUndeadElixir
	iSleepingPills
	iBeerKeg
	iLockPick
	iMeepToy
	iDragonsBreath
	iLeather
	iChainmail
	iDagger
	iGargoyleCard
	iLasso
	iShovel
	iCleoBones
	iBallerinaCard
	iAMTMCard
	iInflatableBear
	iFingerBone
	iGiantClothes
	iWhistle
	iBong
	iLoveLetter
	iGhostMoney
	iLastInvItem	;this MUST be last
)

; Times of Day
(enum
	TIME_DAWN
	TIME_MIDMORNING
	TIME_MIDDAY
	TIME_MIDAFTERNOON
	TIME_SUNSET
	TIME_NIGHT
	TIME_MIDNIGHT
	TIME_NOTYETDAWN
)

;Ware return values
(define notEnough -1)
(define buyNothing 0)
;afterwards is room-specific

;Content levels
(enum
	contentCLEAN
	contentMODERATE
	contentEXPLICIT
)

;Death reasons
(enum
	deathGENERIC
	deathSTARVATION
	deathSODOMY
	deathGANGSTERS
	deathBAGI
	deathGUARD
	deathCAVEMEN
	deathCHIEFTHIEF
	deathWITCH
	deathGIANTFAIRY
	deathBANKROBBERY
	deathOVERWORK
	deathFLAMED
	deathATTACK_REDNECKS
	deathBELLA_VAMPIRE
	deathAMULET_TRAP
	deathBEAUDINE
	deathBEAUDINE_TALK
	deathDUNGEON
	deathGOBLIN
	deathGUARD_FIGHT
	deathROGUE
	deathDAGGER
	deathBURGLAR_JURG
)

;Which badge is being worn
(enum
	badgeNONE
	badgeTIEDYE
	badgeCONFED
	badgeUNDEAD
)

;Bella's state
(enum
	bellaInitial
	bellaWantsToTalk
	bellaKilled
	bellaVampire
	bellaGotLoveNote
	bellaGone
)

;Cleo's state
(enum
	cleoInitial
	cleoFirstTalk
	cleoGotShovel
	cleoDugUpBones
	cleoBuriedBones
	cleoLastTalk
	cleoDugUpMoney
	cleoGaveMoney
	cleoMurdererKilled
)

;Meeps' state
(enum
	meepInitial
	meepRichardTaunt
	meepHealed
	meepKilledBeaudine
	meepThrewFlute
	meepGotFlute
)