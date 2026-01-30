;;; Sierra Script 1.0 - (do not remove this comment)\
;********************************************************************
;***
;***	GRAPHICS.SH
;***	  Defines for views and pictures
;***
;********************************************************************

;
; Ego views
(define	vEgo		0)
(define vEgoStand	1)
(define vEgoTalk	10)

;
; Inventory items
(define vInvItems	700)
	(define lInvCursors	1)

;
; Death icons
(define vDeathSkull 800)

;
; Interface views
(define vIcons	900)
	(enum
		lIconWalk
		lIconLook
		lIconHand
		lIconTalk
		lIconInvItem
		lIconInventory
		lIconExit
		lIconControls
		lIconScore
		lIconHelp
		lIconCustom
		lIconDisabled
	)

(define vInvIcons 901)
	(enum
		lInvHand
		lInvHelp
		lInvLook
		lInvOK
		lInvSelect
		lInvUp
		lInvDown
	)

(define vGameControls			947)
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
	
(define vIconCursors	992)
(define vInvWindow		993)

; Picture defines
(define pWhite 98)
(define pBlack 99)
