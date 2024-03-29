;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEEGO.SC
;
;	 GameEgo is a game-specific subclass of Ego. Here, you can put default answers for
;	 looking, talking and performing actions on yourself.
;
;

(script# GAME_EGO)
(include game.sh) (include "7.shm")
(use Main)
(use Grooper)
(use StopWalk)
(use Invent)
(use Ego)

(public
	GameEgo 0
)

(class GameEgo of Ego
	(properties
		noun N_EGO
		modNum GAME_EGO
		view vEgo
		
	)
	
	(method (doVerb theVerb)
		;add interactivity with the player character here
		(switch theVerb
			(else 
				(super doVerb: theVerb &rest)
			)
		)
	)
	
	(method (normalize theView)
		;sets up ego's normal animation
		(= view (if argc theView else vEgo))
		(ego
			setLoop: -1
			setCel: -1
			setPri: -1
			setMotion: 0
			setCycle: egoStopWalk -1
			z: 0
			illegalBits: 0
			ignoreActors: FALSE
		)
	)

	(method (showInv)
		(theIconBar hide:)
		(inventory showSelf: ego)
	)
)

(instance egoStopWalk of StopWalk)