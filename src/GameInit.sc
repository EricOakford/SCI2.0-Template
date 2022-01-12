;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEINIT.SC
;
;	Add things to initialize at game start here.
;	Make sure they don't require any objects or methods in MAIN.SC.
;
;
(script# GAME_INIT)
(include game.sh) (include "0.shm")
(use Main)
(use GameEgo)
(use GameWindow)
(use GameIconBar)
(use Print)
(use GameInv)
(use Plane)
(use Talker)
(use User)
(use System)
(use SpeedTst)
(use String)

(local
	platType
	colorDepth
	quitStr
)

(public
	GameInitCode 0
)

(instance GameInitCode of Code	
	(method (doit)
		;When you quit the game, a random message will appear at the DOS prompt.
		;Customize these messages in the message editor as you see fit.
		(= quitStr (String new:))
		(Message MsgGet MAIN N_QUIT_STR NULL NULL (Random 1 4) quitStr)
		(SetQuitStr quitStr)
		
		; These correspond to font codes used in messages.
		; By default, render messages in font 0 (system font).
		; Render messages with the |f1| tag in userFont (default 1).
		; Render messages with the |f2| tag in smallFont (default 4).
		; Render messages with the |f3| tag in font 1307.		
		(TextFonts SYSFONT userFont smallFont 1307)
		; These correspond to color codes used in messages (values into global palette).
		; By default, render messages as color 0.
		; Render messages with the |c1| tag as color 15.
		; Render messages with the |c2| tag as color 23.
		; Render messages with the |c3| tag as color 5.		
		(TextColors 0 15 23 5)
		
		;now set up the interface colors
		(= myTextColor 0)
		(= myBackColor 5)
		(= myLowlightColor (Palette PalMatch 159 159 159))
		(= myHighlightColor 0)
		(= userFont USERFONT)
		(= systemPlane GameWindow)
		(Print
			back: 5
			fore: 0
		)
		((= narrator Narrator)
			font: userFont
			fore: myTextColor
			back: myBackColor
		)
		(= useSortedFeatures TRUE)
		(= eatMice 30)
		
		(= msgType TEXT_MSG)		
		(= scoreFont 9)
		(= possibleScore 999)
		(= score 0)
		(= numVoices (DoSound SndNumVoices))
		(= numDACs (DoSound SndNumDACs))
		(= debugging TRUE)	;Set this to FALSE to disable the debug features
		(= howFast (SpeedTest))
		(= platType (Platform GetPlatType))
		(= colorDepth (Platform GetColorDepth))
		(Printf
			{howFast is %d\n
			numVoices is %d\n
			numDACs is %d\n
			IsHiRes is %d\n
			platType is %d\n
			colorDepth is %d
			}
			howFast numVoices numDACs (IsHiRes) platType colorDepth
		)
		(theIconBar enable:)
	)
)