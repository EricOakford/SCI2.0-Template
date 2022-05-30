;;; Sierra Script 1.0 - (do not remove this comment)\
;********************************************************************
;***
;***	VIEWS.SH
;***	  View defines
;***
;********************************************************************

(define	vEgo		0)
(define vEgoRun		1)
(define vEgoSneak	2)
(define vEgoStand	3)
(define vTestBlock	4)
(define vHippie		10)
(define vBarnard	14)
(define vBella		15)
(define vRichard	16)
(define vCaveman	17)
(define vMerv		18)
(define vMonk		19)
(define vHero6		20)
(define vButcher	23)
(define vPterry		55)

(define vEgoHoes	140)

(define vOttoMobster	403)

(define vEgoDies		516)
(define vEgoFlameDart	522)

;Combat views
(define vKnFight	602)
(define vKnThrust	603)
(define vKnDodgeL	604)
(define vKnDodgeR	605)
(define vKnGetHit	606)
(define vFightSpell 607)
(define vKnZap		608)
(define vSwFight		609)
(define vSwThrustLow	610)
(define vSwParryLow		611)
(define vSwDodgeR		612)
(define vSwGetHit		613)
(define vSwThrustHigh	614)
(define vSwParryHigh	615)
(define vSwDodgeL		616)
(define vEgoUnarmed		617)
(define vFightFX		618)
(define vGoblin	645)
(define vGuard	650)
(define vRogue	655)

(define vStatusBar 803)

(define vInvItems	900)
	(define lInvCursors	1)


(define vControlIcons 950)
	(enum
		lSaveButton
		lRestoreButton
		lAboutButton
		lHelpButton
		lVoiceOnButton
		lVoiceOffButton
		lTextOnButton
		lTextOffButton
		lRestartButton
		lQuitButton
		lOKButton
		lControlFixture
		lControlBars
		lTestBar
	)
	
(define vIconBar	990)
	(enum
		lWalkIcon
		lLookIcon
		lDoIcon
		lTalkIcon
		lItemIcon
		lInvIcon
		lRunIcon
		lControlIcon
		lSneakIcon
		lHelpIcon
		lActionIcon
		lCastIcon
		lLeftBorder
		lRightBorder
		lDisabledIcon
		lExitIcon
	)
	
(define vInvIcons 991)
	(enum
		lInvHand
		lInvHelp
		lInvLook
		lInvOK
		lInvSelect
		lInvMore
	)
(define vIconCursors	992)
(define vInvWindow		993)
(define vActionBar		994)

	
(define vDeathIcons 2000)