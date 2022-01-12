;;; Sierra Script 1.0 - (do not remove this comment)\
;********************************************************************
;***
;***	VIEWS.SH
;***	  View defines
;***
;********************************************************************

(define	vEgo		0)
(define vEgoStand	1)
(define vTestBlock	4)
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
	
(define vWalkCursor 980)
(define vLookCursor 981)
(define vDoCursor	982)
(define vTalkCursor 983)
(define vHelpCursor 989)
(define vIconBar	990)
	(enum
		lWalkIcon
		lLookIcon
		lDoIcon
		lTalkIcon
		lItemIcon
		lInvIcon
		lExitIcon
		lControlIcon
		lHelpIcon
		lCustomIcon
		lScoreIcon
		lDisabledIcon
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
	
(define vDeathSkull 2000)