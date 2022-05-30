;;; Sierra Script 1.0 - (do not remove this comment)
(script# ACTIONBAR)
(include game.sh) (include "15.shm")
(use Main)
(use Plane)
(use Procs)
(use IconBar)
(use Time)
(use System)

(public
	actionBar 0
)

(local
	actionCast
)

(define ACTION_X 10)
(define ACTION_Y 13)

(instance actionPlane of Plane)

(instance actionBar of IconBar
	(method (init)
		(= plane actionPlane)
		(= actionCast (Cast new:))
		(self
			add:
				iconNormal
				iconRun
				iconSneak
				iconRest
				iconSheet
				iconTime
				iconOk
				iconActionHelp
		)
		(if (& disabledActions ACTION_WALK)
			(self disable: iconNormal)
		)
		(if (& disabledActions ACTION_RUN)
			(self disable: iconRun)
		)
;;;		(if (or (& disabledActions ACTION_SNEAK) (not [egoStats STEALTH]))
;;;			(self disable: iconSneak)
;;;		)
		(if (& disabledActions ACTION_REST)
			(self disable: iconRest)
		)
		(self
			;don't init: yet
			eachElementDo: #modNum ACTIONBAR
			eachElementDo: #highlightColor -1
			eachElementDo: #lowlightColor -1
			eachElementDo: #y ACTION_Y
			curIcon: iconSheet
			helpIconItem: iconActionHelp
			state: NOCLICKHELP
		)
		(super init:)
		(plane
			back: myBackColor
			addCast: actionCast
		)
		;NOW init: everything
		(self eachElementDo: #init self)
		(plane
			setSize:
			posn: -1 -1
		)
	)

	(method (showSelf)
		(sounds pause:)
		(if (and pMouse (pMouse respondsTo: #stop))
			(pMouse stop:)
		)
		(= curIcon iconSheet)
		(self show: doit: hide:)
	)
)

(instance iconNormal of IconItem
	(properties
		mainView vActionBar
		mainLoop 0
		mainCel 0
		x ACTION_X
		message V_WALK
		signal (| VICON HIDEBAR FIXED_POSN RELVERIFY IMMEDIATE)
		maskView vActionBar
		maskLoop 9
		noun N_WALK
		modNum ACTIONBAR
		helpVerb V_HELP
	)
	
	(method (select)
		(return
			(if (super select: &rest)
				(actionBar hide:)
				(theIconBar curIcon: (theIconBar at: ICON_WALK))
				(ego changeGait: MOVE_WALK TRUE)
				(return TRUE)
			else
				(return FALSE)
			)
		)
	)
)

(instance iconRun of IconItem
	(properties
		mainView vActionBar
		mainLoop 1
		mainCel 0
		x (+ ACTION_X (* 29 1))
		message V_WALK
		signal (| VICON HIDEBAR FIXED_POSN RELVERIFY IMMEDIATE)
		maskView vActionBar
		maskLoop 9
		noun N_RUN
		modNum ACTIONBAR
		helpVerb V_HELP
	)
	
	(method (select)
		(return
			(if (super select: &rest)
				(actionBar hide:)
				(theIconBar curIcon: (theIconBar at: ICON_WALK))
				(ego changeGait: MOVE_RUN TRUE)
				(return TRUE)
			else
				(return FALSE)
			)
		)
	)
)

(instance iconSneak of IconItem
	(properties
		mainView vActionBar
		mainLoop 2
		mainCel 0
		x (+ ACTION_X (* 29 2))
		message V_WALK
		signal (| VICON HIDEBAR FIXED_POSN RELVERIFY IMMEDIATE)
		maskView vActionBar
		maskLoop 9
		noun N_SNEAK
		modNum ACTIONBAR
		helpVerb V_HELP
	)
	
	(method (select)
		(return
			(if (super select: &rest)
				(actionBar hide:)
				(if (!= egoGait MOVE_SNEAK)
					(if (ego trySkill: STEALTH 5 0)
						(theIconBar curIcon: (theIconBar at: ICON_WALK))
						(ego changeGait: MOVE_SNEAK TRUE)
					else
						(messager say: N_SNEAK NULL C_NO_STEALTH 0 0 ACTIONBAR)
					)
				)
				(return TRUE)
			else
				(return FALSE)
			)
		)
	)
)

(instance iconRest of IconItem
	(properties
		mainView vActionBar
		mainLoop 3
		mainCel 0
		x (+ ACTION_X (* 29 3))
		message V_SLEEP
		signal (| VICON HIDEBAR FIXED_POSN RELVERIFY IMMEDIATE)
		maskView vActionBar
		maskLoop 9
		noun N_REST
		modNum ACTIONBAR
		helpVerb V_HELP
	)
	
	(method (select)
		(return
			(if (super select: &rest)
				(actionBar hide:)
				(SleepChoice)
				(return TRUE)
			else
				FALSE
			)
		)
	)
)

(instance iconSheet of IconItem
	(properties
		mainView vActionBar
		mainLoop 5
		mainCel 0
		x (+ ACTION_X (* 29 4))
		signal (| VICON HIDEBAR FIXED_POSN RELVERIFY IMMEDIATE)
		maskView vActionBar
		maskLoop 9
		noun N_SHEET
		modNum ACTIONBAR
		helpVerb V_HELP
	)
	
	(method (select)
		(return
			(if (super select: &rest)
				(actionBar hide:)
				((ScriptID CHARSHEET) doit:)
				(return TRUE)
			else
				FALSE
			)
		)
	)
)

(instance iconTime of IconItem
	(properties
		mainView vActionBar
		mainLoop 6
		mainCel 0
		x (+ ACTION_X (* 29 5))
		signal (| VICON HIDEBAR FIXED_POSN RELVERIFY IMMEDIATE)
		maskView vActionBar
		maskLoop 9
		noun 8
		modNum ACTIONBAR
		helpVerb V_HELP
	)
	
	(method (select)
		(return
			(if (super select: &rest)
				(actionBar hide:)
				(ShowTime)
				(return TRUE)
			else
				FALSE
			)
		)
	)
)

(instance iconOk of IconItem
	(properties
		mainView vActionBar
		mainLoop 4
		mainCel 0
		x (+ ACTION_X (* 29 6))
		signal (| VICON HIDEBAR FIXED_POSN RELVERIFY IMMEDIATE)
		maskView vActionBar
		maskLoop 9
		noun 3
		modNum ACTIONBAR
		helpVerb V_HELP
	)
	
	(method (select)
		(return
			(if (super select: &rest)
				(actionBar hide:)
				(return TRUE)
			else
				FALSE
			)
		)
	)
)

(instance iconActionHelp of IconItem
	(properties
		mainView vActionBar
		mainLoop 8
		mainCel 0
		cursorView vIconCursors
		cursorLoop 0
		cursorCel 4
		x (+ ACTION_X (* 29 7))
		message V_HELP
		signal (| VICON HIDEBAR FIXED_POSN RELVERIFY IMMEDIATE)
		noun N_HELP
		modNum ACTIONBAR
		helpVerb V_HELP
	)
)
