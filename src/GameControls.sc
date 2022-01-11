;;; Sierra Script 1.0 - (do not remove this comment)
(script# GAME_CONTROLS)
(include game.sh) (include "5.shm")
(use Main)
(use Plane)
(use Print)
(use IconBar)
(use Cursor)
(use Tutorial)
(use Game)
(use Actor)
(use System)

(public
	TheGameControls 0
)

(local
	controlCast
)
(class ControlIcon of IconItem
	(properties
		theObj 0
	)
	
	(method (doit)
		(if theObj
			(if (& signal HIDEBAR)
				(gameControls hide:)
			)
			(theGame panelObj: theObj panelSelector: selector)
		)
	)
)

(class SlideBar of IconItem
	(properties
		signal FIXED_POSN
		maxValue 0
		minValue 0
		minX 0
		maxX 0
		resolution 0
		inc 0
		value 0
		position 0
		theObj 0
		barObj 0
		oppose 0
	)
	
	(method (show)
		(|= signal IB_ACTIVE)
		(if (and pMouse (pMouse respondsTo: #stop))
			(pMouse stop:)
		)
		(= resolution (+ (/ (- maxX minX) inc) 1))
		(if (not position) (= position (self valueToPosn:)))
		(self refresh: position)
	)
	
	(method (select param1 &tmp newEvent theTheMinX theMinX)
		(if (& signal DISABLED) (return 0))
		(return
			(if (and argc param1)
				(= theTheMinX 500)
				(while (!= ((= newEvent (Event new:)) type?) 2)
					(newEvent localize: (TheGameControls plane?))
					(if
					(!= (= theMinX (- (newEvent x?) 7)) theTheMinX)
						(if (< theMinX minX) (= theMinX minX))
						(if (> theMinX maxX) (= theMinX maxX))
						(= position theMinX)
						(self refresh:)
					)
					(= theTheMinX theMinX)
					(newEvent dispose:)
				)
				(= value (self posnToValue: position))
				(newEvent dispose:)
			else
				(return 1)
			)
		)
	)
	
	(method (highlight)
	)
	
	(method (mask)
	)
	
	(method (advance)
		(if (< position maxX)
			(= position (+ position inc))
			(self refresh:)
			(= value (self posnToValue: position))
		)
	)
	
	(method (retreat)
		(if (> position minX)
			(= position (- position inc))
			(self refresh:)
			(= value (self posnToValue: position))
		)
	)
	
	(method (valueToPosn)
		(= position
			(+
				minX
				(* inc (/ (* value resolution) (- maxValue minValue)))
			)
		)
	)
	
	(method (posnToValue param1 &tmp theMinValue)
		(= theMinValue
			(+
				(/
					(* (- maxValue minValue) (- param1 minX))
					(- maxX minX)
				)
				1
			)
		)
		(if (<= param1 minX) (= theMinValue minValue))
		(if (>= param1 (- nsRight nsLeft))
			(= theMinValue maxValue)
		)
		(cond 
			((< theMinValue minValue) (= theMinValue minValue))
			((> theMinValue maxValue) (= theMinValue maxValue))
		)
		(return theMinValue)
	)
	
	(method (refresh &tmp [temp0 3])
		(if (> position (- (CelWide view loop cel) 6))
			(= position (- (CelWide view loop cel) 6))
		)
		(barObj setInsetRect: 0 0 position 10)
		(UpdateScreenItem barObj)
		(UpdatePlane (TheGameControls plane?))
		(FrameOut)
	)
)

(instance controlWind of Plane)

(class TheGameControls of IconBar
	(method (init)
		(= gameControls self)
		(= controlCast (Cast new:))
		(= plane controlWind)
		(self
			add:
				iconExit
				(iconVoice theObj: iconVoice selector: #doit yourself:)
				(iconText theObj: iconText selector: #doit yourself:)
				(iconAbout theObj: theGame selector: #showAbout yourself:)
				iconHelp
				(iconQuit theObj: theGame selector: #quitGame yourself:)
				(iconRestart theObj: theGame selector: #restart yourself:)
				(iconRestore theObj: theGame selector: #restore yourself:)
				(iconSave theObj: theGame selector: #save yourself:)
;;;				(detailBar theObj: theGame selector: #detailLevel yourself:)
				(speedBar theObj: ego selector: #setSpeed yourself:)
;;;				soundBar
;;;				musicBar
;;;				textBar
			eachElementDo: #highlightColor 0
			eachElementDo: #lowlightColor 2
			eachElementDo: #modNum GAME_CONTROLS
			curIcon: iconRestore
			helpIconItem: iconHelp
			state: NOCLICKHELP
		)
		(super init:)
		(plane
			back: 3
			addCast: controlCast
		)
;;;		(controlCel init: controlCast)
		(testBar init: controlCast)
;;;		(testBar1 init: controlCast)
;;;		(testBar2 init: controlCast)
;;;		(testBar3 init: controlCast)
;;;		(testBar4 init: controlCast)
;;;		(maskObj init: controlCast hide:)
		(plane
			setSize:
			posn: -1 50
		)
	)
	
	(method (showSelf)
		(sounds pause:)
		(if (and pMouse (pMouse respondsTo: #stop))
			(pMouse stop:)
		)
		(= curIcon iconExit)
		(self show:)
		(self doit:)
		(self hide:)
	)
)

(instance iconText of ControlIcon
	(properties
		noun 32
		x 44
		y 99
		signal $0183
		mainView vControlIcons
		mainLoop lTextOffButton
		helpVerb V_HELP
	)

	(method (doit &tmp temp0)
		(if (not (DoSound SndNumDACs))
			(Prints {A digital audio driver is not loaded.})
			(return)
		)
		(if (and (== mainLoop lTextOnButton) (== (iconVoice mainLoop?) lVoiceOffButton))
			(Prints {Either TEXT or VOICE must be set to ON.})
			(return)
		)
		(= mainLoop (= loop (if (== mainLoop lTextOffButton) lTextOnButton else lTextOffButton)))
		(UpdateScreenItem self)
		(switch mainLoop
			(lTextOnButton
				(|= msgType TEXT_MSG)
			)
			(lTextOffButton
				(^= msgType TEXT_MSG)
			)
		)
	)
	
	(method (show)
		(= mainLoop (if (& msgType TEXT_MSG) lTextOnButton else lTextOffButton))
		(super show: &rest)
	)
)

(instance iconVoice of ControlIcon
	(properties
		noun 31
		x 67
		y 99
		signal $0183
		message 0
		mainView vControlIcons
		mainLoop lVoiceOffButton
		helpVerb V_HELP
	)
	
	(method (doit)
		(if (not (DoSound SndNumDACs))
			(Prints {A digital audio driver is not loaded.})
			(return)
		)
		(if (and (== mainLoop lVoiceOnButton) (== (iconText mainLoop?) lTextOffButton))
			(Prints {Either TEXT or VOICE must be set to ON.})
			(return)
		)
		(= mainLoop (= loop (if (== mainLoop lVoiceOnButton) lVoiceOffButton else lVoiceOnButton)))
		(UpdateScreenItem self)
		(switch mainLoop
			(lVoiceOnButton
				(|= msgType CD_MSG)
			)
			(lVoiceOffButton
				(^= msgType CD_MSG)
			)
		)
	)
	
	(method (show)
		(= mainLoop (if (& msgType CD_MSG) lVoiceOnButton else lVoiceOffButton))
		(super show: &rest)
	)
)

(instance iconAbout of ControlIcon
	(properties
		noun N_ABOUT
		x 120
		y 22
		signal $01c1
		message 0
		mainView vControlIcons
		mainLoop lAboutButton
		helpVerb V_HELP
	)
)

(instance iconHelp of IconItem
	(properties
		noun N_HELP
		x 120
		y 70
		signal $0183
		message V_HELP
		mainView vControlIcons
		mainLoop lHelpButton
		helpVerb V_HELP
		cursorView vIconCursors
		cursorLoop 0
		cursorCel 4
	)
)

(instance iconExit of IconItem
	(properties
		noun N_OK
		x 120
		y 102
		signal $01c3
		message 0
		mainView vControlIcons
		mainLoop lOKButton
		helpVerb V_HELP
	)
	
	(method (select &tmp temp0)
		(return (if (super select: &rest) (return 1) else (return 0)))
	)
)

(instance iconQuit of ControlIcon
	(properties
		noun N_QUIT
		x 120
		y 6
		signal $01c3
		message 0
		mainView vControlIcons
		mainLoop lQuitButton
		helpVerb V_HELP
	)
)

(instance iconRestart of ControlIcon
	(properties
		noun N_RESTART
		x 120
		y 86
		signal $01c3
		message 0
		mainView vControlIcons
		mainLoop lRestartButton
		helpVerb V_HELP
	)
)

(instance iconRestore of ControlIcon
	(properties
		noun N_RESTORE
		x 120
		y 38
		signal $01c3
		message 0
		mainView vControlIcons
		mainLoop lRestoreButton
		helpVerb V_HELP
	)
)

(instance iconSave of ControlIcon
	(properties
		noun N_SAVE
		x 120
		y 54
		signal $01c3
		message 0
		mainView vControlIcons
		mainLoop lSaveButton
		helpVerb V_HELP
	)
)

(instance speedBar of SlideBar
	(properties
		noun N_SPEED
		x 7
		y 6
		mainView vControlIcons
		mainLoop 15
		helpVerb V_HELP
		maxValue 12
		minX 28
		maxX 106
		inc 2
		oppose 1
	)
	
;;;	(method (doit)
;;;		(if (== value 11) (= value maxValue))
;;;		(theGame currentSpeed: (- maxValue value))
;;;		(ego setSpeed: (theGame currentSpeed?))
;;;	)
;;;	
;;;	(method (show)
;;;		(= value (- maxValue (theGame currentSpeed?)))
;;;		(if (or (not (theGame isHandsOn?)) (Btst 200))
;;;			(= signal 132)
;;;		else
;;;			(= signal 128)
;;;		)
;;;		(= signal (| signal $0020))
;;;		(if (and pMouse (pMouse respondsTo: #stop))
;;;			(pMouse stop:)
;;;		)
;;;		(= resolution (+ (/ (- maxX minX) inc) 1))
;;;		(if (not position) (= position (self valueToPosn:)))
;;;		(if (& signal $0004)
;;;			(maskObj show:)
;;;			(UpdateScreenItem maskObj)
;;;		else
;;;			(maskObj hide:)
;;;			(self refresh: position)
;;;		)
;;;	)
)

(instance musicBar of SlideBar
	(properties
		noun N_VOLUME
		x 7
		y 21
		mainView vControlIcons
		mainLoop 15
		mainCel 1
		helpVerb V_HELP
		maxValue 85
		minX 28
		maxX 106
		inc 2
	)
	
	(method (doit &tmp temp0 temp1)
		(if (> value maxValue) (= value maxValue))
		(if (< value minValue) (= value minValue))
;;;		(= temp0 0)
;;;		(while (< temp0 (Sounds size:))
;;;			(if (== ((= temp1 (Sounds at: temp0)) type?) 0)
;;;				(temp1 setVol: value musicVolume: value)
;;;			)
;;;			(++ temp0)
;;;		)
	)
)

(instance soundBar of SlideBar
	(properties
		noun N_VOLUME
		x 7
		y 36
		mainView vControlIcons
		mainLoop 15
		mainCel 2
		helpVerb V_HELP
		maxValue 127
		minX 28
		maxX 106
		inc 2
	)
	
	(method (init)
		(= barObj testBar)
		(super init: &rest)
	)

	
	(method (doit &tmp temp0 temp1)
		(if (> value maxValue) (= value maxValue))
		(if (< value minValue) (= value minValue))
;;;		(= temp0 0)
;;;		(while (< temp0 (Sounds size:))
;;;			(if (== ((= temp1 (Sounds at: temp0)) type?) 1)
;;;				(temp1 setVol: value soundVolume: value)
;;;			)
;;;			(++ temp0)
;;;		)
	)
)

(instance textBar of SlideBar
	(properties
		noun 34
		x 7
		y 51
		mainView vControlIcons
		mainLoop lControlBars
		mainCel 4
		helpVerb V_HELP
		maxValue 8
		minX 28
		maxX 106
		inc 2
		oppose 1
	)
	
	(method (doit &tmp [temp0 2])
		(if (> value maxValue) (= value maxValue))
		(if (< value minValue) (= value minValue))
		(= textSpeed (- maxValue value))
	)
	
	(method (show)
		(= value (- maxValue textSpeed))
		(super show:)
	)
)

(instance detailBar of SlideBar
	(properties
		noun N_DETAIL
		x 7
		y 66
		mainView vControlIcons
		mainLoop lControlBars
		mainCel 0
		helpVerb V_HELP
		maxValue 3
		minX 28
		maxX 106
		inc 2
	)
)

(instance testBar of View
	(properties
		x 7
		y 6
		view vControlIcons
		loop lTestBar
	)
)

(instance testBar1 of View
	(properties
		x 7
		y 21
		view vControlIcons
		loop lTestBar
	)
)

(instance testBar2 of View
	(properties
		x 7
		y 36
		view vControlIcons
		loop lTestBar
	)
)

(instance testBar3 of View
	(properties
		x 7
		y 51
		view vControlIcons
		loop lTestBar
	)
)

(instance testBar4 of View
	(properties
		x 7
		y 66
		view vControlIcons
		loop lTestBar
	)
)

(instance controlCel of View
	(properties
		view vControlIcons
		loop lControlFixture
	)
)

(instance maskObj of View
	(properties
		x 7
		y 6
		view vControlIcons
		loop 14
	)
)

(instance myP of Print
	(method (init)
		(if (not plane) (= plane (systemPlane new:)))
		(dialog mouseHiliting: 1)
		(plane picture: -2)
		(super init: &rest)
	)
)

(instance theArrowCurs of Cursor
	(properties
		view ARROW_CURSOR
	)
)
