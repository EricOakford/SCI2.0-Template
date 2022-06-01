;;; Sierra Script 1.0 - (do not remove this comment)
(script# 156)
(include game.sh) (include "156.shm")
(use Main)
(use rgForest)
;(use Encounter)
(use GameRoom)
(use Procs)
(use Actor)
(use Talker)
(use System)

(public
	rm156 0
)

;This is where Cleo's murderer is located after the church
; gets the ghost money.

(instance rm156 of HeroRoom ;EncRoom
	(properties
		picture 701
		horizon 54
		north 142
		east 157
		south 166
		west rHealerOutside
		;encChance 15
		;entrances (| reNORTH reEAST reSOUTH)
	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(ego normalize:)
		(cond
			((== cleoState cleoMurdererKilled)
				;(= encChance 0)
				(rogue init:)
				;init the rogue's dead body here
			)
			((and (ego has: iFingerBone) (== cleoState cleoGaveMoney))
				;(= encChance 0)
				(rogue init:)
				(messager say: N_ROOM NULL C_ROGUE_HERE)
			)
		)
		(if (not (OneOf prevRoomNum vGoblin vGuard vRogue))
			(= egoX (ego x?))
			(= egoY (ego y?))
		)
		;(self setRegions: rgENCOUNTER)
	)
	
	(method (doVerb)
		(return FALSE)
	)
)

(instance rogue of Actor
	(verbs
		(V_LOOK
			(if (== cleoState cleoMurdererKilled)
				(messager say: N_ROGUE V_LOOK C_KILLED_ROGUE)
			else
				(messager say: N_ROGUE V_LOOK C_ROGUE_HERE)
			)
		)
		(V_DO
			(if (== cleoState cleoMurdererKilled)
				(if (Btst fSearchedRogue)
					(messager say: N_ROGUE V_DO C_KILLED_ROGUE)
				else
					(messager say: N_ROGUE V_DO C_SEARCH_HIM)
					(ego get: iMoney 2)
					(Bset fSearchedRogue)
				)
			else
				(messager say: N_ROGUE V_DO C_ROGUE_HERE)
			)
		)
		(V_TALK
			;add in later
		)
		(V_SWORD
			;add fightHim script later
		)
	)
)

(instance rogueTalker of Talker)